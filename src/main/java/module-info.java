module com.example.multi2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.multi2 to javafx.fxml;
    exports com.example.multi2;
}