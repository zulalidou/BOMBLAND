package com.example.bombland;

import java.io.IOException;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class sets up the UI for the Map Selection page.
 */
public class MapSelectionController {
  private static MapSelectionController instance;

  @FXML
  VBox mapSelectionPage;

  @FXML
  VBox mapSelectionPageContainer;

  @FXML
  HBox mapSelectionPageContainerTopLeftChild;

  @FXML
  Button backBtn;

  @FXML
  ImageView backBtnImgView;

  @FXML
  HBox mapSelectionPageContainerTopMiddleChild;

  @FXML
  Label mapSelectionPageTitle;

  @FXML
  HBox mapSelectionPageContainerTopRightChild;

  @FXML
  VBox mapSelectionPageContainerBottom;

  @FXML
  HBox upperMapsContainer;

  @FXML
  VBox rectangleMapSelectorContainer;

  @FXML
  Label rectangleMapSelectorName;

  @FXML
  VBox bombMapSelectorContainer;

  @FXML
  Label bombMapSelectorName;

  @FXML
  HBox lowerMapsContainer;

  @FXML
  VBox faceMapSelectorContainer;

  @FXML
  ImageView faceMapImgView;

  @FXML
  Image faceMapImg;

  @FXML
  Label faceMapSelectorName;

  @FXML
  VBox flowerMapSelectorContainer;

  @FXML
  Label flowerMapSelectorName;


  private MapSelectionController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return An instance of this class.
   */
  public static MapSelectionController getInstance() {
    if (instance == null) {
      instance = new MapSelectionController();
    }

    return instance;
  }

  /**
   * Creates and populates the Map selection page.
   */
  @FXML
  public void initialize() {
    // Prevents the width of the mapSelectionPageContainer VBox
    // from having the same width as its parent container (mapSelectionPage)
    mapSelectionPage.setFillWidth(false);

    mapSelectionPageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.75),
            Main.mainStage.heightProperty().multiply(0.6),
            Main.mainStage.widthProperty().multiply(0.02))
    );

    mapSelectionPageContainerTopLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.33))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;", Main.mainStage.widthProperty().multiply(0.1))
    );

    backBtnImgView.fitWidthProperty().bind(mapSelectionPageContainerTopLeftChild.widthProperty().multiply(0.1));
    backBtnImgView.fitHeightProperty().bind(mapSelectionPageContainerTopLeftChild.heightProperty().multiply(0.4));

    mapSelectionPageContainerTopMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.34))
    );

    mapSelectionPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    mapSelectionPageContainerTopRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.33))
    );

    mapSelectionPageContainerBottom.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.55))
    );

    rectangleMapSelectorContainer.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.2),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    rectangleMapSelectorName.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.02))
    );

    bombMapSelectorContainer.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.2),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    bombMapSelectorName.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.02))
    );

    faceMapSelectorContainer.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.2),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    faceMapSelectorName.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.02))
    );

    if (AppCache.getInstance().getGameDifficulty().equals("Medium")) {
      faceMapImg = new Image(Objects.requireNonNull(
          getClass().getResourceAsStream("/com/example/bombland/Images/poker-face.png"))
      );
      faceMapImgView.setImage(faceMapImg);
    } else if (AppCache.getInstance().getGameDifficulty().equals("Hard")) {
      faceMapImg = new Image(Objects.requireNonNull(
          getClass().getResourceAsStream("/com/example/bombland/images/sad-face.png"))
      );
      faceMapImgView.setImage(faceMapImg);
    }

    flowerMapSelectorContainer.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.2),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    flowerMapSelectorName.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.02))
    );

    mapSelectionPageContainer.setFillWidth(false);
    upperMapsContainer.setFillHeight(false);
    lowerMapsContainer.setFillHeight(false);

    upperMapsContainer.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.0075))
    );

    lowerMapsContainer.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.0075))
    );

    VBox.setVgrow(mapSelectionPageContainerBottom, Priority.ALWAYS);
    VBox.setVgrow(upperMapsContainer, Priority.ALWAYS);
    VBox.setVgrow(lowerMapsContainer, Priority.ALWAYS);
  }

  /**
   * Redirects the user back to the Difficulty Selection page.
   */
  @FXML
  private void goToDifficultySelection() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/difficulty-selection-view.fxml")
    );

    DifficultySelectionController difficultyController = DifficultySelectionController.getInstance();
    loader.setController(difficultyController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - goToDifficultySelection(): Could not open the difficulty selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * The user picked the "Rectangle" Map.
   */
  @FXML
  private void pickRectangleMap() {
    AppCache.getInstance().setGameMap("Rectangle");
    startGame();
  }

  /**
   * The user picked the "Bomb" Map.
   */
  @FXML
  private void pickBombMap() {
    AppCache.getInstance().setGameMap("Bomb");
    startGame();
  }

  /**
   * The user picked the "Face" Map.
   */
  @FXML
  private void pickFaceMap() {
    AppCache.getInstance().setGameMap("Face");
    startGame();
  }

  /**
   * The user picked the "Flower" Map.
   */
  @FXML
  private void pickFlowerMap() {
    AppCache.getInstance().setGameMap("Flower");
    startGame();
  }

  /**
   * Sends the user to the Game map.
   */
  @FXML
  private void startGame() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/play-view.fxml")
    );

    PlayController playController = PlayController.getInstance();
    loader.setController(playController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - MapSelectionController.startGame(): Could not open the game map.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }
}