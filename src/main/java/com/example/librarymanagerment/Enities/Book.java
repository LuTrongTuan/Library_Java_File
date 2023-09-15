package com.example.librarymanagerment.Enities;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.serializer.Deserializer;

import java.io.Serializable;

@Data
public class Book implements Serializable {
    private String title;
    private String authorName;
    private int publishing;
    private int quantity;

    public Book(){}
    public Book(String title, String authorName, int publishing, int quantity){

        this.title = title;
        this.authorName = authorName;
        this.publishing = publishing;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "title: " + title + ", authorName: "
                + authorName + ", publishYear: " + publishing + ", quantity: " + quantity +"\n";
    }
}
