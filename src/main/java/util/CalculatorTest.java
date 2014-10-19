package util;


public class CalculatorTest {
  public static void main(String[] args) {
    Calculator calc = new Calculator();
    System.out.println("(1+((4/2)x4x(3-1))");
    Double answer = calc.solve("1+((4/2)x4x(3-1))");
    System.out.println("The answer should be 17! It is: "+answer);
  }
}
