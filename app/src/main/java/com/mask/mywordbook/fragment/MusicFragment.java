package com.mask.mywordbook.fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mask.mywordbook.R;
import com.mask.mywordbook.bean.MusicBean;
import com.mask.mywordbook.util.MusicServiceRxjava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//在线播放音乐
public class MusicFragment extends Fragment implements View.OnClickListener{
    public TextView mMusicTitle;
    public Button mBtnLeft,mBtnRight,mBtnOn_Off;
    public RadioGroup mRadioGroup;
    public ImageView mIvMusic;
    public SeekBar mSeekBar;
    public Context mContext;
    public MediaPlayer mMediaPlayer;
    public List<MusicBean> mMusicBeans = new ArrayList<>();
    public int mMusicIndex = 0;
    public boolean isChangeMusic = false;//是否是切换歌曲
    public MusicFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_fragment,container,false);
        mContext = getContext();
        initView(view);
        return view;
    }

    public void initView(View view){
        mMusicTitle = view.findViewById(R.id.music_tv_title);
        mBtnLeft = view.findViewById(R.id.music_btn_left);
        mBtnLeft.setOnClickListener(this);
        mBtnRight = view.findViewById(R.id.music_btn_right);
        mBtnRight.setOnClickListener(this);
        mBtnOn_Off = view.findViewById(R.id.music_btn_on_off);
        mBtnOn_Off.setOnClickListener(this);
        mBtnOn_Off.setEnabled(false);
        mIvMusic = view.findViewById(R.id.music_iv_image);
        mRadioGroup = view.findViewById(R.id.music_rg_group);
        mSeekBar = view.findViewById(R.id.seekBar);
        mSeekBar.setVisibility(View.GONE);//暂时不用mSeekBar,以后添加
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mMediaPlayer!= null){
                    int dest = seekBar.getProgress();
                    int time = mMediaPlayer.getDuration();
                    int max = seekBar.getMax();
                    mMediaPlayer.seekTo(time*dest/max);
                }
            }
        });
        rightMusic();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.music_btn_left:
                isChangeMusic = true;
                leftMusic();
                break;
            case R.id.music_btn_right:
                isChangeMusic = true;
                rightMusic();
                break;
            case R.id.music_btn_on_off:
                if(!mMediaPlayer.isPlaying()){//没播放
                        mMediaPlayer.start();
                    mBtnOn_Off.setBackgroundResource( R.drawable.off);
                }else{//播放中
                    mMediaPlayer.pause();
                    mBtnOn_Off.setBackgroundResource(R.drawable.play);
                }
                break;
        }
    }

    public void initMusic(String sort){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.uomg.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MusicServiceRxjava musicServiceRxjava = retrofit.create(MusicServiceRxjava.class);
        Observable<MusicBean> musicBeanObservable = musicServiceRxjava.getMusicJson(sort,"json");
        musicBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MusicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(MusicBean musicBean) {
                        if(musicBean != null){
                            mMusicBeans.add(musicBean);
                            mMusicTitle.setText(musicBean.getData().getName());
                            Glide.with(getContext())
                                    .load(musicBean.getData().getPicurl())
                                    .into(mIvMusic);
                            if(mMediaPlayer == null){
                                mMediaPlayer = new MediaPlayer();
                                //播放完成事件
                                mMediaPlayer.setOnCompletionListener(mp -> {
                                    rightMusic();
                                });
                                //网络缓冲情况事件
                                mMediaPlayer.setOnBufferingUpdateListener((mp, percent) -> Log.e("TAG", "音乐缓冲的百分比onBufferingUpdate: "+percent ));
                                //准备完成
                                mMediaPlayer.setOnPreparedListener(mp -> {
                                    mBtnOn_Off.setEnabled(true);
                                    if(isChangeMusic){//点击了切换
                                        mMediaPlayer.start();
                                        mBtnOn_Off.setBackgroundResource( R.drawable.off);
                                        isChangeMusic = false;
//                                            Observable.interval(0,100, TimeUnit.MILLISECONDS)
//                                            .subscribe(new Consumer<Long>() {
//                                                @Override
//                                                public void accept(Long aLong) throws Exception {
////                                                    Log.e("？？？", "获取当前播放的位置accept: "+ mMediaPlayer.getCurrentPosition());
////                                                    Log.e("？？？", "得到文件的时间accept: "+ mMediaPlayer.getDuration());
//                                                    int position = mMediaPlayer.getCurrentPosition();
//                                                    int time = mMediaPlayer.getDuration();
//                                                    int max = mSeekBar.getMax();
//                                                    mSeekBar.setProgress(position*max/time);
//                                                }
//                                            });
//                                        Observable.interval(0,100,TimeUnit.MILLISECONDS)
//                                                .doOnNext(new Consumer<Long>() {
//                                                    @Override
//                                                    public void accept(Long aLong) throws Exception {
//                                                        int position = mMediaPlayer.getCurrentPosition();
//                                                        int time = mMediaPlayer.getDuration();
//                                                        int max = mSeekBar.getMax();
//                                                        mSeekBar.setProgress(position*max/time);
//                                                    }
//                                                }).subscribe(new Observer<Long>() {
//                                                    @Override
//                                                    public void onSubscribe(Disposable d) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onNext(Long aLong) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onError(Throwable e) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onComplete() {
//
//                                                    }
//                                                });
                                    }
                                });
                                mMediaPlayer.setOnErrorListener((mp, what, extra) -> false);
                            }
                            mMediaPlayer.reset();
                            try {
                                mMediaPlayer.setDataSource(musicBean.getData().getUrl());
                                mMediaPlayer.prepareAsync();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void leftMusic(){//上一首
        mMusicIndex--;
        if(mMusicIndex < 0){
            mMusicIndex++;
            String str = ((RadioButton)mRadioGroup.findViewById(mRadioGroup.getCheckedRadioButtonId())).getText().toString();
            initMusic(str);
        }else{
            playMusic();
        }
    }

    public void rightMusic(){//下一首
        mMusicIndex++;
        if(mMusicIndex >= mMusicBeans.size()){
            String str = ((RadioButton)mRadioGroup.findViewById(mRadioGroup.getCheckedRadioButtonId())).getText().toString();
            initMusic(str);
        }else{
            playMusic();
        }
    }

    public void playMusic(){
        MusicBean musicBean = mMusicBeans.get(mMusicIndex);
        mMusicTitle.setText(musicBean.getData().getName());
        Glide.with(getContext())
                .load(musicBean.getData().getPicurl())
                .into(mIvMusic);
        if(mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(musicBean.getData().getUrl());
            mMediaPlayer.prepare();
            if(isChangeMusic){
                mMediaPlayer.start();
                isChangeMusic = false;
                mBtnOn_Off.setBackgroundResource(R.drawable.off);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
