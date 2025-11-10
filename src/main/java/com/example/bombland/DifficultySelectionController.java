package com.example.bombland;

import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class implements the UI for the Difficulty Selection page.
 */
public class DifficultySelectionController {
  private static DifficultySelectionController instance;

  @FXML
  VBox difficultySelectionPage;

  @FXML
  VBox difficultySelectionPageContainer;

  @FXML
  HBox difficultySelectionPageContainerTopLeftChild;

  @FXML
  Button backBtn;

  @FXML
  ImageView backBtnImgView;

  @FXML
  HBox difficultySelectionPageContainerTopMiddleChild;

  @FXML
  Label difficultySelectionPageTitle;

  @FXML
  HBox difficultySelectionPageContainerTopRightChild;

  @FXML
  VBox difficultySelectionPageContainerBottom;

  @FXML
  VBox difficultySelectionPageContainerBottomInner;

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
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.25))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;", difficultySelectionPageContainerTopLeftChild.widthProperty().multiply(0.1))
    );

    backBtnImgView.fitWidthProperty().bind(difficultySelectionPageContainerTopLeftChild.widthProperty().multiply(0.155));
    backBtnImgView.fitHeightProperty().bind(difficultySelectionPageContainerTopLeftChild.heightProperty().multiply(0.4));

    difficultySelectionPageContainerTopMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.5))
    );

    difficultySelectionPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    difficultySelectionPageContainerTopRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.25))
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

  /**
   * Redirects the user back to the Mode Selection page.
   */
  @FXML
  private void goToModeSelection() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/mode-selection-view.fxml")
    );

    ModeSelectionController modeController = ModeSelectionController.getInstance();
    loader.setController(modeController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - DifficultySelectionController.goToModeSelection(): Could not return to the mode selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * The user picked the "Easy" difficulty.
   */
  @FXML
  private void pickEasyDifficulty() {
    AppCache.getInstance().setGameDifficulty("Easy");
    openMapSelectionPage();
  }

  /**
   * The user picked the "Medium" difficulty.
   */
  @FXML
  private void pickMediumDifficulty() {
    AppCache.getInstance().setGameDifficulty("Medium");
    openMapSelectionPage();
  }

  /**
   * The user picked the "Hard" difficulty.
   */
  @FXML
  private void pickHardDifficulty() {
    AppCache.getInstance().setGameDifficulty("Hard");
    openMapSelectionPage();
  }

  /**
   * Sends the user to the Map Selection page.
   */
  @FXML
  private void openMapSelectionPage() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/map-selection-view.fxml")
    );

    MapSelectionController mapController = MapSelectionController.getInstance();
    loader.setController(mapController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - openMapSelectionPage(): Could not open the map selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }
}