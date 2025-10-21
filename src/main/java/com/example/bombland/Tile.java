package com.example.bombland;

import javafx.scene.control.Button;

/**
 * This class creates and manages the states of each tile.
 */
public class Tile {
  private boolean isCovered;
  private boolean isFlagged;
  private TileValue value;
  private String backgroundColor;
  private int row;
  private int col;
  private int surroundingBombs;

  enum TileValue {
    UNKNOWN,
    EMPTY,
    NUMBER,
    BOMB,
    DISABLED
  }

  /**
   * Default constructor.
   */
  public Tile() {
    isCovered = true;
    isFlagged = false;
    value = TileValue.UNKNOWN;
    backgroundColor = "";
    row = -1;
    col = -1;
    surroundingBombs = 0;
  }

  public boolean isCovered() {
    return isCovered;
  }

  public void setCovered(boolean covered) {
    isCovered = covered;
  }

  public boolean isFlagged() {
    return isFlagged;
  }

  public void setFlagged(boolean flagged) {
    isFlagged = flagged;
  }

  public TileValue getValue() {
    return value;
  }

  public void setValue(TileValue value) {
    this.value = value;
  }

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public int getSurroundingBombs() {
    return surroundingBombs;
  }

  public void setSurroundingBombs(int surroundingBombs) {
    this.surroundingBombs = surroundingBombs;
  }
}