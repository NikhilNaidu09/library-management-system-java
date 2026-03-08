import java.util.Scanner;
import service.LibraryService;
import util.Menu;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LibraryService lib = new LibraryService();

        while (true) {

            Menu.showMenu();

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    lib.dashboard();
                    break;

                case 2:
                    lib.addBook();
                    break;

                case 3:
                    lib.viewBooks();
                    break;

                case 4:
                    lib.issueBook();
                    break;

                case 5:
                    lib.returnBook();
                    break;

                case 6:
                    lib.searchBook();
                    break;

                case 7:
                    lib.deleteBook();
                    break;

                case 8:
                    System.exit(0);

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}