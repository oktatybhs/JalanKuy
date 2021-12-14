package com.onv.jalankuy.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.onv.jalankuy.R;
import com.onv.jalankuy.model.HistoryModel;
import com.onv.jalankuy.adapter.HistoryAdapter;
import com.onv.jalankuy.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    //SessionManager session;
    String id_book = "", asal, tujuan, tanggal, dewasa, anak, riwayat, total;
    String id_book_hotel = "", lokasi, hotel, orang, tanggalci, tanggalco ;
    String email;
    TextView tvNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        tvNotFound = findViewById(R.id.noHistory);

//        session = new SessionManager(getApplicationContext());
//
//        HashMap<String, String> user = session.getUserDetails();
//
//        email = user.get(SessionManager.KEY_EMAIL);

        refreshList();
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbHistory);
        toolbar.setTitle("Riwayat Booking");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshList() {
        final ArrayList<HistoryModel> hasil = new ArrayList<>();
        cursor = db.rawQuery("SELECT * FROM TB_BOOK, TB_HARGA WHERE TB_BOOK.id_book = TB_HARGA.id_book AND username='" + email + "'", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id_book = cursor.getString(0);
            asal = cursor.getString(1);
            tujuan = cursor.getString(2);
            tanggal = cursor.getString(3);
            dewasa = cursor.getString(4);
            anak = cursor.getString(5);
            total = cursor.getString(10);
            riwayat = "Berhasil melakukan booking untuk \nmelakukan perjalanan\nDari                                                  : " +
                    asal + "\nMenuju                                            : " + tujuan +
                    "\nPada Tanggal                                 : " + tanggal +
                    "\nJumlah pembelian Tiket Dewasa     : " + dewasa + "\nJumlah pembelian Tiket Anak-Anak: " + anak + "\n";
            hasil.add(new HistoryModel(id_book, tanggal, riwayat, total, R.drawable.profile));
        }


        cursor = db.rawQuery("SELECT * FROM TB_BHTL, TB_HARGATOTAL WHERE TB_BHTL.id_bhtl = TB_HARGATOTAL.id_bhtl AND username='" + email + "'", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id_book = cursor.getString(0);
            lokasi= cursor.getString(1);
            hotel = cursor.getString(2);
            tanggalci = cursor.getString(3);
            tanggalco = cursor.getString(4);
            orang = cursor.getString(6);
            total = cursor.getString(11);
            riwayat = "Berhasil melakukan booking hotel\n\nLokasi        : " + lokasi +
                    "\nHotel          : " + hotel +
                    "\nCheck-in    : " + tanggalci +
                    "\nCheck-out  : " + tanggalco +
                    "\nOrang          : " + orang + "\n";
            hasil.add(new HistoryModel(id_book, tanggalci, riwayat, total, R.drawable.profile));
        }

        ListView listBook = findViewById(R.id.list_booking);
        HistoryAdapter arrayAdapter = new HistoryAdapter(this, hasil);
        listBook.setAdapter(arrayAdapter);

        //delete data
        listBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String selection = hasil.get(i).getIdBook();

                final CharSequence[] dialogitem = {"Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        try {
                            db.execSQL("DELETE FROM TB_BOOK where id_book = " + selection + "");
                            db.execSQL("DELETE FROM TB_BHTL where id_book_hotel = " + selection + "");
                            id_book = "";
                            id_book_hotel = "";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        refreshList();
                    }
                });
                builder.create().show();
            }
        });

        if (id_book.equals("")) {
            tvNotFound.setVisibility(View.VISIBLE);
            listBook.setVisibility(View.GONE);
        } else {
            tvNotFound.setVisibility(View.GONE);
            listBook.setVisibility(View.VISIBLE);
        }


    }
}