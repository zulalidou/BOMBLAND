package com.example.bombland;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONObject;

/**
 * If for whatever reason a high score is not saved in the main database (DynamoDB),
 * it'll temporarily be stored in SQLite. Once the app is able to save new high scores
 * to the main database, all high scores in the SQLite database will be transferred over
 * to DynamoDB. This class manages the SQLite connection and provides methods for creating
 * the necessary table, and performing basic operations.
 */
public class SqliteDbHelper {
  private static final String URL = "jdbc:sqlite:highscores.db";

  /**
   * This function tries to connect to the SQLite database.
   *
   * @return An instance of the SQLite database connection.
   * @throws SQLException is thrown if the app is not able to connect to the SQLite database.
   */
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL);
  }

  /**
   * This function checks to see it there's a table in the SQLite database called "high_score."
   * If such a table doesn't exist, it creates it.
   */
  public static void createTable() throws SQLException {
    String sql = """
                CREATE TABLE IF NOT EXISTS high_scores (
                    time INTEGER NOT NULL,
                    id TEXT NOT NULL,
                    score INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    difficulty TEXT NOT NULL,
                    map TEXT NOT NULL
                );
                """;

    try (Connection connection = getConnection();
         Statement stmt = connection.createStatement();) {
      stmt.executeUpdate(sql);
    }
  }

  /**
   * This function checks to see if there are items in the high_scores table in the SQLite database.
   * If such is the case, it'll send those items to the appropriate tables in the DynamoDB database.
   */
  public static void sendDataFromSqliteToDynamoDb() throws Exception {
    String selectSql = "SELECT time, id, score, name, difficulty, map FROM high_scores ORDER BY time ASC";
    String deleteSql = "DELETE FROM high_scores WHERE time = ? AND id = ? AND score = ? AND name = ? AND difficulty = ? AND map = ?";

    try (Connection localConnection = getConnection();
         Statement stmt = localConnection.createStatement();
         ResultSet rs = stmt.executeQuery(selectSql);) {

      while (rs.next()) {
        JSONObject newScoreInfo = new JSONObject();
        newScoreInfo.put("time", rs.getLong("time"));
        newScoreInfo.put("id", rs.getString("id"));
        newScoreInfo.put("score", rs.getLong("score"));
        newScoreInfo.put("name", rs.getString("name"));
        newScoreInfo.put("difficulty", rs.getString("difficulty"));
        newScoreInfo.put("map", rs.getString("map"));

        DynamoDbClientUtil.saveNewHighScore(newScoreInfo,
            "BOMBLAND_" + rs.getString("difficulty") + "HighScores");

        // If the condition below doesn't execute, things will still be fine because the
        // dynamoDbConnectionTask regularly pulls the high scores from DynamoDB, so
        // sooner or later all players will have access to the latest scores.
        if (AppCache.getInstance().serverConnectionIsGood()) {
          // Send new score to WebSocket server (to be distributed to other active users)
          Main.socketClient.sendHighScore(String.valueOf(newScoreInfo));
        }

        PreparedStatement deleteStmt = localConnection.prepareStatement(deleteSql);
        deleteStmt.setLong(1, rs.getLong("time"));
        deleteStmt.setString(2, rs.getString("id"));
        deleteStmt.setLong(3, rs.getLong("score"));
        deleteStmt.setString(4, rs.getString("name"));
        deleteStmt.setString(5, rs.getString("difficulty"));
        deleteStmt.setString(6, rs.getString("map"));
        deleteStmt.executeUpdate();
      }
    }
  }
}