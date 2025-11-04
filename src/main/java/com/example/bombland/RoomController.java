package com.example.bombland;

import java.io.IOException;
import java.net.URL;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.json.JSONObject;

/**
 * This class sets up the UI for the Room page.
 */
public class RoomController {
  private static RoomController instance;

  @FXML
  VBox topPageOuterContainer;

  @FXML
  HBox topPageContainer;

  @FXML
  HBox backBtnContainer;

  @FXML
  Button backBtn;

  @FXML
  VBox roomIdContainer;

  @FXML
  Label roomIdTitleLabel;

  @FXML
  Label roomIdLabel;

  @FXML
  VBox roomNameContainer;

  @FXML
  Label roomNameTitleLabel;

  @FXML
  Label roomNameLabel;

  @FXML
  VBox totalPlayersContainer;

  @FXML
  Label totalPlayersTitleLabel;

  @FXML
  Label totalPlayersLabel;

  @FXML
  HBox middlePageContainer;

  @FXML
  VBox settingsContainer;

  @FXML
  Label settingsContainerTitleLabel;

  @FXML
  Region sectionSpace1;

  @FXML
  VBox mapContainer;

  @FXML
  Label selectMapLabel;

  @FXML
  HBox mapContainerInner;

  @FXML
  Button leftMapBtn;

  @FXML
  Region mapContainerSpace1;

  @FXML
  VBox mapBox;

  @FXML
  ImageView mapImgView;

  @FXML
  Label mapNameLabel;

  @FXML
  Region mapContainerSpace2;

  @FXML
  Button rightMapBtn;

  @FXML
  Region sectionSpace2;

  @FXML
  VBox difficultyContainer;

  @FXML
  Label selectDifficultyLabel;

  @FXML
  HBox difficultyContainerInner;

  @FXML
  Button leftDifficultyBtn;

  @FXML
  Region difficultyContainerSpace1;

  @FXML
  VBox difficultyBox;

  @FXML
  ImageView difficultyImgView;

  @FXML
  Label difficultyNameLabel;

  @FXML
  Region difficultyContainerSpace2;

  @FXML
  Button rightDifficultyBtn;

  @FXML
  Region sectionSpace3;

  @FXML
  Label leaveRoomPopupErrorMsg;

  @FXML
  VBox buttonsContainer;

  @FXML
  Region buttonSpace;

  @FXML
  Button startGameButton;

  @FXML
  VBox playersContainer;

  @FXML
  Label playersContainerTitleLabel;

  @FXML
  HBox playerIconsContainer;

  @FXML
  VBox player1BoxContainer;

  @FXML
  VBox player1Box;

  @FXML
  Label player1NameLabel;

  @FXML
  Region playerProfileSpace;

  @FXML
  VBox player2BoxContainer;

  @FXML
  VBox player2Box;

  @FXML
  Label player2NameLabel;

  @FXML
  HBox utilitiesContainer;

  @FXML
  VBox voiceChatBoxContainer;

  @FXML
  VBox voiceChatBox;

  @FXML
  Label voiceChatLabel;

  @FXML
  HBox bottomPageContainer;

  @FXML
  VBox leaveRoomPopup;

  @FXML
  Label leaveRoomPopupTitle;

  @FXML
  VBox leaveRoomPopupImgContainer;

  @FXML
  ImageView leaveRoomPopupImg;

  @FXML
  HBox leaveRoomPopupButtonsContainer;

  @FXML
  Button leaveRoomPopupYesBtn;

  @FXML
  Button leaveRoomPopupNoBtn;

  @FXML
  VBox kickOutPlayer2Popup;

  @FXML
  Label kickOutPlayer2PopupTitle;

  @FXML
  VBox kickOutPlayer2PopupImgContainer;

  @FXML
  ImageView kickOutPlayer2PopupImg;

  @FXML
  Label kickOutPlayer2PopupErrorMsg;

  @FXML
  Button kickOutPlayer2PopupCloseButton;

  @FXML
  VBox startGamePopup;

  @FXML
  Label startGamePopupTitle;

  @FXML
  VBox startGamePopupImgContainer;

  @FXML
  ImageView startGamePopupImg;

  @FXML
  Label startGamePopupMsg;

  @FXML
  HBox startGamePopupButtonsContainer;

  @FXML
  Button startGamePopupYesBtn;

  @FXML
  Button startGamePopupNoBtn;


  private RoomController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return An instance of this class.
   */
  public static RoomController getInstance() {
    if (instance == null) {
      instance = new RoomController();
    }

    return instance;
  }

  /**
   * This function sets up the Room page.
   */
  @FXML
  public void initialize() {
    displayUi();
  }

  /**
   * Displays the page UI.
   */
  private void displayUi() {
    backBtnContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", topPageContainer.widthProperty().multiply(0.05))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            backBtnContainer.widthProperty().multiply(0.2))
    );

    roomIdContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", topPageContainer.widthProperty().multiply(0.3))
    );

    roomIdTitleLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.01))
    );

    roomIdLabel.setText(AppCache.getInstance().getMultiplayerRoom().get("id").toString());

    roomIdLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.02))
    );

    roomNameContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", topPageContainer.widthProperty().multiply(0.3))
    );

    roomNameTitleLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.01))
    );

    roomNameLabel.setText(AppCache.getInstance().getMultiplayerRoom().get("name").toString());

    roomNameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.02))
    );

    totalPlayersContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", topPageContainer.widthProperty().multiply(0.3))
    );

    totalPlayersTitleLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.01))
    );

    totalPlayersLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.02))
    );


    middlePageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;", Main.mainStage.heightProperty().multiply(0.82))
    );


    settingsContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", middlePageContainer.widthProperty().multiply(0.5))
    );

    settingsContainerTitleLabel.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-pref-height: %.2fpx;",
            settingsContainer.widthProperty().multiply(1),
            settingsContainer.widthProperty().multiply(0.051),
            settingsContainer.heightProperty().multiply(0.07))
    );

    sectionSpace1.prefHeightProperty().bind(settingsContainer.heightProperty().multiply(0.04));

    selectMapLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", mapContainer.widthProperty().multiply(0.04))
    );

    mapContainerSpace1.prefWidthProperty().bind(mapContainerInner.widthProperty().multiply(0.025));
    mapContainerSpace2.prefWidthProperty().bind(mapContainerInner.widthProperty().multiply(0.025));

    mapBox.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
                + "-fx-background-radius: %.2fpx;",
            mapContainerInner.widthProperty().multiply(0.01),
            mapContainerInner.widthProperty().multiply(0.25),
            mapContainerInner.widthProperty().multiply(0.01))
    );

    mapNameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", mapBox.widthProperty().multiply(0.125))
    );

    selectDifficultyLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", difficultyContainer.widthProperty().multiply(0.04))
    );

    sectionSpace2.prefHeightProperty().bind(settingsContainer.heightProperty().multiply(0.04));


    difficultyContainerSpace1.prefWidthProperty().bind(difficultyContainerInner.widthProperty().multiply(0.025));
    difficultyContainerSpace2.prefWidthProperty().bind(difficultyContainerInner.widthProperty().multiply(0.025));

    difficultyBox.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
                + "-fx-background-radius: %.2fpx;",
            difficultyContainerInner.widthProperty().multiply(0.01),
            difficultyContainerInner.widthProperty().multiply(0.25),
            difficultyContainerInner.widthProperty().multiply(0.01))
    );

    difficultyNameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", difficultyBox.widthProperty().multiply(0.125))
    );

    sectionSpace3.prefHeightProperty().bind(settingsContainer.heightProperty().multiply(0.04));


    buttonSpace.prefHeightProperty().bind(buttonsContainer.heightProperty().multiply(0.05));

    startGameButton.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            buttonsContainer.widthProperty().multiply(0.05),
            buttonsContainer.widthProperty().multiply(0.01))
    );


    playersContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.5))
    );

    playersContainerTitleLabel.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; -fx-pref-height: %.2fpx;",
            playersContainer.widthProperty().multiply(1),
            playersContainer.widthProperty().multiply(0.051),
            playersContainer.heightProperty().multiply(0.07))
    );

    playerIconsContainer.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;", playersContainer.heightProperty().multiply(0.465))
    );

    player1BoxContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; ", playersContainer.widthProperty().multiply(0.35))
    );

    player1Box.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
                + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.15),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    player1NameLabel.setText(AppCache.getInstance().getMultiplayerRoom().get("player1Name").toString());

    player1NameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            player1Box.widthProperty().multiply(1))
    );


    utilitiesContainer.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;", playersContainer.heightProperty().multiply(0.465))
    );

    voiceChatBoxContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", playersContainer.widthProperty().multiply(0.35))
    );

    voiceChatBox.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
                + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            voiceChatBoxContainer.widthProperty().multiply(0.35),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    voiceChatLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            player1Box.widthProperty().multiply(1))
    );


    bottomPageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;", Main.mainStage.heightProperty().multiply(0.09))
    );


    if (!AppCache.getInstance().getMultiplayerRoom().getString("player2Name").equals("N/A")) {
      if (!isPlayer1()) {
        addPlayer2ComponentsToUi();
      }

      displayPlayer2Icon();
    }
  }

  /**
   * Displays Player2's UI components (to Player2).
   */
  private void addPlayer2ComponentsToUi() {
    leftMapBtn.setVisible(false);
    leftMapBtn.setManaged(false);
    rightMapBtn.setVisible(false);
    rightMapBtn.setManaged(false);

    leftDifficultyBtn.setVisible(false);
    leftDifficultyBtn.setManaged(false);
    rightDifficultyBtn.setVisible(false);
    rightDifficultyBtn.setManaged(false);

    buttonSpace.setVisible(false);
    buttonSpace.setManaged(false);
    startGameButton.setVisible(false);
    startGameButton.setManaged(false);
  }

  /**
   * Displays the player icon for Player 2.
   */
  void displayPlayer2Icon() {
    updateActivePlayersLabel();

    playerProfileSpace.setVisible(true);
    playerProfileSpace.setManaged(true);
    playerProfileSpace.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; ", playersContainer.widthProperty().multiply(0.1))
    );

    player2BoxContainer.setVisible(true);
    player2BoxContainer.setManaged(true);
    player2BoxContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; ", playersContainer.widthProperty().multiply(0.35))
    );

    player2Box.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
                + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.15),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    player2NameLabel.setText(AppCache.getInstance().getMultiplayerRoom().get("player2Name").toString());

    player2NameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            player1Box.widthProperty().multiply(1))
    );
  }

  /**
   * Updates the number of active players shown in the top right corner of the room, and determines
   * whether to disable the startGame button (for Player1).
   */
  private void updateActivePlayersLabel() {
    int activePlayers = 0;

    if (AppCache.getInstance().getMultiplayerRoom().getBoolean("isPlayer1InRoom")) {
      activePlayers++;
    }

    if (AppCache.getInstance().getMultiplayerRoom().getBoolean("isPlayer2InRoom")) {
      activePlayers++;
    }

    totalPlayersLabel.setText(activePlayers + " / 2");

    if (isPlayer1()) {
      if (activePlayers == 1) {
        startGameButton.setDisable(true);
      } else {
        startGameButton.setDisable(false);
      }
    }
  }

  /**
   * This function displays a popup to ask the user if they're sure they'd like to leave the room.
   */
  @FXML
  private void displayLeaveRoomPopup() {
    topPageOuterContainer.setEffect(new GaussianBlur()); // blurs room page
    topPageOuterContainer.setMouseTransparent(true); // makes items in room page "unclickable"

    leaveRoomPopup.setManaged(true);
    leaveRoomPopup.setVisible(true);

    leaveRoomPopup.setMaxWidth(Main.mainStage.widthProperty().get() * 0.33);
    leaveRoomPopup.setMaxHeight(Main.mainStage.heightProperty().get() * 0.475);

    leaveRoomPopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;  -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    leaveRoomPopupTitle.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.04) + "px;");

    leaveRoomPopupImgContainer.setStyle("-fx-pref-height: " + (Main.mainStage.getHeight() * 0.1) + "px;");
    leaveRoomPopupImg.setFitWidth(Main.mainStage.getWidth() * 0.1);
    leaveRoomPopupImg.setFitHeight(Main.mainStage.getWidth() * 0.1);


    leaveRoomPopupErrorMsg.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-padding: %.2fpx 0 %.2fpx 0;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    if (isPlayer1()) {
      leaveRoomPopupErrorMsg.setText("Are you sure you want to leave? Doing so will delete the room.");
    } else {
      leaveRoomPopupErrorMsg.setText("Are you sure you want to leave?");
    }

    leaveRoomPopupErrorMsg.setTextAlignment(TextAlignment.CENTER); // Center each line of text


    leaveRoomPopupButtonsContainer.setSpacing(Main.mainStage.getWidth() * 0.05);

    leaveRoomPopupYesBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.0125),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
    leaveRoomPopupNoBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.0125),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
  }

  /**
   * Determines whether the current user is Player1.
   *
   * @return A boolean that represents whether the current user is Player1.
   */
  private boolean isPlayer1() {
    String currentPlayerName = AppCache.getInstance().getPlayerName();
    String player1Name = AppCache.getInstance().getMultiplayerRoom().getString("player1Name");

    return currentPlayerName.equals(player1Name);
  }

  /**
   * Redirects the user to the Main Menu page.
   */
  @FXML
  private void goToMainMenu() {
    String currentPlayerName = AppCache.getInstance().getPlayerName();
    Main.socketClient.leaveRoom(currentPlayerName);

    AppCache.getInstance().setMultiplayerRoom(null);
    AppCache.getInstance().setPlayerName(null);

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
      System.out.println("ERROR - RoomController.goToMainMenu(): Could not return to the main menu page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * This function closes an error popup.
   */
  @FXML
  private void closeLeaveRoomPopup() {
    leaveRoomPopup.setManaged(false);
    leaveRoomPopup.setVisible(false);
    topPageOuterContainer.setEffect(null);
    topPageOuterContainer.setMouseTransparent(false);
  }

  /**
   * When the button to the left of the map is clicked, it updates the UI to reflect the
   * change made. (If another player is also in the room, a signal gets sent to the server
   * so that they also see the change in real-time.)
   */
  @FXML
  private void goToLeftMap() {
    String newMap = "";

    if (mapNameLabel.getText().equals("Rectangle")) {
      mapNameLabel.setText("Flower");
      newMap = "flower";
    } else if (mapNameLabel.getText().equals("Bomb")) {
      mapNameLabel.setText("Rectangle");
      newMap = "rectangle";
    } else if (mapNameLabel.getText().equals("Face")) {
      mapNameLabel.setText("Bomb");
      newMap = "bomb";
    } else {
      mapNameLabel.setText("Face");

      if (difficultyNameLabel.getText().equals("Easy")) {
        newMap = "smiley-face";
      } else if (difficultyNameLabel.getText().equals("Medium")) {
        newMap = "poker-face";
      } else {
        newMap = "sad-face";
      }
    }

    URL imageUrl = getClass().getResource("/com/example/bombland/Images/" + newMap + ".png");
    Image newImage = new Image(imageUrl.toString());
    mapImgView.setImage(newImage);

    if (AppCache.getInstance().getMultiplayerRoom().has("player2Name")) {
      sendNewSettingToPlayer2("map", newMap);
    }
  }

  /**
   * When the button to the right of the map is clicked, it updates the UI to reflect the
   * change made. (If another player is also in the room, a signal gets sent to the server
   * so that they also see the change in real-time.)
   */
  @FXML
  private void goToRightMap() {
    String newMap = "";

    if (mapNameLabel.getText().equals("Rectangle")) {
      mapNameLabel.setText("Bomb");
      newMap = "bomb";
    } else if (mapNameLabel.getText().equals("Bomb")) {
      mapNameLabel.setText("Face");

      if (difficultyNameLabel.getText().equals("Easy")) {
        newMap = "smiley-face";
      } else if (difficultyNameLabel.getText().equals("Medium")) {
        newMap = "poker-face";
      } else {
        newMap = "sad-face";
      }
    } else if (mapNameLabel.getText().equals("Face")) {
      mapNameLabel.setText("Flower");
      newMap = "flower";
    } else {
      mapNameLabel.setText("Rectangle");
      newMap = "rectangle";
    }

    URL imageUrl = getClass().getResource("/com/example/bombland/Images/" + newMap + ".png");
    Image newImage = new Image(imageUrl.toString());
    mapImgView.setImage(newImage);

    if (AppCache.getInstance().getMultiplayerRoom().has("player2Name")) {
      sendNewSettingToPlayer2("map", newMap);
    }
  }

  /**
   * When the button to the left of the difficulty is clicked, it updates the UI to reflect the
   * change made. (If another player is also in the room, a signal gets sent to the server
   *  so that they also see the change in real-time.)
   */
  @FXML
  private void goToLeftDifficulty() {
    if (difficultyNameLabel.getText().equals("Easy")) {
      difficultyNameLabel.setText("Hard");
    } else if (difficultyNameLabel.getText().equals("Medium")) {
      difficultyNameLabel.setText("Easy");
    } else {
      difficultyNameLabel.setText("Medium");
    }

    URL imageUrl = getClass().getResource("/com/example/bombland/Images/" + difficultyNameLabel.getText().toLowerCase() + "-difficulty.png");
    Image newImage = new Image(imageUrl.toString());
    difficultyImgView.setImage(newImage);

    if (AppCache.getInstance().getMultiplayerRoom().has("player2Name")) {
      sendNewSettingToPlayer2("difficulty", difficultyNameLabel.getText());
    }


    if (mapNameLabel.getText().equals("Face")) {
      String imgName;

      if (difficultyNameLabel.getText().equals("Easy")) {
        imgName = "smiley-face";
      } else if (difficultyNameLabel.getText().equals("Medium")) {
        imgName = "poker-face";
      } else {
        imgName = "sad-face";
      }

      imageUrl = getClass().getResource("/com/example/bombland/Images/" + imgName + ".png");
      newImage = new Image(imageUrl.toString());
      mapImgView.setImage(newImage);

      if (AppCache.getInstance().getMultiplayerRoom().has("player2Name")) {
        sendNewSettingToPlayer2("map", imgName);
      }
    }
  }

  /**
   * When the button to the right of the difficulty is clicked, it updates the UI to reflect the
   * change made. (If another player is also in the room, a signal gets sent to the server
   * so that they also see the change in real-time.)
   */
  @FXML
  private void goToRightDifficulty() {
    if (difficultyNameLabel.getText().equals("Easy")) {
      difficultyNameLabel.setText("Medium");
    } else if (difficultyNameLabel.getText().equals("Medium")) {
      difficultyNameLabel.setText("Hard");
    } else {
      difficultyNameLabel.setText("Easy");
    }

    URL imageUrl = getClass().getResource("/com/example/bombland/Images/" + difficultyNameLabel.getText().toLowerCase() + "-difficulty.png");
    Image newImage = new Image(imageUrl.toString());
    difficultyImgView.setImage(newImage);

    if (AppCache.getInstance().getMultiplayerRoom().has("player2Name")) {
      sendNewSettingToPlayer2("difficulty", difficultyNameLabel.getText());
    }


    if (mapNameLabel.getText().equals("Face")) {
      String imgName;

      if (difficultyNameLabel.getText().equals("Easy")) {
        imgName = "smiley-face";
      } else if (difficultyNameLabel.getText().equals("Medium")) {
        imgName = "poker-face";
      } else {
        imgName = "sad-face";
      }

      imageUrl = getClass().getResource("/com/example/bombland/Images/" + imgName + ".png");
      newImage = new Image(imageUrl.toString());
      mapImgView.setImage(newImage);

      if (AppCache.getInstance().getMultiplayerRoom().has("player2Name")) {
        sendNewSettingToPlayer2("map", imgName);
      }
    }
  }

  /**
   * When Player1 makes a change to the settings, a message gets sent to the server, and from
   * there to Player2.
   *
   * @param setting map or difficulty
   * @param value the map or difficulty chosen
   */
  @FXML
  private void sendNewSettingToPlayer2(String setting, String value) {
    JSONObject info = new JSONObject();
    info.put("setting", setting);
    info.put("value", value);
    Main.socketClient.updateP2Ui(info);
  }

  /**
   * When Player1 changes the map or difficulty, this function will update Player2's screen to
   * reflect those changes.
   *
   * @param settingsInfo an object that stores the specific setting that was changed, and the
   *                    value it was set to.
   */
  @FXML
  void updatePlayer2SettingsUi(JSONObject settingsInfo) {
    URL imageUrl;

    if (settingsInfo.get("setting").equals("map")) {
      if (settingsInfo.get("value").equals("rectangle")) {
        imageUrl = getClass().getResource("/com/example/bombland/Images/rectangle.png");
        mapNameLabel.setText("Rectangle");
      } else if (settingsInfo.get("value").equals("bomb")) {
        imageUrl = getClass().getResource("/com/example/bombland/Images/bomb.png");
        mapNameLabel.setText("Bomb");
      } else if (settingsInfo.get("value").equals("flower")) {
        imageUrl = getClass().getResource("/com/example/bombland/Images/flower.png");
        mapNameLabel.setText("Flower");
      } else {
        if (settingsInfo.get("value").equals("smiley-face")) {
          imageUrl = getClass().getResource("/com/example/bombland/Images/smiley-face.png");
        } else if (settingsInfo.get("value").equals("poker-face")) {
          imageUrl = getClass().getResource("/com/example/bombland/Images/poker-face.png");
        } else {
          imageUrl = getClass().getResource("/com/example/bombland/Images/sad-face.png");
        }

        mapNameLabel.setText("Face");
      }

      Image newImage = new Image(imageUrl.toString());
      mapImgView.setImage(newImage);
    } else {
      if (settingsInfo.get("value").equals("Easy")) {
        imageUrl = getClass().getResource("/com/example/bombland/Images/easy-difficulty.png");
        difficultyNameLabel.setText("Easy");
      } else if (settingsInfo.get("value").equals("Medium")) {
        imageUrl = getClass().getResource("/com/example/bombland/Images/medium-difficulty.png");
        difficultyNameLabel.setText("Medium");
      } else {
        imageUrl = getClass().getResource("/com/example/bombland/Images/hard-difficulty.png");
        difficultyNameLabel.setText("Hard");
      }

      Image newImage = new Image(imageUrl.toString());
      difficultyImgView.setImage(newImage);
    }
  }

  /**
   * When Player2 leaves the room (with Player1 left in it), this function will run to update Player1's
   * UI, so they know that Player2 has left the room.
   */
  @FXML
  void removePlayer2FromRoom() {
    AppCache.getInstance().getMultiplayerRoom().remove("player2Name");

    totalPlayersLabel.setText("1 / 2");
    startGameButton.setDisable(true);
    removePlayer2Icon();
  }

  /**
   * This function displays a message to Player2 to let them know that they're being kicked out of
   * the room because Player1 exited the room.
   */
  @FXML
  void kickOutPlayer2FromRoom() {
    totalPlayersLabel.setText("1 / 2");

    removePlayer1Icon();

    topPageOuterContainer.setEffect(new GaussianBlur()); // blurs room page
    topPageOuterContainer.setMouseTransparent(true); // makes items in room page "unclickable"

    kickOutPlayer2Popup.setManaged(true);
    kickOutPlayer2Popup.setVisible(true);

    kickOutPlayer2Popup.setMaxWidth(Main.mainStage.widthProperty().get() * 0.33);
    kickOutPlayer2Popup.setMaxHeight(Main.mainStage.heightProperty().get() * 0.4);

    kickOutPlayer2Popup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;  -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    kickOutPlayer2PopupTitle.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.03) + "px;");

    kickOutPlayer2PopupImgContainer.setStyle("-fx-pref-height: " + (Main.mainStage.getHeight() * 0.1) + "px;");
    kickOutPlayer2PopupImg.setFitWidth(Main.mainStage.getWidth() * 0.1);
    kickOutPlayer2PopupImg.setFitHeight(Main.mainStage.getWidth() * 0.1);

    kickOutPlayer2PopupErrorMsg.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-padding: %.2fpx 0 %.2fpx 0; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01),
            kickOutPlayer2Popup.widthProperty().multiply(1))
    );

    kickOutPlayer2PopupErrorMsg.setTextAlignment(TextAlignment.CENTER); // Center each line of text

    kickOutPlayer2PopupCloseButton.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-background-radius: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.31),
            Main.mainStage.widthProperty().multiply(0.02),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.011))
    );
  }

  /**
   * Removes Player1's icon (from the UI).
   */
  void removePlayer1Icon() {
    player1BoxContainer.setVisible(false);
    player1BoxContainer.setManaged(false);

    playerProfileSpace.setVisible(false);
    playerProfileSpace.setManaged(false);
  }

  /**
   * Removes Player2's icon (from the UI).
   */
  void removePlayer2Icon() {
    playerProfileSpace.setVisible(false);
    playerProfileSpace.setManaged(false);

    player2BoxContainer.setVisible(false);
    player2BoxContainer.setManaged(false);
  }

  /**
   * When the START GAME button is clicked, this function displays a popup message that asks Player1
   * if they're sure they'd like to start the game.
   */
  @FXML
  void displayStartGamePopup() {
    topPageOuterContainer.setEffect(new GaussianBlur()); // blurs room page
    topPageOuterContainer.setMouseTransparent(true); // makes items in room page "unclickable"

    startGamePopup.setManaged(true);
    startGamePopup.setVisible(true);

    startGamePopup.setMaxWidth(Main.mainStage.widthProperty().get() * 0.33);
    startGamePopup.setMaxHeight(Main.mainStage.heightProperty().get() * 0.475);

    startGamePopup.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;  -fx-border-radius: %.2fpx; -fx-border-width: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.005),
            Main.mainStage.widthProperty().multiply(0.0015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    startGamePopupTitle.setStyle("-fx-font-size: " + (Main.mainStage.getWidth() * 0.04) + "px;");

    startGamePopupImgContainer.setStyle("-fx-pref-height: " + (Main.mainStage.getHeight() * 0.1) + "px;");
    startGamePopupImg.setFitWidth(Main.mainStage.getWidth() * 0.1);
    startGamePopupImg.setFitHeight(Main.mainStage.getWidth() * 0.1);


    startGamePopupMsg.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-padding: %.2fpx 0 %.2fpx 0;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    startGamePopupMsg.setTextAlignment(TextAlignment.CENTER); // Center each line of text


    startGamePopupButtonsContainer.setSpacing(Main.mainStage.getWidth() * 0.05);

    startGamePopupYesBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.0125),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
    startGamePopupNoBtn.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.0125),
            Main.mainStage.widthProperty().multiply(0.05),
            Main.mainStage.widthProperty().multiply(0.15))
    );
  }

  /**
   * Closes the START GAME popup.
   */
  @FXML
  void closeStartGamePopup() {
    startGamePopup.setManaged(false);
    startGamePopup.setVisible(false);
    topPageOuterContainer.setEffect(null);
    topPageOuterContainer.setMouseTransparent(false);
  }

  /**
   * If the current player is Player1, a msg gets sent to Player2 about the map settings, and they
   * get sent to the Game map. If the current player is Player2, they just get sent to the Game map.
   */
  @FXML
  void startGame() {
    if (isPlayer1()) {
      String map = mapNameLabel.getText();
      String difficulty = difficultyNameLabel.getText();
      AppCache.getInstance().setGameMap(map);
      AppCache.getInstance().setGameDifficulty(difficulty);

      JSONObject gameMapInfo = new JSONObject();
      gameMapInfo.put("roomId", AppCache.getInstance().getMultiplayerRoom().get("id"));
      gameMapInfo.put("map", map);
      gameMapInfo.put("difficulty", difficulty);

      Main.socketClient.sendPlayer2ToGameMap(gameMapInfo);
    }

    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/multiplayer-play-view.fxml")
    );

    MultiplayerPlayController multiplayerPlayController = MultiplayerPlayController.getInstance();
    loader.setController(multiplayerPlayController);

    try {
      Scene scene = new Scene(loader.load());
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - RoomController.startGame(): Could not open the game map.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }
}