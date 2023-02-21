import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {

    private String operator;


    private BigDecimal firstNumber;


    private BigDecimal secondNumber;

    // 精度默认2位小数
    private int scale;


    private Expression pre;


    private Expression next;


    private BigDecimal calValue;


    public String getOperator() {
        return operator;
    }

    public BigDecimal getFirstNumber() {
        return firstNumber;
    }

    public BigDecimal getSecondNumber() {
        return secondNumber;
    }

    public int getScale() {
        return scale;
    }

    public void setCalValue(BigDecimal calValue) {
        this.calValue = calValue;
    }

    public  Expression(String expression ){

        String mather = "[\\+\\-\\*/]";
        Pattern pattern = Pattern.compile(mather);
        Matcher m = pattern.matcher(expression);
        String[] split = expression.split("[\\+\\-\\*/]");
        firstNumber = new BigDecimal(split[0]);
        operator = String.valueOf(m.group(1));
        secondNumber = new BigDecimal(split[1]);

    }

    public Expression() {
    }

    @Override
    public String toString() {
        return "Expression{" +
                "operator='" + operator + '\'' +
                ", firstNumber=" + firstNumber +
                ", secondNumber=" + secondNumber +
                ", scale=" + scale +
                ", calValue=" + calValue +
                '}';
    }

    public BigDecimal getCalValue() {
        BigDecimal ret = BigDecimal.ZERO;
        switch (operator){
            case "+":
                ret = firstNumber.add(secondNumber);
                break;
            case "-":
                ret = firstNumber.subtract(secondNumber).setScale(scale, RoundingMode.HALF_UP);
                break;
            case "*":
                ret = firstNumber.multiply(secondNumber).setScale(scale, RoundingMode.HALF_UP);
                break;
            case "/":
                ret = firstNumber.divide(secondNumber, RoundingMode.HALF_UP);
                break;
        }
        return ret;
    }

}
