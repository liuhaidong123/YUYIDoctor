package com.doctor.yuyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.activity.CardMessageActivity;
import com.doctor.yuyi.activity.CommentInformationActivity;
import com.doctor.yuyi.activity.InformationMessageActivity;
import com.doctor.yuyi.activity.My_praise_Activity;
import com.doctor.yuyi.bean.Bean_DeleteMyPraise;
import com.doctor.yuyi.bean.Bean_MyPostData;
import com.doctor.yuyi.bean.Bean_MyPraise;
import com.doctor.yuyi.lzh_utils.DataUtils;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.lzh_utils.toast;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/4/7.
 */

public class My_PraiseAdapter extends BaseAdapter{
    private List<Bean_MyPraise.RowsBean> list;
    private Context context;
    private int current;
    private String resStr;
    private Boolean isDelete=false;//是否正在执行删除操作
    private notifi notifi;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isDelete=false;
                    toast.toast_faild(context);
                    break;
                case 1:
                    isDelete=false;
                    try{
                        Bean_DeleteMyPraise deleteMyPraise=okhttp.gson.fromJson(resStr,Bean_DeleteMyPraise.class);
                        if ("0".equals(deleteMyPraise.getCode())){
                            Toast.makeText(context,"取消成功",Toast.LENGTH_SHORT).show();
                            list.remove(current);
                            notifyDataSetChanged();
                            if (list!=null&&list.size()==0){
                                notifi.notifiDelete(true);
                            }
                           else {
                                notifi.notifiDelete(false);
                            }

                        }
                        else {
                            Toast.makeText(context,"取消失败："+deleteMyPraise.getResult(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        toast.toast_gsonFaild(context);
                    }
                    break;
            }
        }
    };
    public My_PraiseAdapter(List<Bean_MyPraise.RowsBean> list,Context context,notifi notifi){
        this.list=list;
        this.context=context;
        this.notifi=notifi;
    }
    @Override
    public int getCount() {
//        return list==null?0:list.size();
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
    public View getView( int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView==null){
            hodler=new ViewHodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.my_praise_item,null);
            hodler.forumpost_listitem_title_praise= (TextView) convertView.findViewById(R.id.forumpost_listitem_title_praise);

            hodler.forumpost_listitem_content_praise= (TextView) convertView.findViewById(R.id.forumpost_listitem_content_praise);

            hodler.forumpost_listitem_time_praise= (TextView) convertView.findViewById(R.id.forumpost_listitem_time_praise);

            hodler.forumposts_listview_item_msgNum_praise= (TextView) convertView.findViewById(R.id.forumposts_listview_item_msgNum_praise);

            hodler.forumposts_listview_item_postNum_praise= (TextView) convertView.findViewById(R.id.forumposts_listview_item_postNum_praise);

            hodler.forumposts_listview_item_postImage_praise= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_postImage_praise);

            hodler.forumposts_listview_item_msgImage_praise= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_msgImage_praise);

            hodler.forumpost_listitem_layout_msg_praise= (LinearLayout) convertView.findViewById(R.id.forumpost_listitem_layout_msg_praise);

            hodler.forumpost_listitem_layout_post_praise= (LinearLayout) convertView.findViewById(R.id.forumpost_listitem_layout_post_praise);

            hodler.forumposts_listview_item_photoImage_praise= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_photoImage_praise);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.forumpost_listitem_title_praise.setText(list.get(position).getTitle());
        hodler.forumpost_listitem_content_praise.setText(list.get(position).getSummary());
        if (!"".equals(list.get(position).getCreateTimeString())&&!TextUtils.isEmpty(list.get(position).getCreateTimeString()))
        {
//            2017-04-06 16:56:24
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long millionSeconds;
            try{
                millionSeconds = sdf.parse(list.get(position).getCreateTimeString()).getTime();//毫秒
            }
            catch (Exception e){
                e.printStackTrace();
                millionSeconds=0;
            }
            if (millionSeconds!=0){
                String data= DataUtils.getData(millionSeconds);
                if (!"0".equals(data)&&!TextUtils.isEmpty(data)){
                    hodler.forumpost_listitem_time_praise.setText(data);
                }
                else {
                    hodler.forumpost_listitem_time_praise.setText(list.get(position).getCreateTimeString());
                }
            }
            else {
                hodler.forumpost_listitem_time_praise.setText(list.get(position).getCreateTimeString());
            }
        }
        hodler.forumposts_listview_item_msgNum_praise.setText(""+(list.get(position).getCommentNum()==null?0:(list.get(position).getCommentNum())));
        hodler.forumposts_listview_item_postNum_praise.setText(""+(list.get(position).getLikeNum()==null?0:(list.get(position).getLikeNum())));
        if (!"".equals(list.get(position).getPicture())&&!TextUtils.isEmpty(list.get(position).getPicture())){
            String url=list.get(position).getPicture();
            if (url.contains(";")){
                url=url.substring(0,url.indexOf(";"));
            }
            Log.i("url---",Ip.ImgPth+url);
            Picasso.with(context).load(Ip.ImgPth+url).error(R.mipmap.logo).into(hodler.forumposts_listview_item_photoImage_praise);
        }
        else {
            hodler.forumposts_listview_item_photoImage_praise.setVisibility(View.GONE);
        }

        hodler.forumpost_listitem_layout_post_praise.setTag(position);
        hodler.forumpost_listitem_layout_post_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
//                    Toast.makeText(context,"取消点赞",Toast.LENGTH_SHORT).show();
                if (isDelete==false){
                    removePraise(pos);
                }
                else {
                    Toast.makeText(context,"正在取消点赞，请稍后重试",Toast.LENGTH_SHORT).show();
                }
            }
        });
        hodler.forumpost_listitem_layout_msg_praise.setTag(position);
        hodler.forumpost_listitem_layout_msg_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                Long likeType=list.get(pos).getLikeType();
                Intent intent=new Intent();
                if (likeType==1){//咨询评论
                    intent.setClass(context,CommentInformationActivity.class);
                    intent.putExtra("id",list.get(pos).getContentId());
                    Log.i("id---",list.get(pos).getContentId()+"");
                }
                else if (likeType==2){//帖子评论
                    intent.setClass(context,CardMessageActivity.class);
                    intent.putExtra("id",list.get(pos).getContentId());
                    Log.i("id---",list.get(pos).getContentId()+"");
                }
//                Toast.makeText(context,"评论",Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
            hodler.forumposts_listview_item_postImage_praise.setSelected(true);
        return convertView;
    }
    class ViewHodler{
        TextView forumpost_listitem_title_praise,forumpost_listitem_content_praise,forumpost_listitem_time_praise;//标题，内容，时间
        TextView forumposts_listview_item_msgNum_praise,forumposts_listview_item_postNum_praise;//评论代数量，点赞的数量
        ImageView forumposts_listview_item_postImage_praise,forumposts_listview_item_msgImage_praise;//点赞，评论的image
        LinearLayout forumpost_listitem_layout_post_praise,forumpost_listitem_layout_msg_praise;//点赞,评论的layout;
        ImageView forumposts_listview_item_photoImage_praise;
    }

    public void removePraise(int pos){
        isDelete=true;
        current=pos;
        Map<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.testToken);
        mp.put("ids",list.get(pos).getId()+"");
        okhttp.getCall(Ip.URL+Ip.interface_DeleteMyPraise,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("取消我的点赞贴--"+current,resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }

   public interface notifi{
        void notifiDelete(Boolean flag);
    }
}
