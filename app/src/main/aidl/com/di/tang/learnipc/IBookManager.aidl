// IBookManager.aidl
package com.di.tang.learnipc;

// Declare any non-default types here with import statements
import com.di.tang.learnipc.Book;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

