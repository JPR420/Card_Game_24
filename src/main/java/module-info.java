module edu.farmingdale.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.farmingdale.demo to javafx.fxml;
    exports edu.farmingdale.demo;
}