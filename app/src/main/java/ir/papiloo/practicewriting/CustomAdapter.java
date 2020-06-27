package ir.papiloo.practicewriting;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

import ir.papiloo.practicewriting.R;

public class CustomAdapter extends BaseAdapter {
    private Context mContext;

    public CustomAdapter() {
    }

    public CustomAdapter(Context mContext, ArrayList<Item> mItem) {
        this.mContext = mContext;
        this.mItem = mItem;
    }

    private ArrayList<Item> mItem;
    @Override
    public int getCount() {
        return mItem.size();
    }

    @Override
    public Object getItem(int i) {
        return mItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vHolder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup, false);
            vHolder = new ViewHolder(view);
            view.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) view.getTag();
        }

        Item currentItem = (Item) getItem(i);

        //vHolder.txtItemName.setText(currentItem.getTxtName());
        //vHolder.txtItemTime.setText(currentItem.getTxtDate());
        vHolder.txtItemMessage.setText(currentItem.getTxtMessage());

        String mUri = "@drawable/" + currentItem.getImgAvatar();
        int imageSource = mContext.getResources().getIdentifier(mUri, null, mContext.getPackageName());
        vHolder.imgItemAvatar.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), imageSource, null));

        return view;
    }
    public class ViewHolder{
        ImageView imgItemAvatar;
        TextView txtItemName;
        TextView txtItemTime;
        TextView txtItemMessage;

        ViewHolder(View mview){
            imgItemAvatar=mview.findViewById(R.id.icon);
            //txtItemName=mview.findViewById(R.id.txt_name);
            txtItemMessage=mview.findViewById(R.id.txt_message);
            //txtItemTime=mview.findViewById(R.id.txt_time);
        }
    }
}

