package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.doctor.yuyi.R;
import com.doctor.yuyi.lzh_utils.BitMapUtils;
import com.sina.weibo.sdk.api.share.Base;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/4/11.
 */

public class PostPhotoGridviewAdapter extends BaseAdapter{
    private List<Map<String,String>> list;
    private Context context;
    private SelectIn selectIn;
    private int pos;
    public PostPhotoGridviewAdapter( List<Map<String,String>> list,Context context, SelectIn selectIn){
        this.context=context;
        this.list=list;
        this.selectIn=selectIn;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHodler hodler;
        if (convertView==null){
            hodler=new ViewHodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.item,null);
            hodler.image= (ImageView) convertView.findViewById(R.id.image);
            hodler.CheckBox= (ImageView) convertView.findViewById(R.id.img2);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }

        Picasso.with(context).load(list.get(position).get("url")).centerCrop().resize(BitMapUtils.getWindowWidth(context)/3,BitMapUtils.getWindowWidth(context)/3).error(R.mipmap.ic_launcher).into(hodler.image);
        String type=list.get(position).get("select");
        if ("0".equals(type)){//未选中
            hodler.CheckBox.setSelected(false);
        }
        else if ("1".equals(type)){
            hodler.CheckBox.setSelected(true);
        }

        hodler.CheckBox.setTag(position);
        hodler.CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                if ("0".equals(list.get(pos).get("select"))){//未选中状态
                    int c=getSelectCount();
                    if (getSelectCount()<6){//做多6张图
                        list.get(pos).put("select","1");
                        v.setSelected(true);
                    }
                    else {
                        Toast.makeText(context,"最多可用选择6张图",Toast.LENGTH_SHORT).show();
                    }
                }
                else if ("1".equals(list.get(pos).get("select"))){//选中状态
                    list.get(pos).put("select","0");
                    v.setSelected(false);
                }
                selectIn.select(getSelectCount());
            }
        });
        return convertView;
    }
    class ViewHodler{
        ImageView image;
        ImageView CheckBox;
    }

    public interface SelectIn{
        void select(int count);
    }

    public int getSelectCount(){
        int count=0;
        for (int i=0;i<list.size();i++){
            if ("1".equals(list.get(i).get("select"))){
                count++;
            }
        }
        return count;
    }
}
