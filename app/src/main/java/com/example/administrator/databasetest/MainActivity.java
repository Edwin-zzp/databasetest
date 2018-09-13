package com.example.administrator.databasetest;


import android.app.Activity;
import android.content.ContentValues;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;



public class MainActivity extends Activity {

    private DBHelper dbHelper;
    private Button addButton = null;
    private Button listButton =null;

    private EditText name , ip , port ;

    //private RadioGroup radio;
    private String nameStr,ipStr,portStr;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

//        ArrayList<Item> itemlist = new ArrayList<>();

        addButton = (Button) findViewById(R.id.add_bt);
        listButton = (Button) findViewById(R.id.list_bt);

        name = (EditText) findViewById(R.id.name_et);
        ip = (EditText) findViewById(R.id.ip_et);
        port = (EditText) findViewById(R.id.port_et);



        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().length() != 0){
                    nameStr = name.getText().toString();
                }else{
                    Toast.makeText(getApplication(), "电脑名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ip.getText().toString().length() != 0){
                    ipStr = ip.getText().toString();
                }else{
                    Toast.makeText(getApplication(), "IP不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(port.getText().toString().length() != 0){
                    portStr = port.getText().toString();
                }else{
                    Toast.makeText(getApplication(), "端口不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //实例化一个ContentValues， ContentValues是以键值对的形式，键是数据库的列名，值是要插入的值
                ContentValues values = new ContentValues();
                values.put("name", nameStr);
                values.put("ip", ipStr);
                values.put("port", portStr);


                //调用insert插入数据库
                dbHelper.insert(values);

                Toast.makeText(getApplication(),"数据添加成功",Toast.LENGTH_SHORT).show();

                //将三个输入框重置下
                reset();
            }
        });


        listButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(MainActivity.this, Result.class);
                startActivity(intent);

            }
        });

    }

    //重置edittext
    private void reset(){
        name.setText("");
        ip.setText("");
        port.setText("");
    }

}
