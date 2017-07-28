
package tictactoegui;

/**
 *
 * @author Kit Lai      14840038
 */

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import javax.swing.*;
import static tictactoegui.Accounts.score;

public class TicTacToeGui extends JFrame{

    public static final int ROWS = 3;                                       // rows and columns
   public static final int COLUMNS = 3;
 
   // Named-constants of the various dimensions used for graphics drawing
   public static final int CELL_SIZE = 200;                                 // cell width and height (square size)
   public static final int CANVAS_WIDTH = CELL_SIZE * COLUMNS;              // the drawing canvas
   public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
   public static final int GRID_WIDTH = 7;                                  // Grid-line width
   public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;                // Grid-line half-width
   
   // Symbols (cross/nought) are displayed inside a cell, with padding from border
   public static final int CELL_PADDING = CELL_SIZE / 6;
   public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;      // width/height
   public static final int SYMBOL_STROKE_WIDTH = 8;                         // pen stroke width
 
   public static GameState currentState;                                    // the current game state
   public static GameState GameState;
   public static Seed currentPlayer;                                        // the current player
   public static Seed[][] board   ;                                         // Game board of ROWS-by-COLUMNS cells
   public static Seed Seed;
   private DrawCanvas canvas;                                               // Drawing canvas (JPanel) for the game board
   public static JLabel statusBar;                                          // Status Bar
   private HashMap<String, Accounts> users;
   
   
   
   /** Constructor to setup the game board and GUI components */
   public TicTacToeGui() {
      canvas = new DrawCanvas();                                            // Construct a drawing canvas (a JPanel)
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));  //Dimension size
 
      // The canvas (JPanel) makes a MouseEvent upon mouse-click
      canvas.addMouseListener(new MouseAdapter() {
         @Override                                                          //override MouseListener/MouseAdapter
         public void mouseClicked(MouseEvent e) {                           // mouse-clicked handler
            int mouseX = e.getX();
            int mouseY = e.getY();
            
            // Getting the row and column clicked
            int rowSelected = mouseY / CELL_SIZE;
            int columnSelected = mouseX / CELL_SIZE;
 
            if (currentState == GameState.PLAYING) {
               if (rowSelected >= 0 && rowSelected < ROWS && columnSelected >= 0
                     && columnSelected < COLUMNS && board[rowSelected][columnSelected] == Seed.EMPTY) {
                  board[rowSelected][columnSelected] = currentPlayer;       // Makes a move
                  updateGame(currentPlayer, rowSelected, columnSelected);   // updates state
                  // Switches player
                  currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
               }
            } else {                                                         // game over
               initGame();                                                  // restarts the game
            }
            // Refresh drawing canvas
            repaint();                                                      // Calls-back paintComponent().
         }
      });
      
      // Setup the status bar (JLabel) to display status message
      statusBar = new JLabel("  ");
      statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
      statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
 
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(canvas, BorderLayout.CENTER);
      cp.add(statusBar, BorderLayout.PAGE_END);                             // same as SOUTH
      //cp.add(score, BorderLayout.SOUTH);
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();                                                               // pack all the components in this JFrame
      setTitle("Tic Tac Toe");
      setVisible(true);                                                     // show this JFrame
 
      board = new Seed[ROWS][COLUMNS];                                      // allocates array ROWS by COLUMNS
      initGame();                                                           // initialize the game board contents and game variables
   }

   /* Initialize the game-board contents and the status */
   public void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int columns = 0; columns < COLUMNS; ++columns) {
            board[row][columns] = Seed.EMPTY;                               // all cells empty
         }
      }
      currentState = GameState.PLAYING;                                     // ready to play
      currentPlayer = Seed.CROSS;                                           // cross starts first
   }
 
   /* Updates the currentState after the player with "theSeed" has placed on
       (rowSelected, columnSelected). */
   public void updateGame(Seed theSeed, int rowSelected, int columnSelected) {
       
       if (hasWon(theSeed, rowSelected, columnSelected)) {                  // check for win
         currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WIN : GameState.NOUGHT_WIN;
         
      } else if (isDraw()) {                                                // checks for draw
         currentState = GameState.DRAW;
      }
      // Otherwise, no change to current state (still GameState.PLAYING).
   }
 
   /** Return true if it is a draw (i.e no more empty cell) */
   public boolean isDraw() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLUMNS; ++col) {
            if (board[row][col] == Seed.EMPTY) {
               return false;                                                // an empty cell found, not draw, exits
            }
         }
      }
      return true;                                                          // no more empty cell, it's a draw
   }
 
   /** Return true if the player with "theSeed" has won after placing at
       (rowSelected, columnSelected) */
   public boolean hasWon(Seed theSeed, int rowSelected, int columnSelected) {
      return (board[rowSelected][0] == theSeed                               // 3-in-the-row
            && board[rowSelected][1] == theSeed
            && board[rowSelected][2] == theSeed
       ||      board[0][columnSelected] == theSeed                           // 3-in-the-column
            && board[1][columnSelected] == theSeed
            && board[2][columnSelected] == theSeed
       || rowSelected == columnSelected                                      // 3-in-the-diagonal
            && board[0][0] == theSeed
            && board[1][1] == theSeed
            && board[2][2] == theSeed
       || rowSelected + columnSelected == 2                                 // 3-in-the-opposite-diagonal
            && board[0][2] == theSeed
            && board[1][1] == theSeed
            && board[2][0] == theSeed);
   }
   
   //Checks User usernames and score
   public Accounts checkUser(String username,String password) {
        Accounts user;
        if (this.users.containsKey(username)) {
            user = this.users.get(username);
            System.out.println("Your current score: " + user.score);
        }else if(this.users.containsKey(password)){
            user = this.users.get(password);
        } 
        else {
            user = new Accounts();
            this.users.put(username, user);
        }
        return user;
    }
      
   
    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            DrawCanvas canvas;
            TicTacToeGui ttgui = new TicTacToeGui();
            
       /**CUI prompt user information to save accounts
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String user = scanner.nextLine();
            System.out.print("Enter your password: ");
            String userpw = scanner.nextLine();
            Accounts users = ttgui.checkUser(user,userpw); **/
            
         }
      });
    }
        
}
