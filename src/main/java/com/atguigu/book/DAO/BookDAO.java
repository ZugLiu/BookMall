package com.atguigu.book.DAO;

import com.atguigu.book.pojo.Book;

import java.util.List;

public interface BookDAO {
    List<Book> getBookList();
    Book getBookById(Integer bookId);
}
