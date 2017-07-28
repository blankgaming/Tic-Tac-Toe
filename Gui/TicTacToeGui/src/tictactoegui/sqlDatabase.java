/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoegui;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter.Entry;
import static tictactoegui.Accounts.totalAccounts;
/**
 *
 * @author Kit Lai
 */
public class sqlDatabase {
    Connection connection = null;
    String url = "jdbc:derby://localhost:1527/TicTacToeDB;",username,password;
    Statement statement;
    ResultSet rs;
    
    public void autoConnectTicTacToeDB(){
        try{
            connection = DriverManager.getConnection(url);                  //connects to the database
            
            //database query
            statement = connection.createStatement();                       //create statement object with connection to database
            checkTableExisting("Accounts");
            String mySql = "SELECT PLAYERNAME, PASSWORD, SCORE";            //database attributes
            rs = statement.executeQuery(mySql);                             //create result set to query the database and gets results
            
            while(rs.next()){
                Accounts player = new Accounts();
                player.setUsername(rs.getString("PLAYERNAME"));
                player.setPassword(rs.getString("PASSWORD"));
                player.setScore(rs.getInt("SCORE"));
                
            }
            
            rs.close();                                                     //close result set
            statement.close();                                              //close statement
            connection.close();                                             //close connection
        }catch(SQLException se){
            System.err.println("SQL Exception: "+se.getMessage());
        }catch(Exception e){                                                //Handle errors 
            databaseError();
      }
    }
    
    private void checkTableExisting(String newTableName) {
        try {
            System.out.println("check existing tables.... ");
            String[] types = {"Accounts"};
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);    //types
            Statement dropStatement = null;

            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                System.out.println("found: " + tableName);
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + "  needs to be deleted");
                    String sqlDropTable = "DROP TABLE " + newTableName;
                    dropStatement = connection.createStatement();
                    dropStatement.executeUpdate(sqlDropTable);
                    System.out.println("table deleted");
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
            if (dropStatement != null) {
                dropStatement.close();
            }

        } catch (SQLException ex) {
        }

    }

    // add the hashmap of accounts into the database
    public void updateDatabase(){
        
        try {
            connection = DriverManager.getConnection(url);                  //connects to the database
            statement = connection.createStatement();                       //create statement object with connection to database
            
            // overides the current ACCOUNTS table with new accounts
            statement.executeUpdate("DROP TABLE ACCOUNTS");                 //drops the current table
            
            // create the new table
            String sqlCreateTable="CREATE TABLE ACCOUNTS (PLAYERNAME varchar(20), PASSWORD varchar(12), SCORE int)";

            statement.executeUpdate(sqlCreateTable);                       //create new table
           // Set<Entry<Integer, Accounts>> set;                            
            //set = totalAccounts.entrySet();
            //Iterator i = set.iterator();                                   //create Iterator

           // while(i.hasNext()) {
            //   Map.Entry me = (Map.Entry)i.next();

               Accounts player = new Accounts();
               //((Accounts) me.getValue());               //player object for each object in the hashmap
              
                    statement.executeUpdate("INSERT INTO ACCOUNTS (PLAYERNAME, PASSWORD, SCORE) VALUES ("
                    +player.getUsername()+",'"+player.getPassword()+"','"+player.getScore()+")");

              // }
            statement.close();                                                  //close the statement
            connection.close();                                                 //close the connection
            
        } catch (SQLException ex) {
            databaseError();
        }
    }
    
    // if any exception is thrown because of database, notify user
    private static void databaseError(){
        JOptionPane.showMessageDialog(null,"An error has occurred with the database.");
    }
}
