package com.example.bombland;

import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class sets up the UI for the Main menu page.
 */
public class MainController {
  private static MainController instance;

  @FXML
  VBox mainMenuPage;

  @FXML
  VBox mainMenuOptionsContainer;

  @FXML
  HBox logoContainer;


  @FXML
  Text logoTextBeforeO;

  @FXML
  ImageView bombImgView;

  @FXML
  Text logoTextAfterO;

  @FXML
  Button playBtn;

  @FXML
  Button instructionsBtn;

  @FXML
  Button highScoresBtn;

  @FXML
  Button exitBtn;

  private MainController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return An instance of this class.
   */
  public static MainController getInstance() {
    if (instance == null) {
      instance = new MainController();
    }

    return instance;
  }

  /**
   * Creates and populates the Main menu page.
   */
  @FXML
  public void initialize() {
    // Prevents the width of the mainMenuOptionsContainer VBox
    // from having the same width as its parent container (mainMenuPage)
    mainMenuPage.setFillWidth(false);

    mainMenuOptionsContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.75),
            Main.mainStage.heightProperty().multiply(0.75))
    );


    logoTextBeforeO.styleProperty().bind(
        // Sets the font size to be 9% of the app window's width
        Bindings.format("-fx-font-size: %.2fpx; -fx-font-weight: bold;",
            Main.mainStage.widthProperty().multiply(0.045))
    );

    bombImgView.fitHeightProperty().bind(Bindings.min(
        logoContainer.heightProperty().multiply(0.8),
        logoContainer.widthProperty().multiply(1)
    ));

    logoTextAfterO.styleProperty().bind(
        // Sets the font size to be 9% of the app window's width
        Bindings.format("-fx-font-size: %.2fpx; -fx-font-weight: bold;",
            Main.mainStage.widthProperty().multiply(0.045))
    );


    playBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.4),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.1))
    );

    instructionsBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.4),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.1))
    );

    highScoresBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.4),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.1))
    );

    exitBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.4),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.1))
    );
  }

  @FXML
  private void openDifficultySelectionPage(ActionEvent event) {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/difficulty-selection-view.fxml")
    );

    DifficultySelectionController difficultyController = DifficultySelectionController.getInstance();
    loader.setController(difficultyController);

    try {
      Scene scene = new Scene(loader.load(), 1024, 768);
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - openDifficultySelectionPage(): Could not open the difficulty selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  @FXML
  private void openInstructionsPage(ActionEvent event) {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/instructions-view.fxml")
    );

    InstructionsController instructionsController = InstructionsController.getInstance();
    loader.setController(instructionsController);

    try {
      Scene scene = new Scene(loader.load(), 1024, 768);
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - openInstructionsPage(): Could not open the instructions page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  @FXML
  private void openHighScoresPage(ActionEvent event) {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/high-scores-view.fxml")
    );

    HighScoresController highScoresController = HighScoresController.getInstance();
    loader.setController(highScoresController);

    try {
      Scene scene = new Scene(loader.load(), 1024, 768);
      Main.mainStage.setScene(scene);
      Main.mainStage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - openHighScoresPage(): Could not open the high scores page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  @FXML
  private void closeApp() {
    Platform.exit();
  }
}