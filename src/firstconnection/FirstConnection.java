
package firstconnection;

import java.util.ArrayList;


public class FirstConnection {


    public static void main(String[] args) {
     Db db=new Db();
     //db.addUser("Juhi", "Budapest");
     //db.showAllUsers();
     //db.showMetaData();
     //User user1=new User("Laci", "Eger");
     //db.addUser(user1);
     ArrayList<User> users=db.getAllUsers();
     
        for (User user : users) {
            System.out.println(user.getName()+"|"+user.getAddress());
        }
    }
    
}
