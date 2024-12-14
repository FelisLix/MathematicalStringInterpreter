package computeranalysis.mathematicalstringinterpreter;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class MainController {
    public TextField getInputString;
    public TextField showResultString;
    public Button calculateButton;
    public TextArea showErrorsField;
    public TextField yField;
    public TextField zField;
    public TextField xField;

    public void showResults(ActionEvent actionEvent) {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        String expression = getInputString.getText();

        Map<String, Double> variables = new HashMap<>();
        variables.put("x", Double.valueOf(xField.getText()));
        variables.put("y", Double.valueOf(yField.getText()));
        variables.put("z", Double.valueOf(zField.getText()));
        try {
            double result = evaluator.evaluate(expression, variables);
            showErrorsField.clear();
            showResultString.setText(String.valueOf(result));
        } catch (Exception e) {
            showResultString.clear();
            showErrorsField.setText(e.getMessage());
        }
    }
}