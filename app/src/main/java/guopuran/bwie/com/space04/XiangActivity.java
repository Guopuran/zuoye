package guopuran.bwie.com.space04;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import guopuran.bwie.com.space04.adapter.FragAdapter;

public class XiangActivity extends AppCompatActivity {
    @BindView(R.id.xiang_tab)
    TabLayout tabLayout;
    @BindView(R.id.xiang_viewpager)
    ViewPager viewPager;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang);
        ButterKnife.bind(this);
        FragAdapter fragAdapter=new FragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Intent intent=getIntent();
        pid = intent.getStringExtra("pid");
    }
    public void getdata(int position){
        viewPager.setCurrentItem(position);
    }
    public String getpid(){
        return pid;
    }
}
