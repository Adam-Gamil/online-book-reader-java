import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.*;

abstract class  User {

    private String userName;
    private String name;
    private String email;
    private String password;
    protected boolean admin;


    User(String userName, String name, String email, String password, boolean admin) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    User(){

    }

    // Read user details during signup
    public void enterUserDetails(String userName){
        Scanner in = new Scanner(System.in);
        this.userName = userName;
        System.out.println("Enter your Name: ");
        this.name = in.nextLine();
        System.out.println("Enter your Email: ");
        this.email = in.nextLine();
        System.out.println("Enter your Password: ");
        this.password = in.nextLine();

    }

    abstract void showMenu(BooksManager booksManager, Map<String, User> userNameAndUserObject);

    abstract void doAction(BooksManager booksManager, Map<String, User> userNameAndUserObject);

    // Print user details
    protected void Print()  {
        System.out.println("User name: " + userName + " Real name: " + name + "\n");
    }


    // Get and set user details (getter/setter methods)
    public String getEmail()  {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getName()  {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }


    String getPassword()  {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }


    String getUserName()  {
        return userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return admin;
    }


}


class Book{


    private  String title;
    private  int totalPages;
    private  String author;




    Book(String title, int totalPages, String author){
        this.title = title;
        this.totalPages = totalPages;
        this.author = author;
    }

    Book(){

    }



    // Enter book details from admin input
    void enterNewBook(String bookName) {

        Scanner in = new Scanner(System.in);
        this.title = bookName;

        System.out.println("Enter Author's Name: ");

        this.author = in.nextLine();

        System.out.println("Enter Number of Pages: ");

        this.totalPages = in.nextInt();


    }



    public void PrintBookDetails(){

        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Total Pages: " + totalPages);

    }


    // Getter and setter for book title
    String getBookName()  {
        return title;
    }
    void setBookName(String bookName) {
        this.title = bookName;
    }

    // Getter and setter for total pages
    int getTotalPages() {
        return totalPages;
    }
    void setTotalPages(int total_pages) {
        this.totalPages = total_pages;
    }


    // Getter and setter for author
    String GetAuthor()  {
        return author;
    }
    void SetAuthor(String author_name) {
        this.author = author_name;
    }

}




/*


################################################################
################################################################
########################## Customer ############################
################################################################
################################################################


 */

class Customer extends User{


   private int numOfBooks; // Tracks the number of books a user has read
   private Map<Book, AbstractMap.SimpleEntry<Integer, String>> booksIRead = new HashMap<>() ;// Stores books read by the user, with last page read and last access time

    Scanner in = new Scanner(System.in);

    Customer(String userName, String name, String email, String password) {
        super(userName, name, email, password,false);
        numOfBooks = 0;
    }
    Customer(){
        numOfBooks = 0;
        admin = false;
    }

    public static String returnCurrentTimeAndDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    // Increment the number of books read by the user
    void incrementNumberOfBooks(){
        numOfBooks++;
    }

    // Get the last page read for a particular book
    private int getLastPage(Book currentBook){
        String name = currentBook.getBookName();
        int lastPage = 0;
        for(Map.Entry<Book, AbstractMap.SimpleEntry<Integer, String>> entry : booksIRead.entrySet()){

            Book book = entry.getKey();

            if(book.getBookName().equals(name)){
                lastPage = entry.getValue().getKey();

                return lastPage;
            }
        }

        return lastPage;
    }

    // Set the last page read for a book and record the time
    private void setLastPage(int num,Book currentBook, String lastTime){

        booksIRead.put(currentBook, new AbstractMap.SimpleEntry<>(num, lastTime));
    }

    // Check if a user has already read the book
    private boolean bookFound(Book currentBook){

        if (booksIRead.containsKey(currentBook)) {
            return true;  // User already started reading in this book
        } else {
            booksIRead.put(currentBook, new AbstractMap.SimpleEntry<>(0, ""));
            return false; // User didn't start reading in this book, initialize book progress with 0 pages
        }


    }

    // Check if the user has read any book
    private boolean checkUserBook(){

        return numOfBooks != 0;
    }

    // List all books read by the user with progress
    public void listAllBookIRead(){

        int cnt = 1;
        for(Map.Entry<Book, AbstractMap.SimpleEntry<Integer, String>> entry : booksIRead.entrySet()){

            System.out.println(cnt + "-Name of the book: "+  entry.getKey().getBookName() + "\n Number of pages you read: ("+ entry.getValue().getKey()+") out of ("+entry.getKey().getTotalPages()+")\n");
            System.out.println("Last time opened the book: "+ entry.getValue().getValue() + "\n");
            if(Objects.equals(entry.getValue().getKey(), entry.getKey().getTotalPages())){
                System.out.println("You finished the book: "+ entry.getKey().getBookName() + "\n");
            }

            cnt++;
        }

    }

    @Override
    void showMenu(BooksManager booksManager, Map<String, User> userNameAndUserObject) {
        showCustomerMenu(booksManager);
    }

    @Override
    void doAction(BooksManager booksManager, Map<String, User> userNameAndUserObject) {

    }

    // Show menu options for customer users
    void showCustomerMenu(BooksManager booksManager){
        while(true){

            System.out.println("\nChoose on of the following options:\n1- List all available books\n2- See my progress\n3- continue reading\n4- Exit");
            String choice;
            choice = in.nextLine();
            if(Objects.equals(choice, "1")){
                if(!booksManager.checkEmpty()){
                    System.out.println("There are no books available at the moment. Come back soon when books are available\n ");
                }
                else{
                    booksManager.listAllBooks();
                }
            }
            else if(Objects.equals(choice, "2")){
                if(!checkUserBook()){
                    System.out.println("You haven't read any book yet. Start reading a book now\n");
                }
                else{
                    listAllBookIRead();
                }
            }
            else if(Objects.equals(choice, "3")){
                if(!booksManager.checkEmpty()){
                    System.out.println("There are no books available at the moment. Come back soon when books are available\n ");
                    continue;
                }
                if(!checkUserBook()){
                    System.out.println("You haven't read any book yet. do you want to choose a book now ? (1-Yes anything else-No)\n");
                    String option;
                    option = in.nextLine();
                    if(!option.equals("1")){
                        continue;
                    }
                }
                booksManager.listAllBooks();
                int option;
                System.out.println( "\nChoose the book you want to read: \n");
                while(true) {
                    option = in.nextInt();
                    if (option < 0 || option > booksManager.numberOfBooks()) {
                        System.out.println("Please,choose a correct option\n");
                    }
                    else{
                        break;
                    }
                }
                Book cur_book = booksManager.GetBook(option);
                reading(cur_book);

            }
            else if(Objects.equals(choice, "4")){
                System.out.println("\n\n");
                break;
            }
            else{
                System.out.println("Choose a correct choice please\n\n");
            }

        }

    }

    // Read a book, save progress, and update user's reading list
    void reading(Book cur_book){

        int last_page = getLastPage(cur_book);
        if(last_page == 0 && !bookFound(cur_book)){
            incrementNumberOfBooks();
        }
        boolean finished_book = false;
        while(true){
            int choice;
            if(last_page == 0){
                System.out.println("1- next page\n2- exit");
                choice = in.nextInt();
                if(choice == 1){
                    System.out.println("You are now reading page (" + (++last_page) + ")\n");
                }
                else if (choice == 2){
                    break;
                }
                else{
                    System.out.println("Choose a correct choice please\n\n");
                }
            }
            else if(last_page < cur_book.getTotalPages()){
                System.out.println("1- next page\n2- previous page\n 3- exit");
                choice = in.nextInt();
                if(choice == 1){
                    System.out.println("You are now reading page (" + (++last_page) + ")\n");
                }
                else if (choice == 2){
                    if(last_page == 1){
                        System.out.println("You can't go back further\n ");
                    }
                    else {
                        System.out.println("You are now reading page (" + (--last_page) + ")\n");
                    }
                }
                else if (choice == 3){
                    break;
                }
                else{
                    System.out.println("Choose a correct choice please\n\n");
                }

            }
            else{
                if(!finished_book){
                    System.out.println("congrats you just finished reading (" + cur_book.getBookName() +") \n");
                    finished_book = true;
                }
                System.out.println("1- previous\n2- exit");
                choice = in.nextInt();
                if(choice == 1){
                    System.out.println("You are now reading page (" + (--last_page) + ")\n");
                }
                else if (choice == 2){
                    break;
                }
                else{
                    System.out.println("Choose a correct choice please\n\n");
                }
            }
        }
        setLastPage(last_page,cur_book,returnCurrentTimeAndDate());
        in.nextLine();  // Clear buffer
        System.out.println("\n\n");

    }

}

class Admin extends User{



    Admin(String userName, String name, String email, String password) {
        super(userName, name, email, password,true);
    }
    Admin(){
        admin = true;
    }

    Scanner in = new Scanner(System.in);

    @Override
    void showMenu(BooksManager booksManager, Map<String, User> userNameAndUserObject){
        showAdminMenu(booksManager, userNameAndUserObject);
    }

    @Override
    void doAction(BooksManager booksManager, Map<String, User> userNameAndUserObject) {
        booksManager.addBook();
    }

    // Show menu options for admin users
    public void showAdminMenu(BooksManager booksManager, Map<String, User> userNameAndUserObject){
        String choice;
        while(true) {
            System.out.println("\nChoose on of the following options:\n1- Add a new book\n2- View all users\n3- Exit\n");

            choice = in.nextLine();

            if (Objects.equals(choice, "1")) {

                doAction(booksManager, userNameAndUserObject);

            }
            else if(Objects.equals(choice, "2")){
                for (Map.Entry<String, User> it : userNameAndUserObject.entrySet()) {
                    if(!it.getValue().isAdmin()) {
                        it.getValue().Print();
                        System.out.println("\n\n");
                    }
                }
            }
            else if (Objects.equals(choice, "3")){
                System.out.println("\n\n");

                break;
            }
            else{
                System.out.println("Enter a correct choice please\n\n");
            }


        }
    }
}




class BooksManager{

    private Map<String, Book> bookNameBookObjectMap = new HashMap<>();// Map to store books by their titles
    int numOfBooks;// Static variable to track the number of books

    Scanner in = new Scanner(System.in);



    BooksManager() {
        loadBooksFromFile("books.txt"); // Load books when BooksManager is created
    }

    private void loadBooksFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isBookSection = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.equals("# Books")) {
                    isBookSection = true;
                    continue;
                }
                if (!isBookSection) continue;

                String[] parts = line.split(",");
                if (parts.length != 3) continue;

                String title = parts[0].trim();
                int totalPages = Integer.parseInt(parts[1].trim());
                String author = parts[2].trim();

                Book book = new Book(title, totalPages, author);
                bookNameBookObjectMap.put(title, book);
                numOfBooks++;
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading books file: " + e.getMessage());
        }
    }
    // Add a new book to the system

    public void addBook() {
        // Allocate memory for a new book
        // Pointer to the current book being added
        Book currentBook = new Book();

        String title;
        // Prompt admin to fill in book details
        System.out.println( "Enter book title: ");

        title = in.nextLine();

        currentBook.enterNewBook(title);

        // Store the book in the map using the title as the key

        bookNameBookObjectMap.put(title, currentBook);

        numOfBooks++;
        System.out.println( "Book added successfully!\n");
    }

    // List all books stored in the system
    void listAllBooks()  {
        int cnt = 1;
        for (Map.Entry<String,Book> pair : bookNameBookObjectMap.entrySet()) {
            System.out.println("Book("+ cnt + ") :\n");
            pair.getValue().PrintBookDetails();
            System.out.println("\n\n");
            cnt++;
        }
    }


    // Check if there are any books in the system
    boolean checkEmpty() {
        return numOfBooks != 0;
    }

    // Return the number of books
    int numberOfBooks()  {
        return numOfBooks;
    }

    // Retrieve a book based on its index
    Book GetBook(int place) {
        int cnt = 0;
        place--;

        if(place < 0 || place > bookNameBookObjectMap.size()){
            return null;
        }
        for (Map.Entry<String,Book> pair : bookNameBookObjectMap.entrySet()) {
           if (place == cnt) {
               return pair.getValue();
           }
           cnt++;
        }


        return null;  // Return null if the index is invalid
    }

}




class UserManager{

   Scanner in = new Scanner(System.in);


   private User currentUser; //  current logged-in user
   private Map<String, User> userNameAndUserObject = new HashMap<>(); // Map to store users by their usernames

    UserManager() {
        loadUsersFromFile("users.txt"); // Load users when UserManager is created
    }

    private void loadUsersFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isCustomer = false, isAdmin = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.equals("# Customers")) {
                    isCustomer = true;
                    isAdmin = false;
                    continue;
                } else if (line.equals("# Admins")) {
                    isCustomer = false;
                    isAdmin = true;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                String userName = parts[0].trim();
                String name = parts[1].trim();
                String email = parts[2].trim();
                String password = parts[3].trim();

                if (isCustomer) {
                    userNameAndUserObject.put(userName, new Customer(userName, name, email, password));
                } else if (isAdmin) {
                    userNameAndUserObject.put(userName, new Admin(userName, name, email, password));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
    }

    // Function to log in or sign up
    void AccessSystem() {
        System.out.println("Choose on of the following options:\n1- Login\n2- Signup\n3- Exit");
        int choice;
        choice = in.nextInt();
        in.nextLine();  // Clear buffer
        if (choice == 1)
            DoLogin();
        else if(choice == 2)
            DoSignUp();
        else
            System.exit(0);
    }

    // Show the appropriate menu based on user role
    void ShowMenu(BooksManager booksManager){

        currentUser.showMenu(booksManager, userNameAndUserObject);
    }

    // login menu
    void DoLogin() {


        while (true) {
            String name, pass;
            System.out.println("Enter user name: ");
            name = in.nextLine();
            System.out.println("Enter password: ");
            pass = in.nextLine();

            // First, check if the username exists
            if (!userNameAndUserObject.containsKey(name)) {
                System.out.println("\nInvalid user name or password. Try again\n\n");
                continue;
            }

            // Retrieve the user object from the map
            User user_exist = userNameAndUserObject.get(name);

            // Now check if the password is correct
            if (!Objects.equals(pass, user_exist.getPassword())) {
                System.out.println("\nInvalid user name or password. Try again\n\n");
                continue;
            }

            // If both username and password are correct, assign the user to current_user
            currentUser = user_exist;
            break;
        }

    }

    // sign up menu
    void DoSignUp() {
        String userName;

        while (true) {
            System.out.println("Enter user name. (No spaces): ");
            userName = in.next();

            if (userNameAndUserObject.containsKey(userName)) {
                System.out.println("\nuser name is already taken\n\n");
            }
            else{
                break;
            }

        }


        currentUser = new Customer();  // Allocate memory for the new user
        currentUser.enterUserDetails(userName);

        userNameAndUserObject.put(currentUser.getUserName(), currentUser);

    }



    public void ListUsersNamesIds() {
        for (Map.Entry<String,User> pair : userNameAndUserObject.entrySet()) {
            System.out.println("\t\tName: "+ pair.getValue().getName() + "\n");
        }
    }

    User GetCurrentUser()  {
        return currentUser;
    }

}

class OnlineBookReader{


    private UserManager usersManager; // pointer for a current users manager
    private BooksManager booksManager;  // pointer for a current books manager


    OnlineBookReader() {
        usersManager = new UserManager();  // Allocate UsersManager
        booksManager = new BooksManager();  // Allocate BooksManager
    }


    void Run_app(){
        System.out.println("Welcome to the Online Book Reader!\n");
        while(true) {
            usersManager.AccessSystem();
            usersManager.ShowMenu(booksManager);
        }


    }

};



public class Main {
    public static void main(String[] args) {

       OnlineBookReader reader = new OnlineBookReader();
       reader.Run_app();

    }
}