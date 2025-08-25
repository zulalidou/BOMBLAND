package com.example.bombland;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.json.JSONObject;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityClient;
import software.amazon.awssdk.services.cognitoidentity.model.Credentials;
import software.amazon.awssdk.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import software.amazon.awssdk.services.cognitoidentity.model.GetIdRequest;
import software.amazon.awssdk.services.cognitoidentity.model.GetIdResponse;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

/**
 * This class serves as an API for interacting with the app's database.
 */
public class DynamoDbClientUtil {
  private static DynamoDbAsyncClient dynamodbAsyncClient;
  private static Credentials awsCredentials;

  /**
   * This function retrieves the high scores from the database.
   */
  public static void getHighScores() {
    // The AWS credentials have expired
    if (awsCredentials == null || awsCredentials.expiration().isBefore(Instant.now())) {
      try {
        getTemporaryAwsCredentials();
      } catch (Exception e) {
        System.out.println("\n====================================================================");
        System.out.println("ERROR - getHighScores(): Could not get temporary AWS credentials.");
        System.out.println("====================================================================\n");
        return;
      }
    }

    try {
      pullHighScoresFromDb();
    } catch (CompletionException e) {
      System.out.println("\n====================================================================");
      System.out.println("ERROR - getHighScores(): Could not pull high scores from DynamoDB.");
      System.out.println("====================================================================\n");
      return;
    }

    sortHighScores();
    categorizeHighScores();
  }

  // Gets temporary AWS credentials for unauthenticated users.
  private static void getTemporaryAwsCredentials() {
    CognitoIdentityClient cognitoClient = CognitoIdentityClient.builder()
        .region(Region.US_WEST_2)
        .build();

    try {
      GetIdRequest getIdRequest = GetIdRequest.builder()
          .identityPoolId(AppCache.getInstance().getIdentityPoolId())
          .build();

      GetIdResponse getIdResponse = cognitoClient.getId(getIdRequest);
      String identityId = getIdResponse.identityId();

      GetCredentialsForIdentityRequest credentialsRequest = GetCredentialsForIdentityRequest
          .builder()
          .identityId(identityId)  // Pass the identityId retrieved from GetId
          .build();

      awsCredentials = cognitoClient.getCredentialsForIdentity(credentialsRequest).credentials();
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * This function retrieves high scores from the database.
   */
  public static void pullHighScoresFromDb() {
    dynamodbAsyncClient = DynamoDbAsyncClient.builder()
        .region(Region.US_WEST_2)
        .credentialsProvider(() -> AwsSessionCredentials.create(
            awsCredentials.accessKeyId(),
            awsCredentials.secretKey(),
            awsCredentials.sessionToken()
        ))
        .build();

    CompletableFuture<Void> allScans = CompletableFuture.allOf(
        scanTable("BOMBLAND_EasyHighScores", "Easy"),
        scanTable("BOMBLAND_MediumHighScores", "Medium"),
        scanTable("BOMBLAND_HardHighScores", "Hard")
    );

    try {
      // Waits for all scans to complete
      allScans.join();
    } catch (CompletionException e) {
      throw e;
    }

    dynamodbAsyncClient.close();
  }

  // Performs asynchronous scan operations on a given table.
  private static CompletableFuture<Void> scanTable(String tableName, String gameDifficulty) {
    ScanRequest scanRequest = ScanRequest.builder()
        .tableName(tableName)
        .build();

    CompletableFuture<ScanResponse> scanResponse = dynamodbAsyncClient.scan(scanRequest);

    // Handle the scan response
    return scanResponse.thenAccept(response -> {
      List<Map<String, AttributeValue>> highScores = response.items();
      final ArrayList<JSONObject> highScoresList = new ArrayList<>();

      for (Map<String, AttributeValue> item : highScores) {
        JSONObject highScoresObj = new JSONObject();
        highScoresObj.put("id", item.get("id").s());
        highScoresObj.put("name", item.get("name").s());
        highScoresObj.put("score", item.get("score").n());
        highScoresObj.put("time", item.get("time").n());
        highScoresObj.put("map", item.get("map").s());

        highScoresList.add(highScoresObj);
      }

      AppCache.getInstance().setHighScore(highScoresList, gameDifficulty);
    });
  }

  private static void sortHighScores() {
    sortHighScoreList(AppCache.getInstance().getHighScores("Easy"));
    sortHighScoreList(AppCache.getInstance().getHighScores("Medium"));
    sortHighScoreList(AppCache.getInstance().getHighScores("Hard"));
  }

  private static void sortHighScoreList(ArrayList<JSONObject> highScoresList) {
    highScoresList.sort(Comparator.comparingLong(a -> a.getLong("time")));
    highScoresList.sort(Comparator.comparingLong(a -> a.getLong("score")));
  }

  private static void categorizeHighScores() {
    getTopHighScoresForEachMap("Easy");
    getTopHighScoresForEachMap("Medium");
    getTopHighScoresForEachMap("Hard");
  }


  // Places the top 10 high scores of each map in the APP_CACHE (based on the difficulty provided)
  private static void getTopHighScoresForEachMap(String mode) {
    ArrayList<JSONObject> rectangleMapHighScores = new ArrayList<>();
    ArrayList<JSONObject> bombMapHighScores = new ArrayList<>();
    ArrayList<JSONObject> faceMapHighScores = new ArrayList<>();
    ArrayList<JSONObject> flowerMapHighScores = new ArrayList<>();

    for (JSONObject score : AppCache.getInstance().getHighScores(mode)) {
      if (score.getString("map").equals("Rectangle")) {
        rectangleMapHighScores.add(score);
      } else if (score.getString("map").equals("Bomb")) {
        bombMapHighScores.add(score);
      } else if (score.getString("map").equals("Face")) {
        faceMapHighScores.add(score);
      } else {
        flowerMapHighScores.add(score);
      }
    }

    trimHighScoreList(rectangleMapHighScores);
    trimHighScoreList(bombMapHighScores);
    trimHighScoreList(faceMapHighScores);
    trimHighScoreList(flowerMapHighScores);

    ArrayList<JSONObject> highScores = new ArrayList<>();
    highScores.addAll(rectangleMapHighScores);
    highScores.addAll(bombMapHighScores);
    highScores.addAll(faceMapHighScores);
    highScores.addAll(flowerMapHighScores);

    AppCache.getInstance().setHighScore(highScores, mode);
  }

  private static void trimHighScoreList(ArrayList<JSONObject> highScoresList) {
    if (highScoresList.size() > 10) {
      int startIdx = 10;
      highScoresList.subList(startIdx, highScoresList.size()).clear();
    }
  }

  /**
   * This function saves a new high score in the database.
   *
   * @param info The JSONObject that holds the score's info.
   * @param tableName The name of the table that will store the high score.
   */
  public static void saveNewHighScore(JSONObject info, String tableName) {
    Map<String, AttributeValue> newHighScoreInfo = getNewHighScoreInfo(info);

    // The AWS credentials have expired
    if (awsCredentials == null || awsCredentials.expiration().isBefore(Instant.now())) {
      getTemporaryAwsCredentials();
    }

    putScoreInDb(newHighScoreInfo, tableName);
  }

  /**
   * This function converts the score info into a format that can be stored in the database.
   *
   * @param info The JSONObject that stores the score's info.
   * @return A DynamoDB-formated map that stores info about the newest high score.
   */
  public static Map<String, AttributeValue> getNewHighScoreInfo(JSONObject info) {
    Map<String, AttributeValue> newHighScoreInfo = new HashMap<>();

    newHighScoreInfo.put("time", AttributeValue.builder().n(info.get("time").toString()).build());
    newHighScoreInfo.put("id", AttributeValue.builder().s(info.get("id").toString()).build());
    newHighScoreInfo.put("score", AttributeValue.builder().n(info.get("score").toString()).build());
    newHighScoreInfo.put("name", AttributeValue.builder().s(info.get("name").toString()).build());
    newHighScoreInfo.put("difficulty", AttributeValue.builder().s(info.get("difficulty").toString())
        .build());
    newHighScoreInfo.put("map", AttributeValue.builder().s(info.get("map").toString()).build());

    return newHighScoreInfo;
  }


  // Use temporary AWS credentials to interact with DynamoDB
  private static void putScoreInDb(Map<String, AttributeValue> newHighScoreInfo, String tableName) {
    try {
      // Use the temporary credentials to create a DynamoDB client
      DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
          .region(Region.US_WEST_2)
          .credentialsProvider(() -> AwsSessionCredentials.create(
                  awsCredentials.accessKeyId(),
                  awsCredentials.secretKey(),
                  awsCredentials.sessionToken()
          ))
          .build();

      PutItemRequest insertNewHighScoreRequest = PutItemRequest.builder()
          .tableName(tableName)
          .item(newHighScoreInfo)
          .build();

      dynamoDbClient.putItem(insertNewHighScoreRequest);
    } catch (Exception e) {
      System.out.println("\n\n__ERROR OCCURRED__:");
      e.printStackTrace();
    }
  }
}