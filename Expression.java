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


    public Expression pre;


    public Expression next;


    private BigDecimal calValue;


    public void setFirstNumber(BigDecimal firstNumber) {
        this.firstNumber = firstNumber;
    }

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


    public Expression(String firstNumber, String operator,   String secondNumber) {
        this.operator = operator;
        this.firstNumber = firstNumber != null ? new BigDecimal(firstNumber) : null;
        this.secondNumber = new BigDecimal(secondNumber);;
    }

    public Expression() {
    }

    @Override
    public String toString() {
        return "Expression{" +
                "firstNumber='" + firstNumber + '\'' +
                ", operator=" + operator +
                ", secondNumber=" + secondNumber +
                ", scale=" + scale +
                ", calValue=" + calValue +
                '}';
    }

    public BigDecimal getCalValue() {
        return this.calValue;
    }

}
