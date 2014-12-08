package util;/* Solves equations in infix notation using the method by Dr. Manna.
*  @author Robert Correa
*/
import java.io.*;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.Stack;

public class Calculator {

  private boolean DEBUG;

  //Constructor
  public Calculator() {
    DEBUG = false;
  }

  //Constructor with manual debug setting
  public Calculator(boolean DEBUG) {
    this.DEBUG = DEBUG;
  }

  /** Returns true if the new character has precedence to be pushed on to the
  *   stack. Returns false if not.
  *   @param previous operator already on the stack
  *   @param newChar operator being added to the stack
  *   @return boolean of whether the operator has precedence
  */
  private boolean hasPrecedence(Character previous, Character newChar) {
    //if the previous has top precedence then the new doesn't
    if (previous == '*' || previous == '/')
      return false;
    //subtract previous first then new
    else if (previous == '-' && newChar == '-')
      return false;
    //subtract previous then add new
    else if (previous == '-' && newChar == '+')
      return false;
    else {
      return true;
    }
  }

  /** Pops two operands and one operator and performs the operation.
  *   @param operands stack of the operands
  *   @param operators stack of the operators
  *   @return answer to the operation
  */
  private double popStackAndSolve(Stack<Double> operands,Stack<Character>
                                  operators) {
    double operand1,operand2,answer;
    char operator;
    operand2 = operands.pop(); //pop the second first because stack ordering
    operand1 = operands.pop();
    operator = operators.pop();
    answer = calc(operand1,operand2,operator);
    return answer;
  }

  /** @param operand1 the first operand in the equation
  *   @param operand2 the second operand in the equation
  *   @param operator the operator to perform on the two operands
  *   @return the answer
  */
  public double calc(double operand1, double operand2, char operator) {
    double calculatedAnswer = 0;
    switch (operator) {
      case 'x':
      case 'X':
      case '*':
        calculatedAnswer = (operand1 * operand2);
      break;
      case '/':
        calculatedAnswer = operand1 / operand2;
        break;
      case '+':
        calculatedAnswer = operand1 + operand2;
        break;
      case '-':
        calculatedAnswer = operand1 - operand2;
        break;
    }
    if (DEBUG) {
      System.out.println("calculation: "+operand1+operator+operand2+"="+
                         calculatedAnswer);
    }
    return calculatedAnswer;
  }

  /** Solves an infix equation and returns the answer as a Double. Limits to 3
  *   decimal places.
  *   @param equation infix String equation to be solved.
  *   @return double answer to equation
  */
  public Double solve(String equation) {
    DecimalFormat format = new DecimalFormat("#.###");
    Stack<Double> operands = new Stack<Double>();
    Stack<Character> operators = new Stack<Character>();
    char[] pieces = equation.toCharArray();
    char c;
    String number = "";
    boolean needsToBeAdded = false; //signals when a operand is finished
    for (int index = 0; index < pieces.length; index++) {
      c = pieces[index]; //changed for loop for java 7
      //if the number is complete, push to operand stack
      if (needsToBeAdded && (!Character.isDigit(c) && c != '.')) {
        operands.push(Double.parseDouble(number));
        needsToBeAdded = false;
        number = "";
      }
      //if it is a digit or '.' add to the number string
      if (Character.isDigit(c) || c == '.') {
        number += c;
        needsToBeAdded = true;
        //if it is the last character in the equation, add push to stack
        if (index == equation.length() - 1)
          operands.push(Double.parseDouble(number));
      }
      //if it is an operator and not a parentheses, push to stack or solve
      //in order of precedence and then push to stack
      else if (c != ')' && c != '('){
        if (operators.empty())
          operators.push(c);
        else if (hasPrecedence(operators.peek(),c))
          operators.push(c);
        else {
          operands.push(popStackAndSolve(operands,operators));
          operators.push(c);
        }
      }
      //keep solving and pushing till a closing parentheses is found
      else if (c == ')'){
        while (operators.peek() != '(') {
          operands.push(popStackAndSolve(operands,operators));
        }
        operators.pop();
      }
      //the only case left is if it is a '(' and just push to stack
      else {
        operators.push(c);
      }
    }
    //while there are still operators perform operations and push answer
    while (!operators.empty()) {
      operands.push(popStackAndSolve(operands, operators));
    }
    return Double.parseDouble(format.format(operands.pop()));
  }

  public static void main(String[] args) throws IOException{
    if (args.length != 1) {
      System.out.println("USAGE: filename");
    }
    else {
      Calculator calc = new Calculator();
      File file = new File(args[0]);
      Scanner input = new Scanner(file);
      PrintWriter output = new PrintWriter("updated_"+file);
      String equation;
      String[] pieces;
      Double answer;
      while (input.hasNextLine()) {
          pieces = input.nextLine().split("=");
          equation = pieces[0];
          answer = calc.solve(equation);
          output.println(equation+"="+answer);
      }
      output.flush();
      output.close();
      input.close();
    }
  }
}
