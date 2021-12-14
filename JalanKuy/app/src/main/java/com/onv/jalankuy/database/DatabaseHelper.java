package com.onv.jalankuy.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db_travel";
    public static final String TABLE_USER = "tb_user";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAME = "name";
    public static final String TABLE_BOOK = "tb_book";
    public static final String COL_ID_BOOK = "id_book";
    public static final String COL_ASAL = "asal";
    public static final String COL_TUJUAN = "tujuan";
    public static final String COL_TANGGAL = "tanggal";
    public static final String COL_DEWASA = "dewasa";
    public static final String COL_ANAK = "anak";
    public static final String TABLE_HARGA = "tb_harga";
    public static final String COL_HARGA_DEWASA = "harga_dewasa";
    public static final String COL_HARGA_ANAK = "harga_anak";
    public static final String COL_HARGA_TOTAL = "harga_total";
    public static final String TABLE_BOOK_HOTEL = "tb_bhtl";
    public static final String COL_ID_BOOK_HOTEL = "id_bhtl";
    public static final String COL_LOKASI = "lokasi";
    public static final String COL_HOTEL = "hotel";
    public static final String COL_TANGGALCI = "tanggalci";
    public static final String COL_TANGGALCO = "tanggalco";
    public static final String COL_KAMAR = "kamar";
    public static final String COL_ORANG = "orang";
    public static final String TABLE_HARGATOTAL = "tb_hargatotal";
    public static final String COL_HARGA_KAMAR = "harga_kamar";
    public static final String COL_HARGA_ORANG = "harga_orang";
    public static final String COL_HARGA_TOTAL_HOTEL = "harga_total_hotel";


    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("create table " + TABLE_USER + " (" + COL_USERNAME + " TEXT PRIMARY KEY, " + COL_PASSWORD +
                " TEXT, " + COL_NAME + " TEXT)");
        db.execSQL("create table " + TABLE_BOOK + " (" + COL_ID_BOOK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ASAL + " TEXT, " + COL_TUJUAN + " TEXT" + ", " + COL_TANGGAL + " TEXT, " + COL_DEWASA + " TEXT, "
                + COL_ANAK + " TEXT)");
        db.execSQL("create table " + TABLE_BOOK_HOTEL + " (" + COL_ID_BOOK_HOTEL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_LOKASI + " TEXT, " + COL_HOTEL + " TEXT, "+ COL_TANGGALCI + " TEXT, " + COL_TANGGALCO + " TEXT, " + COL_KAMAR + " TEXT, "
                + COL_ORANG + " TEXT)");
        db.execSQL("create table " + TABLE_HARGA + " (" + COL_USERNAME + " TEXT, " + COL_ID_BOOK + " INTEGER, " +
                COL_HARGA_DEWASA + " TEXT, " + COL_HARGA_ANAK + " TEXT, " + COL_HARGA_TOTAL +
                " TEXT, FOREIGN KEY(" + COL_USERNAME + ") REFERENCES " + TABLE_USER
                + ", FOREIGN KEY(" + COL_ID_BOOK + ") REFERENCES " + TABLE_BOOK + ")");
        db.execSQL("create table " + TABLE_HARGATOTAL + " (" + COL_USERNAME + " TEXT, " + COL_ID_BOOK_HOTEL + " INTEGER, " +
                COL_HARGA_KAMAR + " TEXT, " + COL_HARGA_ORANG + " TEXT, " + COL_HARGA_TOTAL_HOTEL +
                " TEXT, FOREIGN KEY(" + COL_USERNAME + ") REFERENCES " + TABLE_USER
                + ", FOREIGN KEY(" + COL_ID_BOOK_HOTEL + ") REFERENCES " + TABLE_BOOK_HOTEL + ")");
        db.execSQL("insert into " + TABLE_USER + " values ('onv@gmail.com','onv','Okta Toyibah');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

}