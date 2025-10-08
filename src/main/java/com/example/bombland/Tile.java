package com.example.bombland;

import javafx.scene.control.Button;

/**
 * This class creates and manages the states of each tile.
 */
public class Tile {
  boolean isCovered;
  boolean isFlagged;
  TileValue value;
  String backgroundColor;
  int row;
  int col;
  int surroundingBombs;
  Button tileBtn;

  enum TileValue {
    UNKNOWN,
    EMPTY,
    NUMBER,
    BOMB,
    DISABLED
  }

  Tile(Button tb) {
    isCovered = true;
    isFlagged = false;
    value = TileValue.UNKNOWN;
    backgroundColor = "";
    row = -1;
    col = -1;
    surroundingBombs = 0;
    tileBtn = tb;
  }
}