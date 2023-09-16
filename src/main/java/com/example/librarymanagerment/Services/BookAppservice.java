package com.example.librarymanagerment.Services;

import com.example.librarymanagerment.Enities.Book;

import java.io.*;
import java.util.*;

public class BookAppservice {
    List<Book> bookList;
    Scanner scanner;
    File file;

    public BookAppservice() {
        scanner = new Scanner(System.in);
        bookList = new ArrayList<>();
        file = new File("data.text");
    }

    // Create data when run app
    public void runCreateFile(){
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(file)))  {

                        bookList.add(new Book("A", "1", 1000, 5));
                        bookList.add(new Book("B", "2", 2999, 6));
                        bookList.add(new Book("C", "3", 2002, 3));

                        int count = 0;
                        for (Book book : bookList) {
                            String input = book.toString();
                            writer.write(input.toLowerCase());
                            count++;
                            if (count < bookList.size()) {
                                writer.newLine();
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println("exception : " + ex.getMessage());
                    }
                }
            } catch (IOException ex) {
                System.out.printf("exception createFile: " + ex.getMessage());
            }
        }
    }

    // Get list all book
    public List<Book> getListBook() {
        try
        {
            BufferedReader bufferedReader = new BufferedReader (new FileReader(file));
            String line = bufferedReader.readLine();
            bookList.clear();
            while (line != null) {
                String[] parts = line.split(", ");
                if (parts.length == 4) {
                    String title = parts[0].trim().substring(7);
                    String author = parts[1].trim().substring(8);
                    int publishYear = Integer.parseInt(parts[2].trim().substring(8).trim());
                    int quantity = Integer.parseInt(parts[3].trim().substring(10).trim());
                    bookList.add(new Book(title, author, publishYear, quantity));
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException  e)
        {
            e.printStackTrace();
        }
        return bookList;
    }
    // Create book
    public void createBook(){
        try{
            boolean flag = false;
            int isflag;
            do {
                System.out.println("How many more books do you want?: ");
                int input = scanner.nextInt();
                scanner.nextLine();

                for (var i= 1; i <= input; i++){
                    bookList.clear();
                    System.out.println("Title: ");
                    String title = scanner.nextLine();
                    while (exitsTitle(title)){
                        System.out.println("Title: ");
                        title = scanner.nextLine();
                    }
                    System.out.println("Author name: ");
                    String author = scanner.nextLine();
                    System.out.println("Publish year: ");
                    int publish = scanner.nextInt();
                    System.out.println("Quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    var book = new Book(title, author, publish, quantity);

                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                    writer.newLine();
                    writer.write(book.toString().toLowerCase());
                    System.out.println("Add book successfully !");
                    writer.close();
                }

                System.out.println("Do you want to create?: ");
                System.out.println("1: Yes");
                System.out.println("2: No");
                isflag = scanner.nextInt();
                switch (isflag){
                    case 1:
                        flag = true;
                        break;
                    case 2:
                        flag = false;
                        break;
                }
            }while (flag);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Search book by title or author
    public void searchBookByTitleOrAuthor() {
            List<Book> foundBooks = new ArrayList<>();
            boolean flag = false;
            int isflag;
            List<Book> listBook = getListBook();
            do {
                foundBooks.clear();
                System.out.println("Search by title or author: ");
                String input = scanner.nextLine().trim();

                // Get list all book to check and add in list =/
                for (Book book : listBook) {
                    if (book.getTitle().equalsIgnoreCase(input) || book.getAuthorName().equalsIgnoreCase(input)) {
                        foundBooks.add(book);
                    }
                }

                // Get list add when search
                for (Book book : foundBooks) {
                    System.out.println(book);
                }
                System.out.println("Not found: " + input);
                System.out.println("Do you want to search?: ");
                System.out.println("1: Yes");
                System.out.println("2: No");
                isflag = scanner.nextInt();
                scanner.nextLine();

                switch (isflag){
                   case 1:
                       flag = true;
                       break;
                   case 2:
                       flag = false;
                       break;
               }
            }while (flag);

    }

    // Borrow book
    public void borrowBook(){
        List<Book> listBook = getListBook();
        try{
            System.out.println("Search books by title: ");
            String title = scanner.nextLine().trim();
            System.out.println("How many books do you want to borrow: ");
            int quantity = scanner.nextInt();
            while (exitsQuantityBorrowBook(title, quantity)){
                quantity = scanner.nextInt();
            }
            scanner.nextLine();

            for (Book book : listBook) {
                if (book.getTitle().equalsIgnoreCase(title) && book.getQuantity() > 0) {
                    book.decreaseQuantity(quantity);

                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                    for (Book b : listBook) {
                        bufferedWriter.write(b.toString());
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.close();

                    System.out.println("Successfully borrowed book: " + title);
                    return;
                }
            }
            System.out.println("Book not available for borrowing: " + title);

        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    // Return book
    public void returnBook(){
        List<Book> listBook = getListBook();
        try{
            System.out.println("Search books by title: ");
            String title = scanner.nextLine().trim();
            System.out.println("How many books do you want to return: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            for (Book book : listBook) {
                if (book.getTitle().equalsIgnoreCase(title)) {
                    book.increaseQuantity(quantity);

                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

                    for (Book b : listBook) {
                        bufferedWriter.write(b.toString());
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.close();

                    System.out.println("Successfully returned book: " + title);
                    return;
                }
            }
            System.out.println("Book not available for returned: " + title);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check exists title
    private boolean exitsTitle(String title){
        List<Book> listBook = getListBook();
        for (Book book : listBook) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Title already exists");
                System.out.println("Please again: ");
                return true;
            }
        }
        return false;
    }

    // Check quantity when borrow book
    private boolean exitsQuantityBorrowBook(String title, int quantity){
        List<Book> listBook = getListBook();
        for (Book book : listBook) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getQuantity() < quantity) {
                System.out.println("The quantity you need is not enough!1");
                System.out.println("Please again: ");
                return true;
            }
        }
        return false;
    }

}
