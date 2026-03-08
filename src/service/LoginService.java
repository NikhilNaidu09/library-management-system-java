package service;

import database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class LoginService {

    Scanner sc = new Scanner(System.in);

    public boolean login() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.println("===== Library Login =====");

            System.out.print("Username: ");
            String username = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            String query = "SELECT * FROM users WHERE username=? AND password=?";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                System.out.println("Login Successful\n");
                return true;
            } else {
                System.out.println("Invalid username or password\n");
                return false;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}