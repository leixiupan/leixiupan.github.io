package com.example.studentmgr;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {
    private ArrayList<String> studentList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private int longClickedPosition = -1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_add:
                openStudentActivity01();

                return true;

            case R.id.action_refresh:
                //暂时没有实际的功能
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSearch = findViewById(R.id.btn_search); // 假设你的查询按钮ID为btn_search



        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityStudent.class);
                startActivityForResult(intent, 1);
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);

        ListView listView = findViewById(R.id.lvwStudent);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedPosition = position;
                return false;
            }
        });
        btnSearch.setOnClickListener(view -> {

            // 1. 创建一个输入框来输入查询内容
            EditText input = new EditText(ActivityMain.this);

            // 2. 创建对话框，包含输入框和两个按钮
            new AlertDialog.Builder(ActivityMain.this)
                    .setTitle("请输入查询关键词")
                    .setView(input)
                    .setPositiveButton("确定", (dialog, which) -> {

                        // 获取输入的查询内容
                        String query = input.getText().toString();

                        // 对学生信息进行查询
                        List<String> result = searchStudentInfo(query);

                        // 如果查询结果为空，使用Toast提示用户
                        if (result.isEmpty()) {
                            Toast.makeText(ActivityMain.this, "没有查询到结果", Toast.LENGTH_SHORT).show();
                        } else {
                            // 否则，更新ListView的数据源并刷新ListView
                            studentList.clear();
                            studentList.addAll(result);
                            ((ArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
    }

    // 查询方法
    private List<String> searchStudentInfo(String query) {
        List<String> result = new ArrayList<>();

        for (String info : studentList) {
            if (info.contains(query)) {
                result.add(info);
            }
        }

        return result;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        switch (item.getItemId()) {
            case R.id.menu_edit:
                openStudentActivity(position);
                return true;

            case R.id.menu_delete:
                showDeleteConfirmationDialog(position);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteStudent(int position) {
        studentList.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(ActivityMain.this, "学生信息已成功删除", Toast.LENGTH_SHORT).show();

    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除该条学生信息吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteStudent(position);
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    private void openStudentActivity(int position) {
        Intent intent = new Intent(ActivityMain.this, ActivityStudent.class);
        intent.putExtra("position", position);
        startActivityForResult(intent, 2);
    }
    private void openStudentActivity01() {
        Intent intent = new Intent(ActivityMain.this, ActivityStudent.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String studentInfo = data.getStringExtra("studentInfo");
                studentList.add(studentInfo);
                adapter.notifyDataSetChanged();
            } else if (requestCode == 2) {
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    String updatedStudentInfo = data.getStringExtra("studentInfo");
                    studentList.set(position, updatedStudentInfo);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}