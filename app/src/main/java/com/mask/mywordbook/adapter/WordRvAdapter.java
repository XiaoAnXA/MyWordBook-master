package com.mask.mywordbook.adapter;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mask.mywordbook.R;
import com.mask.mywordbook.db.EnglishDbHelper;
import com.mask.mywordbook.db.EnglishWordDbBean;

import java.util.List;

public class WordRvAdapter extends RecyclerView.Adapter<WordRvAdapter.MyViewHolder> {

    public List<EnglishWordDbBean> mEnglishWordDbBeans;
    public Context mContext;
    public EnglishDbHelper mEnglishDbHelper;
    public SQLiteDatabase mSQLiteDatabase;

    public WordRvAdapter(List<EnglishWordDbBean> englishBeans,Context context){
        mEnglishWordDbBeans = englishBeans;
        mContext = context;
        mEnglishDbHelper = new EnglishDbHelper(context,"Word.db",null,1);
        mSQLiteDatabase = mEnglishDbHelper.getWritableDatabase();
    }

    @NonNull
    @Override
    public WordRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.word_rv_item,viewGroup,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myViewHolder.getAdapterPosition();
                show(String.valueOf(mEnglishWordDbBeans.get(position).getId()));
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = myViewHolder.getAdapterPosition();
                update(mEnglishWordDbBeans.get(position),String.valueOf(mEnglishWordDbBeans.get(position).getId()));
                return true;
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        EnglishWordDbBean englishWordDbBean = mEnglishWordDbBeans.get(i);
        myViewHolder.mTvTarget.setText(englishWordDbBean.getTarget());
        myViewHolder.mTvResult.setText(englishWordDbBean.getResult());
    }

    @Override
    public int getItemCount() {
        return mEnglishWordDbBeans.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mTvTarget;
        public TextView mTvResult;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTarget = itemView.findViewById(R.id.word_target);
            mTvResult = itemView.findViewById(R.id.word_result);

        }
    }
    AlertDialog mAlertDialog;

    public void show(final String id){
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("确认删除")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSQLiteDatabase.delete(EnglishDbHelper.TABLE,"id = ?",new String[]{id});
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();

                    }
                });
        mAlertDialog = builder.show();
    }

    EditText mEtResult;
    EditText mEtTarget;

    public void update(EnglishWordDbBean englishWordDbBean, final String id){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog, null);
        mEtResult = view.findViewById(R.id.updata_et_result);
        mEtTarget = view.findViewById(R.id.updata_et_target);
        mEtResult.setText(englishWordDbBean.getResult());
        mEtTarget.setText(englishWordDbBean.getTarget());
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("确认修改")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        values.put("target",mEtTarget.getText().toString());
                        values.put("result",mEtResult.getText().toString());
                        mSQLiteDatabase.update(EnglishDbHelper.TABLE,values,"id = ?",new String[]{id});
                        values.clear();
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                    }
                })
                .setView(view);
        mAlertDialog = builder.show();
    }
}
