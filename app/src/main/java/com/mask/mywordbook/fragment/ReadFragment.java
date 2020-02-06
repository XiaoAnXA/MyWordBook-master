package com.mask.mywordbook.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mask.mywordbook.R;
import com.mask.mywordbook.adapter.ReadRvAdapter;
import com.mask.mywordbook.bean.EnglishBean;
import com.mask.mywordbook.bean.Song;
import com.mask.mywordbook.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReadFragment extends Fragment implements View.OnClickListener {
    public RecyclerView mRecyclerView;
    public ReadRvAdapter mReadRvAdapter;
    public List<EnglishBean.ShowapiResBodyBean.DataBean> mAllDataBeans = new ArrayList<EnglishBean.ShowapiResBodyBean.DataBean>() ;

    public SwipeRefreshLayout mSwipeRefreshLayout;
    public FloatingActionButton mFloatingActionButton;

    private ArrayList<Song> mSongs;
    private MediaPlayer mMediaPlayer;

    private TextView mTvMusicTitle;
    private Button mBtnLeft;
    private Button mBtnRight;
    private Button mBtnPlay;

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mAllDataBeans.clear();
                    mAllDataBeans.addAll(0, (ArrayList<? extends EnglishBean.ShowapiResBodyBean.DataBean>) msg.obj);
                    mReadRvAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    public MyHandler mMyHandler = new MyHandler();

    public static ReadFragment newInstance(String param1, String param2) {
        ReadFragment fragment = new ReadFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReadRvAdapter = new ReadRvAdapter(mAllDataBeans);

        //如果没有权限，则动态申请授权
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        initAudioList();
        mMediaPlayer = new MediaPlayer();

    }

    public void refreshData(){
        HttpUtil.sendOkHttpRequestEnglish(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure: " +e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("tag", "onResponse: "+json );
                EnglishBean englishBean = new Gson().fromJson(json,EnglishBean.class);
                if(englishBean.getShowapi_res_code() == 0){
                    List<EnglishBean.ShowapiResBodyBean.DataBean> mDataBeans = englishBean.getShowapi_res_body().getData();
                    Message message = mMyHandler.obtainMessage();
                    message.what = 0;
                    message.obj = mDataBeans;
                    mMyHandler.sendMessage(message);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment,container,false);
        mFloatingActionButton = view.findViewById(R.id.read_fab_refresh);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
                mRecyclerView.scrollToPosition(0);
            }
        });
        mSwipeRefreshLayout = view.findViewById(R.id.read_srl);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.like);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView = view.findViewById(R.id.read_rv_english);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mReadRvAdapter);
        refreshData();
        mTvMusicTitle = view.findViewById(R.id.music_tv_title);
        mBtnLeft = view.findViewById(R.id.music_btn_left);
        mBtnLeft.setOnClickListener(this);
        mBtnRight = view.findViewById(R.id.music_btn_right);
        mBtnRight.setOnClickListener(this);
        mBtnPlay = view.findViewById(R.id.music_btn_on_off);
        mBtnPlay.setOnClickListener(this);
        mTvMusicTitle.setText(mSongs.get(MusicIndex).getTitle());
        return view;
    }
    /**
     * 获得媒体库
     */
    private void initAudioList() {
        //刷新新媒体库
        MediaScannerConnection.scanFile(getContext(),new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()},
                null,null);
        mSongs = new ArrayList<Song>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musics = getActivity().getContentResolver().query(uri,new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA},null,null,null);
        String fileName, title,singer,album,size,filePath="";
        int duration;
        Song song;
        if (musics!=null) {
            if (musics.moveToFirst()) {
                do {
                    fileName = musics.getString(1);
                    title = musics.getString(2);
                    duration = musics.getInt(3);
                    singer = musics.getString(4);
                    album = musics.getString(5);
                    size = (musics.getString(6) == null) ? "未知" : musics.getInt(6) / 1024 / 1024 + "MB";
                    if (musics.getString(7) != null) filePath = musics.getString(7);
                    song = new Song(fileName, title, duration, singer, album, size, filePath);
                    mSongs.add(song);
                } while (musics.moveToNext());
                musics.close();
            }
        }
        mSongs = randomList(mSongs);
    }

    /**
     * 开始播放歌曲
     * @param song
     */
    private void initMediaPlayer(Song song){
        mTvMusicTitle.setText(song.getTitle());
        try {
            if (mMediaPlayer==null){
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(song.getFilePath());
            mMediaPlayer.prepare();
            // mMediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAudioList();
                } else {
                    Toast.makeText(getContext(), "未获取SD卡访问的权限", Toast.LENGTH_LONG).show();
                }
        }
    }

    public int MusicIndex = 0;
    public static boolean isPlay = false;
    public int position = 0;
    public boolean isFirst = true;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.music_btn_left:
                MusicIndex = MusicIndex - 1;
                if(MusicIndex == -1){
                    MusicIndex = mSongs.size() - 1;
                }
                initMediaPlayer(mSongs.get(MusicIndex));
                mBtnPlay.setBackgroundResource(R.drawable.play);
                mMediaPlayer.start();
                isPlay = true;
                position = 0;
                mBtnPlay.setBackgroundResource(R.drawable.off);
                break;
            case R.id.music_btn_right:
                MusicIndex = MusicIndex + 1;
                if(MusicIndex == mSongs.size()){
                    MusicIndex = 0;
                }
                initMediaPlayer(mSongs.get(MusicIndex));
                mBtnPlay.setBackgroundResource(R.drawable.play);
                mMediaPlayer.start();
                isPlay = true;
                position = 0;
                mBtnPlay.setBackgroundResource(R.drawable.off);
                break;
            case R.id.music_btn_on_off:
                if(isFirst){
                    initMediaPlayer(mSongs.get(MusicIndex));
                    isFirst = false;
                }

                if(isPlay){
                    if (mMediaPlayer != null && mMediaPlayer.isPlaying()){
                        position=mMediaPlayer.getCurrentPosition();
                        mMediaPlayer.pause();
                        mBtnPlay.setBackgroundResource(R.drawable.play);
                        isPlay = false;
                    }
                } else {
                    if (mMediaPlayer != null){
                        mMediaPlayer.start();
                        mMediaPlayer.seekTo(position);
                        mBtnPlay.setBackgroundResource(R.drawable.off);
                        isPlay = true;
                    }
                }
                break;
        }
    }


    /**
     * 把ArrayList的数据打乱
     * @param sourceList
     * @param <V>
     * @return
     */

    public static <V> ArrayList<V> randomList(ArrayList<V> sourceList){
        if (isEmpty(sourceList)) {
            return sourceList;
        }

        ArrayList<V> randomList = new ArrayList<V>( sourceList.size( ) );
        do{
            int randomIndex = Math.abs( new Random( ).nextInt( sourceList.size() ) );
            randomList.add( sourceList.remove( randomIndex ) );
        }while( sourceList.size( ) > 0 );
        return randomList;
    }

    public static <V> boolean isEmpty(ArrayList<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }
}
