package com.android.excelreadwrite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileViewActivity extends AppCompatActivity {

    @BindView(R.id.fileview)
    FileView fileview;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);
        ButterKnife.bind(this);
        path=getIntent().getStringExtra("path");
        if (!TextUtils.isEmpty(path)){
            showFile(path);
        }else{
            Toast.makeText(this, "path为空", Toast.LENGTH_SHORT).show();
        }
    }
    public void showFile(String path) {
        File file = new File(path);
        fileview.displayFile(file, new FileView.showFileListener() {
            @Override
            public void Success() {
                Toast.makeText(FileViewActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void Failed(String message) {
                Toast.makeText(FileViewActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fileview != null) {
            fileview.onStopDisplay();
        }
    }

}
