package guopuran.bwie.com.space04.presenter;

import java.util.Map;

import guopuran.bwie.com.space04.model.ImodelImpl;
import guopuran.bwie.com.space04.util.MyCallBack;
import guopuran.bwie.com.space04.view.IView;

public class IpresenterImpl implements Ipresenter {
    private ImodelImpl mImodelImpl;
    private IView mIView;

    public IpresenterImpl(IView mIView) {
        this.mIView = mIView;
        //实例化
        mImodelImpl=new ImodelImpl();
    }

    //解绑
    public void Deatch(){
        if (mImodelImpl!=null){
            mImodelImpl=null;
        }
        if (mIView!=null){
            mIView=null;
        }
    }
    @Override
    public void request(String url, Map<String, String> params, Class clazz) {
        mImodelImpl.requestmodel(url, params, clazz, new MyCallBack() {
            @Override
            public void getdata(Object object) {
                mIView.getdata(object);
            }
        });
    }
}
