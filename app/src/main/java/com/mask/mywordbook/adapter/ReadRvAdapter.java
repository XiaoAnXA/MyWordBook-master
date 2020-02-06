package com.mask.mywordbook.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mask.mywordbook.R;
import com.mask.mywordbook.bean.EnglishBean;

import java.util.List;

public class ReadRvAdapter extends RecyclerView.Adapter<ReadRvAdapter.MyViewHolder> {

    public List<EnglishBean.ShowapiResBodyBean.DataBean> mEnglishBeans;

    public ReadRvAdapter(List<EnglishBean.ShowapiResBodyBean.DataBean> englishBeans){
        mEnglishBeans = englishBeans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.read_rv_item,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        EnglishBean.ShowapiResBodyBean.DataBean englishBean = mEnglishBeans.get(i);
        myViewHolder.mTvEnglish.setText(englishBean.getEnglish());
        myViewHolder.mTvChinese.setText(englishBean.getChinese());
    }

    @Override
    public int getItemCount() {
        return mEnglishBeans.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mTvEnglish;
        public TextView mTvChinese;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvChinese = itemView.findViewById(R.id.item_chinese);
            mTvEnglish = itemView.findViewById(R.id.item_english);
        }
    }
}
