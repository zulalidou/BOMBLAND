package com.example.bombland;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

/**
 * This class implements the UI for the Join-A-Room page.
 */
public class JoinRoomController {
  private static JoinRoomController instance;

  @FXML
  VBox page;

  @FXML
  VBox pageContainer;

  @FXML
  HBox pageContainerTop;

  @FXML
  HBox pageContainerTopLeftChild;

  @FXML
  Button backBtn;

  @FXML
  ImageView backBtnImgView;

  @FXML
  HBox pageContainerTopMiddleChild;

  @FXML
  Label pageTitle;

  @FXML
  HBox pageContainerTopRightChild;

  @FXML
  VBox roomIdContainer;

  @FXML
  Label roomIdText;

  @FXML
  TextField roomIdTextField;

  @FXML
  Label roomIdError;

  @FXML
  VBox playerNameContainer;

  @FXML
  Label playerNameText;

  @FXML
  TextField playerNameTextField;

  @FXML
  VBox changePlayerNameContainer;

  @FXML
  Button changePlayerNameBtn;

  @FXML
  VBox joinRoomBtnContainer;

  @FXML
  Button joinRoomBtn;

  @FXML
  VBox errorPopup1;

  @FXML
  Label errorPopupTitle1;

  @FXML
  ImageView error1ImgView;

  @FXML
  Label errorLastSentence1;

  @FXML
  Button closeErrorPopup1Btn;

  @FXML
  VBox errorPopup2;

  @FXML
  Label errorPopupTitle2;

  @FXML
  ImageView error2ImgView;

  @FXML
  Label errorLastSentence2;

  @FXML
  Button closeErrorPopup2Btn;


  private JoinRoomController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return an instance of this class.
   */
  public static JoinRoomController getInstance() {
    if (instance == null) {
      instance = new JoinRoomController();
    }

    return instance;
  }

  /**
   * This function sets up the multiplayer Selection page.
   */
  @FXML
  public void initialize() {
    // Prevents the width of the pageContainer VBox
    // from having the same width as its parent container (page)
    page.setFillWidth(false);

    pageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.75),
            Main.mainStage.heightProperty().multiply(0.6),
            Main.mainStage.widthProperty().multiply(0.02)
        )
    );

    pageContainerTop.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;",
            pageContainer.heightProperty().multiply(0.15))
    );

    pageContainerTopLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;", Main.mainStage.widthProperty().multiply(0.1))
    );

    backBtnImgView.fitWidthProperty().bind(pageContainerTopLeftChild.widthProperty().multiply(0.235));
    backBtnImgView.fitHeightProperty().bind(pageContainerTopLeftChild.heightProperty().multiply(0.4));

    Image backBtnImg = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/back-button.png"))
    );
    backBtnImgView.setImage(backBtnImg);

    pageContainerTopMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.6))
    );

    pageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", pageContainerTop.widthProperty().multiply(0.05))
    );

    pageContainerTopRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );

    roomIdContainer.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;",
            pageContainer.heightProperty().multiply(0.25))
    );

    roomIdText.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", roomIdContainer.widthProperty().multiply(0.03))
    );

    roomIdTextField.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-font-size: %.2fpx;",
            roomIdContainer.widthProperty().multiply(0.5),
            roomIdContainer.widthProperty().multiply(0.04),
            roomIdContainer.widthProperty().multiply(0.02))
    );

    playerNameContainer.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;",
            pageContainer.heightProperty().multiply(0.25))
    );

    playerNameText.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", playerNameContainer.widthProperty().multiply(0.03))
    );

    playerNameTextField.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-font-size: %.2fpx;",
            playerNameContainer.widthProperty().multiply(0.5),
            playerNameContainer.widthProperty().multiply(0.04),
            playerNameContainer.widthProperty().multiply(0.02))
    );

    setPlayerName();

    changePlayerNameContainer.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;",
            pageContainer.heightProperty().multiply(0.15))
    );

    changePlayerNameBtn.styleProperty().bind(
        Bindings.format(
            "-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            changePlayerNameContainer.widthProperty().multiply(0.015),
            changePlayerNameContainer.widthProperty().multiply(0.05))
    );

    joinRoomBtnContainer.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;",
            pageContainer.heightProperty().multiply(0.2))
    );

    joinRoomBtn.styleProperty().bind(
        Bindings.format(
            "-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            joinRoomBtnContainer.widthProperty().multiply(0.4),
            joinRoomBtnContainer.widthProperty().multiply(0.025),
            joinRoomBtnContainer.widthProperty().multiply(0.1))
    );
  }

  /**
   * Redirects the user back to the Multiplayer Selection page.
   */
  @FXML
  private void goToMultiplayerSelection() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/multiplayer-selection-view.fxml")
    );

    MultiplayerSelectionController multiplayerSelectionController = MultiplayerSelectionController.getInstance();
    loader.setController(multiplayerSelectionController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - JoinRoomController.goToMultiplayerSelection(): Could not return to the multiplayer selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * Sets the player's username in the text box.
   */
  @FXML
  private void setPlayerName() {
    String firstHalf = Usernames.getFirstHalfOfName();
    String secondHalf = Usernames.getSecondHalfOfName();
    playerNameTextField.setText(firstHalf + "-" + secondHalf);
  }

  /**
   * Attempts to join a multiplayer room.
   */
  @FXML
  private void joinRoom() {
    if (roomIdTextField.getText().isBlank()) {
      roomIdError.setVisible(true); // display error
      roomIdTextField.setText("");
    } else {
      roomIdError.setVisible(false);

      JSONObject roomInfo = new JSONObject();
      roomInfo.put("id", roomIdTextField.getText().strip().toLowerCase());
      roomInfo.put("player2", playerNameTextField.getText());

      try {
        // It checks if the room exists in the checkRoom() function below
        // - If it does, the user is sent to the room
        // - If it doesn't, an error popup message gets displayed

        Main.socketClient.checkRoom(roomInfo);
      } catch (Exception e) {
        displayGenericErrorPopup();
      }
    }
  }

  /**
   * Displays an error popup to let the user know that a room doesn't exist.
   */
  @FXML
  void displayRoomDoesNotExistPopup() {
    pageContainer.setEffect(new GaussianBlur());
    pageContainer.setMouseTransparent(true);

    errorPopup1.setManaged(true);
    errorPopup1.setVisible(true);

    errorPopup1.setMaxWidth(Main.mainStage.getWidth() * 0.4);
    errorPopup1.setMaxHeight(Main.mainStage.getHeight() * 0.28);

    errorPopup1.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    errorPopupTitle1.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            errorPopup1.widthProperty().multiply(0.07),
            errorPopup1.widthProperty().multiply(1))
    );

    Image errorImg = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/error.png"))
    );
    error1ImgView.setImage(errorImg);

    errorLastSentence1.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-padding: %.2fpx 0 %.2fpx 0;",
            errorPopup1.widthProperty().multiply(0.03),
            errorPopup1.widthProperty().multiply(0.01),
            errorPopup1.widthProperty().multiply(0.01))
    );

    closeErrorPopup1Btn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            errorPopup1.widthProperty().multiply(1),
            errorPopup1.widthProperty().multiply(0.01),
            errorPopup1.widthProperty().multiply(0.0275))
    );
  }

  /**
   * Closes the RoomDoesNotExist popup.
   */
  @FXML
  private void closeRoomDoesNotExistPopup() {
    errorPopup1.setManaged(false);
    errorPopup1.setVisible(false);
    pageContainer.setEffect(null);
    pageContainer.setMouseTransparent(false);
  }

  /**
   * Displays a generic error popup.
   */
  @FXML
  private void displayGenericErrorPopup() {
    pageContainer.setEffect(new GaussianBlur());
    pageContainer.setMouseTransparent(true);

    errorPopup2.setManaged(true);
    errorPopup2.setVisible(true);

    errorPopup2.setMaxWidth(Main.mainStage.getWidth() * 0.4);
    errorPopup2.setMaxHeight(Main.mainStage.getHeight() * 0.4);

    errorPopup2.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    errorPopupTitle2.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            errorPopup2.widthProperty().multiply(0.07),
            errorPopup2.widthProperty().multiply(1))
    );

    Image errorImg = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/error.png"))
    );
    error2ImgView.setImage(errorImg);

    // Find all nodes with the "databaseErrorText" style class
    Set<Node> foundNodes = errorPopup2.lookupAll(".errorText");

    // Iterate over the set and cast each Node to a Label
    for (Node node : foundNodes) {
      // Use an instanceof check for safety
      if (node instanceof Label) {
        Label label = (Label) node;
        label.setWrapText(true);
        label.styleProperty().bind(
            Bindings.format("-fx-font-size: %.2fpx;",
                errorPopup2.widthProperty().multiply(0.03))
        );
      }
    }

    errorLastSentence2.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-padding: %.2fpx 0 %.2fpx 0",
            errorPopup2.widthProperty().multiply(0.03),
            errorPopup2.widthProperty().multiply(0.01),
            errorPopup2.widthProperty().multiply(0.01))
    );

    closeErrorPopup2Btn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            errorPopup2.widthProperty().multiply(1),
            errorPopup2.widthProperty().multiply(0.01),
            errorPopup2.widthProperty().multiply(0.0275))
    );
  }

  /**
   * Closes the generic error popup.
   */
  @FXML
  private void closeGenericErrorPopup() {
    errorPopup2.setManaged(false);
    errorPopup2.setVisible(false);
    pageContainer.setEffect(null);
    pageContainer.setMouseTransparent(false);
  }

  /**
   * Sends the user to the multiplayer room page.
   */
  @FXML
  void goToRoom() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/room-view.fxml")
    );

    RoomController roomController = RoomController.getInstance();
    loader.setController(roomController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - JoinRoomController.goToRoom(): Could not go to the room page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }
}