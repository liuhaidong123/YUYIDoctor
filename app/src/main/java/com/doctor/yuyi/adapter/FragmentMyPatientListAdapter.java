package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.PatientList.Root;
import com.doctor.yuyi.bean.PatientList.Rows;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/4/6.
 */

public class FragmentMyPatientListAdapter extends BaseAdapter{
    private Context context;
    private List<Rows> list=new ArrayList<>();
    public  FragmentMyPatientListAdapter(Context context,List<Rows> list){
        this.context=context;
        this.list=list;
    }

    public List<Rows> getList() {
        return list;
    }

    public void setList(List<Rows> list) {
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHodler hodler;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_mypaientlist_item,null);
            hodler=new ViewHodler();
            hodler.frag_my_patientList_image= (ImageView) convertView.findViewById(R.id.frag_my_patientList_image);
            hodler.frag_my_patientList_textVname= (TextView) convertView.findViewById(R.id.frag_my_patientList_textVname);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
//
        Picasso.with(context).load(UrlTools.BASE+list.get(position).getAvatar()).error(R.mipmap.error_small).into(hodler.frag_my_patientList_image);
        hodler.frag_my_patientList_textVname.setText(list.get(position).getTrueName());
        return convertView;
    }
    class ViewHodler{
        ImageView frag_my_patientList_image;
        TextView frag_my_patientList_textVname;
    }
}
