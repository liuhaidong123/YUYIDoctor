package com.doctor.yuyi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.bean_MedicalRecordList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by wanyu on 2017/8/31.
 */

public class ElectronicMessListViewAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<bean_MedicalRecordList.ResultBean> list;
    public ElectronicMessListViewAdapter(Context mContext,List<bean_MedicalRecordList.ResultBean> list) {
        this.mContext = mContext;
        mInflater=LayoutInflater.from(this.mContext);
        this.list=list;
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
            convertView=mInflater.inflate(R.layout.elec_listview_item,null);
            hodler=new ViewHodler();
            hodler.data_year= (TextView) convertView.findViewById(R.id.data_year);
            hodler.data_monthDay= (TextView) convertView.findViewById(R.id.data_monthDay);
            hodler.hospital_keshi= (TextView) convertView.findViewById(R.id.hospital_keshi);
            hodler.hospital_hospitalName= (TextView) convertView.findViewById(R.id.hospital_hospitalName);
            hodler.hospital_msg= (TextView) convertView.findViewById(R.id.hospital_msg);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        if (!"".equals(list.get(position).getCreateTimeString())&&!TextUtils.isEmpty(list.get(position).getCreateTimeString())){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d= null;
            try {
                d = format.parse(list.get(position).getCreateTimeString());
                Calendar c=Calendar.getInstance();
                c.setTime(d);
                hodler.data_year.setText(c.get(Calendar.YEAR)+"");
                hodler.data_monthDay.setText((c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));
            } catch (ParseException e) {
                hodler.data_year.setText("时间");
                hodler.data_monthDay.setText("格式错误");
                e.printStackTrace();
            }
        }else {
            hodler.data_year.setText("时间");
            hodler.data_monthDay.setText("缺失");
            TextView hospital_keshi;//科室名称
        }
        hodler.hospital_keshi.setText(!"".equals(list.get(position).getDepartmentName())&&!TextUtils.isEmpty(list.get(position).getDepartmentName())?list.get(position).getDepartmentName():"科室");
        hodler.hospital_hospitalName.setText(!"".equals(list.get(position).getHospitalName())&&!TextUtils.isEmpty(list.get(position).getHospitalName())?list.get(position).getHospitalName():"医院");
        hodler.hospital_msg.setText(!"".equals(list.get(position).getMedicalrecord())&&!TextUtils.isEmpty(list.get(position).getMedicalrecord())?list.get(position).getMedicalrecord():"信息未获取");
        return convertView;
    }

    class ViewHodler{
        TextView data_year,data_monthDay;//年，月日
        TextView hospital_keshi;//科室
        TextView hospital_hospitalName;//医院名称
        TextView hospital_msg;//病症描述
    }
}
