module org.example.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.smartcardio;


    opens org.example.cincuentazo to javafx.fxml;
    exports org.example.cincuentazo;
    exports org.example.cincuentazo.controller to javafx.fxml;
    opens org.example.cincuentazo.controller to javafx.fxml;
    exports org.example.cincuentazo.view;
    opens org.example.cincuentazo.view to javafx.fxml;
}