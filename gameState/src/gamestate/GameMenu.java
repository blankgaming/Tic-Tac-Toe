
package gamestate;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Kit lai 14840038
 */
public class GameMenu {
    private GameBoard board;            //Game board
    private GameState currentState;     //current state of the game i.e enum gamestate
    private seeds currentPlayer;        //current player i.e enum seeds
    
    private static Scanner input = new Scanner(System.in);      //inputs scanner to scan user/player inputs
    
    public GameMenu(){
        this.board = new GameBoard();   //allocate game board in the memory address
        //Present game board and current status
        initializeGame();
        //play game once.  players cross and nought move alternately
        
        do{
            playerMove(currentPlayer); //updates content,currentRow and currentColumn
            this.board.paint();        //the board starts to paint/draw itself
            updateGameState(currentPlayer); 
            
            //prints a message when its game over
            if(currentState == GameState.CROSS_WIN){
                System.out.println("'X' has won the Match!");
            }else if(currentState == GameState.NOUGHT_WIN){
                System.out.println("'O' has won the Match!");
            }else if(currentState == GameState.DRAW){
                System.out.println("Its a Draw!");
            }
            //player switch
            currentPlayer = (currentPlayer == seeds.CROSS) ? seeds.NOUGHT: seeds.CROSS;
            
        }
        while (currentState == GameState.PLAYING);      //loop till game over
    }
     

    //Initialize the game board contents and current state
    public void initializeGame(){
        this.board.intialize();         //clear the game board contents
        currentPlayer = seeds.CROSS;    //Cross gonna play first
        currentState = GameState.PLAYING; //Play on
    }
    
    //Player makes one move, a valid input, update boxes content on the current row/column
    public void playerMove(seeds seed){
        boolean validInput = false;         //validates input
        
        do{
            
            if(seed == seeds.CROSS){
                System.out.print("Player 'X', enter your move at(row[1-3] column[1-3]): "); 
            }else{
                System.out.print("Player 'O', enter your move at(row[1-3] column[1-3]): ");
            }
            
            int row = input.nextInt() - 1; //since box rows starts at 0 not 1
            int columns = input.nextInt() -1; // since box columns starts at 0 not 1
            
            if(row >= 0 && row < GameBoard.ROWS && columns >= 0 && columns<GameBoard.COLUMNS
               && this.board.boxes[row][columns].content == seeds.EMPTY){
                this.board.boxes[row][columns].content = seed;
                this.board.currentRow = row;
                this.board.currentColumn = columns;
                validInput = true;
            }else{
                System.out.println("This move at (" + (row + 1) + "," + (columns + 1)
                  + ") is not valid. Please try again...");
            }
            
        }while(!validInput);
    }
    
    //updates state of game after a player has made a move
    public void updateGameState(seeds seed){
        if(this.board.hasWon(seed)){            //check if won
            currentState = (seed == seeds.CROSS) ? GameState.CROSS_WIN: GameState.NOUGHT_WIN;
            
        }else if(this.board.isDraw()){          //check if draw
            currentState = GameState.DRAW;
        }
        //if no win or draw , current state wont change and still playing
    }
    
     public static void main(String[] args) {

            try{
                new GameMenu();  
            }catch(InputMismatchException e){
                System.out.println("Please Enter a valid number. \n" + e);
                
            }
          }
}
