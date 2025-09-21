package com.example.bombland;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * This class implements the UI for the Host Game Settings page.
 */
public class HostGameSettingsController {
  private static HostGameSettingsController instance;

  @FXML
  Button backBtn;

  @FXML
  VBox hostGameSettingsPage;

  @FXML
  VBox hostGameSettingsPageContainer;

  @FXML
  VBox hostGameSettingsPageContainerBottom;

  @FXML
  HBox hostGameSettingsPageContainerTopLeftChild;

  @FXML
  HBox hostGameSettingsPageContainerTopMiddleChild;

  @FXML
  HBox hostGameSettingsPageContainerTopRightChild;

  @FXML
  Label hostGameSettingsPageTitle;

  @FXML
  Label serverNameText;

  @FXML
  TextField serverNameTextField;

  @FXML
  Label playerNameText;

  @FXML
  TextField playerNameTextField;

  @FXML
  Region space;

  @FXML
  Button changePlayerNameBtn;


  final ArrayList<String> COLORS = new ArrayList<>(Arrays.asList(
      "red", "green", "blue", "indigo",
      "orange", "yellow", "violet", "gray",
      "maroon", "black", "olive", "cyan",
      "pink", "magenta", "tan", "teal"
  ));

  final ArrayList<String> ADJECTIVES = new ArrayList<>(Arrays.asList(
      "angry", "big", "clever", "sweaty",
      "clumsy", "tough", "deadly", "fancy",
      "hairy", "happy", "bold", "nice",
      "noisy", "odd", "quiet", "smart"
  ));

  final ArrayList<String> ANIMALS = new ArrayList<>(Arrays.asList(
      "dog", "cat", "horse", "lion",
      "tiger", "snake", "deer", "bear",
      "wolf", "goat", "koala", "giraffe",
      "turtle", "shark", "otter", "panda"
  ));

  final ArrayList<String> NAMES = new ArrayList<>(Arrays.asList(
      "anna", "alex", "sam", "james",
      "john", "toby", "tom", "noah",
      "emma", "amelia", "charlotte", "sophie",
      "isabella", "henry", "nina", "marc"
  ));


  private HostGameSettingsController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return an instance of this class.
   */
  public static HostGameSettingsController getInstance() {
    if (instance == null) {
      instance = new HostGameSettingsController();
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
    hostGameSettingsPage.setFillWidth(false);


    hostGameSettingsPageContainer.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-padding: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.75),
            Main.mainStage.heightProperty().multiply(0.6),
            Main.mainStage.widthProperty().multiply(0.02)
        )
    );

    hostGameSettingsPageContainerTopLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.05)
        )
    );

    hostGameSettingsPageContainerTopMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.6))
    );

    hostGameSettingsPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    hostGameSettingsPageContainerTopRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.2))
    );


    VBox.setVgrow(hostGameSettingsPageContainerBottom, Priority.ALWAYS);


    serverNameTextField.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx; -fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.3),
            Main.mainStage.widthProperty().multiply(0.03),
            Main.mainStage.widthProperty().multiply(0.015))
    );

    serverNameText.styleProperty().bind(
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

    playerNameText.styleProperty().bind(
        Bindings.format(
            "-fx-font-size: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.03)
        )
    );

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

    setPlayerName();
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
      System.out.println("ERROR - goToMultiplayerSelection(): Could not return to the mode selection page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  @FXML
  private void setPlayerName() {
    String firstHalf = getFirstHalfOfName();
    String secondHalf = getSecondHalfOfName();
    playerNameTextField.setText(firstHalf + "-" + secondHalf);
  }

  private String getFirstHalfOfName() {
    Random random = new Random();
    int randomNum = random.nextInt(2);
    int randomIdx = random.nextInt(16);

    if (randomNum == 0) {
      return COLORS.get(randomIdx);
    } else {
      return ADJECTIVES.get(randomIdx);
    }
  }

  private String getSecondHalfOfName() {
    Random random = new Random();
    int randomNum = random.nextInt(2);
    int randomIdx = random.nextInt(16);

    if (randomNum == 0) {
      return ANIMALS.get(randomIdx);
    } else {
      return NAMES.get(randomIdx);
    }
  }
}