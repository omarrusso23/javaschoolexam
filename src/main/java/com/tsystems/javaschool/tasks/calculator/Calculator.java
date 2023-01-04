package com.tsystems.javaschool.tasks.calculator;

import java.util.Stack;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        if (statement==null) {
            return null;
        }
        if (statement.isEmpty()) {
            return null;
        }

        Stack<Double> values = new Stack<>();
        Stack<Character> operations = new Stack<>();
        int openParentheses = 0;
        char lastChar = ' '; // Keep track of the last non-space character

        for (int i = 0; i < statement.length(); i++) {
            char c = statement.charAt(i);

            if (c == ' ') {
                // Ignore spaces
                continue;
            }

            if (Character.isDigit(c) || c == '.') {
                // Parse the number and push it to the values stack
                StringBuilder numberBuilder = new StringBuilder();
                boolean hasDecimal = false;
                while (i < statement.length() && (Character.isDigit(c) || c == '.')) {
                    if (c == '.') {
                        if (hasDecimal) {
                            // Multiple consecutive decimal points
                            return null;
                        }
                        hasDecimal = true;
                    }
                    numberBuilder.append(c);
                    lastChar = c;
                    i++;
                    if (i < statement.length()) {
                        c = statement.charAt(i);
                    }
                }
                i--;
                values.push(Double.parseDouble(numberBuilder.toString()));
            } else if (c == '(') {
                if (lastChar == ')' || lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                    // Consecutive closing and opening parentheses or consecutive operators
                    return null;
                }
                openParentheses++;
                operations.push(c);
                lastChar = c;
            } else if (c == ')') {
                if (lastChar == '(' || lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                    // Consecutive opening and closing parentheses or consecutive operators
                    return null;
                }
                if (openParentheses == 0) {
                    // Closing parentheses without matching opening parentheses
                    return null;
                }
                openParentheses--;
                // Perform the operations in the parentheses
                while (!operations.empty() && operations.peek() != '(') {
                    values.push(performOperation(operations.pop(), values.pop(), values.pop()));
                }
                if (operations.empty()) {
                    // Closing parentheses without matching opening parentheses
                    return null;
                }
                operations.pop();
                lastChar = c;
            } else if (isOperator(c)) {
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                    // Consecutive operators
                    return null;
                }
                // Perform the pending operations until the current operator has lower precedence than the top of the stack
                while (!operations.empty() && hasPrecedence(c, operations.peek())) {
                    values.push(performOperation(operations.pop(), values.pop(), values.pop()));
                }
                operations.push(c);
                lastChar = c;
            } else {
                // Invalid character
                return null;
            }
        }

        if (openParentheses != 0) {
            // Mismatched parentheses
            return null;
        }

        // Perform the pending operations
        while (!operations.empty()) {
            double result = performOperation(operations.pop(), values.pop(), values.pop());
            if (Double.isNaN(result)) {
                return null;
            }
            values.push(result);
        }

        // The result is the top of the values stack
        double result = values.pop();
        if (Double.isNaN(result)) {
            return null;
        }

        else if (result == (int) result) {
            // The result is an integer
            return String.valueOf((int) result);
        } else {
            // Round the result to the nearest integer if it has only one decimal and the decimal is zero
            long roundedResult = Math.round(result);
            if (result == (double) roundedResult) {
                return String.valueOf(roundedResult);
            } else {
                return String.valueOf(result);
            }
        }
    }

    /**
     * Returns true if the operator has lower precedence than the top of the stack.
     */
    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }
    /**
     * Performs the operation specified by the operator.
     */
    private double performOperation(char operation, double secondOperand, double firstOperand) {
        switch (operation) {
            case '+':
                return firstOperand + secondOperand;
            case '-':
                return firstOperand - secondOperand;
            case '*':
                return firstOperand * secondOperand;
            case '/':
                if (secondOperand == 0) {
                    return Double.NaN;
                }
                return firstOperand / secondOperand;
            default:
                return Double.NaN;
        }
    }

    /**
     * Returns true if the character is an operator.
     *
     * @param c the character
     * @return true if c is an operator, false otherwise
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

}