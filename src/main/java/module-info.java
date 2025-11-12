module com.grupp5.agila_schemalggare {
  requires javafx.controls;
  requires javafx.fxml;


  opens com.grupp5.agila_schemalggare to javafx.fxml;
  exports com.grupp5.agila_schemalggare;
}