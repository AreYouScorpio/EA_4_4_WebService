module com.example.ea4_4_webservice {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.ea4_4_webservice to javafx.fxml;
    exports com.example.ea4_4_webservice;
}