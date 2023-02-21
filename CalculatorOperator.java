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


    /**
     * 进行累计计算
     * @param expressions 表达式
     * @return 计算结果
     */
    public BigDecimal calcTwoNum(List<Expression> expressions,int scale) {


        BigDecimal firstNum = expressions.get(expressions.size()-2).getFirstNumber() ;
        Expression expression = expressions.get(expressions.size() - 1);
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
        return ret;
    }


    public static CalculatorOperator getInstance(){
        synchronized (CalculatorOperator.class) {
            if(calculatorOper == null){
                calculatorOper = new CalculatorOperator();
            }
            return  calculatorOper;
        }
    }



    /**
     * 返回上一步操作，返回上一步计算值
     */
    public void undo(List<Expression> expressions){

        //运算回退
        if(expressions.isEmpty()){
            System.out.println("未进行任何操作，不需要返回上一步");
        }else if(expressions.size() ==1){
            System.out.println("undo后值:0,"+"undo前值:"+expressions.get(0).getCalValue());
            start = new Expression();
        }else{
            if (lastStepIndex == -1) {
                lastStepIndex = expressions.size() - 1;
            } else {
                if (lastStepIndex - 1 < 0) {
                    System.out.println("无法再undo!");
                    return;
                }
                lastStepIndex--;
            }
        }
    }

    /**
     * 恢复撤销操作
     */
    public void redo(List<Expression> expressions){
        //运算恢复
        if(lastStepIndex > -1){
            if(lastStepIndex +1 == expressions.size() || lastStepIndex + 1 == maxStep){
                System.out.println("没法再redo");
                return;
            }
        }
        lastStepIndex++;

        this.redoPreOperate(calTotals.get(lastStepIndex),calCommands.get(lastStepIndex-1),calNumbers.get(lastStepIndex-1));
    }


}
