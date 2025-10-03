package com.example.bombland;

import java.io.IOException;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

/**
 * This class implements the UI for the Join-A-Room page.
 */
public class JoinRoomController {
  private static JoinRoomController instance;

  @FXML
  Button backBtn;

  @FXML
  VBox page;

  @FXML
  VBox pageContainer;

  @FXML
  VBox pageContainerBottom;

  @FXML
  HBox pageContainerTopLeftChild;

  @FXML
  HBox pageContainerTopMiddleChild;

  @FXML
  HBox pageContainerTopRightChild;

  @FXML
  Label pageTitle;

  @FXML
  Label roomIdText;

  @FXML
  TextField roomIdTextField;

  @FXML
  Label roomIdError;

  @FXML
  Label playerNameText;

  @FXML
  TextField playerNameTextField;

  @FXML
  Region space;

  @FXML
  Button changePlayerNameBtn;

  @FXML
  Button joinRoomBtn;

  @FXML
  VBox errorPopup1;

  @FXML
  Label errorPopupTitle1;

  @FXML
  Label errorLastSentence1;

  @FXML
  Button closeErrorPopup1Btn;

  @FXML
  VBox errorPopup2;

  @FXML
  Label errorPopupTitle2;

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

    pageContainerTopLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.05)
        )
    );

    pageContainerTopMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.6))
    );

    pageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    pageContainerTopRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );


    VBox.setVgrow(pageContainerBottom, Priority.ALWAYS);


    roomIdTextField.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.3),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.015))
    );

    roomIdText.styleProperty().bind(
        Bindings.format(
            "-fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.03)
        )
    );


    playerNameText.styleProperty().bind(
        Bindings.format(
            "-fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.03)
        )
    );

    playerNameTextField.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.3),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.015))
    );

    setPlayerName();

    space.styleProperty().bind(
        Bindings.format(
            "-fx-pref-height: %.2fpx",
            Main.mainStage.widthProperty().multiply(0.01)
        )
    );

    changePlayerNameBtn.styleProperty().bind(
        Bindings.format(
            "-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.05)
        )
    );

    joinRoomBtn.styleProperty().bind(
        Bindings.format(
            "-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.4),
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.1)
        )
    );
  }

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

  @FXML
  private void setPlayerName() {
    String firstHalf = Usernames.getFirstHalfOfName();
    String secondHalf = Usernames.getSecondHalfOfName();
    playerNameTextField.setText(firstHalf + "-" + secondHalf);
  }

  @FXML
  private void joinRoom() {
    if (roomIdTextField.getText().isBlank()) {
      roomIdError.setVisible(true); // display error
      roomIdTextField.setText("");
    } else {
      roomIdError.setVisible(false);
      String roomId = roomIdTextField.getText().strip().toLowerCase();

      try {
        if (AppCache.getInstance().getIdentityPoolId().isEmpty()) {
          Main.getEnvironmentVariables();
        }

        JSONObject roomInfo = DynamoDbClientUtil.getRoom(roomId);

        if (roomInfo == null) {
          displayRoomDoesNotExistPopup();
        } else {
          AppCache.getInstance().setMultiplayerRoom(roomInfo);
          goToRoom();
        }
      } catch (Exception e) {
        displayGenericErrorPopup();
      }
    }
  }

  @FXML
  private void displayRoomDoesNotExistPopup() {
    pageContainer.setEffect(new GaussianBlur());
    pageContainer.setMouseTransparent(true);

    errorPopup1.setManaged(true);
    errorPopup1.setVisible(true);

    errorPopup1.setMaxWidth(Main.mainStage.getWidth() * 0.33);
    errorPopup1.setMaxHeight(Main.mainStage.getHeight() * 0.32);

    errorPopup1.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    errorPopupTitle1.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.33))
    );


    errorLastSentence1.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-padding: %.2fpx 0 %.2fpx 0; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01),
            errorPopup1.widthProperty().multiply(1))
    );


    closeErrorPopup1Btn.setMaxWidth(Main.mainStage.getWidth() * 0.31);

    closeErrorPopup1Btn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.31),
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.011))
    );
  }

  @FXML
  private void closeRoomDoesNotExistPopup() {
    errorPopup1.setManaged(false);
    errorPopup1.setVisible(false);

    pageContainer.setEffect(null);
    pageContainer.setMouseTransparent(false);
  }

  @FXML
  private void displayGenericErrorPopup() {
    pageContainer.setEffect(new GaussianBlur());
    pageContainer.setMouseTransparent(true);

    errorPopup2.setManaged(true);
    errorPopup2.setVisible(true);

    errorPopup2.setMaxWidth(Main.mainStage.getWidth() * 0.33);
    errorPopup2.setMaxHeight(Main.mainStage.getHeight() * 0.40);

    errorPopup2.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    errorPopupTitle2.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.33))
    );


    // Find all nodes with the "databaseErrorText" style class
    Set<Node> foundNodes = errorPopup2.lookupAll(".errorText");

    // Iterate over the set and cast each Node to a Label
    for (Node node : foundNodes) {
      // Use an instanceof check for safety
      if (node instanceof Label) {
        Label label = (Label) node;

        label.styleProperty().bind(
            Bindings.format("-fx-font-size: %.2fpx;",
                Main.mainStage.widthProperty().multiply(0.01))
        );
      }
    }


    errorLastSentence2.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-text-fill: red; -fx-padding: %.2fpx 0 %.2fpx 0",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01))
    );


    closeErrorPopup2Btn.setMaxWidth(Main.mainStage.getWidth() * 0.31);

    closeErrorPopup2Btn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.31),
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.011))
    );
  }

  @FXML
  private void closeGenericErrorPopup() {
    errorPopup2.setManaged(false);
    errorPopup2.setVisible(false);

    pageContainer.setEffect(null);
    pageContainer.setMouseTransparent(false);
  }

  @FXML
  private void goToRoom() {
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