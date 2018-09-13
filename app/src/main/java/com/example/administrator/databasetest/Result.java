package com.example.administrator.databasetest;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Result extends Activity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private DbAdapter adapter;
    DBHelper dbHelper;
    List<Item> dataList = new ArrayList<Item>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listView = (ListView)findViewById(R.id.listView1);
        dataList = queryData();
        //实例化DbAdapter
        adapter = new DbAdapter(getApplication(), dataList);
        listView.setAdapter(adapter);

    }

    private List<Item> queryData(){
        List<Item> list = new ArrayList<Item>();
        dbHelper = new DBHelper(this);

        //调用query()获取Cursor
        Cursor c = dbHelper.query();
        while (c.moveToNext()){
            int _id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            String ip = c.getString(c.getColumnIndex("ip"));
            String port = c.getString(c.getColumnIndex("port"));
            //String desc = c.getString(c.getColumnIndex("desc"));

            //用一个Person对象来封装查询出来的数据
            Item p = new Item();
            p.set_id(_id);
            p.setName(name);
            p.setIp(ip);
            p.setPort(port);
           // p.setDesc(desc);

            list.add(p);
        }
        return list;
    }


    //自定义DbAdapter
    public class DbAdapter extends BaseAdapter {
        private List<Item> list;
        private Context context;
        private LayoutInflater layoutInflater;

        public DbAdapter(Context context, List<Item> list) {
            layoutInflater = LayoutInflater.from(context);
            this.context = context;
            this.list = list;
        }

        //刷新适配器
        public void refresh(List<Item> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item p = list.get(position);
            ViewHolder holder;

            if(convertView == null){
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.item, null);
                holder.name = (TextView)convertView.findViewById(R.id.textView1);
                holder.ip = (TextView)convertView.findViewById(R.id.textView2);
                holder.port = (TextView)convertView.findViewById(R.id.textView3);
               // holder.desc = (TextView)convertView.findViewById(R.id.textView4);

                convertView.setTag(holder);

            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(p.getName());
            holder.ip.setText(p.getIp());
            holder.port.setText(p.getPort());
           // holder.desc.setText(p.getDesc());

            return convertView;
        }

        public class ViewHolder {
            public TextView name;
            public TextView ip;
            public TextView port;
           // public TextView desc;
            public TextView id;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final Item p = dataList.get(position);
        final long temp = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("真的要删除该记录？").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //调用delete（）删除某条数据
                dbHelper.delete(p.get_id());
                //重新刷新适配器
                adapter.refresh(queryData());
            }
        }).setNegativeButton("否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();


        // 关闭数据库
        dbHelper.close();
    }


}
