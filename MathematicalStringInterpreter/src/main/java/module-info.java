module computeranalysis.mathematicalstringinterpreter {
    requires javafx.controls;
    requires javafx.fxml;


    opens computeranalysis.mathematicalstringinterpreter to javafx.fxml;
    exports computeranalysis.mathematicalstringinterpreter;
}