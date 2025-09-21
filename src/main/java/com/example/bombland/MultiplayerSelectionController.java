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
 * This class implements the UI for the Multiplayer Selection page.
 */
public class MultiplayerSelectionController {
  private static MultiplayerSelectionController instance;

  @FXML
  Button backBtn;

  @FXML
  VBox multiplayerSelectionPage;

  @FXML
  VBox multiplayerSelectionPageContainer;

  @FXML
  VBox multiplayerSelectionPageContainerBottom;

  @FXML
  VBox multiplayerSelectionPageContainerBottomInner;

  @FXML
  HBox multiplayerSelectionPageContainerTopLeftChild;

  @FXML
  HBox multiplayerSelectionPageContainerTopMiddleChild;

  @FXML
  HBox multiplayerSelectionPageContainerTopRightChild;

  @FXML
  Label multiplayerSelectionPageTitle;

  @FXML
  Button hostGameBtn;

  @FXML
  Button joinGameBtn;


  private MultiplayerSelectionController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return an instance of this class.
   */
  public static MultiplayerSelectionController getInstance() {
    if (instance == null) {
      instance = new MultiplayerSelectionController();
    }

    return instance;
  }

  /**
   * This function sets up the multiplayer Selection page.
   */
  @FXML
  public void initialize() {
    // Prevents the width of the multiplayerSelectionPageContainer VBox
    // from having the same width as its parent container (multiplayerSelectionPage)
    multiplayerSelectionPage.setFillWidth(false);


    multiplayerSelectionPageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.75),
            Main.mainStage.heightProperty().multiply(0.6),
            Main.mainStage.widthProperty().multiply(0.02)
        )
    );

    multiplayerSelectionPageContainerTopLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.05)
        )
    );

    multiplayerSelectionPageContainerTopMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.6))
    );

    multiplayerSelectionPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    multiplayerSelectionPageContainerTopRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );


    VBox.setVgrow(multiplayerSelectionPageContainerBottom, Priority.ALWAYS);

    multiplayerSelectionPageContainerBottomInner
        .setSpacing(Main.mainStage.heightProperty().get() * 0.04);

    hostGameBtn.styleProperty().bind(
        Bindings.format(
            "-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.4),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.1)
        )
    );

    joinGameBtn.styleProperty().bind(
        Bindings.format(
            "-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.4),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.1)
        )
    );
  }

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
      System.out.println("ERROR - MultiplayerSelectionController.goToModeSelection(): Could not return to the mode selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  @FXML
  private void pickHostGame() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/host-game-settings-view.fxml")
    );

    HostGameSettingsController hostGameController = HostGameSettingsController.getInstance();
    loader.setController(hostGameController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - pickHostGame(): Could not open the host game settings page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  @FXML
  private void pickJoinGame() {

  }
}