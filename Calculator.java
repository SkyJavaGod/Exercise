
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwenying
 * @date 2023/2/21 11:20
 * @description
 */
public class Calculator {

    //前计算总值
    private BigDecimal preCalTotal = null;

    //当前计算值
    private BigDecimal currentNum;

    //当前操作符
    private String curOperator;

    // 精度默认2位小数
    private int scale = 2;

    //计算总值
    private List<BigDecimal> calTotals = new ArrayList<>();

    //计算数
    private List<BigDecimal> calNumbers = new ArrayList<>();

    //运算符
    private List<String> calCommands = new ArrayList<>();

    //上一步回退步骤
    private int lastStepIndex = -1;

    //可重做最大步骤
    private int maxStep= -1;


    public BigDecimal getPreCalTotal() {
        return preCalTotal;
    }

    public void setPreCalTotal(BigDecimal preCalTotal) {
        this.preCalTotal = preCalTotal;
    }

    public BigDecimal getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(BigDecimal currentNum) {
        if(preCalTotal ==null){
            preCalTotal = currentNum;
        }else {
            this.currentNum = currentNum;
        }
    }

    public String getCurOperator() {
        return curOperator;
    }

    public void setCurOperator(String curOperator) {
        this.curOperator = curOperator;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public List<BigDecimal> getCalTotals() {
        return calTotals;
    }

    public void setCalTotals(List<BigDecimal> calTotals) {
        this.calTotals = calTotals;
    }

    public List<BigDecimal> getCalNumbers() {
        return calNumbers;
    }

    public void setCalNumbers(List<BigDecimal> calNumbers) {
        this.calNumbers = calNumbers;
    }

    public List<String> getCalCommands() {
        return calCommands;
    }

    public void setCalCommands(List<String> calCommands) {
        this.calCommands = calCommands;
    }

    public void calNum(){
        if(curOperator == null){
            System.out.println("请选择操作!");
        }else{
            BigDecimal result = calcTwoNum(preCalTotal, curOperator, currentNum);
            if(lastStepIndex == -1){
                //未进行undo/redo操作
                calNumbers.add(currentNum);
                calCommands.add(curOperator);
                calTotals.add(preCalTotal);
            }else{
                //已进行undo/redo操作
                this.lastStepIndex++;
                this.maxStep = this.lastStepIndex;
                if(this.lastStepIndex < this.calTotals.size() ){
                    this.calTotals.set(this.lastStepIndex, result);
                    this.calNumbers.set(this.lastStepIndex-1, currentNum);
                    this.calCommands.set(this.lastStepIndex-1, curOperator);
                }else{
                    //避免数组越界
                    calNumbers.add(currentNum);
                    calCommands.add(curOperator);
                    calTotals.add(result);
                }
            }
            preCalTotal = result;
            curOperator = null;
            currentNum = null;
            System.out.println("计算值-------：" + preCalTotal);
        }

    }


    /**
     * 进行累计计算
     * @param preTotal 前面已累计值
     * @param curOperator 当前操作
     * @param newNum 新输入值
     * @return 计算结果
     */
    private BigDecimal calcTwoNum(BigDecimal preTotal, String curOperator, BigDecimal newNum) {
        BigDecimal ret = BigDecimal.ZERO;
        curOperator = curOperator == null ? "+" : curOperator;
        switch (curOperator){
            case "+":
                ret = preTotal.add(newNum);
                break;
            case "-":
                ret = preTotal.subtract(newNum).setScale(scale, RoundingMode.HALF_UP);
                break;
            case "*":
                ret = preTotal.multiply(newNum).setScale(scale, RoundingMode.HALF_UP);
                break;
            case "/":
                ret = preTotal.divide(newNum, RoundingMode.HALF_UP);
                break;
        }
        return ret;
    }

    /**
     * 返回上一步操作，返回上一步计算值
     */
    public void undo(){
        if(preCalTotal != null && lastStepIndex == -1){ // 未进行undo/redo操作,存储最后计算结果
            calTotals.add(preCalTotal);
            curOperator = null;
            currentNum = null;
        }
        //运算回退
        if(calTotals.isEmpty()){
            System.out.println("未进行任何操作，不需要返回上一步");
        }else if(calTotals.size() ==1){
            System.out.println("undo后值:0,"+"undo前值:"+preCalTotal);
            preCalTotal = BigDecimal.ZERO;
        }else{
            if (lastStepIndex == -1) {
                lastStepIndex = calCommands.size() - 1;
            } else {
                if (lastStepIndex - 1 < 0) {
                    System.out.println("无法再undo!");
                    return;
                }
                lastStepIndex--;
            }
        }
        this.cancelPreOperate(calTotals.get(lastStepIndex),calCommands.get(lastStepIndex),calNumbers.get(lastStepIndex));
    }

    /**
     * 恢复撤销操作
     */
    public void redo(){
        //运算恢复
        if(lastStepIndex > -1){
            if(lastStepIndex +1 == calTotals.size() || lastStepIndex + 1 == maxStep){
                System.out.println("没法再redo");
                return;
            }
        }
        lastStepIndex++;

        this.redoPreOperate(calTotals.get(lastStepIndex),calCommands.get(lastStepIndex-1),calNumbers.get(lastStepIndex-1));
    }

    private void cancelPreOperate(BigDecimal lastTotal, String lastOpt, BigDecimal lastNum) {
        System.out.println("undo后值:"+lastTotal+",undo前值:"+preCalTotal+",undo的操作:"+lastOpt+",undo操作的值:"+lastNum);
        preCalTotal = lastTotal;
        curOperator = null;
        currentNum = null;
    }

    private void redoPreOperate(BigDecimal lastTotal, String lastOpt, BigDecimal lastNum) {
        System.out.println("redo后值:"+lastTotal+",redo前值:"+preCalTotal+",redo的操作:"+lastOpt+",redo操作的值:"+lastNum);
        preCalTotal = lastTotal;
        curOperator = null;
        currentNum = null;
    }



    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setCurrentNum(new BigDecimal("3"));
        calculator.setCurOperator("+");
        calculator.setCurrentNum(new BigDecimal("3"));
        calculator.calNum();
        calculator.setCurOperator("*");
        calculator.setCurrentNum(new BigDecimal("4"));
        calculator.calNum();
        calculator.undo();
        calculator.redo();
        calculator.setCurOperator("*");
        calculator.setCurrentNum(new BigDecimal("5"));
        calculator.calNum();
        calculator.undo();
        calculator.setCurOperator("+");
        calculator.setCurrentNum(new BigDecimal("5"));
        calculator.calNum();
        calculator.undo();
        calculator.redo();
        List<String> commands = calculator.getCalCommands();
        List<BigDecimal> totals = calculator.getCalTotals();
        List<BigDecimal> numbers = calculator.getCalNumbers();
        System.out.println("运行指令："+ commands.toString());
        System.out.println("计算值："+ totals.toString());
        System.out.println("计算数："+ numbers.toString());
        System.out.println("可回退步骤："+ calculator.lastStepIndex);
    }









}
