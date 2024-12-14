package computeranalysis.mathematicalstringinterpreter;

import java.util.*;

public class ExpressionEvaluator {

    private static final Set<String> VARIABLES = Set.of("Pi", "x", "y", "z");
    private static final Map<String, Double> CONSTANTS = Map.of("Pi", Math.PI);
    private static final Set<String> FUNCTIONS = Set.of(
            "exp", "ln", "sqr", "sqrt", "cos", "sin", "tan", "acos", "asin", "atan", "round"
    );

    private String expression;
    private int pos = -1;
    private int ch;

    public double evaluate(String expression, Map<String, Double> variables) {
        this.expression = expression.replaceAll("\\s+", ""); // Remove spaces
        this.pos = -1;
        this.ch = -1;
        nextChar();
        double result = parseExpression(variables);
        if (pos < expression.length()) {
            throw new IllegalArgumentException("Unexpected character: " + (char) ch);
        }
        return result;
    }

    private void nextChar() {
        ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
    }

    private boolean eat(int charToEat) {
        while (ch == ' ') nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    private double parseExpression(Map<String, Double> variables) {
        double result = parseTerm(variables);
        while (true) {
            if (eat('+')) {
                result += parseTerm(variables);
            } else if (eat('-')) {
                result -= parseTerm(variables);
            } else {
                return result;
            }
        }
    }

    private double parseTerm(Map<String, Double> variables) {
        double result = parseFactor(variables);
        while (true) {
            if (eat('*')) {
                result *= parseFactor(variables);
            } else if (eat('/')) {
                double divisor = parseFactor(variables);
                if (divisor == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result /= divisor;
            } else {
                return result;
            }
        }
    }

    private double parseFactor(Map<String, Double> variables) {
        if (eat('+')) return parseFactor(variables);
        if (eat('-')) return -parseFactor(variables);

        double result;
        int startPos = this.pos;
        if (eat('(')) {
            result = parseExpression(variables);
            if (!eat(')')) {
                throw new IllegalArgumentException("Missing closing parenthesis at position " + pos);
            }
        } else if (Character.isLetter(ch)) {
            while (Character.isLetter(ch)) nextChar();
            String functionOrVariable = expression.substring(startPos, this.pos);
            if (FUNCTIONS.contains(functionOrVariable)) {
                result = parseFactor(variables);
                result = applyFunction(functionOrVariable, result);
            } else if (VARIABLES.contains(functionOrVariable)) {
                result = variables.getOrDefault(functionOrVariable, CONSTANTS.getOrDefault(functionOrVariable, 0.0));
            } else {
                throw new IllegalArgumentException("Unknown function or variable: " + functionOrVariable);
            }
        } else if (Character.isDigit(ch) || ch == '.') {
            while (Character.isDigit(ch) || ch == '.') nextChar();
            result = Double.parseDouble(expression.substring(startPos, this.pos));
        } else {
            throw new IllegalArgumentException("Unexpected character: " + (char) ch);
        }
        if (eat('^')) {
            result = Math.pow(result, parseFactor(variables));
        }
        return result;
    }

    private double applyFunction(String function, double value) {
        return switch (function) {
            case "exp" -> Math.exp(value);
            case "ln" -> {
                if (value <= 0) throw new ArithmeticException("Logarithm of non-positive number");
                yield Math.log(value);
            }
            case "sqr" -> value * value;
            case "sqrt" -> {
                if (value < 0) throw new ArithmeticException("Square root of negative number");
                yield Math.sqrt(value);
            }
            case "cos" -> Math.cos(value);
            case "sin" -> Math.sin(value);
            case "tan" -> Math.tan(value);
            case "acos" -> {
                if (value < -1 || value > 1) throw new ArithmeticException("acos out of range");
                yield Math.acos(value);
            }
            case "asin" -> {
                if (value < -1 || value > 1) throw new ArithmeticException("asin out of range");
                yield Math.asin(value);
            }
            case "atan" -> Math.atan(value);
            case "round" -> (double) Math.round(value);
            default -> throw new IllegalArgumentException("Unknown function: " + function);
        };
    }
}
