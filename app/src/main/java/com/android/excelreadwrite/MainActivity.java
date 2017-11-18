package com.android.excelreadwrite;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.android.excelreadwrite.permissionhelp.GetPermissionListener;
import com.android.excelreadwrite.permissionhelp.PermissionHelp;
import com.android.excelreadwrite.permissionhelp.PermissionType;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.android.excelreadwrite.PathHelp.getPath;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_select_file)
    Button btnSelectFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_select_file)
    public void onViewClicked() {
        PermissionHelp.getPermission(this, PermissionType.STORAGE, 100, new GetPermissionListener() {
            @Override
            public void onSuccess() {
                showFileChooser();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                String path=uri.getPath();
                Toast.makeText(this,path+"11111",Toast.LENGTH_SHORT).show();
                startFileViewActivity(path);
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                String path = PathHelp.getPath(this, uri);
                startFileViewActivity(path);
                Toast.makeText(this,path.toString(),Toast.LENGTH_SHORT).show();
            } else {//4.4一下系统调用方法
                String path = PathHelp.getRealPathFromURI(this,uri);
                startFileViewActivity(path);
                Toast.makeText(MainActivity.this, path+"222222", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startFileViewActivity(String path) {
        Intent intent=new Intent(this,FileViewActivity.class);
        intent.putExtra("path",path);
        startActivity(intent);
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }
}
