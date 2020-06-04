package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class register extends AppCompatActivity {

    myDBHelper myHelper;
    TextView red;
     EditText id;
     EditText pass;
     EditText passck;
     EditText name;
    Button register, validate, btn_pass;
    Context context = this;
    SQLiteDatabase sqlDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myHelper = new myDBHelper(this);
        id = (EditText) findViewById(R.id.et_id);
        pass = (EditText) findViewById(R.id.et_pass);
        passck = (EditText) findViewById(R.id.et_passck);
        name = (EditText) findViewById(R.id.et_name);
        register = (Button) findViewById(R.id.btn_register); // 회원가입
        btn_pass = (Button) findViewById(R.id.passwdButton);
        validate= (Button) findViewById(R.id.validateButton);// 중복
        red = (TextView)findViewById(R.id.red_pass);
        red.setVisibility(View.INVISIBLE);


        //중복 버튼 누를시
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT id FROM user;", null);
//                int count =cursor.getCount();
                int count = 0;
                while(cursor.moveToNext()){
                    count++;
                    if(id.getText().toString().equals(cursor.getString(0))){
                        System.out.println("if문: "+ cursor.getString(0));
                        Toast.makeText(getApplicationContext(),  "ID를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                Toast.makeText(getApplicationContext(), "중복확인",
                        Toast.LENGTH_SHORT).show();

            }
        });

        btn_pass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(pass.getText().toString().equals(passck.getText().toString())){
                    red.setText("비밀번호 일치");
                    red.setVisibility(View.VISIBLE);

                }else{
                    red.setText("비밀번호가 일치하지 않습니다.");
                    red.setVisibility(View.VISIBLE);
                }

            }
        });


        //회원가입
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            try{
                if(!pass.getText().toString().equals(passck.getText().toString())){
                    red.setVisibility(View.VISIBLE);
                    Toast.makeText(context,  "가입 실패", Toast.LENGTH_LONG).show();
                    pass.setText(null);
                    passck.setText(null);
                }else{
                    sqlDB = myHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO user VALUES ( '"
                            + id.getText().toString() + "' , '"
                            + pass.getText().toString() + "', '"
                            + name.getText().toString()+ "');");

                    Toast.makeText(context,  "가입 완료", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    sqlDB.close();
                }
            }catch (Exception e){
                    System.out.println(e.getMessage());
                    Toast.makeText(context,  "가입 실패", Toast.LENGTH_LONG).show();
                    id.setText(null);
                pass.setText(null);
                passck.setText(null);
                name.setText(null);
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
            //db.execSQL("DROP TABLE IF EXISTS user");
        }
    }
}
