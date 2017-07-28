
package gamestate;

/**
 *
 * @author Kit Lai
 */
public class GameBoard {
    //constants for the dimensions
    
    public static final int ROWS = 3;
    public static final int COLUMNS = 3;
    
    Box[][] boxes; //gameboard compose of ROWS x COLUMNS Box instances
    int currentRow, currentColumn; //current seeds row and column
    
    //constructor
    public GameBoard(){
        boxes = new Box[ROWS][COLUMNS]; //allocates array
        for(int row=0; row<ROWS;row++){//allocate element of the array
            for(int columns=0;columns<COLUMNS;columns++){
                boxes[row][columns] = new Box(row,columns);
            }
        }
    }
    
    //Intialize/Re-intialize contents of the game board
    public void intialize(){
        for(int row=0; row<ROWS;row++){
            for(int columns=0;columns<COLUMNS;columns++){
                boxes[row][columns].clear();    // clear the box contents
            }
        }
    }
    
    
    
    //creating a boolean method to ensure it returns true if the player with seed has won afer placing at currentRow,currentColumn
    public boolean hasWon(seeds seed){
        return (boxes[currentRow][0].content == seed    //3 in the row
                &&boxes[currentRow][1].content == seed
                &&boxes[currentRow][2].content == seed
              ||boxes[0][currentColumn].content == seed // 3 in the column
                &&boxes[1][currentColumn].content == seed
                &&boxes[2][currentColumn].content == seed
              ||currentRow == currentColumn             //3 in diagonal i.e from left side 0,0
                &&boxes[0][0].content == seed
                &&boxes[1][1].content == seed
                &&boxes[2][2].content == seed
              ||currentRow+currentColumn == 2           //3 in diagonal i.e from left side 2,0
                &&boxes[0][2].content == seed
                &&boxes[1][1].content == seed
                &&boxes[2][0].content == seed
                );
    }
    
    
    //returns true when there are no empty boxes or if it's a draw
    public boolean isDraw(){
        for(int row=0;row<ROWS;row++){
            for(int columns=0;columns<COLUMNS;columns++){
                if(boxes[row][columns].content == seeds.EMPTY){
                    return false;               //returns false if an empty seed is found, not a draw, so exit
                }
            }
        }
        return true;                            //else returns true, a draw has occured
    }
    
    //Paints/Draw itself
    public void paint(){
        for (int row = 0; row < ROWS; ++row) {
         for (int column = 0; column < COLUMNS; ++column) {
            boxes[row][column].paint();   // each box paints/draw itself
            if (column < COLUMNS - 1) System.out.print("|");
         }
         System.out.println();
         if (row < ROWS - 1) {
            System.out.println("-----------");
         }
      }
    }
    
}
