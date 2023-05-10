/**
 * module 简单使用
 */
// 用open修饰module，表示当前模块是一个开放模块
// 其他模块可以在本模块中的所有软件包上对所有类型使用深层反射
// 但不能在打开的模块中再声明opens语句
/*open*/ module scratchpad.javamodule {
    // 默认情况下，模块里下所有包都是私有的，即使被外部依赖也无法访问，
    // 一个模块之内的包还遵循之前的规则不受模块影响。
    // 可以使用 export 关键字公开特定的包 ,导出的包存在声明Java对象
    exports pers.mine.scratchpad;
    // 定向导出到指定包
    exports pers.mine.scratchpad.module to scratchpad.xxx;

    // opens 意味着允许其他模块对该包中的类型使用深层反射
    opens pers.mine.scratchpad;
    opens pers.mine.scratchpad.module to scratchpad.xxx;

    // java.base 模块默认会自动引入，可不写
    requires java.base;
    // 引入java.xml
    requires java.xml;
    requires java.sql;

    // 静态依赖 : 只在编译时需要一些模块，它们在运行时是可选的
    requires static java.se;
    // 依赖传递，同时依赖java.logging下依赖的包
    requires transitive java.logging;

    // uses 用于指定当前模块所需要的服务类或者接口
    uses java.sql.Driver;
    // 用于 在SPI 场景下，声明服务实现类
    provides java.sql.Driver with pers.mine.scratchpad.module.MyDriver;
}