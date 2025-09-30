package com.example.bombland;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 * This class is used for caching data.
 * It also acts as an API for interacting with the app's database.
 */
public class AppCache {
  private static AppCache instance;
  private boolean gettingData;
  private String identityPoolId;
  private ArrayList<JSONObject> easyHighScores;
  private ArrayList<JSONObject> mediumHighScores;
  private ArrayList<JSONObject> hardHighScores;
  private String gameDifficulty;
  private String gameMap;
  private String mapOfHighScoresBeingShown;
  private boolean isServerConnectionGood;
  private boolean highScoresRetrievedAtLeastOnce;
  private JSONObject roomInfo;

  private AppCache() {
    gettingData = false;
    identityPoolId = "";
    easyHighScores = new ArrayList<>();
    mediumHighScores = new ArrayList<>();
    hardHighScores = new ArrayList<>();
    gameDifficulty = "";
    gameMap = "";
    mapOfHighScoresBeingShown = "";
    isServerConnectionGood = false;
    highScoresRetrievedAtLeastOnce = false;
    roomInfo = null;
  }

  /**
   * This function creates an instance of the AppCache class.
   */
  public static AppCache getInstance() {
    if (instance == null) {
      instance = new AppCache();
    }

    return instance;
  }

  boolean isGettingData() {
    return gettingData;
  }

  void setGettingData(boolean value) {
    gettingData = value;
  }

  String getIdentityPoolId() {
    return identityPoolId;
  }

  void setIdentityPoolId(String id) {
    identityPoolId = id;
  }

  String getGameDifficulty() {
    return gameDifficulty;
  }

  void setGameDifficulty(String gameDiff) {
    gameDifficulty = gameDiff;
  }

  String getGameMap() {
    return gameMap;
  }

  void setGameMap(String newMap) {
    gameMap = newMap;
  }

  String getMapOfHighScoresBeingShown() {
    return mapOfHighScoresBeingShown;
  }

  void setMapOfHighScoresBeingShown(String newMap) {
    mapOfHighScoresBeingShown = newMap;
  }

  ArrayList<JSONObject> getHighScores(String gameDifficulty) {
    if (gameDifficulty.equals("Easy")) {
      return easyHighScores;
    } else if (gameDifficulty.equals("Medium")) {
      return mediumHighScores;
    } else {
      return hardHighScores;
    }
  }

  void setHighScore(ArrayList<JSONObject> highScores, String gameDifficulty) {
    if (gameDifficulty.equals("Easy")) {
      easyHighScores = highScores;
    } else if (gameDifficulty.equals("Medium")) {
      mediumHighScores = highScores;
    } else {
      hardHighScores = highScores;
    }
  }

  boolean serverConnectionIsGood() {
    return isServerConnectionGood;
  }

  void setServerConnectionGood(boolean value) {
    isServerConnectionGood = value;
  }

  boolean highScoresHaveBeenRetrieved() {
    return highScoresRetrievedAtLeastOnce;
  }

  void setHighScoresHaveBeenRetrieved() {
    highScoresRetrievedAtLeastOnce = true;
  }

  void setRoomInfo(JSONObject newRoomInfo) {
    roomInfo = newRoomInfo;
  }

  JSONObject getRoomInfo() {
    return roomInfo;
  }
}