package guopuran.bwie.com.space04.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import guopuran.bwie.com.space04.fragment.FragmentOne;
import guopuran.bwie.com.space04.fragment.FragmentThree;
import guopuran.bwie.com.space04.fragment.FragmentTwo;

public class FragAdapter extends FragmentPagerAdapter {
    private String[] strings=new String[]{
            "商品","详情","评论"
    };

    public FragAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
            default:break;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position];
    }

    @Override
    public int getCount() {
        return strings.length;
    }
}
