package core;

import books.*;
import java.util.Scanner;
import java.util.Set;
import users.*;
import users.exceptions.*;


public class Menu {
    private LibraryService manager;
    private User user;
    private Scanner s;



    public Menu () {
        manager = new LibraryService();
        user = null;
        s = new Scanner(System.in);
    }



    public void start () {
        String id, password, idError = "", passwordError = "";
        boolean loop = true;

        while (loop)
        {
            System.out.print("\n\nWelcome to the Library Management Console App!\n\n");
            System.out.print("Login Page\n");
            System.out.print("Note: If you want to get registered on the system, please contact one of the admins.\n\n");
            System.out.print("Please enter your credentials below\n\n");

            System.out.println(idError);
            System.out.println(passwordError + '\n');
    
            System.out.print("ID: ");
            id = s.nextLine();
            
            System.out.print("Password: ");
            password = s.nextLine();
            System.out.print("\n\n\n");
    

            idError = "";
            passwordError = "";

            try {
                this.user = manager.login(id, password);

                if(user instanceof Admin) {
                    loop &= displayAdminMenu();
                } else if (user instanceof RegularUser) {
                    loop &= displayRegularUserMenu();
                } else {
                    System.out.println("unknown user type");
                }

            } catch (InvalidIDException e) {
                idError = "there is no user with the entered id. Please enter another one";
            } catch (WrongPasswordException e) {
                passwordError = "wrong password. Please enter another one";
            }
        }
    }



    private boolean displayAdminMenu () {
        boolean loop = true, shouldContinue = true;
        while (loop)
        {
            System.out.println("welcome! What do you want to do?\n");
            System.out.println("1. add new book");
            System.out.println("2. edit an existing book");
            System.out.println("3. delete a book");
            System.out.println("4. register a new user");
            System.out.println("5. log out");
            System.out.println("6. exit\n");
            System.out.print("enter your choice: ");
    
            byte choice = s.nextByte();
            this.s.nextLine();

            switch (choice)
            {
                case 1:
                    displayAddNewBookDialogue();
                    break;
                case 2:
                    displayEditBookDialogue();
                    break;
                case 3:
                    displayDeleteBookDialogue();
                    break;
                case 4:
                    displayRegisterNewUserDialogue();
                    break;
                case 5:
                    this.user = null;
                    loop = false;
                    break;
                case 6:
                    loop = false;
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("\n\nInvalid input. Enter a valid choice\n");
            }
        }
        return shouldContinue;
    }

    private void displayAddNewBookDialogue () {
        boolean loop = true;

        while (loop)
        {
            System.out.print("\n\nEnter book details\n");

            System.out.print("Enter book title: ");
            String title = this.s.nextLine();

            System.out.print("Enter book author: ");
            String author = this.s.nextLine();

            System.out.print("Enter book genre (NOVEL_ROMANTIC, NOVEL_ACTION, NOVEL_FANTASY, EDUCATIONAL_COMPUTER_SCIENCE, EDUCATIONAL_MEDICINE, EDUCATIONAL_BUSINESS, OTHER): ");
            String genre = this.s.nextLine();

            System.out.print("Enter the number of available copies for this book: ");
            int no_availableCopies = this.s.nextInt();
            this.s.nextLine();


            if (((Admin) this.user).addBook(title, author, Genre.valueOf(genre), no_availableCopies))
            {
                loop = false;
                System.out.println("\nThe books was added successfully!\n\n");
            }
            else
            {
                System.out.println("An error occured. Please check your entered values");
            }
        }
    }

    private void displayEditBookDialogue () {
        boolean loop = true;

        while (loop)
        {
            System.out.print("Enter the id of the book you want to edit: ");
            String bookId = this.s.nextLine();

            System.out.print("Select and enter the property you want to change (ID, TITLE, AUTHOR, GENRE, NO_AVAILABLE_COPIES): ");
            String attribute = this.s.nextLine();

            System.out.print("Enter its new value (Select from those if you want to edit the genre: (novel: romantic, novel: fantasy, novel: action, educational: computer_science, educational: medicine, educational: business, other)): ");
            String newValue = this.s.nextLine();

            if (((Admin) this.user).editBook(bookId, ModifiableBookAttribute.valueOf(attribute), newValue))
            {
                loop = false;
                System.out.println("The books was edited successfully!\n");
            }
            else
            {
                System.out.println("An error occured. Please check your entered values");
            }
        }
    }

    private void displayDeleteBookDialogue () {
        boolean loop = true;

        while (loop)
        {
            System.out.print("Enter the id of the book you want to delete: ");
            String bookId = this.s.nextLine();
            System.out.println();

            if (((Admin) this.user).deleteBook(bookId))
            {
                loop = false;
                System.out.println("The books was deleted successfully!");
            }
            else
            {
                System.out.println("An error occured. Please check your entered value");
            }
        }
    }

    private void displayRegisterNewUserDialogue () {
        boolean loop = true;

        while (loop)
        {
            System.out.println("Enter user details");

            System.out.print("Enter user password: ");
            String password = this.s.nextLine();

            System.out.print("Enter user name: ");
            String name = this.s.nextLine();

            System.out.print("Enter user role (ADMIN, REGULAR_USER): ");
            String role = this.s.nextLine();


            if (((Admin) this.user).registerUser(password, name, UserRole.valueOf(role)))
            {
                loop = false;
                System.out.println("\nThe user was registered successfully!\n");
            }
            else
            {
                System.out.println("An error occured. Please check your entered values");
            }
        }
    }



    private boolean displayRegularUserMenu () {
        boolean loop = true, shouldContinue = true;
        while (loop)
        {
            System.out.println("welcome! What do you want to do?\n");
            System.out.println("1. view book catalog");
            System.out.println("2. borrow book");
            System.out.println("3. return book");
            System.out.println("4. log out");
            System.out.println("5. exit\n\n");
            System.out.print("enter your choice: ");
    
            byte choice = s.nextByte();
            this.s.nextLine();
            System.out.print("\n\n");

            switch (choice)
            {
                case 1:
                    displayBookCatalog();
                    break;
                case 2:
                    displayBorrowBookDialogue();
                    break;
                case 3:
                    displayReturnBookDialogue();
                    break;
                case 4:
                    this.user = null;
                    loop = false;
                    break;
                case 5:
                    loop = false;
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("\n\nInvalid input. Enter a valid choice\n");
            }
        }
        return shouldContinue;
    }

    private void displayBookCatalog () {
        Set<Book> catalog = manager.getBookCatalog();
        
        for (Book book : catalog)
        {
            System.out.print("id: " + book.id + "\ntitle: " + book.title + "\nauthor: " + book.author + "\ngenre: " + book.genre + "\n" + "-----------------------------------------------------------------------\n\n");
        }

        System.out.println("End of catalog\n\n");
    }

    private void displayBorrowBookDialogue () {
        boolean loop = true;
        String error = "";

        while (loop)
        {
            System.out.print("\n" + error + "\n\n");
            System.out.print("Enter book ID: ");
            String bookId = this.s.nextLine();

            error = "";
            try {
                ((RegularUser) this.user).borrowBook(bookId);

                loop = false;
                System.out.println("\nThe books was borrowed successfully!\n");

            } catch (BookAlreadyBorrowedException e) {
                error = "You already borrowed a copy of this book. Please choose another book.";
            } catch (NoAvailableCopiesException e) {
                error = "Sorry, no copies of this book are available. Please check back again later.";
            } catch (NoSuchBookException e) {
                error = "no book with this id. please enter a valid id";
            }
        }
    }

    private void displayReturnBookDialogue () {
        boolean loop = true;
        String error = "";

        while (loop)
        {
            System.out.print("\n" + error + "\n\n");
            System.out.print("Enter book ID: ");
            String bookId = this.s.nextLine();
            System.out.println();

            error = "";
            try {
                ((RegularUser) this.user).returnBook(bookId);
                loop = false;
                System.out.println("The books was returned successfully!");

            } catch (ReturnUnborrowedBookException e) {
                error = "You didn't borrow this book already.";
            }
        }
    }
}
