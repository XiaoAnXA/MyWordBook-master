package com.mask.mywordbook.util;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.mask.mywordbook.bean.TransBean;
import com.mask.mywordbook.fragment.TranslationFragment;


public class TransTask extends AsyncTask<String ,Boolean , TransBean> {

    private static final String TAG = "TAG" ;
    public TranslationFragment mTranslationFragment;

    public TransTask(TranslationFragment translationFragment) {
        mTranslationFragment = translationFragment;
    }

    @Override
    protected TransBean doInBackground(String... strings) {
        String result;
        String json ;
        if(mTranslationFragment.isEnglish){
            json = HttpUtil.RequestForHttpToChain(strings[0]);
        } else {
            json = HttpUtil.RequestForHttpToEnglish(strings[0]);
        }
        try {
            Gson gson = new Gson();
            TransBean transBean = gson.fromJson(json, TransBean.class);
            return transBean;
        }catch (Exception e){

        }

        return null;
    }

    @Override
    protected void onPostExecute(TransBean transBean) {
        mTranslationFragment.showResult(transBean);
    }

    /**
     * 组织显示的翻译
     * @param transBean
     * @return
     */
    private String handlerTransBean(TransBean transBean) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(transBean.getQuery()+"\n");
        for(String s : transBean.getTranslation()){
            stringBuilder.append(s+" ");
        }
        String data = stringBuilder.toString();
        return data;
    }
}
