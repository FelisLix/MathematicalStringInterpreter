package computeranalysis.mathematicalstringinterpreter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InterpreterApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InterpreterApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 365);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

//2*Pi*(1-cos(3.4*x*(y/z)))/(1+sqrt(x+y+z)/2)
//exp(2)-ln(7)
//sin(Pi/4)
//1/(x-1) (х = 1)
