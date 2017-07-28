/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamestate;

/**
 *
 * @author Kit Lai
 */
public class Box {
    //Models each individual box of the game
    
    //cell content of type seeds
    seeds content; //takes value of seed.CROSS, seed.EMPTY , seed.NOUGHT
    int row, column; // the row and column of this box, wont be used in this program
    
    public Box(int row,int column){
        this.row = row;
        this.column = column;
        clear();    
    }
    
    //Label/paint itself
    public void paint(){
        switch(content){
            case EMPTY: System.out.print("  "); break;
            case CROSS: System.out.print(" X "); break; //X player
            case NOUGHT: System.out.print(" O "); break; //O player
        }
    }
    
    //clear box content to EMPTY
    public void clear(){
        this.content = seeds.EMPTY;
    }
}
