package com.example.bombland;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;

/**
 * This is the first/default class of the application. It establishes a websocket connection
 * with the server app, and sets up the UI for the Main menu page.
 */
public class Main extends Application {
  static Stage mainStage = null;
  static BomblandWebSocketClient socketClient = null;
  private ScheduledExecutorService taskScheduler = null;

  @Override
  public void start(Stage stage) {
    mainStage = stage;
    taskScheduler = Executors.newScheduledThreadPool(2);

    performBackgroundProcessing();

    Image icon = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/bombsmall.png"))
    );
    stage.getIcons().add(icon);
    stage.setTitle("BOMBLAND");
    stage.setResizable(false);

    showSplashScreen(stage);
  }

  @Override
  public void stop() {
    // Closing the entire app doesn't kill all threads,
    // so I'm explicitly having the executor service kill all tasks I queued in the background
    PlayController.getInstance().endTimer();
  }

  /**
   * This function calls the launch() method, which initializes the JavaFX runtime,
   * builds the app instance, calls lifecycle methods, handles command line arguments,
   * and manages the app's termination.
   *
   * @param args A list of arguments.
   */
  public static void main(String[] args) {
    launch();
  }

  /**
   * This function establishes a connection to the server app, and then retrieves high scores.
   */
  public void performBackgroundProcessing() {
    Task<Void> backgroundProcessingTask = new Task<>() {
      @Override
      protected Void call() {
        connectToWebSocketServer();


        AppCache.getInstance().setGettingData(true);

        getEnvironmentVariables();

        if (AppCache.getInstance().serverConnectionIsGood()) {
          DynamoDbClientUtil.getHighScores();
        } else {
          MainController.getInstance().displayServerErrorPopup();
        }

        AppCache.getInstance().setGettingData(false);

        return null;
      }
    };

    new Thread(backgroundProcessingTask).start();
  }

  /**
   * This function creates a task that runs every 30 mins. The purpose of that task is
   * to establish a websocket connection to the server app (assuming one doesn't already exist).
   */
  public void connectToWebSocketServer() {
    Runnable serverConnectionTask = () -> {
      System.out.println("connectToWebSocketServer()");

      if (socketClient == null) {
        try {
          socketClient = new BomblandWebSocketClient();
          socketClient.connectClient();
        } catch (URISyntaxException e) {
          System.out.println("\n==================================================");
          System.out.println("The URI provided in BomblandWebSocketClient() is not valid");
          System.out.println("==================================================\n");
        }
      }
    };

    taskScheduler.scheduleAtFixedRate(serverConnectionTask, 0, 30, TimeUnit.MINUTES);
  }

  /**
   * This function retrieves the environment variables.
   */
  public void getEnvironmentVariables() {
    System.out.println("getEnvironmentVariables()");

    HttpClient httpClient = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://bombland-server.onrender.com/webflux/get-environment-variables"))
            .GET()
            .build();

    try {
      // Send the request and get the response
      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      JSONObject environmentVarsObj = new JSONObject(response.body());
      System.out.println(environmentVarsObj + "\n\n");
      AppCache.getInstance().setIdentityPoolId(environmentVarsObj.getString("identityPoolID"));
    } catch (Exception e) {
      System.out.println("\ngetEnvironmentVariables() -- ERROR!");
      AppCache.getInstance().setServerConnectionGood(false);
    }
  }

  /**
   * This function sets up and displays the splash screen.
   *
   * @param stage The window that displays the GUI for the app.
   */
  public void showSplashScreen(Stage stage) {
    Text textBeforeO = new Text("B");
    textBeforeO.styleProperty().bind(
        // Sets the font size to be 9% of the app window's width
        Bindings.format("-fx-font-size: %.2fpx; -fx-font-weight: bold;",
            stage.widthProperty().multiply(0.09))
    );

    Image image = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/bomb.png"))
    );
    ImageView imageView = new ImageView(image);

    Text textAfterO = new Text("MBLAND");
    textAfterO.styleProperty().bind(
        // Sets the font size to be 9% of the app window's width
        Bindings.format("-fx-font-size: %.2fpx; -fx-font-weight: bold;",
            stage.widthProperty().multiply(0.09))
    );


    HBox logoContainer = new HBox(textBeforeO, imageView, textAfterO);
    logoContainer.setAlignment(javafx.geometry.Pos.CENTER);

    VBox splashScreen = new VBox(logoContainer);
    splashScreen.setAlignment(javafx.geometry.Pos.CENTER);

    Scene splashScene = new Scene(splashScreen, 1024, 768);
    stage.setScene(splashScene);
    stage.show();

    AnimationTimer timer = new AnimationTimer() {
      int i = 0;

      @Override
      public void handle(long now) {
        splashScreen.setStyle("-fx-background-color: rgb(" + i + ", 0, 0);");
        i++;

        if (i >= 255) {
          stop(); // Stop the AnimationTimer

          try {
              showMainMenu(stage);
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
        }
      }
    };

    timer.start();
  }

  /**
   * This function sets up and displays the main menu.
   *
   * @param stage The window that displays the GUI for the app.
   * @throws IOException If the main-view.fxml file is unreachable.
   */
  public void showMainMenu(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/main-view.fxml"));

    MainController mainController = MainController.getInstance();
    loader.setController(mainController);

    Scene scene = new Scene(loader.load(), 1024, 768);
    stage.setScene(scene);
    stage.show();

    /*
    System.out.println("\n- width = " + Main.mainStage.widthProperty());
    System.out.println("- height = " + Main.mainStage.heightProperty());
    System.out.println("- stage = " + Main.mainStage);
     */
  }
}
