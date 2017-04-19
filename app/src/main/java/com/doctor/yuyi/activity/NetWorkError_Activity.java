package com.doctor.yuyi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.doctor.yuyi.R;

/**
 * Created by wanyu on 2017/4/6.
 */

public class NetWorkError_Activity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networkerror);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if (keyCode == KeyEvent.KEYCODE_BACK )
//        {
//           if (NetWorkState.isNetworkAvailable(this)){
//                finish();
//           }
//            else {
//               MyApplication.removeActivity();
//               finish();
//            }
//        }
//        return false;
//    }
}
