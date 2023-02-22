import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 新的计算器类 第二版
 */
public class Calculator2 {


    private static int scale = 2;


    public static void main(String[] args) {
        Expression current = null;
        CalculatorOperator operator = new CalculatorOperator();
        Expression start = new Expression();
        Expression expression = new Expression("4","*" , "8");
        current= operator.calNum(start, scale,expression);
        expression = new Expression(null,"+","9");
        current = operator.calNum(current,scale,expression);
        current = operator.undo(current);
        current = operator.undo(current);
        current = operator.undo(current);
        current = operator.redo(current);
        current = operator.redo(current);
        expression = new Expression(null,"+","200");
        current = operator.calNum(current,scale,expression);
        System.out.println("-----当前计算式"+ current.toString());


    }

}
