package com.example.bombland;

import java.util.Objects;

/**
 * This class is used to store x and y coordinates of tiles.
 */
public class TileCoordinates {
  private int row;
  private int col;

  /**
   * Default constructor.
   */
  public TileCoordinates() {}

  /**
   * Parameterized constructor.
   *
   * @param r the row number.
   * @param c the column number.
   */
  public TileCoordinates(int r, int c) {
    row = r;
    col = c;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int r) {
    row = r;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int c) {
    col = c;
  }

  /**
   * This function defines what makes two TileCoordinates objects identical. In this case, two
   * objects should be equal if their row and col coordinates match.
   *  - This function is necessary in order to use TileCoordinates variables to retrieve values from
   *    a hashmap.
   *
   * @return a hash value.
   */
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    TileCoordinates tcObj = (TileCoordinates) obj;

    return row == tcObj.row && col == tcObj.col;
  }

  /**
   * This function must return the same integer for two objects that are considered equal by
   * equals().
   *  - This function is necessary in order to use TileCoordinates variables to retrieve values from
   *    a hashmap.
   *
   * @return a hash value.
   */
  public int hashCode() {
    return Objects.hash(row, col);
  }
}