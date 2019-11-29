package com.cfysu.service;

import com.cfysu.http.NoLimitServiceApi;
import io.reactivex.annotations.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
//import com.franmontiel.persistentcookiejar.PersistentCookieJar;
//import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
//import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2019/11/9.
 */
@Slf4j
@Service
public class HttpService implements InitializingBean{
    public static final String BASE_URL = "null";
    private NoLimitServiceApi mNoLimitServiceApi;
    /**
     * 初始化Retrifit网络请求
     */
    private void initRetrofit() {

//        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                //统一设置请求头
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36");
                requestBuilder.header("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8");
                // requestBuilder.header("X-Forwarded-For","114.114.114.117")
                requestBuilder.method(original.method(), original.body());
                String host = "";
                //切换服务器地址
                if (StringUtils.isNotEmpty(host)) {
                    host = host.substring(host.indexOf("//") + 2, host.lastIndexOf("/"));
                    if (StringUtils.isNotEmpty(host)) {
                        HttpUrl newUrl = original.url().newBuilder()
                                .host(host)
                                .build();
                        requestBuilder.url(newUrl);
                    }
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
//        builder.cookieJar(cookieJar);
        //设置代理
        //builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("222.66.22.82", 8090)));

        /*HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d(message);
            }
        });*/
//        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
//        builder.addInterceptor(logging);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        mNoLimitServiceApi = retrofit.create(NoLimitServiceApi.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initRetrofit();
    }

    public NoLimitServiceApi getmNoLimitServiceApi() {
        return mNoLimitServiceApi;
    }

    public void setmNoLimitServiceApi(NoLimitServiceApi mNoLimitServiceApi) {
        this.mNoLimitServiceApi = mNoLimitServiceApi;
    }
}
