package com.example.bombland;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
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
 * This class implements the UI for the Create-A-Room page.
 */
public class CreateRoomController {
  private static CreateRoomController instance;

  @FXML
  Button backBtn;

  @FXML
  VBox createRoomPage;

  @FXML
  VBox createRoomPageContainer;

  @FXML
  VBox createRoomPageContainerBottom;

  @FXML
  HBox createRoomPageContainerTopLeftChild;

  @FXML
  HBox createRoomPageContainerTopMiddleChild;

  @FXML
  HBox createRoomPageContainerTopRightChild;

  @FXML
  Label createRoomPageTitle;

  @FXML
  Label roomNameText;

  @FXML
  TextField roomNameTextField;

  @FXML
  Label roomNameError;

  @FXML
  Label playerNameText;

  @FXML
  TextField playerNameTextField;

  @FXML
  Region space;

  @FXML
  Button changePlayerNameBtn;

  @FXML
  Button createRoomBtn;

  @FXML
  VBox errorPopup;

  @FXML
  Label errorPopupTitle;

  @FXML
  Label errorLastSentence;

  @FXML
  Button closeErrorPopupBtn;


  private CreateRoomController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return an instance of this class.
   */
  public static CreateRoomController getInstance() {
    if (instance == null) {
      instance = new CreateRoomController();
    }

    return instance;
  }

  /**
   * This function sets up the multiplayer Selection page.
   */
  @FXML
  public void initialize() {
    // Prevents the width of the createRoomPageContainer VBox
    // from having the same width as its parent container (createRoomPage)
    createRoomPage.setFillWidth(false);


    createRoomPageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.75),
            Main.mainStage.heightProperty().multiply(0.6),
            Main.mainStage.widthProperty().multiply(0.02)
        )
    );

    createRoomPageContainerTopLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.05)
        )
    );

    createRoomPageContainerTopMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.6))
    );

    createRoomPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    createRoomPageContainerTopRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );


    VBox.setVgrow(createRoomPageContainerBottom, Priority.ALWAYS);


    roomNameTextField.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.3),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.015))
    );

    roomNameText.styleProperty().bind(
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

    createRoomBtn.styleProperty().bind(
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

    MultiplayerSelectionController multiplayerController = MultiplayerSelectionController.getInstance();
    loader.setController(multiplayerController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - CreateRoomController.goToMultiplayerSelection(): Could not return to the multiplayer selection page.");
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
  private void createRoom() {
    if (roomNameTextField.getText().isBlank()) {
      roomNameError.setVisible(true); // display error
      roomNameTextField.setText("");
    } else {
      roomNameError.setVisible(false);

      JSONObject roomInfo = new JSONObject();
      roomInfo.put("id", UUID.randomUUID().toString().substring(0, 8));
      roomInfo.put("name", roomNameTextField.getText().strip());
      roomInfo.put("player1", playerNameTextField.getText());

      AppCache.getInstance().setMultiplayerRoom(roomInfo);
      AppCache.getInstance().setPlayerName(playerNameTextField.getText());

      try {
        Main.socketClient.createRoom(roomInfo);
      } catch (Exception e) {
        displayErrorPopup();
      }
    }
  }

  @FXML
  private void displayErrorPopup() {
    createRoomPageContainer.setEffect(new GaussianBlur());
    createRoomPageContainer.setMouseTransparent(true);

    errorPopup.setManaged(true);
    errorPopup.setVisible(true);

    errorPopup.setMaxWidth(Main.mainStage.getWidth() * 0.33);
    errorPopup.setMaxHeight(Main.mainStage.getHeight() * 0.40);

    errorPopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx; -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    errorPopupTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.33))
    );


    // Find all nodes with the "databaseErrorText" style class
    Set<Node> foundNodes = errorPopup.lookupAll(".errorText");

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


    errorLastSentence.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-text-fill: red; -fx-padding: %.2fpx 0 %.2fpx 0",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01))
    );


    closeErrorPopupBtn.setMaxWidth(Main.mainStage.getWidth() * 0.31);

    closeErrorPopupBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.31),
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.011))
    );
  }

  @FXML
  private void closeErrorPopup() {
    errorPopup.setManaged(false);
    errorPopup.setVisible(false);

    createRoomPageContainer.setEffect(null);
    createRoomPageContainer.setMouseTransparent(false);
  }

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
      System.out.println("ERROR - CreateRoomController.goToRoom(): Could not go to the room page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }
}