package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.Bean_RegistertionList;

import java.util.List;

/**
 * Created by wanyu on 2017/3/28.
 */

public class My_Registration_Adapter extends BaseAdapter{
    private Context context;
    private List<Bean_RegistertionList.RowsBean> lis;
    public My_Registration_Adapter(Context context,List<Bean_RegistertionList.RowsBean>lis){
        this.context=context;
        this.lis=lis;
    }
    @Override
    public int getCount() {
        return lis==null?0:lis.size();
    }

    @Override
    public Object getItem(int position) {
        return lis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.my_registration_listview_item,null);
            hodler=new ViewHodler();
            hodler.my_registration_list_KS= (TextView) convertView.findViewById(R.id.my_registration_list_KS);
            hodler.my_registration_list_name= (TextView) convertView.findViewById(R.id.my_registration_list_name);
            hodler.my_registration_list_time= (TextView) convertView.findViewById(R.id.my_registration_list_time);
                convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.my_registration_list_name.setText(lis.get(position).getTrueName());
        hodler.my_registration_list_time.setText(lis.get(position).getCreateTimeString());
        hodler.my_registration_list_KS.setText(lis.get(position).getDepartmentName());
        return convertView;
    }
    class ViewHodler{
TextView my_registration_list_name,my_registration_list_time,my_registration_list_KS;
    }
}
