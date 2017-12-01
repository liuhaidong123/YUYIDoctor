package com.doctor.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doctor.yuyi.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class TestWXPayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_wxpay);
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
       // 将该app注册到微信
        msgApi.registerApp("wxd930ea5d5a258f4f");
        //调起微信支付
        PayReq request = new PayReq();
        request.appId = "wxd930ea5d5a258f4f";
        request.partnerId = "1900000109";
        request.prepayId= "1101000000140415649af9fc314aa427";
        request.packageValue = "Sign=WXPay";
        request.nonceStr= "1101000000140429eb40476f8896f4c9";
        request.timeStamp= "1398746574";
        request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
        msgApi.sendReq(request);
    }
}
