package com.di.tang.learnipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tangdi on 2016/9/8.
 */
public class Book implements Parcelable {

    private int bookId;

    private String bookName;

    public Book(int id, String name){
        bookId = id;
        bookName = name;
    }

    private Book(Parcel parcela){
        bookId = parcela.readInt();
        bookName = parcela.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookId);
        parcel.writeString(bookName);
    }
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>(){


        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int i) {
            return new Book[i];
        }
    };
}
