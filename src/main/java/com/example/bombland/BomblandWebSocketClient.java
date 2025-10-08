package com.example.bombland;

import java.net.URI;
import java.net.URISyntaxException;
import javafx.application.Platform;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
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
    super(new URI("wss://bombland-server.onrender.com/websocket/establish-server-connection"));
//    super(new URI("ws://localhost:8080/websocket/establish-server-connection"));
  }

  /**
   * This function is called when attempting to connect to the server.
   * When "this.connect()" executes, the client initiates a WebSocket handshake with the server.
   * - If the handshake is successful and the connection is open and ready to send and receive data,
   *   onOpen() gets called.
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
   * This is a callback method that's called automatically when the WebSocket connection to the
   * server is successfully established.
   */
  @Override
  public void onOpen(ServerHandshake handshakeData) {
    System.out.println("\nonOpen()");
    isConnected = true;
    AppCache.getInstance().setServerConnectionGood(true);
  }

  /**
   * This is a callback method that gets invoked whenever a message is received from the server.
   */
  @Override
  public void onMessage(String message) {
    System.out.println("\nonMessage()");
    JSONObject responseObject = new JSONObject(message);

    if (responseObject.get("message_type").equals("HIGH_SCORE_INFO")) {
      responseObject.remove("message_type");
      PlayController.updateAppCache(responseObject);
    } else if (responseObject.get("message_type").equals("CHECK_ROOM")) {
      if (responseObject.get("room_exists").equals("False")) {
        JoinRoomController.getInstance().displayRoomDoesNotExistPopup();
      } else {
        responseObject.remove("room_exists");
        joinRoom(responseObject);
      }
    } else if (responseObject.get("message_type").equals("JOIN_ROOM")) {
      responseObject.remove("message_type");

      AppCache.getInstance().setMultiplayerRoom(responseObject);
      AppCache.getInstance().setPlayerName(responseObject.get("player2").toString());

      Platform.runLater(() -> {
        JoinRoomController.getInstance().goToRoom();
      });
    } else if (responseObject.get("message_type").equals("PLAYER_JOINED_ROOM")) {
      String player2 = responseObject.getString("player2");
      JSONObject roomInfo = AppCache.getInstance().getMultiplayerRoom();
      roomInfo.put("player2", player2);
      AppCache.getInstance().setMultiplayerRoom(roomInfo);

      Platform.runLater(() -> {
        RoomController.getInstance().playerJoinedRoom();
      });
    } else if (responseObject.get("message_type").equals("UPDATE_READY_STATE_UI")) {
      boolean isReady = responseObject.get("ready").equals("True");

      Platform.runLater(() -> {
        RoomController.getInstance().updateReadyState(isReady);
      });
    } else if (responseObject.get("message_type").equals("UPDATE_SETTINGS_UI")) {
      System.out.println("It's a UPDATE_SETTINGS_UI msg");

      Platform.runLater(() -> {
        RoomController.getInstance().updatePlayer2SettingsUi(responseObject);
      });
    }
  }

  /**
   * This is a callback method that gets invoked when the WebSocket connection is closed. It's the
   * last method that'll be called for a specific connection instance, and it provides info about
   * the reason for the closure.
   */
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
    AppCache.getInstance().setServerConnectionGood(false);
  }

  /**
   * This is a callback method that gets invoked when any exception or error occurs during the
   * lifecycle of the WebSocket connection.
   */
  @Override
  public void onError(Exception ex) {
    System.out.println("\n====================================================================");
    System.out.println("ERROR - BomblandWebSocketClient onError()");
    System.out.println("---");
    System.out.println(ex.getCause());
    System.out.println("====================================================================\n");
  }

  /**
   * This function sends info about a new high score to the server, for it to be distributed to
   * other (active) users.
   *
   * @param newScoreInfo the info about the new score.
   */
  public void sendHighScore(JSONObject newScoreInfo) {
    if (isConnected && getConnection().isOpen()) {
      newScoreInfo.put("message_type", "HIGH_SCORE_INFO");
      send(String.valueOf(newScoreInfo)); // creates a new thread
    } else {
      System.out.println("sendHighScore(): Connection not open. Unable to send high score.");
    }
  }

  /**
   * This function sends info about a newly created multiplayer room to the server.
   *
   * @param roomInfo an object that contains the info of a newly created multiplayer room.
   */
  public void createRoom(JSONObject roomInfo) {
    roomInfo.put("message_type", "CREATE_ROOM");

    if (isConnected && getConnection().isOpen()) {
      send(String.valueOf(roomInfo)); // creates a new thread
    } else {
      System.out.println("createRoom(): Connection not open. Unable to send room info.");
    }
  }

  /**
   * This function queries the server to see if a room exists.
   *
   * @param roomInfo an object that contains the info of a room being queried.
   */
  public void checkRoom(JSONObject roomInfo) {
    roomInfo.put("message_type", "CHECK_ROOM");

    if (isConnected && getConnection().isOpen()) {
      send(String.valueOf(roomInfo)); // creates a new thread
    } else {
      System.out.println("checkRoom(): Connection not open. Unable to send room info.");
    }
  }

  /**
   * This function lets the server know that a new player has joined a certain room.
   *
   * @param roomInfo the info about the room just joined.
   */
  public void joinRoom(JSONObject roomInfo) {
    roomInfo.put("message_type", "JOIN_ROOM");

    if (isConnected && getConnection().isOpen()) {
      send(String.valueOf(roomInfo)); // creates a new thread
    } else {
      System.out.println("joinRoom(): Connection not open. Unable to send room info.");
    }
  }

  /**
   * This function lets the server know that a player's state has been updated
   * (in a multiplayer room).
   *
   * @param playerState whether the player is ready to start a (multiplayer) game.
   */
  public void updatePlayerState(String playerState) {
    JSONObject playerInfo = new JSONObject();
    playerInfo.put("message_type", "UPDATE_READY_STATE_UI");
    playerInfo.put("roomId", AppCache.getInstance().getMultiplayerRoom().get("id"));
    playerInfo.put("ready", (playerState.equals("READY") ? "True" : "False"));

    if (isConnected && getConnection().isOpen()) {
      send(String.valueOf(playerInfo)); // creates a new thread
    } else {
      System.out.println("updatePlayerState(): Connection not open. Unable to send player info.");
    }
  }

  /**
   * This function sends the server the game setting (map or difficulty) that was changed,
   * and what it was changed to.
   *
   * @param settingsObj an object that stores the info about the settings changed.
   */
  public void updateP2Ui(JSONObject settingsObj) {
    settingsObj.put("message_type", "UPDATE_SETTINGS_UI");
    settingsObj.put("roomId", AppCache.getInstance().getMultiplayerRoom().get("id"));

    if (isConnected && getConnection().isOpen()) {
      send(String.valueOf(settingsObj)); // creates a new thread
    } else {
      System.out.println("updateP2Ui(): Connection not open. Unable to send settings info.");
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