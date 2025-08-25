package com.example.bombland;

import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is used for creating a websocket client.
 * It allows the GUI app to communicate with the server app.
 */
public class BomblandWebSocketClient extends WebSocketClient {
  static boolean isConnected = false;

  /**
   * This function calls the constructor of the WebSocketClient class,
   * and passes it a specific URI.
   *
   * @throws URISyntaxException is thrown if the URI passed is not valid.
   */
  public BomblandWebSocketClient() throws URISyntaxException {
    super(new URI("wss://bombland-server.onrender.com/websocket/distribute-new-highscore"));
//    super(new URI("wss://bombland-server.onrender.com/establish-server-connection"));
  }

  @Override
  public void onOpen(ServerHandshake handshakeData) {
    System.out.println("\nonOpen() - websocketClient class");
    isConnected = true;

    System.out.println("Connected to the WebSocket server.");
    // Send a message (e.g., a high score) when the connection is established
    // sendHighScore("1500");
  }

  @Override
  public void onMessage(String message) {
    System.out.println("\nonMessage()");

    // Handle incoming messages from the server
    System.out.println("--> Received from server: " + message);
    System.out.println("\nmessage: " + message);

    try {
      // The message variable (sent from the server) is a stringified JSONObject object
      // that represents a new high score set on another client
      JSONObject jsonObject = new JSONObject(message);
      PlayController.updateAppCache(jsonObject);
    } catch (JSONException e) {
      System.out.println("The message from the server is a simple string\n");
    }
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    System.out.println("\nonClose()");

    isConnected = false;
    System.out.println("\n====================================================================");
    System.out.println("Disconnected from the server.");
    System.out.println("code: " + code);
    System.out.println("reason: " + reason + "\n");
    System.out.println("Possible reasons:");
    System.out.println("- The WebSocket URL used to connect to the server is incorrect");
    System.out.println("- The server-side WebSocket endpoint might not be correctly configured");
    System.out.println("- A firewall or proxy might be blocking or misrouting the WebSocket request");
    System.out.println("====================================================================\n");

    Main.socketClient = null;
  }

  @Override
  public void onError(Exception ex) {
    System.out.println("\nonError()");
    AppCache.getInstance().setServerConnectionGood(false);
  }

  /**
   * This function sends a score to the server.
   *
   * @param score the value sent to the server.
   */
  public void sendHighScore(String score) {
    if (isConnected && getConnection().isOpen()) {
      System.out.println("score: " + score);
      send(score); // creates a new thread
    } else {
      System.out.println("Connection not open. Unable to send message.");
    }
  }

  /**
   * This function is used for connecting to the server.
   */
  public void connectClient() {
    System.out.println("\nconnectClient() - start");

    if (!isConnected) {
      isConnected = true;
      this.connect();
      System.out.println("connectClient() - end");
    } else {
      System.out.println("Already connected to the server.");
    }
  }

  /**
   * This function is used to close the websocket connection.
   */
  public void close() {
    System.out.println("\nclose()");

    if (isConnected) {
      this.close();
      isConnected = false;
    }
  }
}
