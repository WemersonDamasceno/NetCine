package com.example.netcine.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexaoSQLite extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 1;

    public ConexaoSQLite(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("" +
                    "create table favorito(" +
                    "id integer primary key autoincrement," +
                    "id_user varchar(50)," +
                    "id_filme varchar (50)," +
                    "tipo_midia varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
