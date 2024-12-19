module org.example.towerdefense {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.towerdefense to javafx.fxml;
    exports org.example.towerdefense;
    exports org.example.towerdefense.Controllers;
    opens org.example.towerdefense.Controllers to javafx.fxml;
}