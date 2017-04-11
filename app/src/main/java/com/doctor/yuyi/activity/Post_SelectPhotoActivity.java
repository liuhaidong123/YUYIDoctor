package com.doctor.yuyi.activity;

import android.os.Bundle;
import android.widget.GridView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.PostPhotoGridviewAdapter;
import com.doctor.yuyi.lzh_utils.BitMapUtils;
import com.doctor.yuyi.lzh_utils.MyActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/4/11.
 */

public class Post_SelectPhotoActivity extends MyActivity implements PostPhotoGridviewAdapter.SelectIn{
    private GridView post_selectphoto_gridview;
    private List<String> list;
    private PostPhotoGridviewAdapter adapter;
    private List<Map<String,String>>listCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_selectphoto);
        initView();

    }

    private void initView() {
        post_selectphoto_gridview= (GridView) findViewById(R.id.post_selectphoto_gridview);
        list=new ArrayList<>();
        listCursor= BitMapUtils.getCursor(this);

    }

    @Override
    public void select(int count) {
//        if (count!=0){
//            text.setText("完成"+count);
//        }
//        else {
//            text.setText("完成");
//        }
    }
}
