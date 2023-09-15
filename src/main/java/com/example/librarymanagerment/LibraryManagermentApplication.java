package com.example.librarymanagerment;

import com.example.librarymanagerment.Services.BookAppservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.PrintWriter;
import java.util.Scanner;

@SpringBootApplication
public class LibraryManagermentApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagermentApplication.class, args);
        BookAppservice bookAppservice = new BookAppservice();
        Scanner scanner = new Scanner(System.in);
        bookAppservice.runCreateFile();

        while(true) {
            System.out.print("Menu \n");
            System.out.print("1. Add book \n");
            System.out.print("2. Search \n");
            System.out.print("3. Borrow book \n");
            System.out.print("4. Give book \n");
            System.out.print("5. Show all list book \n");
            System.out.print("0. Exit \n");
            System.out.print("Please choose! \n");
            int choice  = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    bookAppservice.createBook();
                    break;
                case 2:
                    bookAppservice.searchBook();
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                     bookAppservice.getListBook();
                    break;
                case 0:
                    System.out.println("Ứng dụng kết thúc.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }



    }

}
