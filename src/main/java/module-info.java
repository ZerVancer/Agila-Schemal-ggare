module com.grupp5.agila_schemalggare {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
    requires java.desktop;
    requires javafx.base;

  opens com.grupp5.agila_schemalggare to javafx.fxml;
  opens com.grupp5.agila_schemalggare.controllers;
  exports com.grupp5.agila_schemalggare;
}