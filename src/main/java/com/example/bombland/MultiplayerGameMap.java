package com.example.bombland;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class sets up the UI for the game map.
 */
public class MultiplayerGameMap {
  private static MultiplayerGameMap instance;

  int rows;
  int cols;
  int bombs;
  int tilesUncovered;
  int flagsSet;
  int totalTiles;

  String gameMap;
  String gameDifficulty;

  GridPane grid;
  ArrayList<TileCoordinates> bombCoordinates;
  HashMap<TileCoordinates, Tile> gridObjects;
  HashMap<Integer, HashSet<Integer>> tilesEliminated;


  private MultiplayerGameMap() {}

  /**
   * This function creates an instance of this class.
   *
   * @return An instance of this class.
   */
  public static MultiplayerGameMap getInstance() {
    if (instance == null) {
      instance = new MultiplayerGameMap();
    }

    return instance;
  }

  public int getBombs() {
    return bombs;
  }

  public ArrayList<TileCoordinates> getBombCoordinates() {
    return bombCoordinates;
  }

  public HashMap<TileCoordinates, Tile> getGridObjects() {
    return gridObjects;
  }

  void setMapSettings() {
    gameMap = AppCache.getInstance().getGameMap();
    gameDifficulty = AppCache.getInstance().getGameDifficulty();

    if (Objects.equals(gameMap, "Rectangle")) {
      if (Objects.equals(gameDifficulty, "Easy")) {
        rows = 8;
        cols = 8;
        bombs = 10;
      } else if (Objects.equals(gameDifficulty, "Medium")) {
        rows = 16;
        cols = 16;
        bombs = 40;
      } else {
        rows = 16;
        cols = 32;
        bombs = 100;
      }
    } else if (Objects.equals(gameMap, "Bomb")) {
      if (Objects.equals(gameDifficulty, "Easy")) {
        rows = 16;
        cols = 16;
        bombs = 20;
      } else if (Objects.equals(gameDifficulty, "Medium")) {
        rows = 24;
        cols = 32;
        bombs = 45;
      } else {
        rows = 24;
        cols = 64;
        bombs = 200;
      }
    } else if (Objects.equals(gameMap, "Face")) {
      if (Objects.equals(gameDifficulty, "Easy")) {
        rows = 16;
        cols = 16;
        bombs = 25;
      } else if (Objects.equals(gameDifficulty, "Medium")) {
        rows = 24;
        cols = 32;
        bombs = 50;
      } else {
        rows = 24;
        cols = 64;
        bombs = 200;
      }
    } else { // gameMap == Flower
      if (Objects.equals(gameDifficulty, "Easy")) {
        rows = 16;
        cols = 16;
        bombs = 25;
      } else if (Objects.equals(gameDifficulty, "Medium")) {
        rows = 24;
        cols = 32;
        bombs = 60;
      } else {
        rows = 24;
        cols = 64;
        bombs = 150;
      }
    }
  }

  void buildGrid() {
    setMapSettings();

    tilesUncovered = 0;
    flagsSet = 0;
    totalTiles = 0;

    grid = new GridPane();
    VBox.setVgrow(grid, Priority.ALWAYS);
    HBox.setHgrow(grid, Priority.ALWAYS);

    // Create column and row constraints for the grid
    for (int i = 0; i < cols; i++) {
      ColumnConstraints colConstraints = new ColumnConstraints();
      colConstraints.setPercentWidth(100.0 / cols);
      grid.getColumnConstraints().add(colConstraints);
    }
    for (int i = 0; i < rows; i++) {
      RowConstraints rowConstraints = new RowConstraints();
      rowConstraints.setPercentHeight(100.0 / rows);
      grid.getRowConstraints().add(rowConstraints);
    }

    gridObjects = new HashMap<>();
    tilesEliminated = new HashMap<>();
    bombCoordinates = new ArrayList<>();

    if (Objects.equals(gameMap, "Rectangle")) {
      buildRectangleGrid();
    } else { // gameMap == Bomb, Face, or Flower
      buildOtherGrids();
    }
  }

  void buildRectangleGrid() {
    boolean evenTile = true;

    // Creates a grid of X rows and Y columns
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Button tileBtn = new Button();
        tileBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tileBtn.setStyle("-fx-background-color: " + (evenTile ? Color.lightOrange() : Color.orange()) + ";");

        int tileRow = row;
        int tileCol = col;

        // Handles left-clicks on the tiles
        tileBtn.setOnAction(actionEvent -> {
          Tile tileObj = gridObjects.get(new TileCoordinates(tileRow, tileCol));

          if (!tileObj.isFlagged()) {
            boolean gameStarted = MultiplayerPlayController.getInstance().getGameStarted();

            JSONObject dataObj = new JSONObject();
            dataObj.put("row", tileObj.getRow());
            dataObj.put("col", tileObj.getCol());

            if (!gameStarted) {
              eliminateSurroundingTiles(tileObj);
              generateBombCoordinates();

              dataObj.put("bombCoordinates", bombCoordinates);
              Main.socketClient.sendInitialData(dataObj);
            } else {
              try {
                handleTileClick(tileObj, AppCache.getInstance().getPlayerName());
              } catch (IOException e) {
                System.out.println("\n====================================================================");
                System.out.println("ERROR - buildRectangleGrid(): Could not build the rectangle grid.");
                System.out.println("---");
                System.out.println(e.getCause());
                System.out.println("====================================================================\n");
              }
            }
          }
        });

        // Handles right-clicks on the tiles
        tileBtn.setOnMouseClicked(event -> {
          if (event.getButton() == MouseButton.SECONDARY) {
            Tile tileObj = gridObjects.get(new TileCoordinates(tileRow, tileCol));

            if (tileObj.isCovered()) {
              AudioClip clip = new AudioClip(Objects.requireNonNull(
                  MultiplayerGameMap.class.getResource("/com/example/bombland/Sounds/flag_flap.wav")
              ).toExternalForm());
              clip.play();

              if (tileObj.isFlagged()) {
                tileObj.setFlagged(false);
                tileBtn.setStyle("-fx-background-color: " + tileObj.getBackgroundColor() + ";");
                flagsSet -= 1;
              } else {
                tileObj.setFlagged(true);
                tileBtn.setStyle("-fx-background-color: " + tileObj.getBackgroundColor() + "; "
                    + "-fx-background-image: url(\"/com/example/bombland/images/red-flag.png\"); "
                    + "-fx-background-size: 40%; -fx-background-position: center; "
                    + "-fx-background-repeat: no-repeat;");
                flagsSet += 1;
              }

              MultiplayerPlayController.getInstance().setFlagsLeftLbl((bombs - flagsSet) + " flags left");
            }
          }
        });

        Tile tileObj = new Tile();
        tileObj.setRow(row);
        tileObj.setCol(col);
        tileObj.setBackgroundColor(evenTile ? Color.lightOrange() : Color.orange());

        grid.add(tileBtn, col, row);
        gridObjects.put(new TileCoordinates(row, col), tileObj);

        evenTile = !evenTile;
        totalTiles++;
      }

      evenTile = !evenTile;
    }

    MultiplayerPlayController.getInstance().setGridContainer(grid);
    grid.setGridLinesVisible(true);
  }

  void buildOtherGrids() {
    // Creates a grid of X rows and Y columns
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Button tileBtn = new Button();
        tileBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tileBtn.setStyle("-fx-background-color: " + Color.orange() + ";");

        int tileRow = row;
        int tileCol = col;

        // Handles left-clicks on the tiles
        tileBtn.setOnAction(actionEvent -> {
          Tile tileObj = gridObjects.get(new TileCoordinates(tileRow, tileCol));

          if (!tileObj.isFlagged()) {
            boolean gameStarted = MultiplayerPlayController.getInstance().getGameStarted();

            JSONObject dataObj = new JSONObject();
            dataObj.put("row", tileObj.getRow());
            dataObj.put("col", tileObj.getCol());

            if (!gameStarted) {
              eliminateSurroundingTiles(tileObj);
              generateBombCoordinates();

              dataObj.put("bombCoordinates", bombCoordinates);
              Main.socketClient.sendInitialData(dataObj);
            } else {
              try {
                handleTileClick(tileObj, AppCache.getInstance().getPlayerName());
              } catch (IOException e) {
                System.out.println("\n====================================================================");
                System.out.println("ERROR - buildOtherGrids(): Could not build the other grids.");
                System.out.println("---");
                System.out.println(e.getCause());
                System.out.println("====================================================================\n");
              }
            }
          }
        });

        // Handles right-clicks on the tiles
        tileBtn.setOnMouseClicked(event -> {
          if (event.getButton() == MouseButton.SECONDARY) {
            Tile tileObj = gridObjects.get(new TileCoordinates(tileRow, tileCol));

            if (tileObj.isCovered()) {
              AudioClip clip = new AudioClip(Objects.requireNonNull(
                  MultiplayerGameMap.class.getResource("/com/example/bombland/Sounds/flag_flap.wav")
              ).toExternalForm());
              clip.play();

              if (tileObj.isFlagged()) {
                tileObj.setFlagged(false);
                tileBtn.setStyle("-fx-background-color: " + tileObj.getBackgroundColor() + ";");
                flagsSet -= 1;
              } else {
                tileObj.setFlagged(true);
                tileBtn.setStyle("-fx-background-color: " + tileObj.getBackgroundColor() + "; "
                    + "-fx-background-image: url(\"/com/example/bombland/images/red-flag.png\"); "
                    + "-fx-background-size: 40%; -fx-background-position: center; "
                    + "-fx-background-repeat: no-repeat;");
                flagsSet += 1;
              }

              MultiplayerPlayController.getInstance().setFlagsLeftLbl((bombs - flagsSet) + " flags left");
            }
          }
        });

        Tile tileObj = new Tile();
        tileObj.setRow(row);
        tileObj.setCol(col);
        tileObj.setBackgroundColor(Color.orange());

        if (disableTile(row, col)) {
          tileBtn.setDisable(true);
          tileBtn.setStyle("-fx-background-color: " + Color.white());
          tileObj.setValue(Tile.TileValue.DISABLED);

          HashSet<Integer> values;
          if (tilesEliminated.get(row) == null) {
            values = new HashSet<>();
          } else {
            values = tilesEliminated.get(row);
          }

          values.add(col);
          tilesEliminated.put(row, values);
        } else {
          totalTiles++;
        }

        grid.add(tileBtn, col, row);
        gridObjects.put(new TileCoordinates(row, col), tileObj);
      }
    }

    MultiplayerPlayController.getInstance().setGridContainer(grid);
    grid.setGridLinesVisible(true);
  }

  boolean disableTile(int row, int col) {
    if (Objects.equals(gameMap, "Bomb")) {
      if (Objects.equals(gameDifficulty, "Easy")) {
        return (row == 0 && (col <= 4 || col == 6 || col == 9 || col >= 11)) ||
            (row == 1 && (col <= 5 || col >= 10)) ||
            (row == 2 && (col <= 6 || col >= 9)) ||
            (row == 3 && (col <= 5 || col >= 10)) ||
            (row == 4 && (col <= 4 || col == 6 || col == 9 || col >= 11)) ||
            (row == 5 && (col <= 3 || col == 5 || col == 6 || col == 9 || col == 10 || col >= 12)) ||
            (row == 6 && (col <= 2 || (col >= 4 && col <= 6) || (col >= 9 && col <= 11) || col >= 13)) ||
            (row == 7 && (col <= 4 || col >= 11)) ||
            (row == 8 && (col <= 3 || col >= 12)) ||
            (row == 9 && (col <= 2 || col >= 13)) ||
            (row == 10 && (col <= 1 || col >= 14)) ||
            (row == 11 && (col == 0 || col == 15)) ||
            (row == 12 && (col == 0 || col == 15)) ||
            (row == 13 && (col == 0 || col == 15)) ||
            (row == 14 && (col <= 1 || col >= 14)) ||
            (row == 15 && (col <= 2 || col >= 13));
      } else if (Objects.equals(gameDifficulty, "Medium")) {
        return (row == 0 && (col <= 9 || (col >= 12 && col <= 14) || (col >= 17 && col <= 19) || col >= 22)) ||
            (row == 1 && (col <= 10 || (col >= 13 && col <= 14) || (col >= 17 && col <= 18) || col >= 21)) ||
            (row == 2 && (col <= 11 || col == 14 || col == 17 || col >= 20)) ||
            (row == 3 && (col <= 12 || col >= 19)) ||
            (row == 4 && (col <= 9 || col >= 22)) ||
            (row == 5 && (col <= 9 || col >= 22)) ||
            (row == 6 && (col <= 12 || col >= 19)) ||
            (row == 7 && (col <= 11 || col == 14 || col == 17 || col >= 20)) ||
            (row == 8 && (col <= 10 || (col >= 13 && col <= 14) || (col >= 17 && col <= 18) || col >= 21)) ||
            (row == 9 && (col <= 9 || (col >= 12 && col <= 14) || (col >= 17 && col <= 19) || col >= 22)) ||
            (row == 10 && (col <= 12 || col >= 19)) ||
            (row == 11 && (col <= 11 || col >= 20)) ||
            (row == 12 && (col <= 9 || col >= 22)) ||
            (row == 13 && (col <= 8 || col >= 23)) ||
            (row == 14 && (col <= 7 || col >= 24)) ||
            (row == 15 && (col <= 6 || col >= 25)) ||
            (row == 16 && (col <= 6 || col >= 25)) ||
            (row == 17 && (col <= 6 || col >= 25)) ||
            (row == 18 && (col <= 6 || col >= 25)) ||
            (row == 19 && (col <= 6 || col >= 25)) ||
            (row == 20 && (col <= 7 || col >= 24)) ||
            (row == 21 && (col <= 8 || col >= 23)) ||
            (row == 22 && (col <= 9 || col >= 22)) ||
            (row == 23 && (col <= 10 || col >= 21));
      } else { // gameDifficulty is hard
        return (row == 0) ||
            (row == 1 && (col <= 3 || (col >= 14 && col <= 19) || (col >= 23 && col <= 29) || (col >= 34 && col <= 40) || (col >= 44 && col <= 49) || col >= 60)) ||
            (row == 2 && (col <= 2 || (col >= 15 && col <= 20) || (col >= 24 && col <= 29) || (col >= 34 && col <= 39) || (col >= 43 && col <= 48) || col >= 61)) ||
            (row == 3 && (col <= 1 || (col >= 16 && col <= 21) || (col >= 25 && col <= 29) || (col >= 34 && col <= 38) || (col >= 42 && col <= 47) || col >= 62)) ||
            (row == 4 && (col == 0 || (col >= 17 && col <= 22) || (col >= 26 && col <= 29) || (col >= 34 && col <= 37) || (col >= 41 && col <= 46) || col == 63)) ||
            (row == 5 && ((col >= 18 && col <= 23) || (col >= 27 && col <= 29) || (col >= 34 && col <= 36) || (col >= 40 && col <= 45))) ||
            (row == 6 && ((col >= 18 && col <= 24) || (col >= 28 && col <= 29) || (col >= 34 && col <= 35) || (col >= 39 && col <= 45))) ||
            (row == 7 && ((col >= 20 && col <= 25) || col == 29 || col == 34 || (col >= 38 && col <= 43))) ||
            (row == 8 && ((col >= 20 && col <= 26) || (col >= 37 && col <= 43))) ||
            (row == 9 && ((col >= 20 && col <= 27) || (col >= 36 && col <= 43))) ||
            (row == 14 && ((col >= 20 && col <= 27) || (col >= 36 && col <= 43))) ||
            (row == 15 && ((col >= 20 && col <= 26) || (col >= 37 && col <= 43))) ||
            (row == 16 && ((col >= 20 && col <= 25) || col == 29 || col == 34 || (col >= 38 && col <= 43))) ||
            (row == 17 && ((col >= 18 && col <= 24) || (col >= 28 && col <= 29) || (col >= 34 && col <= 35) || (col >= 39 && col <= 45))) ||
            (row == 18 && ((col >= 18 && col <= 23) || (col >= 27 && col <= 29) || (col >= 34 && col <= 36) || (col >= 40 && col <= 45))) ||
            (row == 19 && (col == 0 || (col >= 17 && col <= 22) || (col >= 26 && col <= 29) || (col >= 34 && col <= 37) || (col >= 41 && col <= 46) || col == 63)) ||
            (row == 20 && (col <= 1 || (col >= 16 && col <= 21) || (col >= 25 && col <= 29) || (col >= 34 && col <= 38) || (col >= 42 && col <= 47) || col >= 62)) ||
            (row == 21 && (col <= 2 || (col >= 15 && col <= 20) || (col >= 24 && col <= 29) || (col >= 34 && col <= 39) || (col >= 43 && col <= 48) || col >= 61)) ||
            (row == 22 && (col <= 3 || (col >= 14 && col <= 19) || (col >= 23 && col <= 29) || (col >= 34 && col <= 40) || (col >= 44 && col <= 49) || col >= 60)) ||
            (row == 23);
      }
    } else if (Objects.equals(gameMap, "Face")) {
      if (Objects.equals(gameDifficulty, "Easy")) {
        return (row == 0) ||
            (row == 1 && (col <= 4 || col >= 11)) ||
            (row == 2 && (col <= 3 || col >= 12)) ||
            (row == 3 && (col <= 2 || col >= 13)) ||
            (row == 4 && (col <= 1 || col >= 14)) ||
            (row == 5 && (col <= 1 || col == 5 || col == 10 || col >= 14)) ||
            (row == 6 && (col == 0 || col == 15)) ||
            (row == 7 && (col == 0 || col == 15)) ||
            (row == 8 && (col == 0 || col == 15)) ||
            (row == 9 && (col == 0 || col == 4 || col == 11 || col == 15)) ||
            (row == 10 && (col <= 1 || col == 5 || col == 10 || col >= 14)) ||
            (row == 11 && (col <= 1 || (col >= 6 && col <= 9) || col >= 14)) ||
            (row == 12 && (col <= 2 || col >= 13)) ||
            (row == 13 && (col <= 3 || col >= 12)) ||
            (row == 14 && (col <= 4 || col >= 11)) ||
            (row == 15);
      } else if (Objects.equals(gameDifficulty, "Medium")) {
        return (row == 0) ||
            (row == 1 && (col <= 11 || col >= 20)) ||
            (row == 2 && (col <= 10 || col >= 21)) ||
            (row == 3 && (col <= 9 || col >= 22)) ||
            (row == 4 && (col <= 8 || col >= 23)) ||
            (row == 5 && (col <= 7 || col >= 24)) ||
            (row == 6 && (col <= 6 || col == 11 || col == 12 || col == 19 || col == 20 || col >= 25)) ||
            (row == 7 && (col <= 5 || col == 11 || col == 12 || col == 19 || col == 20 || col >= 26)) ||
            (row == 8 && (col <= 5 || col >= 26)) ||
            (row == 9 && (col <= 5 || col >= 26)) ||
            (row == 10 && (col <= 5 || col >= 26)) ||
            (row == 11 && (col <= 5 || col >= 26)) ||
            (row == 12 && (col <= 5 || col >= 26)) ||
            (row == 13 && (col <= 5 || col >= 26)) ||
            (row == 14 && (col <= 5 || col >= 26)) ||
            (row == 15 && (col <= 5 || col >= 26)) ||
            (row == 16 && (col <= 5 || (col >= 11 && col <= 20) || col >= 26)) ||
            (row == 17 && (col <= 6 || (col >= 11 && col <= 20) || col >= 25)) ||
            (row == 18 && (col <= 7 || col >= 24)) ||
            (row == 19 && (col <= 8 || col >= 23)) ||
            (row == 20 && (col <= 9 || col >= 22)) ||
            (row == 21 && (col <= 10 || col >= 21)) ||
            (row == 22 && (col <= 11 || col >= 20)) ||
            (row == 23);
      } else {
        return (row == 0) ||
            (row == 1 && (col <= 7 || (col >= 16 && col <= 27) || (col >= 36 && col <= 47) || col >= 56)) ||
            (row == 2 && (col <= 6 || (col >= 17 && col <= 26) || (col >= 37 && col <= 46) || col >= 57)) ||
            (row == 3 && (col <= 5 || (col >= 18 && col <= 25) || (col >= 38 && col <= 45) || col >= 58)) ||
            (row == 4 && (col <= 4 || (col >= 19 && col <= 24) || (col >= 39 && col <= 44) || col >= 59)) ||
            (row == 5 && (col <= 3 || (col >= 20 && col <= 23) || (col >= 40 && col <= 43) || col >= 60)) ||
            (row == 6 && (col <= 2 || col == 6 || col == 7 || col == 16 || col == 17 || col == 21 || col == 22 || col == 27 || col == 28 || col == 35 || col == 36 || col == 41 || col == 42 || col == 47 || col == 48 || col == 55 || col == 56 || col >= 61)) ||
            (row == 7 && (col <= 1 || col == 6 || col == 7 || col == 16 || col == 17 || col == 27 || col == 28 || col == 35 || col == 36 || col == 47 || col == 48 || col == 55 || col == 56 || col >= 61)) ||
            (row == 8 && (col <= 1 || col >= 62)) ||
            (row == 9 && (col <= 1 || col >= 62)) ||
            (row == 10 && (col <= 1 || col >= 62)) ||
            (row == 11 && (col <= 1 || col >= 62)) ||
            (row == 12 && (col <= 1 || col >= 62)) ||
            (row == 13 && (col <= 1 || col >= 62)) ||
            (row == 14 && (col <= 1 || col >= 62)) ||
            (row == 15 && (col <= 1 || col >= 62)) ||
            (row == 16 && (col <= 1 || (col >= 9 && col <= 14) || (col >= 29 && col <= 34) || (col >= 49 && col <= 54) || col >= 62)) ||
            (row == 17 && (col <= 2 || col == 8 || col == 15 || col == 21 || col == 22 || col == 28 || col == 35 || col == 41 || col == 42 || col == 48 || col == 55 || col >= 61)) ||
            (row == 18 && (col <= 3 || col == 7 || col == 16 || (col >= 20 && col <= 23) || col == 27 || col == 36 || (col >= 40 && col <= 43) || col == 47 || col == 56 || col >= 60)) ||
            (row == 19 && (col <= 4 || (col >= 19 && col <= 24) || (col >= 39 && col <= 44) || col >= 59)) ||
            (row == 20 && (col <= 5 || (col >= 18 && col <= 25) || (col >= 38 && col <= 45) || col >= 58)) ||
            (row == 21 && (col <= 6 || (col >= 17 && col <= 26) || (col >= 37 && col <= 46) || col >= 57)) ||
            (row == 22 && (col <= 7 || (col >= 16 && col <= 27) || (col >= 36 && col <= 47) || col >= 56)) ||
            (row == 23);
      }
    } else { //gameMap == Flower
      if (Objects.equals(gameDifficulty, "Easy")) {
        return (row == 0) ||
            (row == 1 && (col <= 6 || col >= 9)) ||
            (row == 2 && (col <= 5 || col >= 10)) ||
            (row == 3 && (col <= 5 || col >= 10)) ||
            (row == 4 && (col <= 3 || col >= 12)) ||
            (row == 5 && (col <= 3 || col >= 12)) ||
            (row == 6 && (col <= 1 || col >= 14)) ||
            (row == 7 && (col == 0 || col == 7 || col == 8 || col == 15)) ||
            (row == 8 && (col == 0 || col == 7 || col == 8 || col == 15)) ||
            (row == 9 && (col <= 1 || col >= 14)) ||
            (row == 10 && (col <= 3 || col >= 12)) ||
            (row == 11 && (col <= 3 || col >= 12)) ||
            (row == 12 && (col <= 5 || col >= 10)) ||
            (row == 13 && (col <= 5 || col >= 10)) ||
            (row == 14 && (col <= 6 || col >= 9)) ||
            (row == 15);
      } else if (Objects.equals(gameDifficulty, "Medium")) {
        return (row == 0) ||
            (row == 1 && (col <= 12 || col >= 19)) ||
            (row == 2 && (col <= 11 || col >= 20)) ||
            (row == 3 && (col <= 10 || col >= 21)) ||
            (row == 4 && (col <= 10 || col >= 21)) ||
            (row == 5 && (col <= 10 || col >= 21)) ||
            (row == 6 && (col <= 10 || col >= 21)) ||
            (row == 7 && (col <= 3 || col >= 28)) ||
            (row == 8 && (col <= 2 || col >= 29)) ||
            (row == 9 && (col <= 1 || col >= 30)) ||
            (row == 10 && (col <= 1 || col == 15 || col == 16 || col >= 30)) ||
            (row == 11 && (col == 0 || (col >= 14 && col <= 17) || col == 31)) ||
            (row == 12 && (col == 0 || (col >= 14 && col <= 17) || col == 31)) ||
            (row == 13 && (col <= 1 || col == 15 || col == 16 || col >= 30)) ||
            (row == 14 && (col <= 1 || col >= 30)) ||
            (row == 15 && (col <= 2 || col >= 29)) ||
            (row == 16 && (col <= 3 || col >= 28)) ||
            (row == 17 && (col <= 10 || col >= 21)) ||
            (row == 18 && (col <= 10 || col >= 21)) ||
            (row == 19 && (col <= 10 || col >= 21)) ||
            (row == 20 && (col <= 10 || col >= 21)) ||
            (row == 21 && (col <= 11 || col >= 20)) ||
            (row == 22 && (col <= 12 || col >= 19)) ||
            (row == 23);
      } else {
        return (row == 0) ||
            (row == 1 && (col <= 12 || (col >= 19 && col <= 44) || col >= 51)) ||
            (row == 2 && (col <= 11 || (col >= 20 && col <= 43) || col >= 52)) ||
            (row == 3 && (col <= 10 || (col >= 21 && col <= 42) || col >= 53)) ||
            (row == 4 && (col <= 10 || (col >= 21 && col <= 42) || col >= 53)) ||
            (row == 5 && (col <= 10 || (col >= 21 && col <= 42) || col >= 53)) ||
            (row == 6 && (col <= 10 || (col >= 21 && col <= 42) || col >= 53)) ||
            (row == 7 && (col <= 3 || (col >= 28 && col <= 35) || col >= 60)) ||
            (row == 8 && (col <= 2 || (col >= 29 && col <= 34) || col >= 61)) ||
            (row == 9 && (col <= 1 || (col >= 30 && col <= 33) || col >= 62)) ||
            (row == 10 && (col <= 1 || col == 15 || col == 16 || (col >= 30 && col <= 33) || col == 47 || col == 48 || col >= 62)) ||
            (row == 11 && (col == 0 || (col >= 14 && col <= 17) || (col >= 46 && col <= 49) || col == 63)) ||
            (row == 12 && (col == 0 || (col >= 14 && col <= 17) || (col >= 46 && col <= 49) || col == 63)) ||
            (row == 13 && (col <= 1 || col == 15 || col == 16 || (col >= 30 && col <= 33) || col == 47 || col == 48 || col >= 62)) ||
            (row == 14 && (col <= 1 || (col >= 30 && col <= 33) || col >= 62)) ||
            (row == 15 && (col <= 2 || (col >= 29 && col <= 34) || col >= 61)) ||
            (row == 16 && (col <= 3 || (col >= 28 && col <= 35) || col >= 60)) ||
            (row == 17 && (col <= 10 || (col >= 21 && col <= 42) || col >= 53)) ||
            (row == 18 && (col <= 10 || (col >= 21 && col <= 42) || col >= 53)) ||
            (row == 19 && (col <= 10 || (col >= 21 && col <= 42) || col >= 53)) ||
            (row == 20 && (col <= 10 || (col >= 21 && col <= 42) || col >= 53)) ||
            (row == 21 && (col <= 11 || (col >= 20 && col <= 43) || col >= 52)) ||
            (row == 22 && (col <= 12 || (col >= 19 && col <= 44) || col >= 51)) ||
            (row == 23);
      }
    }
  }

  // This function makes sure that there are no adjacent bombs on the first tile clicked
  void eliminateSurroundingTiles(Tile tileObj) {
    int rowCount = 1;

    for (int i = tileObj.getRow() - 1; rowCount <= 3; rowCount++, i++) {
      if (i < 0 || i > rows - 1) {
        continue;
      }

      int colCount = 1;

      for (int j = tileObj.getCol() - 1; colCount <= 3; colCount++, j++) {
        if (j < 0 || j > cols - 1) {
          continue;
        }

        if (tilesEliminated.get(i) == null) {
          tilesEliminated.put(i, new HashSet<>());
        }

        HashSet<Integer> values = tilesEliminated.get(i);
        values.add(j);
        tilesEliminated.put(i, values);
      }
    }
  }

  void generateBombCoordinates() {
    int bombsAssigned = 0;

    while (bombsAssigned < bombs) {
      Random rand = new Random();
      int newRow = rand.nextInt(rows);
      int newCol = rand.nextInt(cols);

      // Checks if it's safe to place a bomb on the coordinates generated
      if (tilesEliminated.get(newRow) == null || !(tilesEliminated.get(newRow).contains(newCol))) {
        HashSet<Integer> values;

        if (tilesEliminated.get(newRow) == null) {
          values = new HashSet<>();
        } else {
          values = tilesEliminated.get(newRow);
        }

        values.add(newCol);
        tilesEliminated.put(newRow, values);

        bombCoordinates.add(new TileCoordinates(newRow, newCol));
        bombsAssigned++;
      }
    }
  }

  void setupBombTiles() {
    for (TileCoordinates bombCoordinate : bombCoordinates) {
      TileCoordinates coordinates = new TileCoordinates(bombCoordinate.getRow(), bombCoordinate.getCol());

      Tile tile = gridObjects.get(coordinates);
      tile.setValue(Tile.TileValue.BOMB);

      HashSet<Integer> values;

      if (tilesEliminated.get(coordinates.getRow()) == null) {
        values = new HashSet<>();
      } else {
        values = tilesEliminated.get(coordinates.getRow());
      }

      values.add(coordinates.getCol());
    }
  }

  void setupNumberTiles(Tile tileObj) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (gridObjects.get(new TileCoordinates(i, j)).getValue() == Tile.TileValue.UNKNOWN) {
          int kCount = 0;
          int surroundingBombs = 0;

          for (int k = i - 1; kCount < 3; k++, kCount++) {
            if (k < 0 || k > rows - 1) {
              continue;
            }

            int lCount = 0;

            for (int l = j - 1; lCount < 3; l++, lCount++) {
              if (l < 0 || l > cols - 1 || (k == tileObj.getRow() && l == tileObj.getCol())) {
                continue;
              }

              if (gridObjects.get(new TileCoordinates(k, l)).getValue() == Tile.TileValue.BOMB) {
                surroundingBombs++;
              }
            }
          }

          gridObjects.get(new TileCoordinates(i, j)).setSurroundingBombs(surroundingBombs);

          if (surroundingBombs > 0) {
            gridObjects.get(new TileCoordinates(i, j)).setValue(Tile.TileValue.NUMBER);
            gridObjects.get(new TileCoordinates(i, j)).setSurroundingBombs(surroundingBombs);
          } else {
            gridObjects.get(new TileCoordinates(i, j)).setValue(Tile.TileValue.EMPTY);
          }
        }
      }
    }
  }

  void setupEmptyTiles() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (gridObjects.get(new TileCoordinates(i, j)).getValue() == Tile.TileValue.UNKNOWN) {
          gridObjects.get(new TileCoordinates(i, j)).setValue(Tile.TileValue.EMPTY);
        }
      }
    }
  }

  void handleTileClick(Tile tileObj, String clicker) throws IOException {
    if (tileObj.isCovered()) {
      AudioClip clip = new AudioClip(Objects.requireNonNull(
          MultiplayerGameMap.class.getResource("/com/example/bombland/Sounds/tile_click.wav")
      ).toExternalForm());
      clip.play();

      if (tileObj.getValue() == Tile.TileValue.EMPTY) {
        traverse(tileObj.getRow(), tileObj.getCol(), clicker);

        if (tilesUncovered + bombs == totalTiles) {
          MultiplayerPlayController.getInstance().setGameOver(true);
          MultiplayerPlayController.getInstance().setPlayerDied(false);
          Main.socketClient.gameOver();
        }
      } else if (tileObj.getValue() == Tile.TileValue.NUMBER) {
        uncoverTile(tileObj, clicker);

        if (tilesUncovered + bombs == totalTiles) {
          MultiplayerPlayController.getInstance().setGameOver(true);
          MultiplayerPlayController.getInstance().setPlayerDied(false);
          Main.socketClient.gameOver();
        }
      } else { // tile contains a bomb
        MultiplayerPlayController.getInstance().setGameOver(true);
        MultiplayerPlayController.getInstance().setPlayerDied(true);
        Main.socketClient.gameOver();
      }
    }
  }

  void traverse(int row, int col, String clicker) {
    if (row >= 0 && row < rows && col >= 0 && col < cols) {
      Tile currentTile = gridObjects.get(new TileCoordinates(row, col));

      if (currentTile.isCovered() && (currentTile.getValue() == Tile.TileValue.EMPTY || currentTile.getValue() == Tile.TileValue.NUMBER)) {
        uncoverTile(currentTile, clicker);

        if (currentTile.getSurroundingBombs() > 0) {
          return;
        }

        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            traverse(row + i, col + j, clicker);
          }
        }
      }
    }
  }

  void uncoverTile(Tile tile, String clicker) {
    if (tile.isFlagged() && !MultiplayerPlayController.getInstance().getGameOver()) {
      flagsSet--;
      MultiplayerPlayController.getInstance().setFlagsLeftLbl((bombs - flagsSet) + " flags left");
    }

    Button tileBtn = getTileButton(grid, tile.getRow(), tile.getCol());
    boolean isBombTile = false;

    if (tile.getValue() == Tile.TileValue.EMPTY) {
      tile.setBackgroundColor(currentUserClickedTile(clicker) ? getCurrentPlayerColor() : getOtherPlayerColor());
      tileBtn.setStyle("-fx-background-color: " + tile.getBackgroundColor() + ";");
    } else if (tile.getValue() == Tile.TileValue.NUMBER) {
      tile.setBackgroundColor(currentUserClickedTile(clicker) ? getCurrentPlayerColor() : getOtherPlayerColor());
      displayNumberIcon(tile);
    } else { // bomb tile
      isBombTile = true;
      tile.setBackgroundColor(Color.red());
      tileBtn.setStyle("-fx-background-color: " + tile.getBackgroundColor() + "; "
          + "-fx-background-image: url(\"/com/example/bombland/images/bomb.png\"); "
          + "-fx-background-size: 50%; -fx-background-position: center; -fx-background-repeat: no-repeat;");
    }

    tile.setCovered(false);
    tilesUncovered++;

    JSONObject tileInfoObj = new JSONObject();
    tileInfoObj.put("row", tile.getRow());
    tileInfoObj.put("col", tile.getCol());

    if (AppCache.getInstance().getPlayerName().equals(clicker) && !isBombTile) {
      Main.socketClient.sendTileCoordinates(tileInfoObj);
    }
  }

  /**
   * Iterates over the grid pane to retrieve a specific tile.
   *
   * @param gridPane Contains all the tiles (which are buttons) on the game map.
   * @param row The row the tile is located on in the game map.
   * @param col The column the tile is located on in the game map.
   * @return The tile.
   */
  Button getTileButton(GridPane gridPane, int row, int col) {
    for (Node node : gridPane.getChildren()) {
      Integer rowIdx = GridPane.getRowIndex(node);
      Integer colIdx = GridPane.getColumnIndex(node);

      if (rowIdx != null && colIdx != null && rowIdx == row && colIdx == col) {
        return (Button) node;
      }
    }

    return null;
  }

  void displayNumberIcon(Tile tile) {
    StringBuilder numberFile = new StringBuilder();

    numberFile = switch (tile.getSurroundingBombs()) {
      case 1 -> new StringBuilder("1.png");
      case 2 -> new StringBuilder("2.png");
      case 3 -> new StringBuilder("3.png");
      case 4 -> new StringBuilder("4.png");
      case 5 -> new StringBuilder("5.png");
      case 6 -> new StringBuilder("6.png");
      case 7 -> new StringBuilder("7.png");
      case 8 -> new StringBuilder("8.png");
      default -> numberFile;
    };

    Button tileBtn = getTileButton(grid, tile.getRow(), tile.getCol());

    tileBtn.setStyle("-fx-background-color: " + tile.getBackgroundColor() + "; "
        + "-fx-background-image: url(\"/com/example/bombland/images/" + numberFile
        + "\"); -fx-background-size: 50%; -fx-background-position: center; -fx-background-repeat: no-repeat;");
  }

  /**
   * This function increments the number of tiles that have been uncovered.
   */
  public void incrementTilesUncovered() {
    tilesUncovered++;
  }

  /**
   * This function clears the map.
   */
  public void clearGrid() {
    grid.getChildren().clear();
  }


  @FXML
  void startMultiplayerGame(JSONObject firstTileObj) {
    Tile tileObj = gridObjects.get(new TileCoordinates(firstTileObj.getInt("row"), firstTileObj.getInt("col")));
    tileObj.setValue(Tile.TileValue.EMPTY);

    tilesEliminated.clear();
    eliminateSurroundingTiles(tileObj);

    retrieveBombCoordinates((JSONArray) firstTileObj.get("bombCoordinates"));
    setupBombTiles();
    setupNumberTiles(tileObj);
    setupEmptyTiles();

    MultiplayerPlayController.getInstance().startTimer();
    MultiplayerPlayController.getInstance().setGameStarted(true);

    try {
      handleTileClick(tileObj, firstTileObj.getString("playerName"));
    } catch (IOException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - startMultiplayerGame(): Could not build the rectangle grid.");
      System.out.println("---");
      System.out.println(e.getCause());
      System.out.println("====================================================================\n");
    }
  }

  void retrieveBombCoordinates(JSONArray bombCoordinatesArr) {
    bombCoordinates.clear();

    for (int i = 0; i < bombCoordinatesArr.length(); i++) {
      // Get the JSONObject at the current index
      JSONObject tileObject = bombCoordinatesArr.getJSONObject(i);

      // Extract the row and col values
      int row = tileObject.getInt("row");
      int col = tileObject.getInt("col");

      // Create a new TileCoordinates object and add it to the list
      TileCoordinates tile = new TileCoordinates(row, col);
      bombCoordinates.add(tile);
    }
  }

  /**
   * Determines whether the current user is the one that clicked a tile.
   *
   * @return A boolean that represents whether the current user is the one that clicked a tile.
   */
  private boolean currentUserClickedTile(String clicker) {
    return clicker.equals(AppCache.getInstance().getPlayerName());
  }

  /**
   * Determines whether the current user is Player1.
   *
   * @return A boolean that represents whether the current user is Player1.
   */
  private boolean isPlayer1() {
    String currentPlayerName = AppCache.getInstance().getPlayerName();
    String player1Name = AppCache.getInstance().getMultiplayerRoom().getString("player1Name");
    return currentPlayerName.equals(player1Name);
  }

  /**
   * Retrieves the color associated with the current player.
   *
   * @return The color associated with the current player.
   */
  private String getCurrentPlayerColor() {
    if (isPlayer1()) {
      return Color.player1Color();
    }

    return Color.player2Color();
  }

  /**
   * Retrieves the color associated with the other player.
   *
   * @return The color associated with the other player.
   */
  private String getOtherPlayerColor() {
    if (isPlayer1()) {
      return Color.player2Color();
    }

    return Color.player1Color();
  }
}