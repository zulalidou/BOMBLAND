package com.example.bombland;

import java.io.IOException;
import java.net.URL;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

/**
 * This class sets up the UI for the Room page.
 */
public class RoomController {
  private static RoomController instance;

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
  VBox buttonsContainer;

  @FXML
  Button readyButton;

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
  Label player1ReadyStateLabel;

  @FXML
  Region playerProfileSpace;

  @FXML
  VBox player2BoxContainer;

  @FXML
  VBox player2Box;

  @FXML
  Label player2NameLabel;

  @FXML
  Label player2ReadyStateLabel;

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

  JSONObject roomInfo;


  private RoomController() {
    roomInfo = AppCache.getInstance().getMultiplayerRoom();
  }

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
    String currentPlayerName = AppCache.getInstance().getPlayerName();
    String player1Name = AppCache.getInstance().getMultiplayerRoom().getString("player1");

    if (currentPlayerName.equals(player1Name)) {
      displayPlayer1Layout();
    } else {
      displayPlayer2Layout();
    }
  }

  private void displayPlayer1Layout() {
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

    roomIdLabel.setText(roomInfo.get("id").toString());

    roomIdLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.02))
    );

    roomNameContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", topPageContainer.widthProperty().multiply(0.3))
    );

    roomNameTitleLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.01))
    );

    roomNameLabel.setText(roomInfo.get("name").toString());

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


    readyButton.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            buttonsContainer.widthProperty().multiply(0.025),
            buttonsContainer.widthProperty().multiply(0.01))
    );

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

    player1NameLabel.setText(AppCache.getInstance().getMultiplayerRoom().get("player1").toString());

    player1NameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            player1Box.widthProperty().multiply(1))
    );

    player1ReadyStateLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.01))
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
  }

  private void displayPlayer2Layout() {
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

    roomIdLabel.setText(roomInfo.get("id").toString());

    roomIdLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.02))
    );

    roomNameContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", topPageContainer.widthProperty().multiply(0.3))
    );

    roomNameTitleLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.01))
    );

    roomNameLabel.setText(roomInfo.get("name").toString());

    roomNameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.02))
    );

    totalPlayersContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", topPageContainer.widthProperty().multiply(0.3))
    );

    totalPlayersTitleLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", topPageContainer.widthProperty().multiply(0.01))
    );

    totalPlayersLabel.setText("2 / 2");

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

    leftMapBtn.setVisible(false);
    leftMapBtn.setManaged(false);
    rightMapBtn.setVisible(false);
    rightMapBtn.setManaged(false);


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

    leftDifficultyBtn.setVisible(false);
    leftDifficultyBtn.setManaged(false);
    rightDifficultyBtn.setVisible(false);
    rightDifficultyBtn.setManaged(false);

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


    readyButton.setDisable(false);

    readyButton.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            buttonsContainer.widthProperty().multiply(0.025),
            buttonsContainer.widthProperty().multiply(0.01))
    );


    buttonSpace.setVisible(false);
    buttonSpace.setManaged(false);
    startGameButton.setVisible(false);
    startGameButton.setManaged(false);


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

    player1NameLabel.setText(AppCache.getInstance().getMultiplayerRoom().get("player1").toString());

    player1NameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            player1Box.widthProperty().multiply(1))
    );

    player1ReadyStateLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.01))
    );

    displayPlayer2Icon();


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
  }

  /**
   * Displays the player icon for Player 2.
   */
  private void displayPlayer2Icon() {
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

    player2NameLabel.setText(AppCache.getInstance().getMultiplayerRoom().get("player2").toString());

    player2NameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            player1Box.widthProperty().multiply(1))
    );

    player2ReadyStateLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.01))
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
      System.out.println("ERROR - RoomController.goToMainMenu(): Could not return to the main menu page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * When the button to the left of the map is clicked, it updates the UI to reflect the
   * change made.
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

    if (roomInfo.has("player2")) {
      sendNewSettingToPlayer2("map", newMap);
    }
  }

  /**
   * When the button to the right of the map is clicked, it updates the UI to reflect the
   * change made.
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

    if (roomInfo.has("player2")) {
      sendNewSettingToPlayer2("map", newMap);
    }
  }

  /**
   * When the button to the left of the difficulty is clicked, it updates the UI to reflect the
   * change made.
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

    if (roomInfo.has("player2")) {
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

      if (roomInfo.has("player2")) {
        sendNewSettingToPlayer2("map", imgName);
      }
    }
  }

  /**
   * When the button to the right of the difficulty is clicked, it updates the UI to reflect the
   * change made.
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

    if (roomInfo.has("player2")) {
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

      if (roomInfo.has("player2")) {
        sendNewSettingToPlayer2("map", imgName);
      }
    }
  }

  /**
   * Whenever one player clicks the "READY/NOT READY" button, a signal gets sent to the other player
   * (via the server) to let them know of this change.
   */
  @FXML
  private void setReadyState() {
    String currentPlayerName = AppCache.getInstance().getPlayerName();
    String player1 = AppCache.getInstance().getMultiplayerRoom().get("player1").toString();
    boolean isPlayer1 = currentPlayerName.equals(player1);

    String playerState;

    if (isPlayer1) {
      if (player1ReadyStateLabel.getText().equals("NOT READY")) {
        player1ReadyStateLabel.setText("READY");
        playerState = "READY";
        readyButton.setText("NOT READY");
      } else {
        player1ReadyStateLabel.setText("NOT READY");
        playerState = "NOT READY";
        readyButton.setText("READY");
      }

      if (player1ReadyStateLabel.getText().equals("READY") && player2ReadyStateLabel.getText().equals("READY")) {
        startGameButton.setDisable(false);
      } else {
        startGameButton.setDisable(true);
      }
    } else {
      if (player2ReadyStateLabel.getText().equals("NOT READY")) {
        player2ReadyStateLabel.setText("READY");
        playerState = "READY";
        readyButton.setText("NOT READY");
      } else {
        player2ReadyStateLabel.setText("NOT READY");
        playerState = "NOT READY";
        readyButton.setText("READY");
      }
    }

    Main.socketClient.updatePlayerState(playerState);
  }

  /**
   * This function runs when a signal is received from the other player (via the server) about
   * a change in their READY state. It then updates the UI for the current player to reflect the
   * updated state.
   */
  @FXML
  void updateReadyState(boolean otherPlayerReady) {
    String currentPlayerName = AppCache.getInstance().getPlayerName();
    String player1 = AppCache.getInstance().getMultiplayerRoom().get("player1").toString();
    boolean isPlayer1 = currentPlayerName.equals(player1);

    if (isPlayer1) {
      if (otherPlayerReady) {
        player2ReadyStateLabel.setText("READY");
      } else {
        player2ReadyStateLabel.setText("NOT READY");
      }

      if (player1ReadyStateLabel.getText().equals("READY") && player2ReadyStateLabel.getText().equals("READY")) {
        startGameButton.setDisable(false);
      } else {
        startGameButton.setDisable(true);
      }
    } else {
      if (otherPlayerReady) {
        player1ReadyStateLabel.setText("READY");
      } else {
        player1ReadyStateLabel.setText("NOT READY");
      }
    }
  }

  /**
   * When Player2 joins the room, the UI on Player1's screen updates accordingly.
   */
  @FXML
  void playerJoinedRoom() {
    totalPlayersLabel.setText("2 / 2");
    readyButton.setDisable(false);
    displayPlayer2Icon();
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
      System.out.println("it's a map setting");

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
      System.out.println("it's a difficulty setting");

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
}