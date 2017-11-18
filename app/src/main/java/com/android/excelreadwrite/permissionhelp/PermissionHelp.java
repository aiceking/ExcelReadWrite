package com.android.excelreadwrite.permissionhelp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * Created by radio on 2017/11/15.
 */

public class PermissionHelp {

public static void getPermission(final Activity activity,final PermissionType permissionType,final int PermissionRequestCode,final GetPermissionListener getPermissionListener){
    AndPermission.with(activity)
            .requestCode(PermissionRequestCode)
            .permission(getPermissionByType(permissionType))
            .rationale(new RationaleListener() {
                @Override
                public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                    rationale.resume();
                }
            }).callback(new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            if (PermissionRequestCode==requestCode){
            if(AndPermission.hasPermission(activity,getPermissionByType(permissionType))) {
                if (getPermissionListener!=null){
                getPermissionListener.onSuccess();
                }
            } else {
                Toast.makeText(activity, "权限授予失败，请自行在设置中授权", Toast.LENGTH_SHORT).show();
            }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if (PermissionRequestCode==requestCode){
            if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                Toast.makeText(activity, "您已主动拒绝权限申请，请自行在设置中进行授权", Toast.LENGTH_SHORT).show();
            }else{
                if (!AndPermission.hasPermission(activity,getPermissionByType(permissionType))){
                Toast.makeText(activity, "请开启权限", Toast.LENGTH_SHORT).show();
            }else{
                    if (getPermissionListener!=null){
                        getPermissionListener.onSuccess();
                    }
                }
            }
            }
        }
    }).start();
}
public static String[] getPermissionByType(PermissionType permissionType){
    switch (permissionType){
        //短信权限
        case SMS:
            return Permission.SMS;
        //打电话权限
        case PHONE:
            return Permission.PHONE;
        //调用摄像头权限
        case CAMERA:
            return Permission.CAMERA;
        //传感器权限
        case SENSORS:
            return Permission.SENSORS;
        //存储权限
        case STORAGE:
            return Permission.STORAGE;
        //系统日历权限
        case CALENDAR:
            return Permission.CALENDAR;
        //通讯录权限
        case CONTACTS:
            return Permission.CONTACTS;
        //定位权限
        case LOCATION:
            return Permission.LOCATION;
        //麦克风权限
        case MICROPHONE:
            return Permission.MICROPHONE;
        default:
            return new String[]{};
    }
}
}
