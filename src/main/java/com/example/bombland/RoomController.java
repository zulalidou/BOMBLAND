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
  Button readyButton;

  @FXML
  Region buttonSpace;

  @FXML
  Button startGameButton;

  @FXML
  VBox playersContainer;

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
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.05))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.05))
    );

    emptySpace1.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.3))
    );

    roomNameLabel.setText(roomInfo.get("name").toString());

    roomNameLabel.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.3),
            Main.mainStage.widthProperty().multiply(0.035))
    );

    emptySpace2.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.3))
    );


    middlePageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-height: %.2fpx;", Main.mainStage.heightProperty().multiply(0.82))
    );

    settingsContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.5))
    );

    settingsContainerTitleLabel.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.5),
            Main.mainStage.widthProperty().multiply(0.025))
    );

    sectionSpace1.prefHeightProperty().bind(settingsContainer.widthProperty().multiply(0.04));

    selectMapLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015))
    );

    mapContainerSpace1.prefWidthProperty().bind(mapContainerInner.widthProperty().multiply(0.025));
    mapContainerSpace2.prefWidthProperty().bind(mapContainerInner.widthProperty().multiply(0.025));

    mapBox.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
                + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.1),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    mapNameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.015))
    );

    selectDifficultyLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015))
    );

    sectionSpace2.prefHeightProperty().bind(settingsContainer.widthProperty().multiply(0.04));

    difficultyContainerSpace1.prefWidthProperty().bind(difficultyContainerInner.widthProperty().multiply(0.025));
    difficultyContainerSpace2.prefWidthProperty().bind(difficultyContainerInner.widthProperty().multiply(0.025));

    difficultyBox.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx; "
                + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.01),
            Main.mainStage.widthProperty().multiply(0.1),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    difficultyNameLabel.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.015))
    );

    sectionSpace3.prefHeightProperty().bind(settingsContainer.widthProperty().multiply(0.04));


    readyButton.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.015),
            Main.mainStage.widthProperty().multiply(0.01))
    );

    buttonSpace.prefHeightProperty().bind(settingsContainer.widthProperty().multiply(0.01));

    startGameButton.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx; -fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.01))
    );


    playersContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.5))
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