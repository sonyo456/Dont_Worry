package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.R.layout;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class search extends AppCompatActivity {
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;
    EditText search;
    ImageButton btn_search;
    ListView listView;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        myHelper = new myDBHelper(this);
        search =  (EditText) findViewById(R.id.search);
        btn_search = (ImageButton) findViewById(R.id.btn_search);
        listView = (ListView)findViewById(R.id.listView);

        btn_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    sqlDB = myHelper.getReadableDatabase();
                    Cursor cursor;
                    cursor = sqlDB.rawQuery("SELECT id FROM user WHERE id LIKE "+"'"+search.getText().toString()+"'"+";", null);

                    int count = cursor.getCount();   // db에 저장된 행 개수를 읽어온다

                    String[] result = new String[count];   // 저장된 행 개수만큼의 배열을 생성

                    for (int i = 0; i < count; i++) {

                        cursor.moveToNext();   // 첫번째에서 다음 레코드가 없을때까지 읽음

                        String id = cursor.getString(0);   // 첫번째 속성

                        result[i] = id ;   // 각각의 속성값들을 해당 배열의 i번째에 저장

                    }
                    System.out.println("select ok");
                    // ArrayAdapter(this, 출력모양, 배열)
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(search.this, android.R.layout.simple_list_item_1, result);

                    listView.setAdapter(adapter);   // listView 객체에 Adapter를 붙인다



                } catch (Exception e) {

                    System.out.println("select Error :  " + e);

                }
            }
        });
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "LoginDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS user");
        }
    }
}
