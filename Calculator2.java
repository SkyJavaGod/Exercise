import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Calculator2 {


    private static int scale = 2;

    private static CalculatorOperator operator = CalculatorOperator.getInstance();


    public static void main(String[] args) {
        List<Expression> expressions = new LinkedList<>();

        Expression start = new Expression("4*8");
        expressions.add(start);
        BigDecimal calcTwoNum = operator.calcTwoNum(expressions,scale);
        start.setCalValue(calcTwoNum);
        Expression expression = new Expression(calcTwoNum + "-5");
        expressions.add(expression);
        calcTwoNum = operator.calcTwoNum(expressions,scale);
        start.setCalValue(calcTwoNum);
        System.out.println("-----当前计算式"+ expressions.toString());


    }

}
