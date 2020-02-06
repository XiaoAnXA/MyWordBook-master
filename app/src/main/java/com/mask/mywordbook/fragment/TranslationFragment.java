package com.mask.mywordbook.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mask.mywordbook.R;
import com.mask.mywordbook.app.MyApplication;
import com.mask.mywordbook.bean.TransBean;
import com.mask.mywordbook.db.EnglishDbHelper;
import com.mask.mywordbook.event.PoetryEvent;
import com.mask.mywordbook.util.HttpUtil;
import com.mask.mywordbook.util.TransTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 翻译功能
 */
public class TranslationFragment extends Fragment {

    public Switch mSwChoose;//选择语中
    public boolean isEnglish;
    public EditText mEtTarget;
    public FloatingActionButton mFabGo;
    public FloatingActionButton mFabDownload;
    public TextView mTvTarget;
    public TextView mTvResult;//翻译的结果
    public CollapsingToolbarLayout mCollapsingToolbarLayout;
    public EnglishDbHelper mEnglishDbHelper;
    public SQLiteDatabase mSQLiteDatabase;

    public TranslationFragment() {
        // Required empty public constructor
        EventBus.getDefault().register(this);
    }

    public static TranslationFragment newInstance(String param1, String param2) {
        TranslationFragment fragment = new TranslationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPoetryEvent(PoetryEvent poetryEvent){
        mCollapsingToolbarLayout.setTitle(poetryEvent.getContent());
        Log.e("TAG", "onPoetryEvent: "+poetryEvent.getContent() );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mEnglishDbHelper = new EnglishDbHelper(getContext(),"Word.db",null,1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translation, container, false);
        mCollapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);

        HttpUtil.sendOkHttpRequestPoetry();
        mSwChoose = view.findViewById(R.id.trans_sw_choose);
        mSwChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isEnglish = isChecked;
            }
        });
        mEtTarget = view.findViewById(R.id.trans_et_target);
        mTvTarget = view.findViewById(R.id.trans_word_tv_target);
        mTvResult = view.findViewById(R.id.trans_word_tv_result);
        mFabDownload = view.findViewById(R.id.trans_fab_download);
        mFabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDb();
            }
        });

        mFabGo = view.findViewById(R.id.trans_fab_go);
        mFabGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TransTask(TranslationFragment.this).execute(mEtTarget.getText().toString());
                hideInput();
            }
        });
        return view;
    }

    public  void showResult(TransBean transBean){
        if(transBean == null){
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : transBean.getTranslation()){
            stringBuilder.append(s+"\n");
        }
        stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
        String data = stringBuilder.toString();
        mTvResult.setText(data);
        mTvTarget.setText(transBean.getQuery());
    }

    public void downloadDb(){
        if(TextUtils.isEmpty(mTvTarget.getText()) &&TextUtils.isEmpty(mTvResult.getText()))
        {
            return;
        }
        mSQLiteDatabase = mEnglishDbHelper.getWritableDatabase();
        String s = mTvTarget.getText().toString();
        Cursor cursor = mSQLiteDatabase.query(EnglishDbHelper.TABLE,new String[]{"target"},"target=?",new String[]{s},null,null,null);
        if(cursor.getCount()!=0){
            Toast.makeText(getContext(),"数据已经存在",Toast.LENGTH_SHORT).show();
            return;
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put("target",mTvTarget.getText().toString());
        values.put("result",mTvResult.getText().toString());
        mSQLiteDatabase.insert(EnglishDbHelper.TABLE,null,values);
        values.clear();
        Toast.makeText(getContext(),"数据已保存",Toast.LENGTH_SHORT).show();
    }

    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        View v = getActivity().getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
