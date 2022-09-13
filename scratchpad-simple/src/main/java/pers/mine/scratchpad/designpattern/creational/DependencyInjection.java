package pers.mine.scratchpad.designpattern.creational;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 依赖注入
 * 1.声明需要创建的对象需要哪些依赖对象
 * 2.注册创建这些依赖对象所需要的类
 * 3.提供一种使用1和2两点思想创建对象的机制
 */
public class DependencyInjection {
    interface Action {
        void some();
    }

    @Component("ActionA")
    static class ActionImpl implements Action {
        @Resource(name = "ServiceA")
        IService service;

        @Override
        public void some() {
            service.doSome();
        }
    }

    interface IService {
        void doSome();
    }

    @Component("ServiceA")
    static class ServiceImpl implements IService {
        @Override
        public void doSome() {
            System.out.println("do some");
        }
    }

    static class IocContext {
        Map<String, Object> beans = new HashMap<>();

        void init() {
            try {
                //扫描所有组件类
                Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("pers.mine.scratchpad.designpattern.creational", Component.class);
                //分两步初始化，简单解决依赖问题
                //构造bean
                for (Class<?> aClass : classes) {
                    Component component = AnnotationUtil.getAnnotation(aClass, Component.class);
                    String name = component.value();
                    Object o = aClass.newInstance();
                    beans.put(name, o);
                }
                //注入
                for (Object bean : beans.values()) {
                    for (Field declaredField : bean.getClass().getDeclaredFields()) {
                        Resource resource = declaredField.getAnnotation(Resource.class);
                        if (resource != null) {
                            String name = resource.name();
                            Object o = beans.get(name);
                            Assert.notNull(o, "{}名称bean未找到！");
                            declaredField.set(bean, o);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        <T> T get(String name) {
            return (T) beans.get(name);
        }
    }

    public static void main(String[] args) {
        IocContext ctx = new IocContext();
        ctx.init();
        Action actionA = ctx.get("ActionA");
        actionA.some();
    }

    public static void main1(String[] args) {
        IService service = new ServiceImpl();
        ActionImpl action = new ActionImpl();
        action.service = service;
        action.some();
    }
}
