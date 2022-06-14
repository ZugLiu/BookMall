package com.atguigu.book.DAO.impl;

import com.atguigu.book.DAO.BookDAO;
import com.atguigu.book.pojo.Book;
import com.atguigu.myssm.basedao.BaseDAO;

import java.util.List;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {
    @Override
    public List<Book> getBookList() {
        return executeQuery("select * from t_book");
    }

    @Override
    public Book getBookById(Integer bookId) {
        return load("select * from t_book where id = ?", bookId);
    }
}
