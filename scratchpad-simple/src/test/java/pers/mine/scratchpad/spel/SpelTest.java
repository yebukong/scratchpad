package pers.mine.scratchpad.spel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author wangxiaoqiang
 * @description TODO
 * @create 2023-01-04 15:17
 */
public class SpelTest {
    @Test
    public void exp() {
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setRootObject(new A());
        TemplateParserContext parserContext = new TemplateParserContext("${", "}");
        // 1 定义解析器
        SpelExpressionParser parser = new SpelExpressionParser();
        // 2 使用解析器解析表达式
        Expression exp = parser.parseExpression("${b['a']['node']}", parserContext);
        System.out.println(exp.getExpressionString());
        if (exp instanceof SpelExpression) {
            SpelExpression expression = (SpelExpression) exp;
            SpelNode ast = expression.getAST();
            System.out.println(ast.toStringAST());
        }

        JSONObject a = new JSONObject();
        a.put("node", "123");
        JSONObject b = new JSONObject();
        b.put("a", a);
        JSONObject root = new JSONObject();
        root.put("b", b);
        System.out.println(exp.getValueType(root));

    }

    static class A {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    public void jsonPath() {
        JSONObject a = new JSONObject();
        a.put("node", new String[]{"test"});
        JSONObject b = new JSONObject();
        b.put("a", a);
        JSONObject root = new JSONObject();
        root.put("b", b);
        JSONPath jsonPath = JSONPath.compile("$.b.a.node[0]");
        System.out.println(jsonPath.getPath());
        Object eval = jsonPath.eval(root);
        System.out.println(eval);
    }
}
