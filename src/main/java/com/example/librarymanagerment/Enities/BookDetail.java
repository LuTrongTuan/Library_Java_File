package com.example.librarymanagerment.Enities;

import java.util.List;

public class BookDetail {
    public List<Book> bookList;
    public BookDetail() {
    }
    public BookDetail(List<Book> bookList) {
        this.bookList = bookList;
    }
    public List<Book> getbookList() {
        return bookList;
    }
    public void setbookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public java.lang.String toString() {
        return "BookDetail{" +
                "bookList=" + bookList +
                '}';
    }

}
