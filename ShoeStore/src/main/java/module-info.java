module com.myapp.shoestore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.validation;
    requires annotations;

    opens com.myapp.shoestore to javafx.fxml;
    exports com.myapp.shoestore;

    opens com.myapp.shoestore.Controller to javafx.fxml;
    exports com.myapp.shoestore.Controller;

    opens com.myapp.shoestore.Model;
    exports com.myapp.shoestore.Model;
    exports com.myapp.shoestore.Service;
    opens com.myapp.shoestore.Service to javafx.fxml;
}