module com.example.my_lms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.my_lms to javafx.fxml;
    exports com.example.my_lms;
}