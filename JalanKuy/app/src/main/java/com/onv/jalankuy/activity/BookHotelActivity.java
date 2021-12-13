package com.onv.jalankuy.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.onv.jalankuy.R;
import com.onv.jalankuy.database.DatabaseHelper;

import java.util.Calendar;
import java.util.Objects;

public class BookHotelActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Spinner spinLokasi, spinKamar, spinOrang, spinHotel;

    String email;
    int id_book_hotel;
    public String sLokasi,sTanggalCI, sTanggalCO, sKamar, sOrang, sHotel;
    int jmlKamar, jmlOrang;
    int hargaKamar, hargaOrang;
    int hargaTotalKamar, hargaTotalOrang, hargaTotal;
    private EditText etTanggalCI, etTanggalCO;
    private DatePickerDialog dpTanggalCI, dpTanggalCO;
    Calendar newCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hotel);

        dbHelper = new DatabaseHelper(BookHotelActivity.this);
        db = dbHelper.getReadableDatabase();

        final String[] lokasi = {"Jakarta", "Bandung", "Purwokerto", "Yogyakarta", "Surabaya"};
        final String[] hotel = {"Aston", "OYO"};
//        final String[] kamar = {"Standar Room", "Superior Room", "Deluxe Room", "Single Room", "Twin Room", "Family Room"};
        final String[] orang = {"1", "2", "3", "4", "5", "6"};

        spinLokasi = findViewById(R.id.lokasi);
        spinHotel = findViewById(R.id.hotel);
//        spinKamar = findViewById(R.id.kamar);
        spinOrang = findViewById(R.id.orang);

        ArrayAdapter<CharSequence> adapterLokasi = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, lokasi);
        adapterLokasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLokasi.setAdapter(adapterLokasi);

        ArrayAdapter<CharSequence> adapterHotel = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, hotel);
        adapterHotel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHotel.setAdapter(adapterHotel);

//        ArrayAdapter<CharSequence> adapterKamar = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, kamar);
//        adapterKamar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinKamar.setAdapter(adapterKamar);

        ArrayAdapter<CharSequence> adapterOrang = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, orang);
        adapterOrang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinOrang.setAdapter(adapterOrang);

        spinLokasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sLokasi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinHotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sHotel = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        spinKamar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                sKamar = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        spinOrang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sOrang = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnBook = findViewById(R.id.bookhtl);

        etTanggalCI = findViewById(R.id.tanggal_checkin);
        etTanggalCI.setInputType(InputType.TYPE_NULL);
        etTanggalCI.requestFocus();
        setDateTimeFieldCI();

        etTanggalCO = findViewById(R.id.tanggal_checkout);
        etTanggalCO.setInputType(InputType.TYPE_NULL);
        etTanggalCO.requestFocus();
        setDateTimeFieldCO();


        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perhitunganHarga();
                if (sLokasi != null && sTanggalCI != null && sTanggalCO != null ) {

                    AlertDialog dialog = new AlertDialog.Builder(BookHotelActivity.this)
                            .setTitle("Ingin booking hotel sekarang?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        db.execSQL("INSERT INTO TB_BHTL (lokasi, hotel, tanggalci, tanggalco, orang) VALUES ('" +
                                                sLokasi + "','" +
                                                sHotel + "','" +
                                                sTanggalCI + "','" +
                                                sTanggalCO + "','" +

                                                sOrang + "');");
                                        cursor = db.rawQuery("SELECT id_bhtl FROM TB_BHTL ORDER BY id_bhtl DESC", null);
                                        cursor.moveToLast();
                                        if (cursor.getCount() > 0) {
                                            cursor.moveToPosition(0);
                                            id_book_hotel = cursor.getInt(0);
                                        }
                                        db.execSQL("INSERT INTO TB_HARGATOTAL (username, id_bhtl, harga_orang, harga_total_hotel) VALUES ('" +
                                                email + "','" +
                                                id_book_hotel + "','" +
                                                hargaTotalOrang + "','" +
                                                hargaTotal + "');");
                                        Toast.makeText(BookHotelActivity.this, "Booking berhasil", Toast.LENGTH_LONG).show();
                                        finish();
                                    } catch (Exception e) {
                                        Toast.makeText(BookHotelActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton("Tidak", null)
                            .create();
                    dialog.show();

                }else {
                    Toast.makeText(BookHotelActivity.this, "Mohon lengkapi data pemesanan!", Toast.LENGTH_LONG).show();
                }
            }
        });

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbHtl);
        toolbar.setTitle("Form Booking");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void perhitunganHarga() {
        if (sLokasi.equalsIgnoreCase("jakarta") && sHotel.equalsIgnoreCase("aston")) {

            hargaOrang = 200000;
        } else if (sLokasi.equalsIgnoreCase("jakarta") && sHotel.equalsIgnoreCase("oyo")) {

            hargaOrang = 400000;
        } else if (sLokasi.equalsIgnoreCase("bandung") && sHotel.equalsIgnoreCase("aston")) {

            hargaOrang = 200000;
        } else if (sLokasi.equalsIgnoreCase("bandung") && sHotel.equalsIgnoreCase("oyo")) {

            hargaOrang = 40000;
        } else if (sLokasi.equalsIgnoreCase("purwokerto") && sHotel.equalsIgnoreCase("aston")) {

            hargaOrang = 400000;
        } else if (sLokasi.equalsIgnoreCase("purwokerto") && sHotel.equalsIgnoreCase("oyo")) {

            hargaOrang = 100000;
        } else if (sLokasi.equalsIgnoreCase("yogyakarta") && sHotel.equalsIgnoreCase("aston")) {

            hargaOrang = 400000;
        } else if (sLokasi.equalsIgnoreCase("yogyakarta") && sHotel.equalsIgnoreCase("oyo")) {

            hargaOrang = 40000;
        } else if (sLokasi.equalsIgnoreCase("surabaya") && sHotel.equalsIgnoreCase("aston")) {

            hargaOrang = 400000;
        } else if (sLokasi.equalsIgnoreCase("surabaya") && sHotel.equalsIgnoreCase("oyo")) {

            hargaOrang = 400000;
        }


            jmlOrang = Integer.parseInt(sOrang);


            hargaTotalOrang = jmlOrang * hargaOrang;
            hargaTotal = hargaTotalOrang;
        }

        private void setDateTimeFieldCI() {
            etTanggalCI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dpTanggalCI.show();
                }
            });

            dpTanggalCI = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei",
                            "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
                    sTanggalCI = dayOfMonth + " " + bulan[monthOfYear] + " " + year;
                    etTanggalCI.setText(sTanggalCI);

                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        }

        private void setDateTimeFieldCO() {
            etTanggalCO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dpTanggalCO.show();
                }
            });

            dpTanggalCO = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei",
                            "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
                    sTanggalCO = dayOfMonth + " " + bulan[monthOfYear] + " " + year;
                    etTanggalCO.setText(sTanggalCO);
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        }
    }