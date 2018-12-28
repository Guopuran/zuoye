package guopuran.bwie.com.space04.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import guopuran.bwie.com.space04.MsgBean;
import guopuran.bwie.com.space04.R;
import guopuran.bwie.com.space04.ShopBean;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<ShopBean.DataBean> list;
    private Context context;
    private boolean flag;
    private final int COUNT_LIN=0;
    private final int COUNT_GRID=1;
    public ShopAdapter(Context context, boolean flag) {
        this.context = context;
        this.flag = flag;
        list=new ArrayList<>();
    }

    public void setList(List<ShopBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<ShopBean.DataBean> getList() {
        return list;
    }

    public ShopBean.DataBean getItem(int positon){
        return list.get(positon);
    }

    @Override
    public int getItemViewType(int position) {
       if (flag){
          return COUNT_LIN;
       }else{
          return COUNT_GRID;
       }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                i == COUNT_LIN ? R.layout.item_lin : R.layout.item_grid
                , viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getdata(getItem(i),context,i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView image;
        private TextView title;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.item_image);
            title=itemView.findViewById(R.id.item_title);
            price=itemView.findViewById(R.id.item_price);
        }

        public void getdata(final ShopBean.DataBean item, Context context, int i) {
            String image_url = item.getImages().split("\\|")[0].replace("https", "http");
            image.setImageURI(Uri.parse(image_url));
            title.setText(item.getTitle());
            price.setText("￥"+item.getPrice());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MsgBean(item.getPid(),"index"));
                }
            });
        }
    }
}
