package com.tedu.lagou.app;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tedu.lagou.constant.Constant;

import java.io.File;

import cn.bmob.im.BmobChat;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * 作者：LiWeiQiang on 2016/4/12 00:21
 * 邮箱：lwqldsyzx@126.com
 */
public class MyApp extends Application {
    //声明一些你认为非常重要，且全局可用的一些属性（变量）
    //该属性用来记录用户在使用软件时的位置
    public  static BmobGeoPoint lastPoint;

    //图片加载用类库，需要进行一次初始化才能用
    public  static ImageLoader imageLoader;

    //提供一个全局上下文对象（使用的时候请慎重！并不是所有场合都可以用！能不用就别瞎用！）
    public static MyApp context;

    @Override
    public void onCreate() {
        super.onCreate();
        BmobChat.DEBUG_MODE = true;
        //BmobIM SDK初始化--只需要这一段代码即可完成初始化
        BmobChat.getInstance(this).init(Constant.Bmob_Key);

        //属性的初始化
        context = this;
        initImageLoader();
    }

    /**
     * 初始化ImageLoader
     * 初始化参数网上copy的
     * http://blog.csdn.net/liu1164316159/article/details/38728259
     * 
     */
    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/Cache");
        ImageLoaderConfiguration configuration= new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY -2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(5 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(200) //缓存的文件数量
                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for releaseapp
                .build();//开始构建
        ImageLoader.getInstance().init(configuration);
    }
}
