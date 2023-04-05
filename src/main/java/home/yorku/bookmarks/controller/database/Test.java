package home.yorku.bookmarks.controller.database;

public class Test {
    public static void main(String[] args) {
        ConnectionMethods method = new ConnectionMethods();
        int test = method.insertUser("ethan", "thispassword","ethanhawk693@gmail.com");
        int test2 = method.insertUser("ethan", "thispassword","ethanhawk693@gmail.com");
        int test3 = method.insertUser("fifle", "thispassword","example@gmail.com");

        if(test3 == 0){
            System.out.println("User added");

        } else if (test3 == 1){
            System.out.println("Username duplicated");

        } else if (test3 == 2){
            System.out.println("Email duplicated");

        } else {
            System.out.println("Error");
        }
    }
}
