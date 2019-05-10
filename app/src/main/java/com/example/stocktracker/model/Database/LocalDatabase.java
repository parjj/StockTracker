package com.example.stocktracker.model.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.stocktracker.model.entity.Company;
import com.example.stocktracker.model.entity.Product;

@Database(entities = {Company.class, Product.class},version = 1,exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();

    private static  LocalDatabase db=null;

    public static LocalDatabase getDb(Context context){

        if(db==null){

          //  db= Room.inMemoryDatabaseBuilder(context,LocalDatabase.class).build();
             db = Room.databaseBuilder(context,
                   LocalDatabase.class, "stock-database").build();

        }
        return  db;
    }
}
