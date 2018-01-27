/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.administrator.aitang.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/********************************************************************
  @description: 动态权限申请，使用第三方库 https://github.com/yanzhenjie/AndPermission
  @author: wangzexin
  @time: 2017/11/27 15:01
  @变更历史:
********************************************************************/
public class PermissionRequest {
    private static final int REQUEST_CODE_PERMISSION = 100; //权限的请求码
    private static final int REQUEST_CODE_SETTING = 300; //跳转设置的请求码

    private Context mContext;
    private PermissionCallback mCallback;

    public PermissionRequest(Context context, PermissionCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
    }

    /**
     * 申请指定的某组权限
     * @param permissionsArray  :Permission.PHONE, Permission.LOCATION
     */
    public void requestGroup(String[]... permissionsArray) {
        // 申请权限。
        AndPermission.with(mContext)
                .requestCode(REQUEST_CODE_PERMISSION)
                .permission(permissionsArray)
                .callback(permissionListener)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(rationaleListener)
                .start();
    }

    /**
     * 申请指定的某个权限
     * @param permissions
     */
    public void request(String... permissions) {
        // 申请权限。
        AndPermission.with(mContext)
                .requestCode(REQUEST_CODE_PERMISSION)
                .permission(permissions)
                .callback(permissionListener)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(rationaleListener)
                .start();
    }
    /**
     * 回调监听
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    mCallback.onSuccessful();
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    mCallback.onFailure();
                    break;
                }
            }

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(mContext, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog((Activity) mContext, REQUEST_CODE_SETTING).show();

                // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingHandle = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingHandle.execute();
//            你的dialog点击了取消调用：
//            settingHandle.cancel();
            }
        }
    };

    private RationaleListener rationaleListener=new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
            AndPermission.rationaleDialog(mContext, rationale).show();
        }
    };

    public interface PermissionCallback {
        void onSuccessful();

        void onFailure();
    }

}
