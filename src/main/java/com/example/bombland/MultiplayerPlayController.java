package com.example.bombland;

import java.io.IOException;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

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
  VBox emptySpace;

  @FXML
  VBox gridContainer;

  @FXML
  VBox exitPagePopup;

  @FXML
  VBox exitPagePopupImgContainer;

  @FXML
  VBox gameOverPopup;

  @FXML
  VBox gameOverPopupImgContainer;

  @FXML
  Label totalBombsLbl;

  @FXML
  Label timeElapsedLbl;

  @FXML
  Label flagsLeftLbl;

  @FXML
  Label exitPagePopupTitle;

  @FXML
  Label exitPagePopupText;

  @FXML
  Label gameOverPopupTitle;

  @FXML
  Label gameOverPopupTimeTaken;

  @FXML
  Button backBtn;

  @FXML
  Button exitPagePopupCancelBtn;

  @FXML
  Button exitPagePopupMainMenuBtn;

  @FXML
  Button gameOverPopupPlayAgainBtn;

  @FXML
  Button gameOverPopupMainMenuBtn;

  @FXML
  HBox backBtnContainer;

  @FXML
  HBox exitPagePopupButtonsContainer;

  @FXML
  HBox gameOverPopupButtonsContainer;

  @FXML
  HBox playPageContainerHeader;

  @FXML
  ImageView exitPagePopupImg;

  @FXML
  ImageView gameOverPopupImg;


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

  @FXML
  private void closeExitPagePopup() {
    exitPagePopup.setManaged(false);
    exitPagePopup.setVisible(false);

    stackpaneChild1.setEffect(null);
    stackpaneChild1.setMouseTransparent(false); // makes items in gameplay page "clickable"

    timerPaused = false;
  }

  @FXML
  private void goToMainMenu() {
    endTimer();

    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/main-view.fxml")
    );

    MainController mainController = MainController.getInstance();
    loader.setController(mainController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - MultiplayerPlayController.goToMainMenu(): Could not return to the main menu page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  @FXML
  private void verifyExitPage() {
    if (!gameStarted) {
      goToMainMenu();
      return;
    }

    timerPaused = true;

    stackpaneChild1.setEffect(new GaussianBlur()); // blurs gameplay page
    stackpaneChild1.setMouseTransparent(true); // makes items in gameplay page "unclickable"

    exitPagePopup.setManaged(true);
    exitPagePopup.setVisible(true);

    exitPagePopup.setMaxWidth(Main.mainStage.widthProperty().get() * 0.4);
    exitPagePopup.setMaxHeight(Main.mainStage.heightProperty().get() * 0.4);

    exitPagePopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    exitPagePopupTitle.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.04) + "px;");

    exitPagePopupImgContainer.setStyle("-fx-pref-height: " + (Main.mainStage.getHeight() * 0.1) + "px;");
    exitPagePopupImg.setFitWidth(Main.mainStage.getWidth() * 0.1);
    exitPagePopupImg.setFitHeight(Main.mainStage.getWidth() * 0.1);

    exitPagePopupText.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.02) + "px;");

    VBox.setVgrow(exitPagePopupButtonsContainer, Priority.ALWAYS);
    exitPagePopupButtonsContainer.setSpacing(Main.mainStage.getWidth() * 0.05);

    exitPagePopupCancelBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
    exitPagePopupMainMenuBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
  }

  /**
   * Creates and populates the game page.
   */
  @FXML
  public void initialize() {
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
            Main.mainStage.widthProperty().multiply(0.035))
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

  void gameOver() {
    AudioClip clip = new AudioClip(Objects.requireNonNull(
        getClass().getResource("/com/example/bombland/Sounds/game_won.wav")).toExternalForm()
    );
    clip.play();

    if (playerDied) {
      ArrayList<TileCoordinates> bombCoordinates = MultiplayerGameMap.getInstance().getBombCoordinates();

      // Uncover all bomb tiles
      for (TileCoordinates coords : bombCoordinates) {
        Tile tile = MultiplayerGameMap.getInstance()
            .getGridObjects().get(new TileCoordinates(coords.getRow(), coords.getCol()));
        MultiplayerGameMap.getInstance().uncoverTile(tile);
        MultiplayerGameMap.getInstance().incrementTilesUncovered();
      }
    }

    Main.socketClient.gameOver(playerDied);
  }

  void displayGameOverPopup() {
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

    gameOverPopupTimeTaken.setText(gameDuration + " second" + ((gameDuration > 1) ? "s" : ""));
    gameOverPopupTimeTaken.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.02) + "px;");

    VBox.setVgrow(gameOverPopupButtonsContainer, Priority.ALWAYS);

    gameOverPopupButtonsContainer.setSpacing(Main.mainStage.getWidth() * 0.05);

    gameOverPopupPlayAgainBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
    gameOverPopupMainMenuBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
  }


  @FXML
  void playAgain() {
    gameOverPopup.setManaged(false);
    gameOverPopup.setVisible(false);

    stackpaneChild1.setEffect(null);
    stackpaneChild1.setMouseTransparent(false); // makes items in gameplay page "clickable"

    try {
      clearGrid();
      buildGrid();
    } catch (Exception e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - playAgain(): Could not rebuild the game map.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * This function clears the grid.
   */
  public void clearGrid() {
    MultiplayerGameMap.getInstance().clearGrid();
    gridContainer.getChildren().remove(0);
  }
}