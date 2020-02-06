# MyWordBook

我的单词本，

这是基于上次的单词本进行改造
把过去的英语阅读面换成了音乐播放界面
进行英语单词翻译《中英互译》



使用框架如下：
图片请求框架：glid:
json解析框架：gson
网络请求框架：okhttp，retrofit2；
异步框架：rxjava2，eventbus；

音乐播放界面通过retrofit2+rxjava2解析音乐数据

该项使用的数据接口源来自网络。

    implementation 'com.android.support:design:28.0.0'
    implementation 'com.github.bumptech.glide:glide:3.7.0'
    implementation 'jp.wasabeef:glide-transformations:2.0.1'
    implementation 'com.ashokvarma.android:bottom-navigation-bar:2.1.0'
    implementation 'com.android.support:cardview-v7:28.0.0'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'org.greenrobot:eventbus:3.1.1'
    implementation 'com.squareup.okhttp3:okhttp:3.12.0'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.17'

    implementation 'com.squareup.retrofit2:retrofit:2.7.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.7.1'
    implementation 'com.squareup.retrofit2:converter-scalars:2.7.1'
    implementation 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'

### 后面还需要优化，初步实现了大体功能
