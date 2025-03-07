module edu.farmingdale.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;
    requires java.net.http;
    requires org.json;


    opens edu.farmingdale.demo to javafx.fxml;
    exports edu.farmingdale.demo;
}