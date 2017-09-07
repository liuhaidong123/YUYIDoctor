package com.doctor.yuyi.MyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wanyu on 2017/9/4.
 */

public class WheelViewData {
   static WheelViewData data;
    private WheelViewData(){

    }
    public static WheelViewData getInstance(){
        if (data==null){
            data=new WheelViewData();
        }
        return data;
    }
    //获取当前月份的剩余日期包括当天
    public List<String> getTimeList(){
        List<String>li=new ArrayList<>();
        SimpleDateFormat format=new SimpleDateFormat();
        Date date=new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        int max=c.getActualMaximum(Calendar.DAY_OF_MONTH);//获取总天数
        int current=date.getDate();//今天的日期
        for (int i=0;i<max-current+1;i++){
            li.add((current+i)+"日");
        }
        return li;
    }

}
