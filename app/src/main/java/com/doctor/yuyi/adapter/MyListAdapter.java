package com.doctor.yuyi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.Bean_MyRegistrationKS;

import java.util.List;

/**
 * Created by wanyu on 2017/4/11.
 */

public class MyListAdapter extends BaseExpandableListAdapter{
    private List<Bean_MyRegistrationKS.ResultBean>list;
    private Context context;
    public MyListAdapter(Context context,List<Bean_MyRegistrationKS.ResultBean>list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getGroupCount() {
        return list==null?0:list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Bean_MyRegistrationKS.ResultBean.ClinicListBean>li=list.get(groupPosition).getClinicList();
        return li==null?0:li.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<Bean_MyRegistrationKS.ResultBean.ClinicListBean>li=list.get(groupPosition).getClinicList();
        return list.get(groupPosition).getClinicList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHodler hodler;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.my_register_listview_ks_item,null);
            hodler=new ParentHodler();
            hodler.my_registration_ks_text= (TextView) convertView.findViewById(R.id.my_registration_ks_text);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ParentHodler) convertView.getTag();
        }
        hodler.my_registration_ks_text.setText(list.get(groupPosition).getDepartmentName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHodler hodler;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.my_register_pop_child,null);
            hodler=new ChildHodler();
            hodler.my_registration_child_text= (TextView) convertView.findViewById(R.id.my_registration_child_text);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ChildHodler) convertView.getTag();
        }
        hodler.my_registration_child_text.setText(list.get(groupPosition).getClinicList().get(childPosition).getClinicName());
//        convertView.setOnClickListener();
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class ParentHodler{
        TextView my_registration_ks_text;
    }

    class ChildHodler{
        TextView my_registration_child_text;
    }

}
