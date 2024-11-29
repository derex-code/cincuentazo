module org.example.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.smartcardio;


    opens org.example.cincuentazo to javafx.fxml;
    exports org.example.cincuentazo;
}