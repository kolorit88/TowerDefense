module org.example.towerdefense {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens org.example.towerdefense to javafx.fxml;
    exports org.example.towerdefense;
}