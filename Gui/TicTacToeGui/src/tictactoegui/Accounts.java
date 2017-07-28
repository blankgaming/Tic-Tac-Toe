/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoegui;

/**
 *
 * @author Kit Lai
 */
public class Accounts {

    
    public String username=null;
    public String password=null;
    public static int score=0;
    public static Integer totalAccounts=0;
    
    //Get methods for username, password, score and totalAccounts
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public String getScore(){
        return this.password;
    }
    
    public Integer getTotalAccounts(){
        return this.totalAccounts;
    }
    
    //Set methods for username, password, score and totalAccounts
    public void setUsername(String username){
        if((username==null)||(username.isEmpty()))
			throw new IllegalArgumentException("Username must not be empty");
	
		this.username = username;
    }
    
    public void setPassword(String password){
        if((password==null)||(password.isEmpty()))
			throw new IllegalArgumentException("Password must not be empty");
	
		this.password = password;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public void setTotalAccounts(int totalAccounts){
        this.totalAccounts = totalAccounts;
    }
}
