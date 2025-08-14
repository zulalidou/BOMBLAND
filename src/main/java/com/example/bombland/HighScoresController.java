package com.example.bombland;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
  StackPane highScoresStackpane;

  @FXML
  VBox highScoresPage;

  @FXML
  VBox highScoresStackpaneChild;

  @FXML
  VBox easyHighScoresColumn;

  @FXML
  VBox mediumHighScoresColumn;

  @FXML
  VBox hardHighScoresColumn;

  @FXML
  VBox easyHighScoresScoreContainer;

  @FXML
  VBox mediumHighScoresScoreContainer;

  @FXML
  VBox hardHighScoresScoreContainer;

  @FXML
  HBox mapSelectorContainer;

  @FXML
  HBox highScoresContainerBottom;

  @FXML
  HBox highScoresContainerLeftChild;

  @FXML
  HBox highScoresContainerMiddleChild;

  @FXML
  HBox highScoresContainerRightChild;

  @FXML
  Label highScoresPageTitle;

  @FXML
  Label easyHighScoreTitle;

  @FXML
  Label mediumHighScoreTitle;

  @FXML
  Label hardHighScoreTitle;

  @FXML
  Button backBtn;

  @FXML
  Button mapBtn1;

  @FXML
  Button mapBtn2;

  @FXML
  Button mapBtn3;

  @FXML
  Button mapBtn4;

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
    highScoresPage.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx;", Main.mainStage.widthProperty().multiply(0.02))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;", Main.mainStage.widthProperty().multiply(0.05))
    );


    highScoresContainerLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.39))
    );

    highScoresContainerMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.39))
    );

    highScoresPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    highScoresContainerRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.39))
    );


    mapSelectorContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.75),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    mapBtn1.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-background-radius: 0px;",
            Main.mainStage.widthProperty().multiply(0.2425))
    );
    mapBtn2.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-background-radius: 0px;",
            Main.mainStage.widthProperty().multiply(0.2425))
    );
    mapBtn3.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-background-radius: 0px;",
            Main.mainStage.widthProperty().multiply(0.2425))
    );
    mapBtn4.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-background-radius: 0px;",
            Main.mainStage.widthProperty().multiply(0.2425))
    );


    easyHighScoreTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.3265))
    );

    mediumHighScoreTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.327))
    );

    hardHighScoreTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.3265))
    );


    VBox.setVgrow(highScoresStackpane, Priority.ALWAYS);

    VBox.setVgrow(highScoresContainerBottom, Priority.ALWAYS);

    HBox.setHgrow(easyHighScoresColumn, Priority.ALWAYS);
    HBox.setHgrow(mediumHighScoresColumn, Priority.ALWAYS);
    HBox.setHgrow(hardHighScoresColumn, Priority.ALWAYS);

    VBox.setVgrow(easyHighScoresScoreContainer, Priority.ALWAYS);
    VBox.setVgrow(mediumHighScoresScoreContainer, Priority.ALWAYS);
    VBox.setVgrow(hardHighScoresScoreContainer, Priority.ALWAYS);


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
      showRectangleMapHighScores();
    }
  }

  private void waitForDataRetrieval() {
    while (AppCache.getInstance().isGettingData()) {
      // Forcing the thread to sleep for a bit in order to let it notice the
      // value of APP_CACHE.getInstance().isGettingData() change
      try {
        Thread.sleep(100);  // Give some time for the state to change
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }


  private void displayLoadingIcon() {
    // blurs the gameplay page
    highScoresStackpaneChild.setEffect(new GaussianBlur());

    // makes items in the high scores page "unclickable"
    highScoresStackpaneChild.setMouseTransparent(true);

    loadingIcon.setManaged(true);
    loadingIcon.setVisible(true);
  }

  private void hideLoadingIcon() {
    highScoresStackpaneChild.setEffect(null);
    highScoresStackpaneChild.setMouseTransparent(false); // makes items in gameplay page "clickable"

    loadingIcon.setManaged(false);
    loadingIcon.setVisible(false);
  }


  @FXML
  private void goToMainMenu() throws IOException {
    AppCache.getInstance().setMapOfHighScoresBeingShown("");

    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/main-view.fxml"));

    MainController mainController = MainController.getInstance();
    loader.setController(mainController);

    Scene scene = new Scene(loader.load(), 1024, 768);
    Main.mainStage.setScene(scene);
    Main.mainStage.show();
  }


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

  private void deselectMapButton(Button button) {
    if (button.getStyleClass().contains("mapBtn_selected")) {
      button.getStyleClass().remove("mapBtn_selected");
      button.getStyleClass().add("mapBtn_unselected");
    }
  }

  private void displayScores(String difficulty, String map, VBox container) {
    container.getChildren().clear();

    ArrayList<JSONObject> easyHighScores = getMapScores(difficulty, map);

    if (!easyHighScores.isEmpty()) {
      addScoresToScreen(easyHighScores, container);
    } else {
      displayPlaceholder(container);
    }
  }

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

  private void addScoresToScreen(ArrayList<JSONObject> scores, VBox scoresContainer) {
    for (JSONObject score : scores) {
      Label scoreBox = new Label(score.getString("name") + ", " + score.getLong("score"));
      scoreBox.getStyleClass().add("highScoreLabel");

      scoreBox.styleProperty().bind(
          Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; "
              + "-fx-spacing: %.2fpx; -fx-padding: %.2fpx;",
              Main.mainStage.widthProperty().multiply(0.02),
              Main.mainStage.widthProperty().multiply(0.01),
              Main.mainStage.widthProperty().multiply(0.04),
              Main.mainStage.widthProperty().multiply(0.0075))
      );

      scoresContainer.getChildren().add(scoreBox);
    }
  }

  private void displayPlaceholder(VBox scoresContainer) {
    Image img = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/smiley-face.png")));
    ImageView imgView = new ImageView();
    imgView.setImage(img);
    imgView.setFitWidth(90);
    imgView.setFitHeight(60);
    imgView.setPreserveRatio(true);
    scoresContainer.getChildren().add(imgView);

    Label noActivityLabel = new Label("No activity yet!");
    noActivityLabel.getStyleClass().add("highScores_activityLabel");
    scoresContainer.getChildren().add(noActivityLabel);
  }
}
