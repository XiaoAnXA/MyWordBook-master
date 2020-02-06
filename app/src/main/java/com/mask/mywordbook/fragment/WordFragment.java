package com.mask.mywordbook.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mask.mywordbook.R;
import com.mask.mywordbook.adapter.WordRvAdapter;
import com.mask.mywordbook.bean.EnglishBean;
import com.mask.mywordbook.db.EnglishDbHelper;
import com.mask.mywordbook.db.EnglishWordDbBean;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class WordFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private WordRvAdapter mWordRvAdapter;
    public EnglishDbHelper mEnglishDbHelper;
    public SQLiteDatabase mSQLiteDatabase;
    private List<EnglishWordDbBean> mEnglishWordDbBeans;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public WordFragment() {

    }

    public static WordFragment newInstance(String param1, String param2) {
        WordFragment fragment = new WordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEnglishDbHelper = new EnglishDbHelper(getContext(),"Word.db",null,1);
        mSQLiteDatabase = mEnglishDbHelper.getWritableDatabase();
        mEnglishWordDbBeans = queryAll();
    }

    public List<EnglishWordDbBean> queryAll(){
        ArrayList<EnglishWordDbBean> englishWordDbBeans = new ArrayList<EnglishWordDbBean>();
        ArrayList<EnglishWordDbBean> allEnglishWordDbBeans = new ArrayList<EnglishWordDbBean>();
        Cursor cursor = mSQLiteDatabase.query(EnglishDbHelper.TABLE,null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                String result = cursor.getString(cursor.getColumnIndex("result"));
                String target = cursor.getString(cursor.getColumnIndex("target"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                EnglishWordDbBean englishWordDbBean = new EnglishWordDbBean();
                englishWordDbBean.setResult(result);
                englishWordDbBean.setTarget(target);
                englishWordDbBean.setId(id);
                englishWordDbBeans.add(englishWordDbBean);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        ListIterator<EnglishWordDbBean> lit = englishWordDbBeans.listIterator();
        while(lit.hasNext()) {
            lit.next();
        }
        while (lit.hasPrevious()) {
            allEnglishWordDbBeans.add(lit.previous());
        }
        return allEnglishWordDbBeans;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_word, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.word_srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView = view.findViewById(R.id.word_rv_word);
        mWordRvAdapter = new WordRvAdapter(mEnglishWordDbBeans,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mWordRvAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        return view;
    }

    public void notifyDataSetChanged(){
        mEnglishWordDbBeans.clear();
        mEnglishWordDbBeans.addAll(0, queryAll());
        mWordRvAdapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();

    }
}
