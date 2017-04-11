package com.doctor.yuyi.adapter;

import android.content.Context;
import android.text.TextUtils;
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
import com.doctor.yuyi.bean.Bean_MyPostData;
import com.doctor.yuyi.lzh_utils.DataUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/3/27.
 */

public class My_forumPosts_Adapter extends BaseAdapter{
    private List<Bean_MyPostData.RowsBean> list;
    private Context context;
    public My_forumPosts_Adapter(List<Bean_MyPostData.RowsBean> list,Context context){
        this.list=list;
        this.context=context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView==null){
            hodler=new ViewHodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.forumposts_listview_item,null);
            hodler.forumpost_listitem_title= (TextView) convertView.findViewById(R.id.forumpost_listitem_title);

            hodler.forumpost_listitem_content= (TextView) convertView.findViewById(R.id.forumpost_listitem_content);

            hodler.forumpost_listitem_time= (TextView) convertView.findViewById(R.id.forumpost_listitem_time);

            hodler.forumposts_listview_item_msgNum= (TextView) convertView.findViewById(R.id.forumposts_listview_item_msgNum);

            hodler.forumposts_listview_item_postNum= (TextView) convertView.findViewById(R.id.forumposts_listview_item_postNum);

            hodler.forumposts_listview_item_postImage= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_postImage);

            hodler.forumposts_listview_item_msgImage= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_msgImage);

            hodler.forumpost_listitem_layout_msg= (LinearLayout) convertView.findViewById(R.id.forumpost_listitem_layout_msg);

            hodler.forumpost_listitem_layout_post= (LinearLayout) convertView.findViewById(R.id.forumpost_listitem_layout_post);

            hodler.forumposts_listview_item_photoImage= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_photoImage);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.forumpost_listitem_title.setText(list.get(position).getTitle());
        hodler.forumpost_listitem_content.setText(list.get(position).getContent());
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
                    hodler.forumpost_listitem_time.setText(data);
                }
                else {
                    hodler.forumpost_listitem_time.setText(list.get(position).getCreateTimeString());
                }
            }
            else {
                hodler.forumpost_listitem_time.setText(list.get(position).getCreateTimeString());
            }
        }

        hodler.forumposts_listview_item_msgNum.setText(list.get(position).getCommentNum()+"");
        hodler.forumposts_listview_item_postNum.setText(list.get(position).getLikeNum()+"");
        if (!"".equals(list.get(position).getPicture())&&!TextUtils.isEmpty(list.get(position).getPicture())){
            Picasso.with(context).load(Ip.URL+list.get(position).getPicture()).error(R.mipmap.logo).into(hodler.forumposts_listview_item_photoImage);
        }
        else {
            hodler.forumposts_listview_item_photoImage.setVisibility(View.GONE);
        }
        hodler.forumpost_listitem_layout_post.setTag(hodler);
        hodler.forumpost_listitem_layout_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHodler ho= (ViewHodler) v.getTag();
                if (ho.forumposts_listview_item_postImage.isSelected()){
                    Toast.makeText(context,"取消点赞",Toast.LENGTH_SHORT).show();
                    ho.forumposts_listview_item_postImage.setSelected(false);
                }
                else {
                    Toast.makeText(context,"点赞",Toast.LENGTH_SHORT).show();
                    ho.forumposts_listview_item_postImage.setSelected(true);
                }
            }
        });
        hodler.forumpost_listitem_layout_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"评论",Toast.LENGTH_SHORT).show();
            }
        });
        if (list.get(position).isIsLike()){
            hodler.forumposts_listview_item_postImage.setSelected(true);
        }
        else {
            hodler.forumposts_listview_item_postImage.setSelected(false);
        }

        return convertView;
    }

    class ViewHodler{
        TextView forumpost_listitem_title,forumpost_listitem_content,forumpost_listitem_time;//标题，内容，时间
        TextView forumposts_listview_item_msgNum,forumposts_listview_item_postNum;//评论代数量，点赞的数量
        ImageView forumposts_listview_item_postImage,forumposts_listview_item_msgImage;//点赞，评论的image
        LinearLayout forumpost_listitem_layout_post,forumpost_listitem_layout_msg;//点赞,评论的layout;
        ImageView forumposts_listview_item_photoImage;
    }
}
