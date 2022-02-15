package com.example.wintertext.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wintertext.R;
import com.example.wintertext.adapters.FragmentPagerAdapter;
import com.example.wintertext.beans.GamePlayer;
import com.example.wintertext.dialogs.AwardDialog;
import com.example.wintertext.dialogs.RuleDialog;
import com.example.wintertext.fragments.FragmentGame;
import com.example.wintertext.fragments.FragmentMessage;
import com.example.wintertext.fragments.FragmentSetting;
import com.example.wintertext.fragments.FragmentStore;
import com.example.wintertext.utilities.MyDatabaseHelper;
import com.example.wintertext.utilities.SharedGamePlayerData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * description ： TODO:主界面
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/1 17:00
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,RuleDialog.OnBtnListener,AwardDialog.OnBtnListener {
    private DrawerLayout drawerLayout;
    private ViewPager2 viewPager2;
    private FragmentPagerAdapter adapter1;
    private RadioGroup radioGroup;
    private Toolbar toolbar;
    private MyDatabaseHelper dbHelper;
    private FragmentGame fragmentGame;
    private FragmentStore fragmentStore;
    private FragmentMessage fragmentMessage;
    private FragmentSetting fragmentSetting;
    private NavigationView navigationView;
    private View headView;
    private ShapeableImageView imageView;
    private BottomSheetDialog bottomDialog;
    private View bottomView;
    private TextView take_picture,open_album,cancel;
    private String TAG = "123";
    private RuleDialog dialog;
    private AwardDialog awardDialog;
    private final int TAKE_PHOTO = 1;
    private final int OPEN_ALBUM = 2;
    private final int PHOTO_CROP = 3;
    private final int PHOTO_CROP_CHOOSE = 4;
    private Uri imageUrl;
    private File file;
    private String real_path = null;
    private final int TIME_MENTION = 1000 * 60 * 60 * 24;
    private AlarmManager alarmManager;
    private Calendar instance;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateTable();
        setContentView(R.layout.activity_main);
        initView();//加载控件
        initAddViewPager2();//添加适配器和fragment
        initEvent();//设置各种事件
    }

    //添加适配器，fragment以及页面联动按钮
    private void initAddViewPager2() {
        radioGroup.setOnCheckedChangeListener(this);
        viewPager2.setAdapter(adapter1);
        adapter1.addFragment(fragmentGame);
        adapter1.addFragment(fragmentStore);
        adapter1.addFragment(fragmentMessage);
        adapter1.addFragment(fragmentSetting);
        viewPager2.setCurrentItem(0);
        viewPager2.setOffscreenPageLimit(4);

        //页面与RadioButton按钮联动
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:((RadioButton)findViewById(R.id.game)).setChecked(true);
                    break;
                    case 1:((RadioButton)findViewById(R.id.store)).setChecked(true);
                    break;
                    case 2:((RadioButton)findViewById(R.id.msg)).setChecked(true);
                    break;
                    case 3:((RadioButton)findViewById(R.id.setting)).setChecked(true);
                    break;
                }
            }
        });
    }

    //加载控件的方法
    private void initView() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        instance = Calendar.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        viewPager2 = findViewById(R.id.main_view_pager2);
        adapter1 = new FragmentPagerAdapter(this);
        radioGroup = findViewById(R.id.radio_group_main);
        toolbar = findViewById(R.id.toolbar);
        fragmentGame = new FragmentGame();
        fragmentStore = new FragmentStore();
        fragmentMessage = new FragmentMessage();
        fragmentSetting = new FragmentSetting();
        dialog = new RuleDialog(this,R.layout.rule_dialog,new int[]{R.id.rule_dialog_ok,R.id.rule_dialog_not_ok});
        dialog.setListener((RuleDialog.OnBtnListener)this);
        awardDialog = new AwardDialog(this,R.layout.award_dialog,R.id.award_get);
        awardDialog.setListener((AwardDialog.OnBtnListener)this);
        navigationView = findViewById(R.id.navigation_view);
        headView = navigationView.inflateHeaderView(R.layout.navigation_layout);
        imageView = headView.findViewById(R.id.navigation_spImageView);
        bottomDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.bottom_dialog,null);
        bottomDialog.setContentView(bottomView);
        take_picture = bottomView.findViewById(R.id.tv_take_pictures);
        open_album = bottomView.findViewById(R.id.tv_open_album);
        cancel = bottomView.findViewById(R.id.tv_cancel);

        file = new File(getExternalCacheDir(),"头像.jpg");
        if(file.exists()){
            if(Build.VERSION.SDK_INT >= 24){
                imageUrl = FileProvider.getUriForFile(MainActivity.this,"camera_fileprovider",file);
            }else{
                imageUrl = Uri.fromFile(file);
            }
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }
    }

    //设置各种事件的方法
    private void initEvent() {
        setSupportActionBar(toolbar);
        instance.set(Calendar.HOUR_OF_DAY,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        alarmIntent = new Intent(this,NotificationBroadCast.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,instance.getTimeInMillis(),TIME_MENTION,pendingIntent);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.show();
            }
        });
        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
                bottomDialog.cancel();
            }
        });
        open_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbum();
                bottomDialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.cancel();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nv_check_rule:
                        dialog.show();
                        break;
                    case R.id.nv_save_data:
                        Toast.makeText(MainActivity.this,"这玩意自动存档",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nv_leave:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("确定要退出吗？")
                                .setMessage("玩到30级了没,这就不玩了？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(MainActivity.this,"这都不退？",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setCancelable(false)
                                .show();
                        break;
                }
                return false;
            }
        });
    }


    class NotificationBroadCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel("1","游戏",NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);
            }
            Notification notification = new NotificationCompat.Builder(MainActivity.this,"1")
                    .setContentTitle("恩索的邀请")
                    .setSmallIcon(R.drawable.android_image)
                    .setContentText("今天救济金领了吗？升到三十级了吗？")
                    .build();
            manager.notify(1,notification);
        }
    }

    //RadioGroup按钮与页面联动
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.game:
                viewPager2.setCurrentItem(0);
                break;
            case R.id.store:
                viewPager2.setCurrentItem(1);
                break;
            case R.id.msg:viewPager2.setCurrentItem(2);
                break;
            case R.id.setting:viewPager2.setCurrentItem(3);
                break;
        }
    }

    //toolbar与menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    //toolbar中menu的点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.take_photo:
                cutPicture();
                break;
            case R.id.award:
                awardDialog.show();
                break;
            default:
                break;
        }
        return true;
    }

    //截屏
    private void cutPicture() {
        Bitmap bitmap = null;
        //设置允许缓存
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();

        //开截
        bitmap = Bitmap.createBitmap(dView.getDrawingCache());

        //防止截屏重复
        dView.setDrawingCacheEnabled(false);
        dView.buildDrawingCache(false);

        //存相册
        saveInAlbum(bitmap);
    }

    private void saveInAlbum(Bitmap bitmap) {
        MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"恩索","一个截图");
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(new File("/sdcard/Boohee/image.jpg"))));
        Toast.makeText(this,"已截图,请到相册查看",Toast.LENGTH_SHORT).show();
    }

    //创建SQLite数据库并初始化数据
    private void CreateTable(){
        dbHelper = new MyDatabaseHelper(this,"Game.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //检查表中是否有数据
        String sql = "select count(*) from Game";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);//根据Count判断是否为空
        cursor.close();
        if(count == 0){
            ContentValues values = new ContentValues();
            //初始化亚索数据
            values.put("name","yasuo");
            values.put("life",585);
            values.put("attack",68);
            values.put("defense",30);
            values.put("strike",0);
            values.put("steal",0);
            values.put("exc",0);
            values.put("max_exc",20);
            values.put("grade",1);
            values.put("money",0);
            db.insert("Game",null,values);
            values.clear();
            //初始化永恩数据
            values.put("name","yong_en");
            values.put("life",630);
            values.put("attack",73);
            values.put("defense",34);
            values.put("strike",10);
            values.put("steal",20);
            values.put("exc",0);
            values.put("max_exc",0);
            values.put("grade",1);
            values.put("money",0);
            db.insert("Game",null,values);
            values.clear();
            //初始化炮车数据
            values.put("name","pao_che");
            values.put("life",912);
            values.put("attack",41);
            values.put("defense",0);
            values.put("strike",0);
            values.put("steal",0);
            values.put("exc",0);
            values.put("max_exc",0);
            values.put("grade",1);
            db.insert("Game",null,values);
            values.clear();
            Toast.makeText(this, "初始化数据完成", Toast.LENGTH_SHORT).show();
        }
    }

    //更换头像中拍照的操作方法
    private void takePhoto(){
        String[] permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        List<String> list = new ArrayList<>();
        for (int i=0;i<permissions.length;i++){
            if(ContextCompat.checkSelfPermission(this,permissions[i])!=PackageManager.PERMISSION_GRANTED){
                list.add(permissions[i]);
            }
        }
        if(list.size()>0){
            ActivityCompat.requestPermissions(this,permissions,1);
        }else{
            try {
                if(file.exists()){
                    file.delete();
                }
                file.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
            if(Build.VERSION.SDK_INT >= 24){
                imageUrl = FileProvider.getUriForFile(MainActivity.this,"camera_fileprovider",file);
            }else{
                imageUrl = Uri.fromFile(file);
            }
            //启动相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl);
            startActivityForResult(intent,TAKE_PHOTO);
        }
    }

    //更改头像中选取相册的操作方法
    private void openAlbum(){
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl);
            startActivityForResult(intent,OPEN_ALBUM);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照
            case TAKE_PHOTO:
                try { 
                    if(resultCode == RESULT_OK){ 
                        startCrop();
                    }else {
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
            }catch (Exception e){
                e.printStackTrace();
            }
                break;
            //相册
            case OPEN_ALBUM:
                if(resultCode == RESULT_OK) {
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            //设置成头像
            case PHOTO_CROP:
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PHOTO_CROP_CHOOSE:
                if(resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
                        imageView.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.download.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.parseLong(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    //裁剪图片
    private void displayImage(String imagePath) {
        try {
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24){
            imageUrl = FileProvider.getUriForFile(MainActivity.this,"camera_fileprovider",file);
        }else{
            imageUrl = Uri.fromFile(file);
        }
        Uri uri = FileProvider.getUriForFile(MainActivity.this,"camera_fileprovider",new File(imagePath));
        Intent intent = new Intent("com.android.camera.action.CROP");
        Log.d(TAG, "displayImage: "+imageUrl);
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("scale",true);
        intent.putExtra("circleCrop",true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, PHOTO_CROP_CHOOSE);
    }

    //获得图片路径
    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //裁剪图片
    private void startCrop(){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUrl,"image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("scale",true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, PHOTO_CROP);
    }

    //游戏规则界面的两个按钮的点击事件
    @Override
    public void OnBtnClick(RuleDialog dialog, View view) {
        switch (view.getId()){
            case R.id.rule_dialog_ok:
                Toast.makeText(MainActivity.this,"懂了就好",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rule_dialog_not_ok:
                Toast.makeText(MainActivity.this,"想明白搞懂！",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("确定要退出吗？")
                .setMessage("玩到30级了没,这就不玩了？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"这都不退？",Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void OnBtnClick(AwardDialog dialog, View view) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String data = dateFormat.format(new Date());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if((sharedPreferences.getString("data",null) == null) | !data.equals(sharedPreferences.getString("data",null))){
            editor.putString("data",data);
            editor.apply();
            SharedGamePlayerData sharedGamePlayerData = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(SharedGamePlayerData.class);
            GamePlayer gamePlayer = new GamePlayer();
            gamePlayer.setMoney(2000);
            sharedGamePlayerData.setData(gamePlayer);
            Toast.makeText(this,"领取成功",Toast.LENGTH_SHORT).show();
            MaterialButton button = view.findViewById(R.id.award_get);
            button.setText("今日已领取");
        }else if(data.equals(sharedPreferences.getString("data",null))) {
            Toast.makeText(this, "一天只能领一次,想啥呢！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"已授权",Toast.LENGTH_SHORT).show();
        }else if(grantResults[0] == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this,"已禁止",Toast.LENGTH_SHORT).show();
        }
    }
}