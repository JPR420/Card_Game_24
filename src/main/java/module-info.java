module edu.farmingdale.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;


    opens edu.farmingdale.demo to javafx.fxml;
    exports edu.farmingdale.demo;
}