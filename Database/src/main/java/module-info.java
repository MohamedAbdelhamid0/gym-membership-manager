module com.gymx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.gymx;

    opens com.gymx to javafx.fxml;
    opens com.gymx.controller to javafx.fxml;
    opens com.gymx.model to javafx.base;
}