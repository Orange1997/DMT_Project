package com.example.dc.dmt.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.example.dc.dmt.R;
import com.example.dc.dmt.ui.CenterTitleActionbar;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

import butterknife.ButterKnife;

import static android.os.Process.killProcess;

/**
 * Created by DC on 2018/9/2.
 */

public class BaseActivity extends BaseCompatActivity   {
    private AlertDialog mAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面都竖屏模式
        if (BaseActivity.this.onScreenOrientation()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        Fresco.initialize(this); //初始化Fresco
        ButterKnife.bind(this);

    }
    public void initActionBar(CenterTitleActionbar centerTitleActionbar) {
        setSupportActionBar(centerTitleActionbar.getToolbar());//toolbar赋予actionbar属性
        centerTitleActionbar.setTitle(this.getTitle().toString());//设置自定义标题
        centerTitleActionbar.isRightShow (false);
        centerTitleActionbar.setNavigationIcon (R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//设置不显示默认标题
    }
    @Override
    protected boolean onNavigation() {
        return true;
    }

    protected  boolean onScreenOrientation(){
        return true;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    @Override
    public void onBackPressed() {
//        if (isTaskRoot())
//            IntentHelper.backMain(this);//切换只后台的退出
//        else
            super.onBackPressed();

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }
    //用于接口调用时提示加载中
    ProgressDialog mBaseProgressDialog;
    public void showLoadingProgress() {
        try {
            if (mBaseProgressDialog == null || !mBaseProgressDialog.isShowing()) {
                mBaseProgressDialog = ProgressDialog.show(this, "", "请稍候...", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideLoadingProgress() {
        try {
            if (mBaseProgressDialog != null && mBaseProgressDialog.isShowing()) {
                mBaseProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    public void basicFailure(int retcode, String prompt) {
//        DTLog.showMessage(this,prompt);
//    }
    public static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    public static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    public static final int REQUEST_CAMERA_ACCESS_PERMISSION = 104;
    public static final int REQUEST_STORAGE_READ_ACCESS_FOR_CROP_PERMISSION = 103;
    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    public void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog( rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this,
                                    new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }
    /**
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    public void showAlertDialog(@Nullable String message,
                                @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                @NonNull String positiveText,
                                @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        mAlertDialog = builder.show();
    }
    /**
     * 不可取消的选择框
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    public void showAlertDialogNoCancel(@Nullable String message,
                                        @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                        @NonNull String positiveText,
                                        @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                        @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.setCancelable(false);
        mAlertDialog = builder.show();
    }
    private static final String IMAGE_UNSPECIFIED = "image/*";
    protected static final int ALBUM_REQUEST_CODE = 2;//选相册
    protected static final int CAMERA_REQUEST_CODE = 1;//选拍照

    /**
     * 用于拍照照片保存路径
     * @return
     */
    protected String getImageFolder()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        }
//        String imageFolder = Environment.getExternalStorageDirectory() + "/vss_m/";
//        File imageFolderFile =new File(imageFolder);
//        if (!imageFolderFile.exists()) {
//            imageFolderFile.mkdirs();
//        }
//        return imageFolder;
        File imagePath = new File(Environment.getExternalStorageDirectory(), "vss_m");
        if (!imagePath.exists())
            imagePath.mkdirs();
        String imageFolder=imagePath.getAbsolutePath();
        return imageFolder;
    }

    /**
     * 调用相机
     */
//    protected void showCamera(String targetPath, String fileName) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.CAMERA,
//                    getString(R.string.permission_camera_rationale),
//                    REQUEST_CAMERA_ACCESS_PERMISSION);
//        }
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
//                    getString(R.string.permission_read_storage_rationale),
//                    REQUEST_STORAGE_READ_ACCESS_FOR_CROP_PERMISSION);
//        } else {
//            File newFile = new File(targetPath, fileName);
//
//            //第二参数是在manifest.xml定义 provider的authorities属性
//            Uri contentUri = FileProvider.getUriForFile(this, getString(R.string.file_provider_authorities), newFile);
//
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            //兼容版本处理，因为 intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION) 只在5.0以上的版本有效
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                ClipData clip = ClipData.newUri(getContentResolver(), "A photo", contentUri);
//                intent.setClipData(clip);
//                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            } else {
//                List<ResolveInfo> resInfoList = getPackageManager()
//                        .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//                for (ResolveInfo resolveInfo : resInfoList) {
//                    String packageName = resolveInfo.activityInfo.packageName;
//                    grantUriPermission(packageName, contentUri,
//                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                }
//            }
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
//            startActivityForResult(intent, CAMERA_REQUEST_CODE);
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_ACCESS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                            getString(R.string.permission_read_storage_rationale),
                            REQUEST_STORAGE_READ_ACCESS_FOR_CROP_PERMISSION);
                }
            }
        }else {
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    /**
     * 调用相册
     */
    protected void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setType(IMAGE_UNSPECIFIED);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.info_choose_from_album)), ALBUM_REQUEST_CODE);
        }
    }


    public void showSimpleDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    /**
     * 关闭app对话框
     */
    private void showFinishDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancelable(false)
                .setMessage(msg)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        killProcess(android.os.Process.myPid());    //获取PID
                        System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
                    }
                })
                .show();
    }
    /**
     * Hide alert dialog if any.
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭加载提示
        hideLoadingProgress();

    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}

