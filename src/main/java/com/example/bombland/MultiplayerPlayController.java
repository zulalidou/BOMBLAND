package com.example.bombland;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.TextAlignment;
import org.json.JSONObject;

/**
 * This class sets up the UI for the game page.
 */
public class MultiplayerPlayController {
  private static MultiplayerPlayController instance;
  private boolean gameStarted;
  private boolean gameOver;
  private boolean playerDied;
  private long gameDuration;
  private ScheduledExecutorService  taskScheduler;
  private boolean timerPaused;


  @FXML
  StackPane playPageContainerInner;

  @FXML
  VBox stackpaneChild1;

  @FXML
  HBox playPageContainerHeader;

  @FXML
  HBox backBtnContainer;

  @FXML
  Button backBtn;

  @FXML
  Label totalBombsLbl;

  @FXML
  Label timeElapsedLbl;

  @FXML
  Label player1PointsLbl;

  @FXML
  Label player2PointsLbl;

  @FXML
  Label flagsLeftLbl;

  @FXML
  VBox emptySpace;

  @FXML
  VBox gridContainer;

  @FXML
  VBox gameOverPopup;

  @FXML
  Label gameOverPopupTitle;

  @FXML
  VBox gameOverPopupImgContainer;

  @FXML
  ImageView gameOverPopupImg;

  @FXML
  Label gameOverPopupPlayer1Name;

  @FXML
  Label gameOverPopupPlayer1Id;

  @FXML
  Label gameOverPopupPlayer2Name;

  @FXML
  Label gameOverPopupPlayer2Id;

  @FXML
  Label gameOverPopupRoundLabel;

  @FXML
  Label gameOverPopupPlayer1Points;

  @FXML
  Label gameOverPopupPlayer2Points;

  @FXML
  Label gameOverPopupStatsLabel;

  @FXML
  Label gameOverPopupPlayer1Wins;

  @FXML
  Label gameOverPopupTies;

  @FXML
  Label gameOverPopupPlayer2Wins;

  @FXML
  Label gameOverPopupPlayer1ReadyStatus;

  @FXML
  Label gameOverPopupPlayer2ReadyStatus;

  @FXML
  HBox gameOverPopupButtonsContainer;

  @FXML
  Button gameOverPopupPlayAgainBtn;

  @FXML
  Button gameOverPopupGoToRoomBtn;

  @FXML
  VBox leaveGamePopup;

  @FXML
  Label leaveGamePopupTitle;

  @FXML
  VBox leaveGamePopupImgContainer;

  @FXML
  ImageView leaveGamePopupImg;

  @FXML
  Label leaveGamePopupErrorMsg;

  @FXML
  HBox leaveGamePopupButtonsContainer;

  @FXML
  Button leaveGamePopupNoBtn;

  @FXML
  Button leaveGamePopupYesBtn;

  @FXML
  VBox kickOutPlayer2Popup;

  @FXML
  Label kickOutPlayer2PopupTitle;

  @FXML
  VBox kickOutPlayer2PopupImgContainer;

  @FXML
  ImageView kickOutPlayer2PopupImg;

  @FXML
  Label kickOutPlayer2PopupErrorMsg;

  @FXML
  Button kickOutPlayer2PopupCloseButton;


  private MultiplayerPlayController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return An instance of this class.
   */
  public static MultiplayerPlayController getInstance() {
    if (instance == null) {
      instance = new MultiplayerPlayController();
    }

    return instance;
  }

  public boolean getGameStarted() {
    return gameStarted;
  }

  public void setGameStarted(boolean gameStarted) {
    this.gameStarted = gameStarted;
  }

  public boolean getGameOver() {
    return gameOver;
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  public boolean isPlayerDead() {
    return playerDied;
  }

  public void setPlayerDied(boolean playerDied) {
    this.playerDied = playerDied;
  }

  /**
   * Updates the label that shows the number of flags left.
   *
   * @param flagsLeftText A string that mentions the number of flags left.
   */
  public void setFlagsLeftLbl(String flagsLeftText) {
    flagsLeftLbl.setText(flagsLeftText);
  }

  /**
   * Creates the grid that contains the map.
   *
   * @param grid A variable that'll contain the map.
   */
  public void setGridContainer(GridPane grid) {
    gridContainer.getChildren().add(grid);
  }

  /**
   * Creates and populates the game page.
   */
  @FXML
  public void initialize() {
    AppCache.getInstance().getMultiplayerRoom().put("isPlayer1InRoom", false);
    AppCache.getInstance().getMultiplayerRoom().put("isPlayer2InRoom", false);

    VBox.setVgrow(playPageContainerInner, Priority.ALWAYS);

    playPageContainerHeader.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;", Main.mainStage.heightProperty().multiply(0.05))
    );

    backBtnContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.05))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.05))
    );


    totalBombsLbl.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.3),
            Main.mainStage.widthProperty().multiply(0.035))
    );

    timeElapsedLbl.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.3),
            Main.mainStage.widthProperty().multiply(0.015))
    );

    player1PointsLbl.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.015))
    );

    player2PointsLbl.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.015))
    );

    flagsLeftLbl.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.3),
            Main.mainStage.widthProperty().multiply(0.035))
    );

    emptySpace.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.05))
    );


    gridContainer.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;", Main.mainStage.heightProperty().multiply(0.95))
    );


    buildGrid();
  }

  /**
   * This function builds the layout of the game map.
   */
  public void buildGrid() {
    gameStarted = false;
    gameOver = false;
    gameDuration = -1;
    taskScheduler = Executors.newScheduledThreadPool(1);
    timerPaused = false;
    timeElapsedLbl.setText("0 seconds");
    player1PointsLbl.setText(AppCache.getInstance().getMultiplayerRoom().getString("player1Name") + ": 0");
    player2PointsLbl.setText(AppCache.getInstance().getMultiplayerRoom().getString("player2Name") + ": 0");

    MultiplayerGameMap.getInstance().buildGrid();

    final int bombs = MultiplayerGameMap.getInstance().getBombs();
    totalBombsLbl.setText(bombs + " bombs");
    flagsLeftLbl.setText(bombs + " flags left");
  }

  void startTimer() {
    Runnable timerTask = () -> {
      if (!timerPaused) {
        gameDuration += 1;

        // Update the UI on the JavaFX Application Thread
        Platform.runLater(() -> {
          timeElapsedLbl.setText(gameDuration + " seconds");
        });
      }
    };

    // The timer task gets added to a new thread, and gets executed every second
    taskScheduler.scheduleAtFixedRate(timerTask, 0, 1, TimeUnit.SECONDS);
  }

  void endTimer() {
    if (taskScheduler != null) {
      taskScheduler.shutdownNow();
    }
  }

  void gameOver(JSONObject statsObj) {
    endTimer();

    if (playerDied) {
      ArrayList<TileCoordinates> bombCoordinates = MultiplayerGameMap.getInstance().getBombCoordinates();

      // Uncover all bomb tiles
      for (TileCoordinates coords : bombCoordinates) {
        Tile tile = MultiplayerGameMap.getInstance()
            .getGridObjects().get(new TileCoordinates(coords.getRow(), coords.getCol()));
        MultiplayerGameMap.getInstance().uncoverTile(tile, AppCache.getInstance().getPlayerName());
        MultiplayerGameMap.getInstance().incrementTilesUncovered();
      }
    }


    AudioClip clip;

    if (statsObj.getString("matchResult").equals("WON") || statsObj.getString("matchResult").equals("TIE")) {
      clip = new AudioClip(Objects.requireNonNull(
          getClass().getResource("/com/example/bombland/Sounds/game_won.wav")).toExternalForm()
      );
    } else {
      clip = new AudioClip(Objects.requireNonNull(
          getClass().getResource("/com/example/bombland/Sounds/game_lost.wav")).toExternalForm()
      );
    }

    clip.play();


    displayGameOverPopup(statsObj);
  }

  void displayGameOverPopup(JSONObject statsObj) {
    stackpaneChild1.setEffect(new GaussianBlur()); // blurs gameplay page
    stackpaneChild1.setMouseTransparent(true); // makes items in gameplay page "unclickable"

    gameOverPopup.setManaged(true);
    gameOverPopup.setVisible(true);

    gameOverPopup.setMaxWidth(Main.mainStage.widthProperty().get() * 0.4);
    gameOverPopup.setMaxHeight(Main.mainStage.heightProperty().get() * 0.4);
    gameOverPopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    gameOverPopupTitle.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.04) + "px;");

    gameOverPopupImgContainer.setStyle("-fx-pref-height: " + (Main.mainStage.getHeight() * 0.1) + "px;");
    gameOverPopupImg.setFitWidth(Main.mainStage.getWidth() * 0.1);
    gameOverPopupImg.setFitHeight(Main.mainStage.getWidth() * 0.1);


    gameOverPopupRoundLabel.setText("ROUND " + statsObj.getInt("round"));
    gameOverPopupRoundLabel.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.02) + "px;");

    gameOverPopupPlayer1Points.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.015) + "px;");
    gameOverPopupPlayer2Points.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.015) + "px;");


    gameOverPopupStatsLabel.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.02) + "px;");

    gameOverPopupPlayer1Id.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.02) + "px;");
    gameOverPopupPlayer1Name.setText(statsObj.getString("player1Name"));
    gameOverPopupPlayer1Name.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.015) + "px;");

    gameOverPopupPlayer2Id.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.02) + "px;");
    gameOverPopupPlayer2Name.setText(statsObj.getString("player2Name"));
    gameOverPopupPlayer2Name.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.015) + "px;");

    gameOverPopupPlayer1Wins.setText(statsObj.getInt("player1Wins") + " WINS");
    gameOverPopupPlayer1Wins.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.015) + "px;");

    gameOverPopupTies.setText(statsObj.getInt("ties") + " TIES");
    gameOverPopupTies.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.015) + "px;");

    gameOverPopupPlayer2Wins.setText(statsObj.getInt("player2Wins") + " WINS");
    gameOverPopupPlayer2Wins.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.015) + "px;");



    gameOverPopupPlayer1ReadyStatus.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.015))
    );

    gameOverPopupPlayer2ReadyStatus.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.015))
    );


    VBox.setVgrow(gameOverPopupButtonsContainer, Priority.ALWAYS);

    gameOverPopupButtonsContainer.setSpacing(Main.mainStage.getWidth() * 0.05);

    gameOverPopupPlayAgainBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );

    gameOverPopupGoToRoomBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );

    populateGameOverPopup(statsObj);

    if (leaveGamePopup.isVisible()) {
      gameOverPopup.setEffect(new GaussianBlur()); // blurs gameplay page
      gameOverPopup.setMouseTransparent(true);
    }
  }

  void populateGameOverPopup(JSONObject statsObj) {
    URL imageUrl;

    if (statsObj.getString("matchResult").equals("WON")) {
      gameOverPopupTitle.setText("YOU WON");
      imageUrl = getClass().getResource("/com/example/bombland/Images/trophy.png");
    } else if (statsObj.getString("matchResult").equals("LOST")) {
      gameOverPopupTitle.setText("YOU LOST");
      imageUrl = getClass().getResource("/com/example/bombland/Images/lost.png");
    } else {
      gameOverPopupTitle.setText("IT'S A TIE");
      imageUrl = getClass().getResource("/com/example/bombland/Images/tie.png");
    }

    Image newImage = new Image(imageUrl.toString());
    gameOverPopupImg.setImage(newImage);


    if (statsObj.getBoolean("playerDied")) {
      if (statsObj.getString("playerName").equals(statsObj.getString("player1Name"))) {
        gameOverPopupPlayer1Points.setText("POINTS: --");
        gameOverPopupPlayer2Points.setText("POINTS: " + statsObj.getInt("player2Points"));
      } else {
        gameOverPopupPlayer1Points.setText("POINTS: " + statsObj.getInt("player1Points"));
        gameOverPopupPlayer2Points.setText("POINTS: --");
      }
    } else {
      gameOverPopupPlayer1Points.setText("POINTS: " + statsObj.getInt("player1Points"));
      gameOverPopupPlayer2Points.setText("POINTS: " + statsObj.getInt("player2Points"));
    }
  }

  @FXML
  void playAgain() {
    if (isPlayer1()) {
      gameOverPopupPlayer1ReadyStatus.setText("READY");
    } else {
      gameOverPopupPlayer2ReadyStatus.setText("READY");
    }

    gameOverPopupPlayAgainBtn.setDisable(true);

    Main.socketClient.playAgain();
  }

  void updatePlayAgainState() {
    if (isPlayer1()) {
      gameOverPopupPlayer2ReadyStatus.setText("READY");
    } else {
      gameOverPopupPlayer1ReadyStatus.setText("READY");
    }
  }

  void buildNewMap() {
    gameOverPopupPlayer1ReadyStatus.setText("NOT READY");
    gameOverPopupPlayer2ReadyStatus.setText("NOT READY");
    gameOverPopupPlayAgainBtn.setDisable(false);

    gameOverPopup.setManaged(false);
    gameOverPopup.setVisible(false);

    stackpaneChild1.setEffect(null);
    stackpaneChild1.setMouseTransparent(false); // makes items in gameplay page "clickable"

    clearGrid();
    buildGrid();
  }

  /**
   * This function clears the grid.
   */
  public void clearGrid() {
    MultiplayerGameMap.getInstance().clearGrid();
    gridContainer.getChildren().remove(0);
  }

  private boolean isPlayer1() {
    String currentPlayerName = AppCache.getInstance().getPlayerName();
    String player1Name = AppCache.getInstance().getMultiplayerRoom().getString("player1Name");

    return currentPlayerName.equals(player1Name);
  }

  /**
   * This function displays a popup to ask the user if they're sure they'd like to leave the room.
   */
  @FXML
  private void displayLeaveGamePopup() {
    if (gameOverPopup.isVisible()) {
      gameOverPopup.setEffect(new GaussianBlur()); // blurs gameplay page
      gameOverPopup.setMouseTransparent(true);
    }

    stackpaneChild1.setEffect(new GaussianBlur()); // blurs room page
    stackpaneChild1.setMouseTransparent(true); // makes items in room page "unclickable"

    leaveGamePopup.setManaged(true);
    leaveGamePopup.setVisible(true);

    leaveGamePopup.setMaxWidth(Main.mainStage.widthProperty().get() * 0.33);
    leaveGamePopup.setMaxHeight(Main.mainStage.heightProperty().get() * 0.475);

    leaveGamePopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;  -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    leaveGamePopupTitle.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.04) + "px;");

    leaveGamePopupImgContainer.setStyle("-fx-pref-height: " + (Main.mainStage.getHeight() * 0.1) + "px;");
    leaveGamePopupImg.setFitWidth(Main.mainStage.getWidth() * 0.1);
    leaveGamePopupImg.setFitHeight(Main.mainStage.getWidth() * 0.1);


    leaveGamePopupErrorMsg.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-padding: %.2fpx 0 %.2fpx 0;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    leaveGamePopupErrorMsg.setTextAlignment(TextAlignment.CENTER); // Center each line of text


    leaveGamePopupButtonsContainer.setSpacing(Main.mainStage.getWidth() * 0.05);

    leaveGamePopupYesBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.0125),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
    leaveGamePopupNoBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.0125),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
  }

  /**
   * This function closes an error popup.
   */
  @FXML
  private void closeLeaveGamePopup() {
    leaveGamePopup.setManaged(false);
    leaveGamePopup.setVisible(false);

    if (gameOverPopup.isVisible()) {
      gameOverPopup.setEffect(null);
      gameOverPopup.setMouseTransparent(false);
    } else {
      stackpaneChild1.setEffect(null);
      stackpaneChild1.setMouseTransparent(false);
    }
  }

  /**
   * When this function runs, a message is sent to the server to let it know that the current user
   * has decided to leave the game map.
   */
  @FXML
  private void confirmToLeaveGameMap() {
    endTimer();
    Main.socketClient.leaveGame();
  }

  /**
   * Redirects the user to the Room page.
   */
  @FXML
  void goToRoom() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/room-view.fxml")
    );

    RoomController roomController = RoomController.getInstance();
    loader.setController(roomController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - MultiplayerPlayController.goToRoom(): Could not go to the room page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * When the other player leaves the game map, this function gets called to also kick out the
   * current player.
   */
  void kickPlayerOutOfGame() {
    if (gameOverPopup.isVisible()) {
      gameOverPopup.setEffect(new GaussianBlur()); // blurs gameplay page
      gameOverPopup.setMouseTransparent(true);
    }

    if (leaveGamePopup.isVisible()) {
      leaveGamePopup.setEffect(new GaussianBlur()); // blurs gameplay page
      leaveGamePopup.setMouseTransparent(true);
    }

    stackpaneChild1.setEffect(new GaussianBlur()); // blurs room page
    stackpaneChild1.setMouseTransparent(true); // makes items in room page "unclickable"

    kickOutPlayer2Popup.setManaged(true);
    kickOutPlayer2Popup.setVisible(true);

    kickOutPlayer2Popup.setMaxWidth(Main.mainStage.widthProperty().get() * 0.33);
    kickOutPlayer2Popup.setMaxHeight(Main.mainStage.heightProperty().get() * 0.4);

    kickOutPlayer2Popup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;  -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    kickOutPlayer2PopupTitle.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.03) + "px;");

    kickOutPlayer2PopupImgContainer.setStyle("-fx-pref-height: " + (Main.mainStage.getHeight() * 0.1) + "px;");
    kickOutPlayer2PopupImg.setFitWidth(Main.mainStage.getWidth() * 0.1);
    kickOutPlayer2PopupImg.setFitHeight(Main.mainStage.getWidth() * 0.1);

    kickOutPlayer2PopupErrorMsg.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-padding: %.2fpx 0 %.2fpx 0;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    kickOutPlayer2PopupErrorMsg.setTextAlignment(TextAlignment.CENTER); // Center each line of text

    kickOutPlayer2PopupCloseButton.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.31),
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.011))
    );
  }
}