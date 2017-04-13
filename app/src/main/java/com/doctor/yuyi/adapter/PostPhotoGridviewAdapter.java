package com.doctor.yuyi.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.doctor.yuyi.R;
import com.doctor.yuyi.lzh_utils.BitMapUtils;
import com.doctor.yuyi.myview.SelectImageVIew;

import java.util.List;
import java.util.Map;

import it.sephiroth.android.library.picasso.Picasso;

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
            hodler.image= (SelectImageVIew) convertView.findViewById(R.id.itemImage);
            hodler.CheckBox= (ImageView) convertView.findViewById(R.id.img2);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        Log.i("uri--",list.get(position).get("url"));
        String url=list.get(position).get("url");
        Picasso.with(context).load(url).centerCrop().resize(BitMapUtils.getWindowWidth(context)/3,
                BitMapUtils.getWindowWidth(context)/3).error(R.mipmap.logo).into(hodler.image);
//        hodler.image.setImageBitmap(getBitmapFromUri(Uri.parse(list.get(position).get("url"))));
        String type=list.get(position).get("select");
        if ("0".equals(type)){//未选中
            hodler.CheckBox.setSelected(false);
            hodler.image.setState(0);
        }
        else if ("1".equals(type)){
            hodler.CheckBox.setSelected(true);
            hodler.image.setState(1);
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
                        notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context,"最多可以选择6张图",Toast.LENGTH_SHORT).show();
                    }
                }
                else if ("1".equals(list.get(pos).get("select"))){//选中状态
                    list.get(pos).put("select","0");
                    v.setSelected(false);
                    notifyDataSetChanged();
                }
                selectIn.select(getSelectCount());
            }
        });
        return convertView;
    }
    class ViewHodler{
        SelectImageVIew image;
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
