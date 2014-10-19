package util;

import java.util.Stack;
public class Calculator {

  public Calculator() {

  }
  public Double solve(String equation) {
    Stack<Character> operators = new Stack<>();
    Stack<Double> operands     = new Stack<>();
    char[] eq = equation.toCharArray();
    String shortEq = "";
    int count = 0;
    double operand2,operand1,answer=0.0;
    char operator;
    for (int index = 0; index<eq.length; index++) {
      if (eq[index]=='(') {
        for (int i = index+1; i<eq.length;i++) {
          if (eq[i]=='(') {
            count++;
            shortEq+=eq[i];
          }
          else if (eq[i]==')') {
            if (count == 0) {
              count = 0;
              operands.push(solve(shortEq));
              index = i;
              shortEq="";
              break;
            }
            else {
              shortEq+=eq[i];
              count--;
            }
          }
          else {
            shortEq+=eq[i];
          }
        }
      }
      else {
        try {
          operands.push(Double.parseDouble(""+eq[index]));
        } catch (NumberFormatException e) {
          operators.push(eq[index]);
        }
      }
      if (operands.size()>1&&operators.size()>0) {
          if (operators.peek()=='x'||operators.peek()=='/') {
            operand2 = operands.pop();
            operand1 = operands.pop();
            operator = operators.pop();
            answer = calc(operand1,operand2,operator);
            operands.push(answer);
          }
      }
    }
    if (operators.isEmpty()) {
      answer = operands.pop();
    }
    else {
      while (operators.size()>0) {
        if (operands.size()>1) {
            operand2 = operands.pop();
            operand1 = operands.pop();
            operator = operators.pop();
            answer = calc(operand1,operand2,operator);
            operands.push(answer);
        }
    }
  }
    return answer;
  }
  public double calc(double operand1, double operand2, char operator) {
    double calculatedAnswer = 0;
    switch (operator) {
      case 'x':
      case 'X':
      case '*':
        calculatedAnswer = (operand1 * operand2);
      break;
      case '-':
        calculatedAnswer = operand1 - operand2;
      break;
      case '/':
        calculatedAnswer = operand1 / operand2;
      break;
      case '+':
        calculatedAnswer = operand1 + operand2;
      break;
    }
    return calculatedAnswer;
  }
}
