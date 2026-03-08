package service;

import database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class LibraryService {

    Scanner sc = new Scanner(System.in);

    // ADD BOOK
    public void addBook() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Book Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Author: ");
            String author = sc.nextLine();

            String query = "INSERT INTO books(title,author,status) VALUES(?,?,?)";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, title);
            pst.setString(2, author);
            pst.setString(3, "Available");

            pst.executeUpdate();

            System.out.println("Book Added Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW BOOKS
    public void viewBooks() {
        try {
            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | " +
                        rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ISSUE BOOK
   public void issueBook() {

    try {

        Connection con = DBConnection.getConnection();

        System.out.print("Enter Book ID to issue: ");
        int id = sc.nextInt();

        String updateBook = "UPDATE books SET status='Issued' WHERE id=?";

        PreparedStatement pst1 = con.prepareStatement(updateBook);
        pst1.setInt(1, id);
        pst1.executeUpdate();

        String insertHistory = "INSERT INTO issue_history(book_id,issue_date) VALUES(?,CURDATE())";

        PreparedStatement pst2 = con.prepareStatement(insertHistory);
        pst2.setInt(1, id);
        pst2.executeUpdate();

        System.out.println("Book Issued Successfully");

    } catch(Exception e) {
        e.printStackTrace();
    }
}

    // RETURN BOOK
    public void returnBook() {

    try {

        Connection con = DBConnection.getConnection();

        System.out.print("Enter Book ID to return: ");
        int id = sc.nextInt();

        String updateBook = "UPDATE books SET status='Available' WHERE id=?";

        PreparedStatement pst1 = con.prepareStatement(updateBook);
        pst1.setInt(1, id);
        pst1.executeUpdate();

        String updateHistory =
        "UPDATE issue_history SET return_date=CURDATE(), fine = DATEDIFF(CURDATE(),issue_date)*2 WHERE book_id=? AND return_date IS NULL";

        PreparedStatement pst2 = con.prepareStatement(updateHistory);
        pst2.setInt(1, id);
        pst2.executeUpdate();

        System.out.println("Book Returned Successfully");

    } catch(Exception e) {
        e.printStackTrace();
    }
}

    // SEARCH BOOK
    public void searchBook() {
        try {
            Connection con = DBConnection.getConnection();

            sc.nextLine();

            System.out.print("Enter Book Title: ");
            String title = sc.nextLine();

            String query = "SELECT * FROM books WHERE title LIKE ?";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, "%" + title + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | " +
                        rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE BOOK
    public void deleteBook() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Book ID to delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM books WHERE id=?";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("Book Deleted Successfully");
            } else {
                System.out.println("Book not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void dashboard() {

    try {

        Connection con = DBConnection.getConnection();

        Statement st = con.createStatement();

        ResultSet total = st.executeQuery("SELECT COUNT(*) FROM books");
        total.next();
        int totalBooks = total.getInt(1);

        ResultSet issued = st.executeQuery("SELECT COUNT(*) FROM books WHERE status='Issued'");
        issued.next();
        int issuedBooks = issued.getInt(1);

        ResultSet available = st.executeQuery("SELECT COUNT(*) FROM books WHERE status='Available'");
        available.next();
        int availableBooks = available.getInt(1);

        System.out.println("\n===== Library Dashboard =====");
        System.out.println("Total Books: " + totalBooks);
        System.out.println("Issued Books: " + issuedBooks);
        System.out.println("Available Books: " + availableBooks);

    } catch(Exception e) {
        e.printStackTrace();
    }
}
}