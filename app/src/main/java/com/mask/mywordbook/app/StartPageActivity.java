package com.mask.mywordbook.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Vibrator;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mask.mywordbook.R;
import com.mask.mywordbook.bean.MusicBean;
import com.mask.mywordbook.util.HttpUtil;
import com.mask.mywordbook.util.MusicServiceRxjava;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 进入页面
 * 首次进入出现弹窗，设置新密码（0-9）（4位）
 * 密码：4位
 * 首次进入输入密码两次
 * 两次不同就重新输入两次
 *
 */

public class StartPageActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = StartPageActivity.class.getSimpleName();

    private Button mButton1,mButton2,mButton3,mButton4,mButton5,mButton6,mButton7,mButton8,mButton9,mButton0;
    private ImageView mIvDelete;
    private ImageView mIvBackground;
    private TextView mTvPrompt;//提示框
    private TextView mTvPassword;

    private StringBuilder mPw = new StringBuilder();

    private SharedPreferences sharedPreferences;
    public static String password = "password";
    private String mPassword;
    public static Boolean isSet;
    public static Boolean isReSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_page);
        initView();
        if (!PasswordExists()){
            mTvPrompt.setText("请设置密码");
            isSet = true;
        }else {
            isSet = false;
            mPassword = getPassword();
        }

        testMain();

    }

    private void testMain() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.uomg.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MusicServiceRxjava musicService = retrofit.create(MusicServiceRxjava.class);
        Observable<MusicBean> getMusic  = musicService.getMusicJson("热歌榜","json");
        getMusic.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MusicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MusicBean musicBean) {
                        Log.e(TAG, "onNext: "+Thread.currentThread().getName() );
                        Log.e(TAG, "onResponse: "+musicBean.getCode() );
                        Log.e(TAG, "onResponse: "+musicBean.getData().getName() );
                        Log.e(TAG, "onResponse: "+musicBean.getData().getArtistsname() );
                        Log.e(TAG, "onResponse: "+musicBean.getData().getPicurl() );
                        Log.e(TAG, "onResponse: "+musicBean.getData().getUrl() );
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.uomg.com/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        MusicService musicService = retrofit.create(MusicService.class);
//
//        retrofit2.Call<MusicBean> call =  musicService.getMusicJson("热歌榜","json");
//
//        call.enqueue(new retrofit2.Callback<MusicBean>() {
//            @Override
//            public void onResponse(retrofit2.Call<MusicBean> call, retrofit2.Response<MusicBean> response) {
//                Log.e(TAG, "onResponse: 请求成功" );
//                Log.e(TAG, "onResponse: "+response.toString() );
//                MusicBean musicBean = response.body();
//
//                Log.e(TAG, "onResponse: "+musicBean.getCode() );
//                Log.e(TAG, "onResponse: "+musicBean.getData().getName() );
//                Log.e(TAG, "onResponse: "+musicBean.getData().getArtistsname() );
//                Log.e(TAG, "onResponse: "+musicBean.getData().getPicurl() );
//                Log.e(TAG, "onResponse: "+musicBean.getData().getUrl() );
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<MusicBean> call, Throwable t) {
//                Log.e(TAG, "onFailure: 请求失败" );
//
//            }
//        });
//


    }

    private boolean PasswordExists() {
        sharedPreferences = getSharedPreferences("Password",MODE_PRIVATE);
        mPassword = sharedPreferences.getString(password,"");
        if(mPassword.length() == 4){
            return true;
        }
        return false;
    }

    public void savePassword(String password){
        SharedPreferences sharedPreferences = getSharedPreferences("Password",MODE_PRIVATE);
        SharedPreferences.Editor editable = sharedPreferences.edit();
        editable.putString(StartPageActivity.password,password);
        editable.apply();
        editable.commit();
    }

    public String getPassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("Password",MODE_PRIVATE);
        return sharedPreferences.getString(password,"");
    }

    private void initView() {
        mButton0 = findViewById(R.id.button0);
        mButton0.setOnClickListener(this);
        mButton1 = findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
        mButton2 = findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
        mButton3 = findViewById(R.id.button3);
        mButton3.setOnClickListener(this);
        mButton4 = findViewById(R.id.button4);
        mButton4.setOnClickListener(this);
        mButton5 = findViewById(R.id.button5);
        mButton5.setOnClickListener(this);
        mButton6 = findViewById(R.id.button6);
        mButton6.setOnClickListener(this);
        mButton7 = findViewById(R.id.button7);
        mButton7.setOnClickListener(this);
        mButton8 = findViewById(R.id.button8);
        mButton8.setOnClickListener(this);
        mButton9 = findViewById(R.id.button9);
        mButton9.setOnClickListener(this);
        mTvPassword = findViewById(R.id.start_tv_password);
        mTvPrompt = findViewById(R.id.start_tv_prompt);
        mIvDelete = findViewById(R.id.start_iv_delete);
        mIvDelete.setVisibility(View.GONE);
        mIvDelete.setOnClickListener(this);
        mIvBackground = findViewById(R.id.start_iv_background);
        try {
            loadBingPic();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequset(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(StartPageActivity.this != null && !StartPageActivity.this.isFinishing()){
                            Glide.with(StartPageActivity.this).load(bingPic).bitmapTransform(new BlurTransformation(StartPageActivity.this,15)).into(mIvBackground);
                        }
                     }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
                mIvDelete.setVisibility(View.VISIBLE);
                mPw.append(((Button)v).getText());
                mTvPassword.setText(mPw.toString());
                if(mPw.length() == 4){
                    if(isSet){
                        if(isReSet){
                            if(mPassword.equals(mPw.toString())){
                                savePassword(mPassword);
                                Intent intent = new Intent(StartPageActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                setVibrator();
                                mTvPrompt.setText("密码不匹配，请重试");
                                mTvPassword.setText("");
                                mIvDelete.setVisibility(View.GONE);
                                mPw=mPw.delete(0, mPw.length());
                            }
                        } else {
                            mPassword = mPw.toString();
                            mPw = mPw.delete(0, mPw.length());
                            mTvPrompt.setText("请再次输入密码");
                            mTvPassword.setText("");
                            isReSet = true;
                        }
                    }else if(mPassword.equals(mPw.toString())) {
                        Intent intent = new Intent(StartPageActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if(!mPassword.equals(mPw.toString())) {
                        setVibrator();
                        mTvPrompt.setText("密码错误，请重新输入");
                        mIvDelete.setVisibility(View.GONE);
                        mPw.delete(0, mPw.length());
                        mTvPassword.setText("");
                    }
                }
                break;
            case R.id.start_iv_delete:
                mPw = mPw.delete(mPw.length()-1,mPw.length());
                mTvPassword.setText(mPw);
                break;
        }
    }

    public void setVibrator(){
        Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    private static final float BITMAP_SCALE = 0.4f;

    /**
     * 模糊图片的具体方法
     *
     * @param context 上下文对象
     * @param image   需要模糊的图片
     * @return 模糊处理后的图片
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blurBitmap(Context context, Bitmap image, float blurRadius) {
        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        // 将缩小后的图片做为预渲染的图片
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        // 创建一张渲染后的输出图片
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(blurRadius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);

        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }
}
