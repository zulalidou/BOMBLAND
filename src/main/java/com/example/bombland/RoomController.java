package com.example.bombland;

import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
  VBox emptySpace1;

  @FXML
  Label roomNameLabel;

  @FXML
  VBox emptySpace2;

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
  Region mapContainerSpace1;

  @FXML
  VBox mapBox;

  @FXML
  Label mapNameLabel;

  @FXML
  Region mapContainerSpace2;

  @FXML
  Region sectionSpace2;

  @FXML
  VBox difficultyContainer;

  @FXML
  Label selectDifficultyLabel;

  @FXML
  HBox difficultyContainerInner;

  @FXML
  Region difficultyContainerSpace1;

  @FXML
  VBox difficultyBox;

  @FXML
  Label difficultyNameLabel;

  @FXML
  Region difficultyContainerSpace2;

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
  Label playerNameLabel;

  @FXML
  Label player1ReadyStateLabel;

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
    roomInfo = AppCache.getInstance().getRoomInfo();
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
    backBtnContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", topPageContainer.widthProperty().multiply(0.05))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            backBtnContainer.widthProperty().multiply(0.2))
    );

    emptySpace1.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", topPageContainer.widthProperty().multiply(0.3))
    );

    roomNameLabel.setText(roomInfo.get("name").toString());

    roomNameLabel.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx;",
            topPageContainer.widthProperty().multiply(0.3),
            topPageContainer.widthProperty().multiply(0.035))
    );

    emptySpace2.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", topPageContainer.widthProperty().multiply(0.3))
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

    playerNameLabel.setText(roomInfo.get("creator").toString());

    playerNameLabel.styleProperty().bind(
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
}