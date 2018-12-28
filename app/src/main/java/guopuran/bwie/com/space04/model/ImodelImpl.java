package guopuran.bwie.com.space04.model;

import com.google.gson.Gson;

import java.util.Map;

import guopuran.bwie.com.space04.util.MyCallBack;
import guopuran.bwie.com.space04.util.RetrofitUtil;

public class ImodelImpl implements Imodel {
    @Override
    public void requestmodel(String url, Map<String, String> params, final Class clazz, final MyCallBack callBack) {
        RetrofitUtil.getInstance().get(url).result(new RetrofitUtil.ICallBack() {
            @Override
            public void success(String data) {
                Object o = new Gson().fromJson(data, clazz);
                callBack.getdata(o);
            }

            @Override
            public void falied(String e) {

            }
        });
    }
}
