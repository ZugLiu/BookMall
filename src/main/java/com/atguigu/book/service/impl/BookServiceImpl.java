package com.atguigu.book.service.impl;

import com.atguigu.book.DAO.BookDAO;
import com.atguigu.book.pojo.Book;
import com.atguigu.book.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    BookDAO bookDAO = null;

    @Override
    public List<Book> getBookList() {
        return bookDAO.getBookList();
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookDAO.getBookById(bookId);
    }
}
