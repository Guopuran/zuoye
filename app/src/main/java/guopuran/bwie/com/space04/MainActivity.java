package guopuran.bwie.com.space04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import guopuran.bwie.com.space04.adapter.ShopAdapter;
import guopuran.bwie.com.space04.presenter.IpresenterImpl;
import guopuran.bwie.com.space04.view.IView;

public class MainActivity extends AppCompatActivity implements IView {
    @BindView(R.id.main_recy) RecyclerView recyclerView;
    @BindView(R.id.main_change) Button button;
    private IpresenterImpl mIpresenterImpl;
    private boolean flag=true;
    private final int COUNT_ITEM=2;
    private ShopAdapter shopAdapter;
    private String url="searchProducts?keywords=笔记本&page=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initPresenter();
        //发送请求
        initUrl();
        initData();
    }

    public void initUrl() {
        mIpresenterImpl.request(url,new HashMap<String, String>(),ShopBean.class);
    }

    //互绑
    private void initPresenter() {
        mIpresenterImpl=new IpresenterImpl(this);
    }


    //点击切换
    @OnClick(R.id.main_change)
    public void dianji(){
        List<ShopBean.DataBean> list = shopAdapter.getList();
        initData();
        shopAdapter.setList(list);
    }

    private void initLin() {
        //布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //方向
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    public void initData(){
        if (flag){
            initLin();
        }else{
            initGrid();
        }
        shopAdapter = new ShopAdapter(this,flag);
        recyclerView.setAdapter(shopAdapter);
        flag=!flag;
    }
    private void initGrid() {
        //布局管理器
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,COUNT_ITEM);
        //方向
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIpresenterImpl.Deatch();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getindex(MsgBean msgBean){
        if (msgBean.getFlag().equals("index")){
            int pid= (int) msgBean.getMsg();
            Intent intent=new Intent(MainActivity.this,XiangActivity.class);
            intent.putExtra("pid",String.valueOf(pid));
            //EventBus.getDefault().postSticky(new MsgBean(String.valueOf(pid),"indexfrag"));
            startActivity(intent);
        }
    }

    @Override
    public void getdata(Object object) {
        if (object instanceof ShopBean){
            ShopBean bean= (ShopBean) object;
            shopAdapter.setList(bean.getData());
        }
    }
}
