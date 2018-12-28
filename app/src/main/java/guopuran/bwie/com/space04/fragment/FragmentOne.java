package guopuran.bwie.com.space04.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import guopuran.bwie.com.space04.MsgBean;
import guopuran.bwie.com.space04.R;
import guopuran.bwie.com.space04.XiangActivity;
import guopuran.bwie.com.space04.XiangBean;
import guopuran.bwie.com.space04.presenter.IpresenterImpl;
import guopuran.bwie.com.space04.view.IView;

public class FragmentOne extends Fragment implements IView {
    private IpresenterImpl mIpresenterImpl;
    private Banner banner;
    private TextView title;
    private TextView price;
    private String url="getProductDetail?pid=%s";
    private List<String> list;
    private Button qing;
    private Button lun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentone,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();


        initView(view);


    }
    

    private void initPresenter() {
        mIpresenterImpl=new IpresenterImpl(this);
    }

    private void initView(View view) {
        //获取资源ID
        banner = view.findViewById(R.id.frag_banner);
        title = view.findViewById(R.id.frag_title);
        price = view.findViewById(R.id.frag_price);
        qing = view.findViewById(R.id.xiang_qing);
        lun = view.findViewById(R.id.xiang_lun);
        initBanner();
        String pid = ((XiangActivity) getActivity()).getpid();
        mIpresenterImpl.request(String.format(url,pid),new HashMap<String, String>(),XiangBean.class);
    }

    private void initBanner() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.isAutoPlay(false);
        banner.setImageLoader(new ImageLoaderInterface<SimpleDraweeView>() {
            @Override
            public void displayImage(Context context, Object path, SimpleDraweeView imageView) {
                    imageView.setImageURI(Uri.parse((String) path));
            }

            @Override
            public SimpleDraweeView createImageView(Context context) {
                SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
                simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_XY);
                return simpleDraweeView;
            }
        });
    }
    /*@Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getpid(MsgBean msgBean){
        if (msgBean.getFlag().equals("indexfrag")){
            String pid = (String) msgBean.getMsg();
            this.pid=pid;
            mIpresenterImpl.request(String.format(url,pid),new HashMap<String, String>(),XiangBean.class);
        }
    }*/
    @Override
    public void getdata(Object object) {
        if (object instanceof XiangBean){
            final XiangBean bean= (XiangBean) object;
            list = new ArrayList<>();
            imageurl(bean.getData().getImages());
            title.setText(bean.getData().getTitle());
            price.setText("￥"+bean.getData().getPrice());
            banner.setImages(list);
            banner.start();
            qing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(new MsgBean(bean.getData().getTitle(),"title"));
                    ((XiangActivity)getActivity()).getdata(1);

                }
            });
            lun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(new MsgBean(String.valueOf(bean.getData().getPrice()),"price"));
                    ((XiangActivity)getActivity()).getdata(2);

                }
            });
        }
    }
    private void imageurl(String images) {
        int i = images.indexOf("|");
        if (i>0){
            String substring = images.substring(0, i);
            list.add(substring);
            imageurl(images.substring(i+1,images.length()));
        }else{
            list.add(images);
        }

    }

}
