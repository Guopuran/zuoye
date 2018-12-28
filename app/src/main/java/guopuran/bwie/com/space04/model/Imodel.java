package guopuran.bwie.com.space04.model;

import java.util.Map;

import guopuran.bwie.com.space04.util.MyCallBack;

public interface Imodel {
    void requestmodel(String url, Map<String,String> params, Class clazz, MyCallBack callBack);
}
