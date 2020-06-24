package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

    myDBHelper myHelper;
    Context context = this;
    SQLiteDatabase sqlDB;
    EditText Id;
    EditText Password;
    Button btn_login, btn_register, btn_home;
    private InputMethodManager imm;
    TextView login_id, login_name;
    LinearLayout layout;
    String myJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = new Intent(this, splash.class);
        startActivity(intent);

        myHelper = new myDBHelper(this);
        Id= (EditText) findViewById(R.id.et_id);
        Password= (EditText) findViewById(R.id.et_pass);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_home = (Button) findViewById(R.id.btn_home);
        layout= (LinearLayout) findViewById(R.id.layout);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        final String[] name = {""};
        Id.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override

            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if(id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL){
                    imm.hideSoftInputFromWindow(Id.getWindowToken(), 0);
                    return true;
                }
                return false;
            }

        });
        Password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        //로그인 버튼 누를시
        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(Id.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(Password.getWindowToken(), 0);
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT id, passwd FROM user;", null);
                int count = 0;
                while(cursor.moveToNext()){
                    count++;
                    if(Id.getText().toString().equals(cursor.getString(0)) && Password.getText().toString().equals(cursor.getString(1))){
                        Toast.makeText(getApplicationContext(),  "로그인", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Intent intent2 = new Intent(getApplicationContext(), favorite.class);
                        intent.putExtra("id", Id.getText().toString());
                        intent2.putExtra("id", Id.getText().toString());
                        startActivity(intent);
                        //finish();
                        break;
                    }else{
//                        Id.setText(null);
//                        Password.setText(null);
                        Toast.makeText(getApplicationContext(),  "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        //회원가입 버튼 누를 시
        btn_register.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
            }
        });
        btn_home.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
    View.OnClickListener myClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            hideKeyboard();
            switch (v.getId())
            {
                case R.id.layout:
                    break;

                case R.id.btn_login :
                    break;
                case R.id. btn_home :
                    break;
                case R.id. btn_register :
                    break;
            }
        }
    };
    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(Id.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(Password.getWindowToken(), 0);
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