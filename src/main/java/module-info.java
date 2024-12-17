module org.example.towerdefense {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.towerdefense to javafx.fxml;
    exports org.example.towerdefense;
    exports org.example.towerdefense.Controllers;
    opens org.example.towerdefense.Controllers to javafx.fxml;
}