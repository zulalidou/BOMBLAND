package com.example.bombland;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
            mainMenuPage.widthProperty().multiply(0.75),
            mainMenuPage.heightProperty().multiply(0.75))
    );

    logoTextBeforeO.styleProperty().bind(
        // Sets the font size to be 4.5% of the app window's width
        Bindings.format("-fx-font-size: %.2fpx;",
            logoContainer.widthProperty().multiply(0.045))
    );

    Image bombImg = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/bomb.png"))
    );
    bombImgView.setImage(bombImg);

    bombImgView.fitHeightProperty().bind(Bindings.min(
        logoContainer.heightProperty().multiply(0.8),
        logoContainer.widthProperty().multiply(1)
    ));

    logoTextAfterO.styleProperty().bind(
        // Sets the font size to be 4.5% of the app window's width
        Bindings.format("-fx-font-size: %.2fpx;",
            logoContainer.widthProperty().multiply(0.045))
    );

    playBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            mainMenuOptionsContainer.widthProperty().multiply(0.55),
            mainMenuOptionsContainer.widthProperty().multiply(0.04),
            mainMenuOptionsContainer.widthProperty().multiply(0.2))
    );

    instructionsBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            mainMenuOptionsContainer.widthProperty().multiply(0.55),
            mainMenuOptionsContainer.widthProperty().multiply(0.04),
            mainMenuOptionsContainer.widthProperty().multiply(0.2))
    );

    highScoresBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            mainMenuOptionsContainer.widthProperty().multiply(0.55),
            mainMenuOptionsContainer.widthProperty().multiply(0.04),
            mainMenuOptionsContainer.widthProperty().multiply(0.2))
    );

    exitBtn.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-font-size: %.2fpx; "
            + "-fx-background-radius: %.2fpx;",
            mainMenuOptionsContainer.widthProperty().multiply(0.55),
            mainMenuOptionsContainer.widthProperty().multiply(0.04),
            mainMenuOptionsContainer.widthProperty().multiply(0.2))
    );
  }

  /**
   * Redirects the user to the Mode Selection page.
   */
  @FXML
  private void openModeSelectionPage() {
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
      System.out.println("ERROR - openModeSelectionPage(): Could not open the mode selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * Redirects the user to the Instructions page.
   */
  @FXML
  private void openInstructionsPage() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/instructions-view.fxml")
    );

    InstructionsController instructionsController = InstructionsController.getInstance();
    loader.setController(instructionsController);

    try {
      Scene scene = new Scene(loader.load());
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

  /**
   * Redirects the user to the High Scores page.
   */
  @FXML
  private void openHighScoresPage() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/high-scores-view.fxml")
    );

    HighScoresController highScoresController = HighScoresController.getInstance();
    loader.setController(highScoresController);

    try {
      Scene scene = new Scene(loader.load());
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

  /**
   * Closes the app.
   */
  @FXML
  private void closeApp() {
    Platform.exit();
  }
}