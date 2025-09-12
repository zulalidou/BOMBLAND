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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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
    setScreenDimensions(stage);

    showSplashScreen(stage);
  }

  @Override
  public void stop() {
    // Closing the entire app doesn't kill all threads,
    // so I'm explicitly having the executor service kill all tasks I queued in the background

    if (taskScheduler != null) {
      taskScheduler.shutdown();
    }

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
  private void performBackgroundProcessing() {
    connectToWebSocketServer();
    connectToSqlDb();
    getHighScoresFromDynamoDb();
  }

  /**
   * This function creates a task that runs every 30 mins. The purpose of that task is
   * to establish a websocket connection to the server app (assuming one doesn't already exist).
   */
  private void connectToWebSocketServer() {
    Runnable serverConnectionTask = () -> {
      if (socketClient == null) {
        try {
          socketClient = new BomblandWebSocketClient();
          socketClient.connectClient();
        } catch (URISyntaxException e) {
          System.out.println("\n==================================================");
          System.out.println("The URI provided in BomblandWebSocketClient() is not valid");
          System.out.println("---");
          System.out.println(e.getCause());
          System.out.println("==================================================\n");
        }
      }
    };

    taskScheduler.scheduleAtFixedRate(serverConnectionTask, 0, 30, TimeUnit.MINUTES);
  }

  /**
   * This function creates a task that runs every 40 mins. The purpose of that task is
   * to see if there are any high scores stored locally that weren't stored in DynamoDB.
   * If that's the case, those high scores are now sent to DynamoDB.
   */
  private void connectToSqlDb() {
    Runnable sqlDbConnectionTask = () -> {
      try {
        if (AppCache.getInstance().getIdentityPoolId().isEmpty()) {
          getEnvironmentVariables();
        }

        SqliteDbHelper.createTable();
        SqliteDbHelper.sendDataFromSqliteToDynamoDb();
      } catch (Exception e) {
        System.out.println("\n==================================================");
        System.out.println("ERROR - connectToSqlDb(): Could not connect to the SQL database.");
        System.out.println("---");
        System.out.println(e.getCause());
        System.out.println("==================================================\n");
      }
    };

    taskScheduler.scheduleAtFixedRate(sqlDbConnectionTask, 0, 40, TimeUnit.MINUTES);
  }

  /**
   * This function creates a task that runs every 45 mins. The purpose of that task is
   * to connect to the DB and retrieve high scores.
   */
  private void getHighScoresFromDynamoDb() {
    Runnable dynamoDbConnectionTask = () -> {
      if (AppCache.getInstance().getIdentityPoolId().isEmpty()) {
        getEnvironmentVariables();
      }

      try {
        AppCache.getInstance().setGettingData(true);
        DynamoDbClientUtil.getHighScores();
        AppCache.getInstance().setHighScoresHaveBeenRetrieved();
      } catch (Exception e) {
        System.out.println("\n====================================================================");
        System.out.println("ERROR - Main.getHighScoresFromDynamoDb(): Could not save get the high scores from DynamoDB.");
        System.out.println("---");
        System.out.println(e.getCause());
        System.out.println("====================================================================\n");
      } finally {
        AppCache.getInstance().setGettingData(false);
      }
    };

    taskScheduler.scheduleAtFixedRate(dynamoDbConnectionTask, 0, 45, TimeUnit.MINUTES);
  }

  /**
   * This function retrieves the environment variables.
   */
  public static void getEnvironmentVariables() {
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
      AppCache.getInstance().setIdentityPoolId(environmentVarsObj.getString("identityPoolID"));
    } catch (Exception e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - getEnvironmentVariables(): Could not get environment variables.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  /**
   * This function retrieves the width and height of the screen.
   */
  private void setScreenDimensions(Stage stage) {
    Screen screen = Screen.getPrimary();
    Rectangle2D screenDimensions = screen.getVisualBounds();
    stage.setWidth(screenDimensions.getWidth());
    stage.setHeight(screenDimensions.getHeight());
  }

  /**
   * This function sets up and displays the splash screen.
   *
   * @param stage The window that displays the GUI for the app.
   */
  private void showSplashScreen(Stage stage) {
    Text textBeforeO = new Text("B");
    textBeforeO.styleProperty().bind(
        // Sets the font size to be 12% of the app window's width
        Bindings.format("-fx-font-size: %.2fpx; -fx-font-weight: bold;",
            stage.widthProperty().multiply(0.12))
    );

    Image image = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/com/example/bombland/Images/bomb.png"))
    );
    ImageView imageView = new ImageView(image);

    Text textAfterO = new Text("MBLAND");
    textAfterO.styleProperty().bind(
        // Sets the font size to be 12% of the app window's width
        Bindings.format("-fx-font-size: %.2fpx; -fx-font-weight: bold;",
            stage.widthProperty().multiply(0.12))
    );


    HBox logoContainer = new HBox(textBeforeO, imageView, textAfterO);
    logoContainer.setAlignment(javafx.geometry.Pos.CENTER);


    // Makes the bomb image responsive
    imageView.setPreserveRatio(true);
    imageView.fitHeightProperty().bind(Bindings.min(
        logoContainer.heightProperty().multiply(0.8),
        logoContainer.widthProperty().multiply(0.5)
    ));


    VBox splashScreen = new VBox(logoContainer);
    splashScreen.setAlignment(javafx.geometry.Pos.CENTER);

    Scene splashScene = new Scene(splashScreen);
    stage.setScene(splashScene);
    stage.show();


    // The timer below helps in changing the background color of the splash screen from black to
    // red. When timer.start() is called, the handle() function will be called repeatedly. It will
    // only stop when the stop() function is called.
    AnimationTimer timer = new AnimationTimer() {
      int redValue = 0;

      @Override
      public void handle(long now) {
        splashScreen.setStyle("-fx-background-color: rgb(" + redValue + ", 0, 0);");
        redValue++;

        if (redValue >= 255) {
          stop(); // Stop the AnimationTimer
          showMainMenu(stage);
        }
      }
    };

    timer.start();
  }

  /**
   * This function sets up and displays the main menu.
   *
   * @param stage The window that displays the GUI for the app.
   */
  private void showMainMenu(Stage stage) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/main-view.fxml"));

    MainController mainController = MainController.getInstance();
    loader.setController(mainController);

    try {
      Scene scene = new Scene(loader.load());
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - showMainMenu(): Could not show the main menu screen.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }

    /*
    System.out.println("\n- width = " + Main.mainStage.widthProperty());
    System.out.println("- height = " + Main.mainStage.heightProperty());
    System.out.println("- stage = " + Main.mainStage);
     */
  }
}