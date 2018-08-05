
package firstconnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Db {
final String JDBC_DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
final String URL="jdbc:derby:sampleDB;create=true";
final String USER_NAME="";
final String PASSWORD="";

Connection conn=null;
DatabaseMetaData dbmd=null;
Statement createStatement=null;
    public Db() {
        
    try {
        conn=DriverManager.getConnection(URL);
        System.out.println("a híd létrejött");
    } catch (SQLException ex) {
        System.out.println(""+ex);
        System.out.println("Valami baj van a connection létrehozásakor");
        Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
    }
    
        if (conn!=null) {
            try {
                createStatement=conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(""+ex);
                System.out.println("Valami baj van a createstatement létrehozásakor");
            }
        }
    
    try {
        dbmd=conn.getMetaData();
    } catch (SQLException ex) {
        Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(""+ex);
        System.out.println("Valami baj van a metadata lekérés létrehozásakor");
    }
    
    try {
        ResultSet rs=dbmd.getTables(null, "APP", "USERS", null);
        if (!rs.next()) {
            createStatement.execute("create table users(name varchar(20),address varchar(20))");
        }
                } catch (SQLException ex) {
        Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(""+ex);
        System.out.println("Valami baj van az adattáblák létrehozásakor");
    }
    }
   
    public void addUser(String name,String address){
    try { 
        String sql="insert into users values(?,?)";
        PreparedStatement prst=conn.prepareStatement(sql);
        prst.setString(1, name);
        prst.setString(2, address);
        prst.execute();
        
        
        
    } catch (SQLException ex) {
        Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(""+ex);
                System.out.println("Valami baj van a user-ek hozzáadásakor.");
    }
    
    }
    
    public void showAllUsers(){
        String sql="select* from users";
    try {
        ResultSet rs=createStatement.executeQuery(sql);
        while (rs.next()){
        String name=rs.getString("name");
        String address=rs.getString("address");
            System.out.println(name + "\n" + address);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(""+ex);
                System.out.println("Valami baj van a user-ek lekérdezésekor.");
    }
    }
    public void showMetaData(){
   String sql="select* from users";
   ResultSet rs=null;
   ResultSetMetaData rsmd=null;
   
    try {
        rs=createStatement.executeQuery(sql);
        rsmd=rs.getMetaData();
        int columnCount=rsmd.getColumnCount();
        
        for (int i = 1; i <=columnCount; i++) {
            System.out.print(rsmd.getColumnName(i)+"|");
        }
    } catch (SQLException ex) {
        Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(""+ex);
                System.out.println("Valami baj van a metaadatok lekérdezésekor.");
    }
    }
    public ArrayList<User> getAllUsers(){
        String sql="select* from users";
        ArrayList<User> users=null;
    try {
        ResultSet rs=createStatement.executeQuery(sql);
        users=new ArrayList<>();
        while (rs.next()){

        User actuallyuser=new User(rs.getString("name"), rs.getString("address"));
        users.add(actuallyuser);
        
           
        }
    } catch (SQLException ex) {
        Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(""+ex);
                System.out.println("Valami baj van a user-ek lekérdezésekor.");
    }
    return users;
    }
    
    public void addUser(User user){
    try { 
        String sql="insert into users values(?,?)";
        PreparedStatement prst=conn.prepareStatement(sql);
        prst.setString(1, user.getName());
        prst.setString(2, user.getAddress());
        prst.execute();
        
        
        
    } catch (SQLException ex) {
        Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(""+ex);
                System.out.println("Valami baj van a user-ek hozzáadásakor.");
    }
    
    }
}
