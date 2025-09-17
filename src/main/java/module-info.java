module com.example.bombland {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.json;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.services.dynamodb;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.services.cognitoidentity;
    requires java.net.http;
    requires org.java_websocket;
    requires java.sql;
  requires javafx.graphics;
  requires javafx.base;

  opens com.example.bombland to javafx.fxml;
    exports com.example.bombland;
}