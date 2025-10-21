package com.example.bombland;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import org.json.JSONObject;

/**
 * This class sets up the UI for the game page.
 */
public class PlayController {
  private static PlayController instance;
  private boolean gameStarted;
  private boolean gameLost;
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
  VBox gameLostPopup;

  @FXML
  VBox gameLostPopupImgContainer;

  @FXML
  VBox gameWonPopup;

  @FXML
  VBox gameWonPopupImgContainer;

  @FXML
  VBox newRecordPopup;

  @FXML
  VBox newRecordPopupImgContainer;

  @FXML
  VBox databaseCommunicationErrorPopup;

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
  Label gameLostPopupTitle;

  @FXML
  Label gameLostPopupTimeTaken;

  @FXML
  Label gameWonPopupTitle;

  @FXML
  Label gameWonPopupTimeTaken;

  @FXML
  Label newRecordPopupTitle;

  @FXML
  Label newRecordPopupTimeTaken;

  @FXML
  Label newRecordPopupText;

  @FXML
  Label playerNameError;

  @FXML
  Label databaseCommunicationErrorPopupTitle;

  @FXML
  Button backBtn;

  @FXML
  Button exitPagePopupCancelBtn;

  @FXML
  Button exitPagePopupMainMenuBtn;

  @FXML
  Button gameLostPopupPlayAgainBtn;

  @FXML
  Button gameLostPopupMainMenuBtn;

  @FXML
  Button gameWonPopupPlayAgainBtn;

  @FXML
  Button gameWonPopupMainMenuBtn;

  @FXML
  Button newRecordPopupPlayAgainBtn;

  @FXML
  HBox backBtnContainer;

  @FXML
  HBox exitPagePopupButtonsContainer;

  @FXML
  HBox gameLostPopupButtonsContainer;

  @FXML
  HBox gameWonPopupButtonsContainer;

  @FXML
  HBox newRecordPopupButtonsContainer;

  @FXML
  HBox playPageContainerHeader;

  @FXML
  TextField playerNameTextField;

  @FXML
  ImageView exitPagePopupImg;

  @FXML
  ImageView gameLostPopupImg;

  @FXML
  ImageView gameWonPopupImg;

  @FXML
  ImageView newRecordPopupImg;

  @FXML
  Label databaseErrorLastSentence;

  @FXML
  Button closeDatabaseErrorPopupBtn;


  private PlayController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return An instance of this class.
   */
  public static PlayController getInstance() {
    if (instance == null) {
      instance = new PlayController();
    }

    return instance;
  }

  public boolean getGameStarted() {
    return gameStarted;
  }

  public void setGameStarted(boolean gameStarted) {
    this.gameStarted = gameStarted;
  }

  public boolean getGameLost() {
    return gameLost;
  }

  public void setGameLost(boolean gameLost) {
    this.gameLost = gameLost;
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
      System.out.println("ERROR - PlayController.goToMainMenu(): Could not return to the main menu page.");
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
    gameLost = false;
    gameDuration = -1;
    taskScheduler = Executors.newScheduledThreadPool(1);
    timerPaused = false;
    timeElapsedLbl.setText("0 seconds");

    GameMap.getInstance().buildGrid();

    final int bombs = GameMap.getInstance().getBombs();
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

  void gameLost() {
    AudioClip clip = new AudioClip(Objects.requireNonNull(
        PlayController.class.getResource("/com/example/bombland/Sounds/game_lost.wav")).toExternalForm()
    );
    clip.play();

    ArrayList<TileCoordinates> bombCoordinates = GameMap.getInstance().getBombCoordinates();

    // Uncover all bomb tiles
    for (TileCoordinates coords : bombCoordinates) {
      Tile tile = GameMap.getInstance()
          .getGridObjects().get(new TileCoordinates(coords.getRow(), coords.getCol()));
      GameMap.getInstance().uncoverTile(tile);
      GameMap.getInstance().incrementTilesUncovered();
    }

    stackpaneChild1.setEffect(new GaussianBlur()); // blurs gameplay page
    stackpaneChild1.setMouseTransparent(true); // makes items in gameplay page "unclickable"

    displayGameLostPopup();
  }

  void displayGameLostPopup() {
    gameLostPopup.setManaged(true);
    gameLostPopup.setVisible(true);

    gameLostPopup.setMaxWidth(Main.mainStage.widthProperty().get() * 0.4);
    gameLostPopup.setMaxHeight(Main.mainStage.heightProperty().get() * 0.4);
    gameLostPopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    gameLostPopupTitle.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.04) + "px;");

    gameLostPopupImgContainer.setStyle("-fx-pref-height: " + (Main.mainStage.getHeight() * 0.1) + "px;");
    gameLostPopupImg.setFitWidth(Main.mainStage.getWidth() * 0.1);
    gameLostPopupImg.setFitHeight(Main.mainStage.getWidth() * 0.1);

    gameLostPopupTimeTaken.setText(gameDuration + " second" + ((gameDuration > 1) ? "s" : ""));
    gameLostPopupTimeTaken.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.02) + "px;");

    VBox.setVgrow(gameLostPopupButtonsContainer, Priority.ALWAYS);

    gameLostPopupButtonsContainer.setSpacing(Main.mainStage.getWidth() * 0.05);

    gameLostPopupPlayAgainBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
    gameLostPopupMainMenuBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
  }

  void gameWon() {
    AudioClip clip = new AudioClip(Objects.requireNonNull(
        getClass().getResource("/com/example/bombland/Sounds/game_won.wav")).toExternalForm()
    );
    clip.play();

    stackpaneChild1.setEffect(new GaussianBlur()); // blurs gameplay page
    stackpaneChild1.setMouseTransparent(true); // makes items in gameplay page "unclickable"

    ArrayList<JSONObject> highScores = AppCache.getInstance()
        .getHighScores(GameMap.getInstance().getGameDifficulty());

    if (highScores.size() < 10 || gameDuration < highScores.get(highScores.size() - 1).getLong("score")) {
      displayRecordSetPopup();
    } else {
      displayGameWonPopup();
    }
  }

  void displayRecordSetPopup() {
    newRecordPopup.setManaged(true);
    newRecordPopup.setVisible(true);

    newRecordPopup.setMaxWidth(Main.mainStage.widthProperty().get() * 0.4);
    newRecordPopup.setMaxHeight(Main.mainStage.heightProperty().get() * 0.4);
    newRecordPopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    newRecordPopupTitle.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.04) + "px;");

    newRecordPopupImgContainer.setStyle("-fx-pref-height: " + (Main.mainStage.getHeight() * 0.1) + "px;");
    newRecordPopupImg.setFitWidth(Main.mainStage.getWidth() * 0.1);
    newRecordPopupImg.setFitHeight(Main.mainStage.getWidth() * 0.1);

    newRecordPopupTimeTaken.setText(gameDuration + " second" + ((gameDuration > 1) ? "s" : ""));
    newRecordPopupTimeTaken.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.02) + "px;");

    newRecordPopupText.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.015) + "px;");

    playerNameTextField.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.3),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.015))
    );

    VBox.setVgrow(newRecordPopupButtonsContainer, Priority.ALWAYS);

    newRecordPopupPlayAgainBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
  }

  @FXML
  void saveNewRecord() {
    if (playerNameTextField.getText().isBlank()) {
      playerNameError.setVisible(true); // display error
      playerNameTextField.setText("");
    } else {
      playerNameError.setVisible(false);
      newRecordPopup.setManaged(false);
      newRecordPopup.setVisible(false);
      displayGameWonPopup();

      Task<Void> saveHighScoreTask = new Task<>() {
        @Override
        protected Void call() {
          JSONObject newScoreInfo = new JSONObject();
          newScoreInfo.put("time", System.currentTimeMillis());
          newScoreInfo.put("id", UUID.randomUUID().toString());
          newScoreInfo.put("score", gameDuration);
          newScoreInfo.put("name", playerNameTextField.getText().strip());
          newScoreInfo.put("difficulty", GameMap.getInstance().getGameDifficulty());
          newScoreInfo.put("map", AppCache.getInstance().getGameMap());

          // Store new high score in DB
          try {
            if (AppCache.getInstance().getIdentityPoolId().isEmpty()) {
              Main.getEnvironmentVariables();
            }

            DynamoDbClientUtil.saveNewHighScore(newScoreInfo,
                "BOMBLAND_" + GameMap.getInstance().getGameDifficulty() + "HighScores");

            // If the condition below doesn't execute, things will still be fine because the
            // dynamoDbConnectionTask regularly pulls the high scores from DynamoDB, so
            // sooner or later all players will have access to the latest scores.
            if (AppCache.getInstance().serverConnectionIsGood()) {
              // Send new score to WebSocket server (to be distributed to other active users)
              Main.socketClient.sendHighScore(newScoreInfo);
            }
          } catch (Exception e) {
            displayDatabaseErrorPopup();
            saveHighScoreToSqlite(newScoreInfo);
          }

          playerNameTextField.setText("");
          updateAppCache(newScoreInfo);

          return null;
        }
      };

      new Thread(saveHighScoreTask).start();
    }
  }

  /**
   * This function displays an error popup.
   */
  public void displayDatabaseErrorPopup() {
    gameWonPopup.setEffect(new GaussianBlur());
    gameWonPopup.setMouseTransparent(true);

    databaseCommunicationErrorPopup.setManaged(true);
    databaseCommunicationErrorPopup.setVisible(true);

    databaseCommunicationErrorPopup.setMaxWidth(Main.mainStage.getWidth() * 0.33);
    databaseCommunicationErrorPopup.setMaxHeight(Main.mainStage.getHeight() * 0.40);

    databaseCommunicationErrorPopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;  -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    databaseCommunicationErrorPopupTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.33))
    );


    // Find all nodes with the "databaseErrorText" style class
    Set<Node> foundNodes = databaseCommunicationErrorPopup.lookupAll(".databaseErrorText");

    // Iterate over the set and cast each Node to a Label
    for (Node node : foundNodes) {
      // Use an instanceof check for safety
      if (node instanceof Label) {
        Label label = (Label) node;

        label.styleProperty().bind(
            Bindings.format("-fx-font-size: %.2fpx;",
                Main.mainStage.widthProperty().multiply(0.01))
        );
      }
    }


    databaseErrorLastSentence.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-padding: %.2fpx 0 %.2fpx 0",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01))
    );


    closeDatabaseErrorPopupBtn.setMaxWidth(Main.mainStage.getWidth() * 0.31);

    closeDatabaseErrorPopupBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.31),
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.011))
    );
  }

  /**
   * This function closes an error popup.
   */
  public void closeDatabaseErrorPopup() {
    databaseCommunicationErrorPopup.setManaged(false);
    databaseCommunicationErrorPopup.setVisible(false);

    gameWonPopup.setEffect(null);
    gameWonPopup.setMouseTransparent(false);
  }

  /**
   * This function saves the new high score to SQLite. (Once connection to DynamoDB is
   * reestablished, the high score will be moved from SQLite to DynamoDB.)
   *
   * @param info The JSONObject that stores the info about the new high score.
   */
  private void saveHighScoreToSqlite(JSONObject info) {
    String sql = "INSERT INTO high_scores(time, id, score, name, difficulty, map) VALUES(?,?,?,?,?,?)";

    try (Connection connection = SqliteDbHelper.getConnection();
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setLong(1, Long.parseLong(info.get("time").toString()));
      pstmt.setString(2, info.get("id").toString());
      pstmt.setInt(3, Integer.parseInt(info.get("score").toString()));
      pstmt.setString(4, info.get("name").toString());
      pstmt.setString(5, info.get("difficulty").toString());
      pstmt.setString(6, info.get("map").toString());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - saveHighScoreToSqlite(): Could not save the new high score to the SQLite database.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  static void updateAppCache(JSONObject newScoreInfo) {
    // 1. Add newScoreInfo to highScores list
    ArrayList<JSONObject> highScores = AppCache.getInstance()
        .getHighScores(newScoreInfo.getString("difficulty"));
    highScores.add(newScoreInfo);

    // 2. Sort highScores list
    highScores.sort(Comparator.comparingLong(a -> a.getLong("time")));
    highScores.sort(Comparator.comparingLong(a -> a.getLong("score")));

    // 3. If highScores.size() > 10, delete last item in highScores
    if (highScores.size() > 10) {
      highScores.remove(highScores.size() - 1);
    }
  }

  void displayGameWonPopup() {
    gameWonPopup.setManaged(true);
    gameWonPopup.setVisible(true);

    gameWonPopup.setMaxWidth(Main.mainStage.widthProperty().get() * 0.4);
    gameWonPopup.setMaxHeight(Main.mainStage.heightProperty().get() * 0.4);
    gameWonPopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    gameWonPopupTitle.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.04) + "px;");

    gameWonPopupImgContainer.setStyle("-fx-pref-height: " + (Main.mainStage.getHeight() * 0.1) + "px;");
    gameWonPopupImg.setFitWidth(Main.mainStage.getWidth() * 0.1);
    gameWonPopupImg.setFitHeight(Main.mainStage.getWidth() * 0.1);

    gameWonPopupTimeTaken.setText(gameDuration + " second" + ((gameDuration > 1) ? "s" : ""));
    gameWonPopupTimeTaken.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.02) + "px;");

    VBox.setVgrow(gameWonPopupButtonsContainer, Priority.ALWAYS);

    gameWonPopupButtonsContainer.setSpacing(Main.mainStage.getWidth() * 0.05);

    gameWonPopupPlayAgainBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
    gameWonPopupMainMenuBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
  }

  @FXML
  void playAgain() {
    if (gameLost) {
      gameLostPopup.setManaged(false);
      gameLostPopup.setVisible(false);
    } else {
      gameWonPopup.setManaged(false);
      gameWonPopup.setVisible(false);
    }

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
    GameMap.getInstance().clearGrid();
    gridContainer.getChildren().remove(0);
  }
}