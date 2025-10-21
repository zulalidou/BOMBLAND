package com.example.bombland;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
      Platform.runLater(() -> {
        RoomController.getInstance().updatePlayer2SettingsUi(responseObject);
      });
    } else if (responseObject.get("message_type").equals("LEAVE_ROOM")) {
      String currentPlayerName = AppCache.getInstance().getPlayerName();
      String player1Name = AppCache.getInstance().getMultiplayerRoom().getString("player1");

      if (currentPlayerName.equals(player1Name)) { // Player2 left
        Platform.runLater(() -> {
          RoomController.getInstance().removePlayer2FromRoom();
        });

      } else { // Player1 left
        Platform.runLater(() -> {
          RoomController.getInstance().kickOutPlayer2FromRoom();
        });
      }
    } else if (responseObject.get("message_type").equals("JOIN_GAME_MAP")) {
      String map = responseObject.get("map").toString();
      String difficulty = responseObject.get("difficulty").toString();

      AppCache.getInstance().setGameMap(map);
      AppCache.getInstance().setGameDifficulty(difficulty);

      Platform.runLater(() -> {
        RoomController.getInstance().startGame();
      });
    } else if (responseObject.get("message_type").equals("FIRST_TILE_CLICK")) {
      JSONObject firstTileInfoObj = new JSONObject();
      firstTileInfoObj.put("row", responseObject.getInt("row"));
      firstTileInfoObj.put("col", responseObject.getInt("col"));
      firstTileInfoObj.put("bombCoordinates", responseObject.get("bombCoordinates"));

      Platform.runLater(() -> {
        MultiplayerGameMap.getInstance().startMultiplayerGame(firstTileInfoObj);
      });
    } else if (responseObject.get("message_type").equals("TILE_CLICKED")) {
      int row = responseObject.getInt("row");
      int col = responseObject.getInt("col");
      Tile tileObj = MultiplayerGameMap.getInstance().gridObjects.get(new TileCoordinates(row, col));

      Platform.runLater(() -> {
        try {
          MultiplayerGameMap.getInstance().handleTileClick(tileObj);
        } catch (IOException e) {
          System.out.println("\n====================================================================");
          System.out.println("ERROR - onMessage() - TILE_CLICKED");
          System.out.println("---");
          System.out.println(e.getCause());
          System.out.println("====================================================================\n");
        }
      });
    } else if (responseObject.get("message_type").equals("GAME_OVER")) {
      String gameResult;

      if (responseObject.getString("winner").equals("N/A")) {
        gameResult = "TIE";
      } else if (responseObject.getString("winner").equals(AppCache.getInstance().getPlayerName())) {
        gameResult = "WON";
      } else {
        gameResult = "LOST";
      }

      Platform.runLater(() -> {
        MultiplayerPlayController.getInstance().displayGameOverPopup();
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
   * This function gets called when both Player1 and Player2 are in a room, and Player1 exits the
   * room. By leaving the room, a message gets sent to the server to have it delete information
   * stored about the room.
   */
  public void leaveRoom(String playerName) {
    JSONObject roomInfo = new JSONObject();
    roomInfo.put("message_type", "LEAVE_ROOM");
    roomInfo.put("roomId", AppCache.getInstance().getMultiplayerRoom().get("id"));
    roomInfo.put("playerName", playerName);

    if (isConnected && getConnection().isOpen()) {
      send(String.valueOf(roomInfo)); // creates a new thread
    } else {
      System.out.println("leaveRoom(): Connection not open. Unable to send room info.");
    }
  }

  /**
   * Sends Player2 to the Game map.
   */
  @FXML
  void sendPlayer2ToGameMap(JSONObject gameMapInfo) {
    gameMapInfo.put("message_type", "JOIN_GAME_MAP");

    if (isConnected && getConnection().isOpen()) {
      send(String.valueOf(gameMapInfo)); // creates a new thread
    } else {
      System.out.println("sendPlayer2ToGameMap(): Connection not open. Unable to send game map info.");
    }
  }

  /**
   * Sends the coordinates of the first tile clicked to the server, along with the bomb coordinates
   * generated from it.
   */
  @FXML
  void sendInitialData(JSONObject initialDataObj) {
    JSONObject firstTileInfo = new JSONObject();
    firstTileInfo.put("message_type", "FIRST_TILE_CLICK");
    firstTileInfo.put("row", initialDataObj.getInt("row"));
    firstTileInfo.put("col", initialDataObj.getInt("col"));
    firstTileInfo.put("bombCoordinates", initialDataObj.get("bombCoordinates"));
    firstTileInfo.put("playerName", AppCache.getInstance().getPlayerName());
    firstTileInfo.put("roomId", AppCache.getInstance().getMultiplayerRoom().get("id"));

    if (isConnected && getConnection().isOpen()) {
      send(String.valueOf(firstTileInfo)); // creates a new thread
    } else {
      System.out.println("sendInitialData(): Connection not open. Unable to send data.");
    }
  }

  /**
   * Sends the tile coordinates of the tile that was clicked (along with a few other data) to the
   * server.
   */
  @FXML
  void sendTileCoordinates(JSONObject dataObj) {
    JSONObject tileInfo = new JSONObject();
    tileInfo.put("message_type", "TILE_CLICKED");
    tileInfo.put("row", dataObj.getInt("row"));
    tileInfo.put("col", dataObj.getInt("col"));
    tileInfo.put("playerName", AppCache.getInstance().getPlayerName());
    tileInfo.put("roomId", AppCache.getInstance().getMultiplayerRoom().get("id"));

    if (isConnected && getConnection().isOpen()) {
      send(String.valueOf(tileInfo)); // creates a new thread
    } else {
      System.out.println("sendTileCoordinates(): Connection not open. Unable to send data.");
    }
  }

  /**
   * Sends info to the server about the end of a multiplayer game.
   */
  @FXML
  void gameOver(boolean playerDied) {
    JSONObject tileInfo = new JSONObject();
    tileInfo.put("message_type", "GAME_OVER");
    tileInfo.put("playerDied", playerDied);
    tileInfo.put("playerName", AppCache.getInstance().getPlayerName());
    tileInfo.put("roomId", AppCache.getInstance().getMultiplayerRoom().get("id"));

    if (isConnected && getConnection().isOpen()) {
      send(String.valueOf(tileInfo)); // creates a new thread
    } else {
      System.out.println("gameOver(): Connection not open. Unable to send data.");
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