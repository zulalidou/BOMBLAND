package com.example.bombland;

import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * This class sets up the UI for the Instructions page.
 */
public class InstructionsController {
  private static InstructionsController instance;

  @FXML
  VBox instructionsPage;

  @FXML
  Button backBtn;

  @FXML
  Label instructionsPageTitle;

  @FXML
  HBox instructionsContainerLeftChild;

  @FXML
  HBox instructionsContainerMiddleChild;

  @FXML
  HBox instructionsContainerRightChild;

  @FXML
  ScrollPane instructionsScrollPane;

  @FXML
  Label section1TitleLbl;

  @FXML
  VBox imgContainer1;

  @FXML
  VBox imgContainer2;

  @FXML
  VBox imgContainer3;

  @FXML
  VBox imgContainer4;

  @FXML
  VBox imgContainer5;

  @FXML
  VBox imgContainer6;

  @FXML
  VBox imgContainer7;

  @FXML
  VBox imgContainer8;

  @FXML
  ImageView imgView1;

  @FXML
  ImageView imgView2;

  @FXML
  ImageView imgView3;

  @FXML
  ImageView imgView4;

  @FXML
  ImageView imgView5;

  @FXML
  ImageView imgView6;

  @FXML
  ImageView imgView7;

  @FXML
  ImageView imgView8;

  @FXML
  Region space1;

  @FXML
  Region space2;

  @FXML
  Region space3;

  @FXML
  Region space4;


  private InstructionsController() {}

  /**
   * This function creates an instance of this class.
   *
   * @return An instance of this class.
   */
  public static InstructionsController getInstance() {
    if (instance == null) {
      instance = new InstructionsController();
    }

    return instance;
  }

  /**
   * Creates and populates the Instructions page.
   */
  @FXML
  public void initialize() {
    instructionsPage.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx;", Main.mainStage.widthProperty().multiply(0.02))
    );


    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;",
            Main.mainStage.widthProperty().multiply(0.05))
    );

    instructionsPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.04))
    );

    instructionsContainerLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.33))
    );

    instructionsContainerMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx", Main.mainStage.widthProperty().multiply(0.34))
    );

    instructionsContainerRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", Main.mainStage.widthProperty().multiply(0.33))
    );


    instructionsScrollPane.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx;", Main.mainStage.widthProperty().multiply(0.02))
    );

    // Makes sure the child of the scrollpane has the same width as the scrollpane
    instructionsScrollPane.setFitToWidth(true);
    VBox.setVgrow(instructionsScrollPane, Priority.ALWAYS);


    section1TitleLbl.styleProperty().bind(
            Bindings.format("-fx-font-size: %.2fpx;", Main.mainStage.widthProperty().multiply(0.03))
    );


    imgContainer1.styleProperty().bind(
            Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx;",
                Main.mainStage.widthProperty().multiply(0.01),
                Main.mainStage.widthProperty().multiply(0.4))
    );
    imgContainer2.styleProperty().bind(
            Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx;",
                Main.mainStage.widthProperty().multiply(0.01),
                Main.mainStage.widthProperty().multiply(0.4))
    );
    imgContainer3.styleProperty().bind(
            Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx;",
                Main.mainStage.widthProperty().multiply(0.01),
                Main.mainStage.widthProperty().multiply(0.4))
    );
    imgContainer4.styleProperty().bind(
            Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx;",
                Main.mainStage.widthProperty().multiply(0.01),
                Main.mainStage.widthProperty().multiply(0.4))
    );
    imgContainer5.styleProperty().bind(
            Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx;",
                Main.mainStage.widthProperty().multiply(0.01),
                Main.mainStage.widthProperty().multiply(0.4))
    );
    imgContainer6.styleProperty().bind(
            Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx;",
                Main.mainStage.widthProperty().multiply(0.01),
                Main.mainStage.widthProperty().multiply(0.4))
    );
    imgContainer7.styleProperty().bind(
            Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx;",
                Main.mainStage.widthProperty().multiply(0.01),
                Main.mainStage.widthProperty().multiply(0.4))
    );
    imgContainer8.styleProperty().bind(
            Bindings.format("-fx-padding: %.2fpx; -fx-pref-width: %.2fpx;",
                Main.mainStage.widthProperty().multiply(0.01),
                Main.mainStage.widthProperty().multiply(0.4))
    );

    imgView1.setFitWidth(Main.mainStage.getWidth() * 0.35);
    imgView1.setFitHeight(Main.mainStage.getHeight() * 0.25);
    imgView2.setFitWidth(Main.mainStage.getWidth() * 0.35);
    imgView2.setFitHeight(Main.mainStage.getHeight() * 0.25);
    imgView3.setFitWidth(Main.mainStage.getWidth() * 0.35);
    imgView3.setFitHeight(Main.mainStage.getHeight() * 0.25);
    imgView4.setFitWidth(Main.mainStage.getWidth() * 0.35);
    imgView4.setFitHeight(Main.mainStage.getHeight() * 0.25);
    imgView5.setFitWidth(Main.mainStage.getWidth() * 0.35);
    imgView5.setFitHeight(Main.mainStage.getHeight() * 0.25);
    imgView6.setFitWidth(Main.mainStage.getWidth() * 0.35);
    imgView6.setFitHeight(Main.mainStage.getHeight() * 0.25);
    imgView7.setFitWidth(Main.mainStage.getWidth() * 0.35);
    imgView7.setFitHeight(Main.mainStage.getHeight() * 0.25);
    imgView8.setFitWidth(Main.mainStage.getWidth() * 0.35);
    imgView8.setFitHeight(Main.mainStage.getHeight() * 0.25);

    HBox.setHgrow(space1, Priority.ALWAYS); // Makes the space element expand
    HBox.setHgrow(space2, Priority.ALWAYS);
    HBox.setHgrow(space3, Priority.ALWAYS);
    HBox.setHgrow(space4, Priority.ALWAYS);
  }

  @FXML
  private void goToMainMenu(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/example/bombland/FXML/main-view.fxml")
    );

    MainController mainController = MainController.getInstance();
    loader.setController(mainController);

    Scene scene = new Scene(loader.load(), 1024, 768);
    Main.mainStage.setScene(scene);
    Main.mainStage.show();
  }
}