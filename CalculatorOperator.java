import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *  @auhtor zhuwenying
 * @desc 计算器操作 计算 撤销 重做
 */
public class CalculatorOperator{


    private volatile static CalculatorOperator calculatorOper;


    //上一步回退步骤
    private int lastStepIndex = -1;

    //可重做最大步骤
    private int maxStep= -1;

    private Expression start;

    private int scale;


    /**
     * 进行累计计算
     * @param expressions 表达式
     * @return 计算结果
     */
    public void calcTwoNum(Expression expression,int scale) {
        BigDecimal firstNum = expression.getFirstNumber() ;
        String operator = expression.getOperator() ;
        BigDecimal secondNum = expression.getSecondNumber();
        BigDecimal ret = BigDecimal.ZERO;
        operator = operator == null ? "+" : operator;
        switch (operator){
            case "+":
                ret = firstNum.add(secondNum);
                break;
            case "-":
                ret = firstNum.subtract(secondNum).setScale(scale, RoundingMode.HALF_UP);
                break;
            case "*":
                ret = firstNum.multiply(secondNum).setScale(scale, RoundingMode.HALF_UP);
                break;
            case "/":
                ret = firstNum.divide(secondNum, RoundingMode.HALF_UP);
                break;
        }
        expression.setCalValue(ret);
    }


    public Expression calNum(Expression current,int scale,Expression next ){
        Expression result  = null;

        if(next == null){
            System.out.println("请选择操作!");
        }else{

            if (current.getCalValue() == null && next.getFirstNumber() == null) {
                next.setFirstNumber(BigDecimal.ZERO);
            }
            current.next = next;
            next.pre = current;
            result = next;
            if(current.getCalValue() !=null){
                next.setFirstNumber(current.getCalValue());
            }
            calcTwoNum(next,scale);
            System.out.println("计算值-------：" + result);
        }
        return result;

    }




    /**
     * 返回上一步操作，返回上一步计算值
     */
    public Expression undo(Expression expression){

        //运算回退
        if(expression.pre == null){
            System.out.println("未进行任何操作，不需要返回上一步");
            return expression;
        }else{
            return expression.pre;
        }
    }

    /**
     * 恢复撤销操作
     */
    public Expression redo(Expression expression){
        if(expression.next == null){
            System.out.println("未进行任何操作，不需要恢复上一步");
            return expression;
        }else{
            return expression.next;
        }
    }


}
