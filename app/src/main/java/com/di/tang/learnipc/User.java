package com.di.tang.learnipc;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by tangdi on 2016/9/8.
 */
public class User implements Parcelable, Serializable{

    private int userId;

    private String userName;

    private boolean isMale;

    public User(int id, String name, boolean male){
        userId = id;
        userName = name;
        isMale = male;
    }


    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(userName);
        parcel.writeByte((byte) (isMale ? 1 : 0));
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userId);
        stringBuilder.append(userName);
        stringBuilder.append(isMale);
        return stringBuilder.toString();
    }
}
