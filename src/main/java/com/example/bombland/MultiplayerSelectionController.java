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
 * This class implements the UI for the Multiplayer Selection page.
 */
public class MultiplayerSelectionController {
  private static MultiplayerSelectionController instance;

  @FXML
  VBox multiplayerSelectionPage;

  @FXML
  VBox multiplayerSelectionPageContainer;

  @FXML
  HBox multiplayerSelectionPageContainerTopLeftChild;

  @FXML
  Button backBtn;

  @FXML
  ImageView backBtnImgView;

  @FXML
  HBox multiplayerSelectionPageContainerTopMiddleChild;

  @FXML
  Label multiplayerSelectionPageTitle;

  @FXML
  HBox multiplayerSelectionPageContainerTopRightChild;

  @FXML
  VBox multiplayerSelectionPageContainerBottom;

  @FXML
  VBox multiplayerSelectionPageContainerBottomInner;

  @FXML
  Button createRoomBtn;

  @FXML
  Button joinRoomBtn;


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
        Bindings.format("-fx-background-radius: %.2fpx;", Main.mainStage.widthProperty().multiply(0.1))
    );

    backBtnImgView.fitWidthProperty().bind(multiplayerSelectionPageContainerTopLeftChild.widthProperty().multiply(0.235));
    backBtnImgView.fitHeightProperty().bind(multiplayerSelectionPageContainerTopLeftChild.heightProperty().multiply(0.4));

    Image backBtnImg = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/back-button.png"))
    );
    backBtnImgView.setImage(backBtnImg);

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

    createRoomBtn.styleProperty().bind(
        Bindings.format(
            "-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.4),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.1)
        )
    );

    joinRoomBtn.styleProperty().bind(
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
      System.out.println("ERROR - MultiplayerSelectionController.goToModeSelection(): Could not return to the mode selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * Sends the user to the Create A Room page.
   */
  @FXML
  private void goToCreateRoom() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/create-room-view.fxml")
    );

    CreateRoomController createRoomController = CreateRoomController.getInstance();
    loader.setController(createRoomController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - MultiplayerSelectionController.goToCreateRoom(): Could not open the create room page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * Sends the user to the Join A Room page.
   */
  @FXML
  private void goToJoinRoom() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/join-room-view.fxml")
    );

    JoinRoomController joinRoomController = JoinRoomController.getInstance();
    loader.setController(joinRoomController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - goToJoinRoom(): Could not open the join room page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }
}