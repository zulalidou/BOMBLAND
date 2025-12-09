package com.example.bombland;

import java.io.IOException;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
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
  HBox instructionsContainerTop;

  @FXML
  HBox instructionsContainerLeftChild;

  @FXML
  Button backBtn;

  @FXML
  ImageView backBtnImgView;

  @FXML
  HBox instructionsContainerMiddleChild;

  @FXML
  Label instructionsPageTitle;

  @FXML
  HBox instructionsContainerRightChild;

  @FXML
  HBox instructionsContainerBottom;

  @FXML
  ScrollPane instructionsScrollPane;

  @FXML
  Label section1TitleLbl;

  @FXML
  VBox imgContainer1;

  @FXML
  ImageView imgView1;

  @FXML
  Region space1;

  @FXML
  VBox imgContainer2;

  @FXML
  ImageView imgView2;

  @FXML
  VBox imgContainer3;

  @FXML
  ImageView imgView3;

  @FXML
  Region space2;

  @FXML
  VBox imgContainer4;

  @FXML
  ImageView imgView4;

  @FXML
  VBox imgContainer5;

  @FXML
  ImageView imgView5;

  @FXML
  Region space3;

  @FXML
  VBox imgContainer6;

  @FXML
  ImageView imgView6;

  @FXML
  VBox imgContainer7;

  @FXML
  ImageView imgView7;

  @FXML
  Region space4;

  @FXML
  VBox imgContainer8;

  @FXML
  ImageView imgView8;


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
    instructionsContainerTop.setPrefHeight(Main.mainStage.getHeight() * 0.1);

    // In some screen resolutions the page header/title glitches, so the line below prevents that
    instructionsContainerTop.setMinHeight(Main.mainStage.getHeight() * 0.1);

    instructionsContainerLeftChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", instructionsContainerTop.widthProperty().multiply(0.33))
    );

    backBtn.styleProperty().bind(
        Bindings.format("-fx-background-radius: %.2fpx;", instructionsContainerLeftChild.widthProperty().multiply(0.1))
    );

    backBtnImgView.fitWidthProperty().bind(instructionsContainerLeftChild.widthProperty().multiply(0.075));
    backBtnImgView.fitHeightProperty().bind(instructionsContainerLeftChild.heightProperty().multiply(0.4));

    Image backBtnImg = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/back-button.png"))
    );
    backBtnImgView.setImage(backBtnImg);

    instructionsContainerMiddleChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx; -fx-pref-height: %.2fpx",
            instructionsContainerTop.widthProperty().multiply(0.34),
            instructionsContainerTop.heightProperty().multiply(1))
    );

    instructionsPageTitle.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", instructionsContainerMiddleChild.widthProperty().multiply(0.1))
    );

    instructionsContainerRightChild.styleProperty().bind(
        Bindings.format("-fx-pref-width: %.2fpx;", instructionsContainerTop.widthProperty().multiply(0.33))
    );

    VBox.setVgrow(instructionsContainerBottom, Priority.ALWAYS);

    instructionsScrollPane.styleProperty().bind(
        Bindings.format("-fx-padding: %.2fpx;", instructionsContainerBottom.widthProperty().multiply(0.02))
    );

    // Makes sure the child of the scroll pane has the same width as the scroll pane
    instructionsScrollPane.setFitToWidth(true);

    section1TitleLbl.styleProperty().bind(
        Bindings.format("-fx-font-size: %.2fpx;", instructionsScrollPane.widthProperty().multiply(0.025))
    );

    imgContainer1.setPrefWidth(Main.mainStage.getWidth() * (0.45));
    imgContainer2.setPrefWidth(Main.mainStage.getWidth() * (0.45));
    imgContainer3.setPrefWidth(Main.mainStage.getWidth() * (0.45));
    imgContainer4.setPrefWidth(Main.mainStage.getWidth() * (0.45));
    imgContainer5.setPrefWidth(Main.mainStage.getWidth() * (0.45));
    imgContainer6.setPrefWidth(Main.mainStage.getWidth() * (0.45));
    imgContainer7.setPrefWidth(Main.mainStage.getWidth() * (0.45));
    imgContainer8.setPrefWidth(Main.mainStage.getWidth() * (0.45));

    imgView1.fitWidthProperty().bind(imgContainer1.widthProperty().multiply(0.5));
    imgView1.setPreserveRatio(true);
    imgView2.fitWidthProperty().bind(imgContainer2.widthProperty().multiply(0.5));
    imgView2.setPreserveRatio(true);
    imgView3.fitWidthProperty().bind(imgContainer3.widthProperty().multiply(0.5));
    imgView3.setPreserveRatio(true);
    imgView4.fitWidthProperty().bind(imgContainer4.widthProperty().multiply(0.5));
    imgView4.setPreserveRatio(true);
    imgView5.fitWidthProperty().bind(imgContainer5.widthProperty().multiply(0.5));
    imgView5.setPreserveRatio(true);
    imgView6.fitWidthProperty().bind(imgContainer6.widthProperty().multiply(0.5));
    imgView6.setPreserveRatio(true);
    imgView7.fitWidthProperty().bind(imgContainer7.widthProperty().multiply(0.5));
    imgView7.setPreserveRatio(true);
    imgView8.fitWidthProperty().bind(imgContainer8.widthProperty().multiply(0.5));
    imgView8.setPreserveRatio(true);

    Image img1 = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/map-theme-1/img1.png"))
    );
    imgView1.setImage(img1);

    Image img2 = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/map-theme-1/img2.png"))
    );
    imgView2.setImage(img2);

    Image img3 = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/map-theme-1/img3.png"))
    );
    imgView3.setImage(img3);

    Image img4 = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/map-theme-1/img4.png"))
    );
    imgView4.setImage(img4);

    Image img5 = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/map-theme-1/img5.png"))
    );
    imgView5.setImage(img5);

    Image img6 = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/map-theme-1/img6.png"))
    );
    imgView6.setImage(img6);

    Image img7 = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/map-theme-1/img7.png"))
    );
    imgView7.setImage(img7);

    Image img8 = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/map-theme-1/img8.png"))
    );
    imgView8.setImage(img8);

    HBox.setHgrow(space1, Priority.ALWAYS); // Makes the space element expand
    HBox.setHgrow(space2, Priority.ALWAYS);
    HBox.setHgrow(space3, Priority.ALWAYS);
    HBox.setHgrow(space4, Priority.ALWAYS);
  }

  /**
   * Redirects the user to the Main Menu page.
   */
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
      System.out.println("ERROR - InstructionsController.goToMainMenu(): Could not return to the main menu page.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }
}