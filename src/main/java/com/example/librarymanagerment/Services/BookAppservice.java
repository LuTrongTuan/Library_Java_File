package com.example.librarymanagerment.Services;

import com.example.librarymanagerment.Enities.Book;
import com.example.librarymanagerment.Enities.BookDetail;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookAppservice {
    List<Book> bookList;
    Scanner scanner;
    File file;

    public BookAppservice() {
        scanner = new Scanner(System.in);
        bookList = new ArrayList<>();
        file = new File("/Java/LibraryManagerment/src/data.text");
    }

    public void runCreateFile(){
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        bookList.add(new Book("A", "1", 1000, 5));
                        bookList.add(new Book("B", "2", 2999, 6));
                        bookList.add(new Book("C", "3", 2000, 3));
                        bookList.add(new Book("D", "4", 3000, 4));
                        int count = 0;
                        for (Book book : bookList) {
                            String input = book.toString();
                            writer.write(input);
                            count++;
                            if (count < bookList.size()) {
                                writer.newLine();
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println("checkAndCreateFile | FileWriter exception: " + ex.getMessage());
                    }
                }
            } catch (IOException ex) {
                System.out.printf("checkAndCreateFile exception: " + ex.getMessage());
            }
        }
    }


    // Danh sách book từ file
    public BookDetail getListBook() {
        BookDetail bookDetails = new BookDetail();
        try
        {
            BufferedReader bufferedReader = new BufferedReader (new FileReader(file));
            String line = bufferedReader.readLine();
            System.out.println("List book:");
            while (line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException  e)
        {
            e.printStackTrace();
        }
        return bookDetails;
    }
    // Thêm sách vào file
    public void createBook(){
        try{
            boolean flag = false;
            do {
                Book book = new Book(null,null,0,0);

                System.out.println("How many books do you want to create: ");
                int input = scanner.nextInt();
                scanner.nextLine();

                for (var i= 1; i <= input; i++){
                    bookList.clear();
                    System.out.println("Title: ");
                    book.setTitle(scanner.nextLine());
                    System.out.println("Author name: ");
                    book.setAuthorName(scanner.nextLine());
                    System.out.println("Publish year: ");
                    book.setPublishing(scanner.nextInt());
                    System.out.println("Quantity: ");
                    book.setQuantity(scanner.nextInt());
                    scanner.nextLine();

                    bookList.add(book);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

                    for (Book b: bookList) {
                        writer.newLine();
                        writer.write(b.toString());
                        System.out.println("Add book successfully !");
                    }
                    writer.close();
                }

                System.out.println("Do you want to create?: ");
                System.out.println("1: Yes");
                System.out.println("2: No");
                int isflag = scanner.nextInt();
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

    public void searchBook() {
        boolean flag = true;
        while (flag) {
            System.out.println("Search Options:");
            System.out.println("1. Search by title");
            System.out.println("2. Search by author");
            System.out.println("3. Exit");
            System.out.println("Please choose: ");
            int input = scanner.nextInt();
            scanner.nextLine();
            switch (input) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    searchBookByAuthor();
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("Option is not valid");
                    break;
            }
        }

    }
    private static Book createObjectFromLine(String line) {
        // Phân tích dòng thành các thuộc tính của đối tượng
        String[] parts = line.split(", ");
        if (parts.length != 4) {
            // Dòng không có đủ thuộc tính, bỏ qua
            return null;
        }

        String prop1 = parts[0].trim();
        String prop2 = parts[1].trim();
        int prop3 = Integer.valueOf(parts[2]);
        int prop4 = Integer.valueOf(parts[3]);

        // Tạo đối tượng từ các thuộc tính
        Book obj = new Book(prop1, prop2, prop3, prop4);
        return obj;
    }

    private void searchBookByTitle() {
        try{
            List<Book> objects = new ArrayList<>();
            System.out.println("Title: ");
            String title = scanner.nextLine().trim();

            BufferedReader bufferedReader = new BufferedReader (new FileReader(file));
           // StringBuilder buffer = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                Book obj = createObjectFromLine(line);
                if (obj != null) {
                    objects.add(obj);
                }
//                if (line.contains("title: " + title)) {
//
//                    System.out.println(line);
//                    break;
//                }else{
//                    System.out.println("Not found: " + title);
//                    break;
//                }

            }
            for (Book obj : objects) {
                System.out.println(obj);
            }
//            String data = buffer.toString();
//
//            List<Book> foundBooks = parseBooks(data, title);
//
//            // In danh sách sách đã tìm thấy
//            for (Book book : foundBooks) {
//                System.out.println("Title: " + book.getTitle());
//                System.out.println("Author Name: " + book.getAuthorName());
//                System.out.println("Publish Year: " + book.getPublishing());
//                System.out.println("Quantity: " + book.getQuantity());
//                System.out.println();
//            }

//            List<Book> listBooks = getListBook().getbookList();
//            for (Book book : listBooks) {
//                if (title.toLowerCase().startsWith(book.getTitle().toLowerCase())) {
//                    System.out.println(book.toString());
//                } else {
//                    System.out.println("This book is not exist");
//                }
//
//            }

        }catch (IOException  e){
            e.printStackTrace();
        }
    }

    private static List<Book> parseBooks(String data, String searchName) {
        List<Book> books = new ArrayList<>();
        Pattern pattern = Pattern.compile("title: (.*?), authorName: (.*?), publishYear: (.*?), quantity: (.*?)\\n");
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            String title = matcher.group(1).trim();
            String authorName = matcher.group(2).trim();
            int publishYear = Integer.parseInt(matcher.group(3).trim());
            int quantity = Integer.parseInt(matcher.group(4).trim());

            if (authorName.equals(searchName)) {
                Book book = new Book();
                book.setTitle(title);
                book.setAuthorName(authorName);
                book.setPublishing(publishYear);
                book.setQuantity(quantity);
                books.add(book);
            }
        }

        return books;
    }

    private void searchBookByAuthor() {
        System.out.printf("Enter author: ");
        String author = scanner.nextLine().trim();
        List<Book> listBooks = getListBook().getbookList();
//        for (Book book : listBooks) {
//            if (author.toLowerCase().startsWith(book.getAuthorName().toLowerCase())) {
//                System.out.println(book);
//            }else {
//                System.out.println("This book is not exist");
//            }
//        }
    }


    //tìm kiếm sách theo tiêu đề hoặc tác giả
//    public void searchBook(){
//        String line;
//        try
//        {
//            System.out.println("Tiêu để hoặc tác giả muốn tìm:");
//            String input = scanner.nextLine();
//
//            BufferedReader bufferedReader = new BufferedReader (new FileReader(file));
//            line = bufferedReader.readLine();
//            line = line.substring(1, line.length() - 1);
//            String[] properties = line.split(", ");
//            while (line  != null) {
//                Book book = new Book(null, null,0,0);
//                for (String property : properties) {
//                    String[] keyValue = property.split(": ");
//                    if (keyValue.length == 2) {
//                        String key = keyValue[0];
//                        String value = keyValue[1];
//                        switch (key) {
//                            case "title":
//                                book.setTitle(value);
//                                break;
//                            case "author name":
//                                book.setAuthorName(value);
//                                break;
//                            case "publish year":
//                                book.setPublishing(Integer.parseInt(value));
//                                break;
//                            case "quantity":
//                                book.setQuantity(Integer.parseInt(value));
//                                break;
//                        }
//                    }
//                }
//
//                // Thêm sách vào danh sách
//                bookList.add(book);

//                String title = null;
//                String author = null;
//
//                // Trích xuất tiêu đề và tác giả từ thuộc tính
//                for (String property : properties) {
//                    if (property.startsWith("title:")) {
//                        title = property.substring(7); // Loại bỏ "title:"
//                    } else if (property.startsWith("author name:")) {
//                        author = property.substring(12); // Loại bỏ "author name:"
//                    }
//                }
//
//                // Kiểm tra nếu tiêu đề hoặc tác giả chứa searchTerm
//                if ((title != null && title.contains(input)) || (author != null && author.contains(input))) {
//                    System.out.println(line);
//                }

//                String[] properties = line.split(", ");
//                String title = "";
//                for (String property : properties) {
//                    if (property.startsWith("title=")) {
//                        title = property.substring(6); // Bỏ "title=" để lấy tiêu đề
//                        break;
//                    }
//                }

                // So sánh tiêu đề và xử lý đối tượng sách nếu trùng khớp
//                if (title.equals(input)) {
//                    System.out.println("Tiêu đề: " + title);
//                    // Xử lý đối tượng sách ở đây nếu cần
//
//                }
//                if (line.trim().equalsIgnoreCase(input)) {
//                    System.out.println(line + "\n");
//                }
//                else {
//                    System.out.println("Không tìm thấy sách với tiêu đề: " + input);
//                }

           // }


//        } catch (IOException  e)
//        {
//            e.printStackTrace();
//        }
//
//        for (Book book : bookList) {
//            System.out.println(book.getTitle() + ", " + book.getAuthorName() + ", " + book.getPublishing() + ", " + book.getQuantity());
//        }
//
////        List<Book> foundBooks = new ArrayList<>();
////        System.out.println("Tiêu để hoặc tác giả muốn tìm:");
////        String input = scanner.nextLine();
////        while (!= null) {
////            if (book.getTitle().equalsIgnoreCase(input) || book.getAuthorName().equalsIgnoreCase(input)) {
////                foundBooks.add(book);
////            }
////        }
////
////        if (!foundBooks.isEmpty()) {
////            System.out.println("Sách có tiêu đề \"" + input + "\" được tìm thấy:");
////            for (Book book : foundBooks) {
////                System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthorName()
////                        + ", Publish year: " + book.getPublishing() + ", Quantity: " + book.getQuantity());
////            }
////        } else {
////            System.out.println("Không tìm thấy sách với tiêu đề: " + input);
////        }
//
//    }

    public void borrowBook(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            bookList = (List<Book>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<Book> foundBooks = new ArrayList<>();
        System.out.println("Bạn muốn mượn cuốn sách nào, mời nhập tiêu bạn muốn:");
        String input = scanner.nextLine();

        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(input)) {
                foundBooks.add(book);
            }
        }

        if (!foundBooks.isEmpty()) {
            System.out.println("Sách có tên \"" + input + "\" được tìm thấy:");
            for (Book book : foundBooks) {
                System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthorName()
                        + ", Publish year: " + book.getPublishing() + ", Quantity: " + book.getQuantity());
            }
        } else {
            System.out.println("Không tìm thấy sách với tên: " + input);
        }

    }
    public void giveBookBack(){

    }


}
