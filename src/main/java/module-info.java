module hotel.mangement.system.hotel_management_system {
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
    requires jdk.compiler;
    requires java.desktop;

    opens hotel.mangement.system.hotel_management_system to javafx.fxml;
    exports hotel.mangement.system.hotel_management_system;
}