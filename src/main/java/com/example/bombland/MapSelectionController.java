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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * This class sets up the UI for the Map Selection page.
 */
public class MapSelectionController {
  private static MapSelectionController instance;

  @FXML
  Button backBtn;

  @FXML
  VBox mapSelectionPage;

  @FXML
  VBox mapSelectionPageContainer;

  @FXML
  VBox mapSelectionPageContainerBottom;

  @FXML
  VBox rectangleMapSelectorContainer;

  @FXML
  VBox bombMapSelectorContainer;

  @FXML
  VBox faceMapSelectorContainer;

  @FXML
  VBox flowerMapSelectorContainer;

  @FXML
  HBox mapSelectionPageContainerTopLeftChild;

  @FXML
  HBox mapSelectionPageContainerTopMiddleChild;

  @FXML
  HBox mapSelectionPageContainerTopRightChild;

  @FXML
  HBox upperMapsContainer;

  @FXML
  HBox lowerMapsContainer;

  @FXML
  Label mapSelectionPageTitle;

  @FXML
  Label rectangleMapSelectorName;

  @FXML
  Label bombMapSelectorName;

  @FXML
  Label faceMapSelectorName;

  @FXML
  Label flowerMapSelectorName;

  @FXML
  Region space1;

  @FXML
  Region space2;

  @FXML
  Image faceMapImg;

  @FXML
  ImageView faceMapImgView;


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
    // Prevents the width of the difficultySelectionPageContainer VBox
    // from having the same width as its parent container (difficultySelectionPage)
    mapSelectionPage.setFillWidth(false);

    mapSelectionPageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.75),
            Main.mainStage.heightProperty().multiply(0.6),
            Main.mainStage.widthProperty().multiply(0.02))
    );

    mapSelectionPageContainerTopLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.05))
    );

    mapSelectionPageContainerTopMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.6))
    );

    mapSelectionPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    mapSelectionPageContainerTopRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );

    mapSelectionPageContainerBottom.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.55))
    );



    rectangleMapSelectorContainer.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.25),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    rectangleMapSelectorName.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.02))
    );


    bombMapSelectorContainer.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.25),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    bombMapSelectorName.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.02))
    );


    faceMapSelectorContainer.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.25),
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
            Main.mainStage.widthProperty().multiply(0.25),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    flowerMapSelectorName.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.02))
    );


    mapSelectionPageContainer.setFillWidth(false);
    upperMapsContainer.setFillHeight(false);
    lowerMapsContainer.setFillHeight(false);

    VBox.setVgrow(mapSelectionPageContainerBottom, Priority.ALWAYS);
    VBox.setVgrow(upperMapsContainer, Priority.ALWAYS);
    VBox.setVgrow(lowerMapsContainer, Priority.ALWAYS);

    HBox.setHgrow(space1, Priority.ALWAYS);
    HBox.setHgrow(space2, Priority.ALWAYS);
  }


  @FXML
  private void goToDifficultySelection() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/difficulty-selection-view.fxml")
    );

    DifficultySelectionController difficultyController = DifficultySelectionController.getInstance();
    loader.setController(difficultyController);

    try {
      Scene scene = new Scene(loader.load(), 1024, 768);
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

  @FXML
  private void pickRectangleMap() {
    AppCache.getInstance().setGameMap("Rectangle");
    startGame();
  }

  @FXML
  private void pickBombMap() {
    AppCache.getInstance().setGameMap("Bomb");
    startGame();
  }

  @FXML
  private void pickFaceMap() {
    AppCache.getInstance().setGameMap("Face");
    startGame();
  }

  @FXML
  private void pickFlowerMap() {
    AppCache.getInstance().setGameMap("Flower");
    startGame();
  }

  @FXML
  private void startGame() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/play-view.fxml")
    );

    PlayController playController = PlayController.getInstance();
    loader.setController(playController);

    try {
      Scene scene = new Scene(loader.load(), 1024, 768);
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - startGame(): Could not open the game map.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }
}