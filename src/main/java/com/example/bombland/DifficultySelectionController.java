package com.example.bombland;

import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class implements the UI for the Difficulty Selection page.
 */
public class DifficultySelectionController {
  private static DifficultySelectionController instance;

  @FXML
  Button backBtn;

  @FXML
  VBox difficultySelectionPage;

  @FXML
  VBox difficultySelectionPageContainer;

  @FXML
  VBox difficultySelectionPageContainerBottom;

  @FXML
  VBox difficultySelectionPageContainerBottomInner;

  @FXML
  HBox difficultySelectionPageContainerTop;

  @FXML
  HBox difficultySelectionPageContainerTopLeftChild;

  @FXML
  HBox difficultySelectionPageContainerTopMiddleChild;

  @FXML
  HBox difficultySelectionPageContainerTopRightChild;

  @FXML
  Label difficultySelectionPageTitle;

  @FXML
  Button easyDifficultyBtn;

  @FXML
  Button mediumDifficultyBtn;

  @FXML
  Button hardDifficultyBtn;


  private DifficultySelectionController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return an instance of this class.
   */
  public static DifficultySelectionController getInstance() {
    if (instance == null) {
      instance = new DifficultySelectionController();
    }

    return instance;
  }

  /**
   * This function sets up the Difficulty Selection page.
   */
  @FXML
  public void initialize() {
    // Prevents the width of the difficultySelectionPageContainer VBox
    // from having the same width as its parent container (difficultySelectionPage)
    difficultySelectionPage.setFillWidth(false);


    difficultySelectionPageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-padding: %.2fpx;",
          Main.mainStage.widthProperty().multiply(0.75),
          Main.mainStage.heightProperty().multiply(0.6),
          Main.mainStage.widthProperty().multiply(0.02)
        )
    );

    difficultySelectionPageContainerTopLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
          Main.mainStage.widthProperty().multiply(0.05)
        )
    );

    difficultySelectionPageContainerTopMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.6))
    );

    difficultySelectionPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    difficultySelectionPageContainerTopRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );


    VBox.setVgrow(difficultySelectionPageContainerBottom, Priority.ALWAYS);

    difficultySelectionPageContainerBottomInner
        .setSpacing(Main.mainStage.heightProperty().get() * 0.04);

    easyDifficultyBtn.styleProperty().bind(
        Bindings.format(
          "-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
          Main.mainStage.widthProperty().multiply(0.4),
          Main.mainStage.widthProperty().multiply(0.03),
          Main.mainStage.widthProperty().multiply(0.1)
        )
    );

    mediumDifficultyBtn.styleProperty().bind(
        Bindings.format(
          "-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
          Main.mainStage.widthProperty().multiply(0.4),
          Main.mainStage.widthProperty().multiply(0.03),
          Main.mainStage.widthProperty().multiply(0.1)
        )
    );

    hardDifficultyBtn.styleProperty().bind(
        Bindings.format(
          "-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
          Main.mainStage.widthProperty().multiply(0.4),
          Main.mainStage.widthProperty().multiply(0.03),
          Main.mainStage.widthProperty().multiply(0.1)
        )
    );
  }

  @FXML
  private void goToMainMenu() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/main-view.fxml")
    );

    MainController mainController = MainController.getInstance();
    loader.setController(mainController);

    try {
      Scene scene = new Scene(loader.load(), 1024, 768);
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - DifficultySelectionController.goToMainMenu(): Could not return to the main menu page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  @FXML
  private void pickEasyDifficulty() {
    AppCache.getInstance().setGameDifficulty("Easy");
    openMapSelectionPage();
  }

  @FXML
  private void pickMediumDifficulty() {
    AppCache.getInstance().setGameDifficulty("Medium");
    openMapSelectionPage();
  }

  @FXML
  private void pickHardDifficulty() {
    AppCache.getInstance().setGameDifficulty("Hard");
    openMapSelectionPage();
  }

  @FXML
  private void openMapSelectionPage() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/map-selection-view.fxml")
    );

    MapSelectionController mapController = MapSelectionController.getInstance();
    loader.setController(mapController);

    try {
      Scene scene = new Scene(loader.load(), 1024, 768);
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - goToMainMenu(): Could not open the map selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }
}