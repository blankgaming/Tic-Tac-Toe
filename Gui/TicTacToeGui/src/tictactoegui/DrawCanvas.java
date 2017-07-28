/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoegui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;
import javax.swing.JPanel;
import static tictactoegui.TicTacToeGui.CANVAS_HEIGHT;
import static tictactoegui.TicTacToeGui.CANVAS_WIDTH;
import static tictactoegui.TicTacToeGui.CELL_PADDING;
import static tictactoegui.TicTacToeGui.CELL_SIZE;
import static tictactoegui.TicTacToeGui.COLUMNS;
import static tictactoegui.TicTacToeGui.GRID_WIDTH_HALF;
import static tictactoegui.TicTacToeGui.GRID_WIDTH;
import static tictactoegui.TicTacToeGui.ROWS;
import static tictactoegui.TicTacToeGui.SYMBOL_SIZE;
import static tictactoegui.TicTacToeGui.SYMBOL_STROKE_WIDTH;
import static tictactoegui.TicTacToeGui.board;
import static tictactoegui.TicTacToeGui.currentPlayer;
import static tictactoegui.TicTacToeGui.currentState;
import static tictactoegui.TicTacToeGui.statusBar;
import static tictactoegui.Accounts.score;

/**
 *
 * @author Kit Lai
 */
public class DrawCanvas extends JPanel {
 
    @Override
      public void paintComponent(Graphics graphics) {                  // invoke via repaint() method
         super.paintComponent(graphics);                               // fill background
         setBackground(Color.BLACK);                                   // set its background color
 
         // Draw the grid-lines
         graphics.setColor(Color.GRAY);
         for (int row = 1; row < ROWS; ++row) {
            graphics.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF,
                                    CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
         }
         for (int columns = 1; columns < COLUMNS; ++columns) {
            graphics.fillRoundRect(CELL_SIZE * columns - GRID_WIDTH_HALF, 0,
                                    GRID_WIDTH, CANVAS_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
         }
 
         // Draw the Seeds of all the cells if they are not empty
         // Using Graphics2D which allows us to set the pen's stroke
         //Can set your own colors
         Graphics2D g2d = (Graphics2D)graphics;
         g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,
                           BasicStroke.JOIN_ROUND));                          // Graphics2D only
         for (int row = 0; row < ROWS; ++row) {
            for (int columns = 0; columns < COLUMNS; ++columns) {
               int x1 = columns * CELL_SIZE + CELL_PADDING;
               int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (board[row][columns] == TicTacToeGui.Seed.CROSS) {
                        g2d.setColor(Color.GREEN);
                        int x2 = (columns + 1) * CELL_SIZE - CELL_PADDING;
                        int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (board[row][columns] == TicTacToeGui.Seed.NOUGHT) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
               }
            }
         }
       
         // Print status-bar message
         if (currentState == TicTacToeGui.GameState.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == TicTacToeGui.Seed.CROSS) {
               statusBar.setText("X Turn");
            } else {
               statusBar.setText("O Turn");
            }
         } else if (currentState == TicTacToeGui.GameState.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw for this Round! Click to play again.");
         } else if (currentState == TicTacToeGui.GameState.CROSS_WIN) {
            statusBar.setForeground(Color.GREEN);
            score++;
            statusBar.setText("'X' has Won this Round! Click to play again.");
            System.out.println(score);
         } else if (currentState == TicTacToeGui.GameState.NOUGHT_WIN) {
            statusBar.setForeground(Color.BLUE);
            score++;
            statusBar.setText("'O' has Won this Round! Click to play again.");
            System.out.println(score);
         }
      }
    }
