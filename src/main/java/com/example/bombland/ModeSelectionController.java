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
 * This class implements the UI for the Mode Selection page.
 */
public class ModeSelectionController {
  private static ModeSelectionController instance;

  @FXML
  Button backBtn;

  @FXML
  VBox modeSelectionPage;

  @FXML
  VBox modeSelectionPageContainer;

  @FXML
  VBox modeSelectionPageContainerBottom;

  @FXML
  VBox modeSelectionPageContainerBottomInner;

  @FXML
  HBox modeSelectionPageContainerTopLeftChild;

  @FXML
  HBox modeSelectionPageContainerTopMiddleChild;

  @FXML
  HBox modeSelectionPageContainerTopRightChild;

  @FXML
  Label modeSelectionPageTitle;

  @FXML
  Button singlePlayerBtn;

  @FXML
  Button multiplayerBtn;


  private ModeSelectionController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return an instance of this class.
   */
  public static ModeSelectionController getInstance() {
    if (instance == null) {
      instance = new ModeSelectionController();
    }

    return instance;
  }

  /**
   * This function sets up the Mode Selection page.
   */
  @FXML
  public void initialize() {
    // Prevents the width of the modeSelectionPageContainer VBox
    // from having the same width as its parent container (modeSelectionPage)
    modeSelectionPage.setFillWidth(false);


    modeSelectionPageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.75),
            Main.mainStage.heightProperty().multiply(0.6),
            Main.mainStage.widthProperty().multiply(0.02)
        )
    );

    modeSelectionPageContainerTopLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.05)
        )
    );

    modeSelectionPageContainerTopMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.6))
    );

    modeSelectionPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    modeSelectionPageContainerTopRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );


    VBox.setVgrow(modeSelectionPageContainerBottom, Priority.ALWAYS);

    modeSelectionPageContainerBottomInner
        .setSpacing(Main.mainStage.heightProperty().get() * 0.04);

    singlePlayerBtn.styleProperty().bind(
        Bindings.format(
            "-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.4),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.1)
        )
    );

    multiplayerBtn.styleProperty().bind(
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
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - ModeSelectionController.goToMainMenu(): Could not return to the main menu page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  @FXML
  private void pickSinglePlayer() {
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
      System.out.println("ERROR - pickSinglePlayer(): Could not open the difficulty selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  @FXML
  private void pickMultiplayer() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/multiplayer-selection-view.fxml")
    );

    MultiplayerSelectionController multiplayerController = MultiplayerSelectionController.getInstance();
    loader.setController(multiplayerController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - pickMultiplayer(): Could not open the multiplayer selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }
}