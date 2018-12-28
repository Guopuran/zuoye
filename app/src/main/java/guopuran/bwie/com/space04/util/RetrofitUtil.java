package guopuran.bwie.com.space04.util;

import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtil {
    private static RetrofitUtil instance;
    private BaseApis baseApis;
    private final String URL="http://www.zhaoapi.cn/product/";
    public static synchronized RetrofitUtil getInstance(){
        if (instance==null){
            instance=new RetrofitUtil();
        }
        return instance;
    }
    public RetrofitUtil(){
        //拦截器
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.readTimeout(10,TimeUnit.SECONDS);
        builder.writeTimeout(10,TimeUnit.SECONDS);
        builder.connectTimeout(10,TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client=builder.build();
        Retrofit retrofit=new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(URL)
                .client(client)
                .build();
        baseApis=retrofit.create(BaseApis.class);
    }

    //get请求
    public RetrofitUtil get(String url){
        baseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
//                //后台执行在那个线程
//                .subscribeOn(Schedulers.io())
//                //最终完成后执行在那个线程
//                .observeOn(AndroidSchedulers.mainThread())
//                //设置我们的rxJava
//                .subscribe();
        return instance;
    }
    public RetrofitUtil post(String url, Map<String,String> params){
        baseApis.post(url,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return instance;

    }
    private Observer observer=new Observer<ResponseBody>() {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (mICallBack!=null){
                mICallBack.falied(e.getMessage());
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try{
                if (mICallBack!=null){
                    mICallBack.success(responseBody.string());
                }
            }catch (Exception e){
                e.printStackTrace();
                if (mICallBack!=null){
                    mICallBack.falied(e.getMessage());
                }
            }
        }
    };
    public  ICallBack mICallBack;
    public void result(ICallBack mICallBack){
        this.mICallBack=mICallBack;
    }
    public interface ICallBack{
        void success(String data);
        void falied( String  e);
    }
}


