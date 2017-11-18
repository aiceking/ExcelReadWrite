package com.android.excelreadwrite;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * Created by static on 2017/11/17/017.
 */

public class FileView extends FrameLayout implements TbsReaderView.ReaderCallback{
    private String Tag="FileView";
    private TbsReaderView mTbsReaderView;
    private int saveTime = -1;
    private Context context;
    public interface showFileListener{
        void Success();
        void Failed(String message);
    }
    public FileView(@NonNull Context context) {
        this(context, null, 0);
    }

    public FileView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FileView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTbsReaderView = new TbsReaderView(context, this);
        this.addView(mTbsReaderView, new LinearLayout.LayoutParams(-1, -1));
        this.context = context;
    }


    private TbsReaderView getTbsReaderView(Context context) {
        return new TbsReaderView(context, this);
    }

    public void displayFile(File mFile,showFileListener showFileListener) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (mFile != null && !TextUtils.isEmpty(mFile.toString())) {
                //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
                String bsReaderTemp = "/storage/emulated/0/TbsReaderTemp";
                File bsReaderTempFile = new File(bsReaderTemp);
                if (!bsReaderTempFile.exists()) {
                    Log.d(Tag, "准备创建/storage/emulated/0/TbsReaderTemp！！");
                    boolean mkdir = bsReaderTempFile.mkdir();
                    if (!mkdir) {
                        Log.e(Tag, "创建/storage/emulated/0/TbsReaderTemp失败！！！！！");
                    }
                }
                //加载文件
                Bundle localBundle = new Bundle();
                Log.d(Tag, mFile.toString());
                localBundle.putString("filePath", mFile.toString());
                localBundle.putString("tempPath", Environment.getExternalStorageDirectory() + "/" + "TbsReaderTemp");

                if (this.mTbsReaderView == null)
                    this.mTbsReaderView = getTbsReaderView(context);

                boolean bool = this.mTbsReaderView.preOpen(getFileType(mFile.toString()), false);
                if (bool) {
                    this.mTbsReaderView.openFile(localBundle);
                    showFileListener.Success();
                }else{
                    showFileListener.Failed("文件路径无效！");
                }
            } else {
                showFileListener.Failed("文件路径无效！");
            }
        }else{
            showFileListener.Failed("SD卡不存在或者不可读写！");
        }
    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d(Tag, "paramString---->null");
            return str;
        }
        Log.d(Tag, "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d(Tag, "i <= -1");
            return str;
        }


        str = paramString.substring(i + 1);
        Log.d(Tag, "paramString.substring(i + 1)------>" + str);
        return str;
    }
    /***
     * 将获取File路径的工作，“外包”出去
     */
    public interface OnGetFilePathListener {
        void onGetFilePath(FileView fileView);
    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        Log.e(Tag,"****************************************************" + integer);
    }

    public void onStopDisplay() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
