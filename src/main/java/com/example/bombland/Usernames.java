package com.example.bombland;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This class generates usernames for users.
 */
public class Usernames {
  static final ArrayList<String> Colors = new ArrayList<>(Arrays.asList(
      "red", "green", "blue", "indigo",
      "orange", "yellow", "violet", "gray",
      "maroon", "black", "olive", "cyan",
      "pink", "magenta", "tan", "teal"
  ));

  static final ArrayList<String> Adjectives = new ArrayList<>(Arrays.asList(
      "angry", "big", "clever", "sweaty",
      "clumsy", "tough", "deadly", "fancy",
      "hairy", "happy", "bold", "nice",
      "noisy", "odd", "quiet", "smart"
  ));

  static final ArrayList<String> Animals = new ArrayList<>(Arrays.asList(
      "dog", "cat", "horse", "lion",
      "tiger", "snake", "deer", "bear",
      "wolf", "goat", "koala", "giraffe",
      "turtle", "shark", "otter", "panda"
  ));

  static final ArrayList<String> Names = new ArrayList<>(Arrays.asList(
      "anna", "alex", "sam", "james",
      "john", "toby", "tom", "noah",
      "emma", "amelia", "charlotte", "sophie",
      "isabella", "henry", "nina", "marc"
  ));

  /**
   * This function returns the first half of an auto-generated username.
   *
   * @return a string that either represents a color or an adjective.
   */
  protected static String getFirstHalfOfName() {
    Random random = new Random();
    int randomNum = random.nextInt(2);
    int randomIdx = random.nextInt(16);

    if (randomNum == 0) {
      return Colors.get(randomIdx);
    } else {
      return Adjectives.get(randomIdx);
    }
  }

  /**
   * This function returns the second half of an auto-generated username.
   *
   * @return a string that either represents an animal or a human name.
   */
  protected static String getSecondHalfOfName() {
    Random random = new Random();
    int randomNum = random.nextInt(2);
    int randomIdx = random.nextInt(16);

    if (randomNum == 0) {
      return Animals.get(randomIdx);
    } else {
      return Names.get(randomIdx);
    }
  }
}