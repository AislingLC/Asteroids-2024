module com.example.asteroids2024 {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
            
    opens com.example.asteroids2024 to javafx.fxml;
    exports com.example.asteroids2024;
}