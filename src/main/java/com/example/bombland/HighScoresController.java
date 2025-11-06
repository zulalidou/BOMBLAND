package com.example.bombland;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

/**
 * This class sets up the UI for the High Scores page.
 */
public class HighScoresController {
  private static HighScoresController instance;

  @FXML
  VBox highScoresPage;

  @FXML
  StackPane highScoresStackpane;

  @FXML
  VBox highScoresStackpaneChild;

  @FXML
  HBox highScoresContainerTop;

  @FXML
  HBox highScoresContainerLeftChild;

  @FXML
  Button backBtn;

  @FXML
  ImageView backBtnImgView;

  @FXML
  HBox highScoresContainerMiddleChild;

  @FXML
  Label highScoresPageTitle;

  @FXML
  HBox highScoresContainerRightChild;

  @FXML
  HBox mapSelectorContainer;

  @FXML
  Button mapBtn1;

  @FXML
  Button mapBtn2;

  @FXML
  Button mapBtn3;

  @FXML
  Button mapBtn4;

  @FXML
  HBox highScoresContainerBottom;

  @FXML
  VBox easyHighScoresColumn;

  @FXML
  Label easyHighScoreTitle;

  @FXML
  VBox easyHighScoresScoreContainer;

  @FXML
  VBox mediumHighScoresColumn;

  @FXML
  Label mediumHighScoreTitle;

  @FXML
  VBox mediumHighScoresScoreContainer;

  @FXML
  VBox hardHighScoresColumn;

  @FXML
  Label hardHighScoreTitle;

  @FXML
  VBox hardHighScoresScoreContainer;

  @FXML
  VBox databaseCommunicationErrorPopup;

  @FXML
  Label databaseCommunicationErrorPopupTitle;

  @FXML
  Label databaseErrorLastSentence;

  @FXML
  Button closeDatabaseErrorPopupBtn;

  @FXML
  ProgressIndicator loadingIcon;


  private HighScoresController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return An instance of this class.
   */
  public static HighScoresController getInstance() {
    if (instance == null) {
      instance = new HighScoresController();
    }

    return instance;
  }

  /**
   * Creates and populates the High Scores page.
   */
  @FXML
  public void initialize() {
    createHighScoresPage();
    populateHighScoresPage();
  }

  /**
   * Creates the High Scores page UI.
   */
  private void createHighScoresPage() {
    VBox.setVgrow(highScoresStackpane, Priority.ALWAYS);

    highScoresContainerLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", highScoresContainerTop.widthProperty().multiply(0.33))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;", highScoresContainerLeftChild.widthProperty().multiply(0.1))
    );

    backBtnImgView.fitWidthProperty().bind(highScoresContainerLeftChild.widthProperty().multiply(0.075));
    backBtnImgView.fitHeightProperty().bind(highScoresContainerLeftChild.heightProperty().multiply(0.4));

    highScoresContainerMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", highScoresContainerTop.widthProperty().multiply(0.34))
    );

    highScoresPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", highScoresContainerMiddleChild.widthProperty().multiply(0.1))
    );

    highScoresContainerRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", highScoresContainerTop.widthProperty().multiply(0.33))
    );

    mapSelectorContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-padding: %.2fpx;",
            highScoresStackpaneChild.widthProperty().multiply(1),
            highScoresStackpaneChild.widthProperty().multiply(0.01))
    );

    mapBtn1.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx;",
            mapSelectorContainer.widthProperty().multiply(0.25),
            mapSelectorContainer.widthProperty().multiply(0.012))
    );
    mapBtn2.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx;",
            mapSelectorContainer.widthProperty().multiply(0.25),
            mapSelectorContainer.widthProperty().multiply(0.012))
    );
    mapBtn3.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx;",
            mapSelectorContainer.widthProperty().multiply(0.25),
            mapSelectorContainer.widthProperty().multiply(0.012))
    );
    mapBtn4.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx;",
            mapSelectorContainer.widthProperty().multiply(0.25),
            mapSelectorContainer.widthProperty().multiply(0.012))
    );


    VBox.setVgrow(highScoresContainerBottom, Priority.ALWAYS);
    easyHighScoresColumn.setPrefWidth(Main.mainStage.getWidth() * 0.33);
    mediumHighScoresColumn.setPrefWidth(Main.mainStage.getWidth() * 0.34);
    hardHighScoresColumn.setPrefWidth(Main.mainStage.getWidth() * 0.33);

    easyHighScoreTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx",
            highScoresContainerBottom.widthProperty().multiply(0.02))
    );

    mediumHighScoreTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx",
            highScoresContainerBottom.widthProperty().multiply(0.02))
    );

    hardHighScoreTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx",
            highScoresContainerBottom.widthProperty().multiply(0.02))
    );

    VBox.setVgrow(easyHighScoresScoreContainer, Priority.ALWAYS);
    VBox.setVgrow(mediumHighScoresScoreContainer, Priority.ALWAYS);
    VBox.setVgrow(hardHighScoresScoreContainer, Priority.ALWAYS);

    easyHighScoresScoreContainer.spacingProperty().bind(Main.mainStage.heightProperty().multiply(0.01));
    mediumHighScoresScoreContainer.spacingProperty().bind(Main.mainStage.heightProperty().multiply(0.01));
    hardHighScoresScoreContainer.spacingProperty().bind(Main.mainStage.heightProperty().multiply(0.01));
  }

  /**
   * Populates the High Scores page UI.
   */
  private void populateHighScoresPage() {
    if (AppCache.getInstance().isGettingData()) {
      displayLoadingIcon();

      Task<Void> waitTask = new Task<>() {
        @Override
        protected Void call() {
          waitForDataRetrieval();
          return null;
        }
      };

      waitTask.setOnSucceeded(event -> {
        hideLoadingIcon();
        showRectangleMapHighScores();
      });

      new Thread(waitTask).start();
    } else {
      // This will run only once. getHighScoresFromDynamoDb() will run if the app hasn't been able
      // to retrieve the high scores from DynamoDB.
      if (!AppCache.getInstance().highScoresHaveBeenRetrieved()) {
        getHighScoresFromDynamoDb();
      }

      showRectangleMapHighScores();
    }
  }

  /**
   * Retrieves the high scores from DynamoDB.
   */
  private void getHighScoresFromDynamoDb() {
    Runnable dynamoDbConnectionTask = () -> {
      if (AppCache.getInstance().getIdentityPoolId().isEmpty()) {
        Main.getEnvironmentVariables();
      }

      try {
        AppCache.getInstance().setGettingData(true);
        DynamoDbClientUtil.getHighScores();
        AppCache.getInstance().setHighScoresHaveBeenRetrieved();
        showRectangleMapHighScores();
      } catch (Exception e) {
        System.out.println("\n====================================================================");
        System.out.println("ERROR - HighScoresController.getHighScoresFromDynamoDb(): Could not save get the high scores from DynamoDB.");
        System.out.println("---");
        System.out.println(e.getCause());
        System.out.println("====================================================================\n");
        displayDatabaseErrorPopup();
      } finally {
        AppCache.getInstance().setGettingData(false);
      }
    };

    new Thread(dynamoDbConnectionTask).start();
  }

  /**
   * Waits for the data retrieval from DynamoDB to end.
   */
  private void waitForDataRetrieval() {
    while (AppCache.getInstance().isGettingData()) {
      // Forcing the thread to sleep for a bit in order to let it notice the
      // value of APP_CACHE.getInstance().isGettingData() change
      try {
        Thread.sleep(100);  // Give some time for the state to change
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();

        System.out.println("\n====================================================================");
        System.out.println("ERROR - waitForDataRetrieval(): An error occurred while the thread was sleeping.");
        System.out.println("---");
        System.out.println(e.getCause());
        System.out.println("====================================================================\n");
      }
    }
  }

  /**
   * Displays a loading icon.
   */
  private void displayLoadingIcon() {
    highScoresStackpaneChild.setEffect(new GaussianBlur()); // blurs the gameplay page
    highScoresStackpaneChild.setMouseTransparent(true); // makes items in the high scores page "unclickable"
    loadingIcon.setManaged(true);
    loadingIcon.setVisible(true);
  }

  /**
   * Hides the loading icon.
   */
  private void hideLoadingIcon() {
    highScoresStackpaneChild.setEffect(null); // un-blurs the gameplay page
    highScoresStackpaneChild.setMouseTransparent(false); // makes items in gameplay page "clickable"
    loadingIcon.setManaged(false);
    loadingIcon.setVisible(false);
  }

  /**
   * Redirects the user to the Main Menu page.
   */
  @FXML
  private void goToMainMenu() {
    AppCache.getInstance().setMapOfHighScoresBeingShown("");

    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/main-view.fxml"));

    MainController mainController = MainController.getInstance();
    loader.setController(mainController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - HighScoresController.goToMainMenu(): Could not return to the main menu page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * Displays the high scores from the Rectangle map.
   */
  @FXML
  private void showRectangleMapHighScores() {
    if (AppCache.getInstance().getMapOfHighScoresBeingShown().equals("Rectangle")) {
      return;
    }

    AppCache.getInstance().setMapOfHighScoresBeingShown("Rectangle");
    selectMapButton("Rectangle");

    displayScores("Easy", "Rectangle", easyHighScoresScoreContainer);
    displayScores("Medium", "Rectangle", mediumHighScoresScoreContainer);
    displayScores("Hard", "Rectangle", hardHighScoresScoreContainer);
  }

  /**
   * Displays the high scores from the Bomb map.
   */
  @FXML
  private void showBombMapHighScores() {
    if (AppCache.getInstance().getMapOfHighScoresBeingShown().equals("Bomb")) {
      return;
    }

    AppCache.getInstance().setMapOfHighScoresBeingShown("Bomb");

    selectMapButton("Bomb");

    displayScores("Easy", "Bomb", easyHighScoresScoreContainer);
    displayScores("Medium", "Bomb", mediumHighScoresScoreContainer);
    displayScores("Hard", "Bomb", hardHighScoresScoreContainer);
  }

  /**
   * Displays the high scores from the Face map.
   */
  @FXML
  private void showFaceMapHighScores() {
    if (AppCache.getInstance().getMapOfHighScoresBeingShown().equals("Face")) {
      return;
    }

    AppCache.getInstance().setMapOfHighScoresBeingShown("Face");

    selectMapButton("Face");

    displayScores("Easy", "Face", easyHighScoresScoreContainer);
    displayScores("Medium", "Face", mediumHighScoresScoreContainer);
    displayScores("Hard", "Face", hardHighScoresScoreContainer);
  }

  /**
   * Displays the high scores from the Flower map.
   */
  @FXML
  private void showFlowerMapHighScores() {
    if (AppCache.getInstance().getMapOfHighScoresBeingShown().equals("Flower")) {
      return;
    }

    AppCache.getInstance().setMapOfHighScoresBeingShown("Flower");

    selectMapButton("Flower");

    displayScores("Easy", "Flower", easyHighScoresScoreContainer);
    displayScores("Medium", "Flower", mediumHighScoresScoreContainer);
    displayScores("Hard", "Flower", hardHighScoresScoreContainer);
  }

  /**
   * Updates the page UI whenever one of the map buttons gets clicked.
   */
  private void selectMapButton(String map) {
    switch (map) {
      case "Rectangle" -> {
        if (mapBtn1.getStyleClass().contains("mapBtn_selected")) {
          return;
        }

        mapBtn1.getStyleClass().remove("mapBtn_unselected");
        mapBtn1.getStyleClass().add("mapBtn_selected");

        deselectMapButton(mapBtn2);
        deselectMapButton(mapBtn3);
        deselectMapButton(mapBtn4);
      }
      case "Bomb" -> {
        mapBtn2.getStyleClass().remove("mapBtn_unselected");
        mapBtn2.getStyleClass().add("mapBtn_selected");

        deselectMapButton(mapBtn1);
        deselectMapButton(mapBtn3);
        deselectMapButton(mapBtn4);
      }
      case "Face" -> {
        mapBtn3.getStyleClass().remove("mapBtn_unselected");
        mapBtn3.getStyleClass().add("mapBtn_selected");

        deselectMapButton(mapBtn1);
        deselectMapButton(mapBtn2);
        deselectMapButton(mapBtn4);
      }
      default -> {
        mapBtn4.getStyleClass().remove("mapBtn_unselected");
        mapBtn4.getStyleClass().add("mapBtn_selected");

        deselectMapButton(mapBtn1);
        deselectMapButton(mapBtn2);
        deselectMapButton(mapBtn3);
      }
    }
  }

  /**
   * Updates the UI to show that a map button has been deselected.
   */
  private void deselectMapButton(Button button) {
    if (button.getStyleClass().contains("mapBtn_selected")) {
      button.getStyleClass().remove("mapBtn_selected");
      button.getStyleClass().add("mapBtn_unselected");
    }
  }

  /**
   * If there are scores that need to be displayed for a specific map at a specific difficulty,
   * it's done so. Otherwise, a placeholder is displayed to let the user know that there are no high
   * scores for the specified map and difficulty.
   *
   * @param difficulty The difficulty of the game.
   * @param map The game map.
   * @param container The container that'll show the high scores (if they exist).
   */
  private void displayScores(String difficulty, String map, VBox container) {
    container.getChildren().clear();

    ArrayList<JSONObject> highScores = getMapScores(difficulty, map);

    if (highScores.isEmpty()) {
      displayPlaceholder(container);
    } else {
      addScoresToScreen(highScores, container);
    }
  }

  /**
   * Retrieves the high scores of a specified difficulty on a specified map.
   *
   * @param difficulty The difficulty of the game when the high score was set.
   * @param map The game map when the high score was set.
   * @return A list of JSONObjects that contains info about the high scores from
   *         the specified difficulty and map.
   */
  private ArrayList<JSONObject> getMapScores(String difficulty, String map) {
    final ArrayList<JSONObject> allHighScores = AppCache.getInstance().getHighScores(difficulty);
    ArrayList<JSONObject> mapHighScores = new ArrayList<>();

    for (JSONObject highScore : allHighScores) {
      if (highScore.getString("map").equals(map)) {
        mapHighScores.add(highScore);
      }
    }

    return mapHighScores;
  }

  /**
   * Places high scores into a container for them to eventually be displayed.
   *
   * @param scores A list of JSONObjects, where each object represents a high score.
   * @param scoresContainer The container that'll store the high scores.
   */
  private void addScoresToScreen(ArrayList<JSONObject> scores, VBox scoresContainer) {
    for (JSONObject score : scores) {
      Label scoreBox = new Label(score.getString("name") + ", " + score.getLong("score"));
      scoreBox.getStyleClass().add("highScoreLabel");

      scoreBox.styleProperty().bind(
          Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; "
              + "-fx-padding: %.2fpx;",
              scoresContainer.widthProperty().multiply(0.04),
              scoresContainer.widthProperty().multiply(0.025),
              scoresContainer.widthProperty().multiply(0.012))
      );

      scoresContainer.getChildren().add(scoreBox);
    }
  }

  /**
   * A placeholder to be shown if there are no high scores for a specific map at a specific
   * difficulty.
   *
   * @param scoresContainer The container that'll store and display the placeholder.
   */
  private void displayPlaceholder(VBox scoresContainer) {
    Image img = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/smiley-face.png"))
    );

    ImageView imgView = new ImageView();
    imgView.setImage(img);
    imgView.fitWidthProperty().bind(easyHighScoresScoreContainer.widthProperty().multiply(0.25));
    imgView.fitHeightProperty().bind(easyHighScoresScoreContainer.heightProperty().multiply(0.25));
    imgView.setPreserveRatio(true);

    scoresContainer.getChildren().add(imgView);

    Label noActivityLabel = new Label("No activity yet!");
    noActivityLabel.getStyleClass().add("highScoresActivityLabel");
    scoresContainer.getChildren().add(noActivityLabel);
  }

  /**
   * This function displays an error popup.
   */
  public void displayDatabaseErrorPopup() {
    highScoresStackpaneChild.setEffect(new GaussianBlur());
    highScoresStackpaneChild.setMouseTransparent(true);

    databaseCommunicationErrorPopup.setManaged(true);
    databaseCommunicationErrorPopup.setVisible(true);

    databaseCommunicationErrorPopup.setMaxWidth(highScoresPage.getWidth() * 0.4);
    databaseCommunicationErrorPopup.setMaxHeight(highScoresPage.getHeight() * 0.4);

    databaseCommunicationErrorPopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    databaseCommunicationErrorPopupTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            databaseCommunicationErrorPopup.widthProperty().multiply(0.07),
            databaseCommunicationErrorPopup.widthProperty().multiply(1))
    );


    // Find all nodes with the "databaseErrorText" style class
    Set<Node> foundNodes = databaseCommunicationErrorPopup.lookupAll(".databaseErrorText");

    // Iterate over the set and cast each Node to a Label
    for (Node node : foundNodes) {
      // Use an instanceof check for safety
      if (node instanceof Label) {
        Label label = (Label) node;
        label.setWrapText(true);
        label.styleProperty().bind(
            Bindings.format("-fx-font-size: %.2fpx;",
                databaseCommunicationErrorPopup.widthProperty().multiply(0.03))
        );
      }
    }

    databaseErrorLastSentence.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-padding: %.2fpx 0 %.2fpx 0",
            databaseCommunicationErrorPopup.widthProperty().multiply(0.03),
            databaseCommunicationErrorPopup.widthProperty().multiply(0.01),
            databaseCommunicationErrorPopup.widthProperty().multiply(0.01))
    );

    closeDatabaseErrorPopupBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            databaseCommunicationErrorPopup.widthProperty().multiply(1),
            databaseCommunicationErrorPopup.widthProperty().multiply(0.01),
            databaseCommunicationErrorPopup.widthProperty().multiply(0.0275))
    );
  }

  /**
   * This function closes an error popup.
   */
  public void closeDatabaseErrorPopup() {
    databaseCommunicationErrorPopup.setManaged(false);
    databaseCommunicationErrorPopup.setVisible(false);
    highScoresStackpaneChild.setEffect(null);
    highScoresStackpaneChild.setMouseTransparent(false);
  }
}