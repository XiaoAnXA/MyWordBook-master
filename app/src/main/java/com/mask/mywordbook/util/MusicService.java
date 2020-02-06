package com.mask.mywordbook.util;

import com.mask.mywordbook.bean.MusicBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//请求示例：https://api.uomg.com/api/rand.music?sort=热歌榜&format=json
public interface MusicService {
    @GET("rand.music")
    Call<MusicBean> getMusicJson(@Query("sort") String sort,@Query("format") String format);
}
