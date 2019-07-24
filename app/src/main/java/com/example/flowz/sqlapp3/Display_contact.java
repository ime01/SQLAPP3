package com.example.flowz.sqlapp3;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Display_contact extends AppCompatActivity {
    int from_where_i_Am_Coming = 0;
    private DBHelper mydb;
    TextView name, phone, email, street, place;
    int id_To_Update = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        name = (TextView)findViewById(R.id.editTextName);
        phone = (TextView)findViewById(R.id.editTextPhone);
        email = (TextView)findViewById(R.id.editTextEmail);
        street = (TextView)findViewById(R.id.editTextStreet);
        place = (TextView)findViewById(R.id.editTextCity);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            int Value = extras.getInt("id");
            if (Value>0){
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                String stree = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
                String plac = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));
                if (!rs.isClosed())
                {
                    rs.close();
                }
                Button b =(Button)findViewById(R.id.buttton1);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                phone.setText((CharSequence)phon);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText((CharSequence)emai);
                email.setFocusable(false);
                email.setClickable(false);

                street.setText((CharSequence)stree);
                street.setFocusable(false);
                street.setClickable(false);

                place.setText((CharSequence)plac);
                place.setFocusable(false);
                place.setClickable(false);

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if (extras!= null)
        {
            int Value = extras.getInt("id");
            if (Value>0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            }else {
                getMenuInflater().inflate(R.menu.mainmenu, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         switch (item.getItemId()){
             case R.id.Edit_Contact:
                 Button b =(Button)findViewById(R.id.buttton1);
                 b.setVisibility(View.VISIBLE);
                 name.setEnabled(true);
                 name.setClickable(true);
                 name.setFocusableInTouchMode(true);

                 phone.setEnabled(true);
                 phone.setClickable(true);
                 phone.setFocusableInTouchMode(true);

                 email.setEnabled(true);
                 email.setClickable(true);
                 email.setFocusableInTouchMode(true);

                 street.setEnabled(true);
                 street.setClickable(true);
                 street.setFocusableInTouchMode(true);

                 place.setEnabled(true);
                 place.setClickable(true);
                 place.setFocusableInTouchMode(true);

                 return true;

             case R.id.Delete_Contact:
                 AlertDialog.Builder builder = new AlertDialog.Builder(this);
                 builder.setMessage(R.string.deleteContact);

                 builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int id) {
                        mydb.deleteContact(id_To_Update);
                         Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();

                         Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                         startActivity(intent);
                     }
                 })
                         .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int id) {
//                                 user cancelled the dialog
                             }
                         });
                 AlertDialog d = builder.create();
                 d.setTitle("Are you sure");
                 d.show();

                 return true;
                 default:
                     return super.onOptionsItemSelected(item);


         }
    }

    public void run(View view){
        Bundle extras = getIntent().getExtras();
        if (extras!= null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateContact(id_To_Update, name.getText().toString(), phone.getText().toString(), email.getText().toString(),
                        street.getText().toString(), place.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();

                }
            } else {
                if (mydb.insertContact(name.getText().toString(), phone.getText().toString(), email.getText().toString(),
                        street.getText().toString(), place.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Not Done", Toast.LENGTH_SHORT).show();

                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
