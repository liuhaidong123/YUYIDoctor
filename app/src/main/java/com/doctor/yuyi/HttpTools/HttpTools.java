package com.doctor.yuyi.HttpTools;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.doctor.yuyi.bean.AdBean.Root;
import com.google.gson.Gson;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.net.URL;

/**
 * Created by liuhaidong on 2017/3/7.
 */

public class HttpTools {

    private FinalHttp mFinalHttp;
    private static HttpTools mHttpTools;
    private Gson mGson = new Gson();

    private HttpTools() {
        if (mFinalHttp == null) {
            mFinalHttp = new FinalHttp();
        }
    }

    //获取本类的实例对象，并且初始化FinalHttp类
    public static HttpTools getHttpToolsInstance() {
        if (mHttpTools == null) {
            //当初始化本类的时候，会初始化mFinalHttp
            mHttpTools = new HttpTools();
        }
        return mHttpTools;
    }

    /**
     * 广告数据
     *
     * @param handler
     */
    public void getADMessage(final Handler handler) {
        String url = UrlTools.BASE + UrlTools.URL_AD;
        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "广告数据");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "广告数据获取成功" + s);

                try {
                    Root root = mGson.fromJson(s, Root.class);
                    Message m = new Message();
                    m.obj = root;
                    m.what = 1;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码：", e.toString());
                    handler.sendEmptyMessage(100);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", "广告数据获取失败" + strMsg.toString());
                handler.sendEmptyMessage(100);
            }
        });
    }

    /**
     * 广告详情
     */

    public void getADMessageDetial(final Handler handler, long id,String token) {
        String url = UrlTools.BASE + UrlTools.URL_AD_MESSAGE + "id=" + id+"&token="+token;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "广告详情开始");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "广告详情成功" + s);
                try {
                    com.doctor.yuyi.bean.AdMessageDetial.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.AdMessageDetial.Root.class);
                    Message m = new Message();
                    m.what = 2;
                    m.obj = root;
                    handler.sendMessage(m);
                } catch (Exception e) {
                    Log.e("错误码：", e.toString());
                    handler.sendEmptyMessage(101);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", "广告详情失败" + strMsg.toString());
                handler.sendEmptyMessage(101);
            }
        });


    }


    /**
     * 今日推荐
     */

    public void getTodayRecommend(final Handler handler, int start, int limit) {
        String url = UrlTools.BASE + UrlTools.URL_TODAY_RECOMMEND + "start=" + start + "&limit=" + limit;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "今日推荐");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "今日推荐成功" + s);
                try {
                    com.doctor.yuyi.bean.TodayRecommendBean.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.TodayRecommendBean.Root.class);
                    Message m = new Message();
                    m.what = 3;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(102);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", "今日推荐失败" + strMsg.toString());
                handler.sendEmptyMessage(102);
            }
        });

    }

    /**
     * 最新
     */

    public void getNew(final Handler handler, int start, int limit) {
        String url = UrlTools.BASE + UrlTools.URL_NEW + "start=" + start + "&limit=" + limit;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "最新");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "最新" + s);
                try {
                    com.doctor.yuyi.bean.TodayRecommendBean.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.TodayRecommendBean.Root.class);
                    Message m = new Message();
                    m.what = 3;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(102);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", "最新" + strMsg.toString());
                handler.sendEmptyMessage(102);
            }
        });

    }


    /**
     * 热门
     */

    public void getHot(final Handler handler, int start, int limit) {
        String url = UrlTools.BASE + UrlTools.URL_HOT + "start=" + start + "&limit=" + limit;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "热门");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "热门" + s);
                try {
                    com.doctor.yuyi.bean.TodayRecommendBean.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.TodayRecommendBean.Root.class);
                    Message m = new Message();
                    m.what = 3;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(102);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", "热门" + strMsg.toString());
                handler.sendEmptyMessage(102);
            }
        });

    }


    /**
     * 获取评论列表
     */
    public void getCommendList(final Handler handler, long id, int start, int limit) {
        String url = UrlTools.BASE + UrlTools.URL_COMMEND_LIST + "id=" + id + "&start=" + start + "&limit=" + limit;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取评论列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取评论列表成功" + s);
                try {
                    com.doctor.yuyi.bean.CommendListBean.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.CommendListBean.Root.class);
                    Message m = new Message();
                    m.what = 4;
                    m.obj = root;
                    handler.sendMessage(m);


                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(103);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", "获取评论列表失败" + strMsg.toString());
                handler.sendEmptyMessage(103);
            }
        });

    }


    /**
     * 提交评论
     */
    public void submitCommentContent(final Handler handler, long telephone, long content_id, String content) {
        String url = UrlTools.BASE + UrlTools.URL_COMMEND + "telephone="+telephone+"&content_id="+content_id+"&Content="+content;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "提交评论");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "提交评论" + s);
                try {
                   com.doctor.yuyi.bean.SubmitComment.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.SubmitComment.Root.class);
                    Message m = new Message();
                    m.what = 5;
                    m.obj = root;
                    handler.sendMessage(m);


                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(104);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", "提交评论失败" + strMsg.toString());
                handler.sendEmptyMessage(104);
            }
        });

    }
    /**
     * 资讯点赞接口
     */
    public void informationPraise(final Handler handler, long id,String token) {
        String url = UrlTools.BASE + UrlTools.URL_PRAISE + "id="+id+"&token="+token;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "资讯点赞开始");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "资讯点赞" + s);
                try {
                    com.doctor.yuyi.bean.InformationPraise.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.InformationPraise.Root.class);
                    Message m = new Message();
                    m.what = 6;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(105);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", "资讯点赞失败" + strMsg.toString());
                handler.sendEmptyMessage(105);
            }
        });
    }

    /**
     * 学术圈热门
     */
    public void circleHot(final Handler handler, int start,int limit,String token) {
        String url = UrlTools.BASE + UrlTools.URL_CIRCLE_HOT +"start="+start+"&limit="+limit+"&token="+token;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", " 学术圈热门开始");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", " 学术圈热门" + s);
                try {
                    com.doctor.yuyi.bean.CircleBean.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.CircleBean.Root.class);
                    Message m = new Message();
                    m.what = 7;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(106);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", " 学术圈热门失败" + strMsg.toString());
                handler.sendEmptyMessage(106);
            }
        });
    }

    /**
     * 学术圈精选
     */
    public void circleSelect(final Handler handler, int start,int limit,String token) {
        String url = UrlTools.BASE + UrlTools.URL_CIRCLE_SELECT +"start="+start+"&limit="+limit+"&token="+token;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", " 学术圈精选开始");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", " 学术圈精选" + s);
                try {
                    com.doctor.yuyi.bean.CircleBean.SelectBean.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.CircleBean.SelectBean.Root.class);
                    Message m = new Message();
                    m.what = 8;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(107);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", " 学术圈精选失败" + strMsg.toString());
                handler.sendEmptyMessage(107);
            }
        });
    }
    /**
     * 学术圈最新
     */
    public void circleNew(final Handler handler, int start,int limit,String token) {
        String url = UrlTools.BASE + UrlTools.URL_CIRCLE_NEW +"start="+start+"&limit="+limit+"&token="+token;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", " 学术圈最新开始");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", " 学术圈最新" + s);
                try {
                    com.doctor.yuyi.bean.CircleBean.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.CircleBean.Root.class);
                    Message m = new Message();
                    m.what = 7;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(106);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", " 学术圈最新失败" + strMsg.toString());
                handler.sendEmptyMessage(106);
            }
        });
    }
    /**
     * 学术圈最新,精选，热门详情
     */
    public void getHotSelectNewMessage(final Handler handler,String token,long start,long limit,long id) {
        String url = UrlTools.BASE + UrlTools.URL_NEW_SELECT_HOT_MESSAGE +"start="+start+"&limit="+limit+"&id="+id+"&token="+token;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "  学术圈最新,精选，热门详情");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "  学术圈最新,精选，热门详情" + s);
                try {
                    com.doctor.yuyi.bean.CircleMessageBean.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.CircleMessageBean.Root.class);
                    Message m = new Message();
                    m.what = 8;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(108);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", "  学术圈最新,精选，热门详情" + strMsg.toString());
                handler.sendEmptyMessage(108);
            }
        });
    }

    /**
     * 学术圈最新,精选，热门点赞接口
     */
    public void circlePraise(final Handler handler, long id,String token) {
        String url = UrlTools.BASE + UrlTools.URL_CIRCLE_PRAISE +"id="+id+"&token="+token;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "  学术圈最新,精选，热门点赞");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "  学术圈最新,精选，热门点赞" + s);
                try {
                    com.doctor.yuyi.bean.InformationPraise.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.InformationPraise.Root.class);
                    Message m = new Message();
                    m.what = 9;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(109);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", "  学术圈最新,精选，热门点赞" + strMsg.toString());
                handler.sendEmptyMessage(109);
            }
        });
    }


    /**
     * 患者数据接口
     */
    public void getPatientData(final Handler handler,String token,long humeuserId) {
        String url = UrlTools.BASE + UrlTools.URL_PATIENT_DATA +"token="+token+"&humeuserId="+humeuserId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "  患者数据接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "  患者数据接口" + s);
                try {
                    com.doctor.yuyi.bean.PatientData.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.PatientData.Root.class);
                    Message m = new Message();
                    m.what = 10;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(110);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", " 患者数据接口" + strMsg.toString());
                handler.sendEmptyMessage(110);
            }
        });
    }

    /**
     * 患者电子病历
     */
    public void getPatientEle(final Handler handler,String token,long humeuserId) {
        String url = UrlTools.BASE + UrlTools.URL_PATIENT_ELE +"token="+token+"&humeuserId="+humeuserId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "  患者电子病历");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "  患者电子病历" + s);
                try {
                    com.doctor.yuyi.bean.PatientEle.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.PatientEle.Root.class);
                    Message m = new Message();
                    m.what = 11;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(111);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", " 患者电子病历" + strMsg.toString());
                handler.sendEmptyMessage(111);
            }
        });
    }

    /**
     * 学术圈详情评论  telephone=18782931355&content_id=1&Content=haha
     */
    public void submitCircleComment(final Handler handler,long telephone,long content_id,String Content) {
        String url = UrlTools.BASE + UrlTools.URL_CIRCLE_COMMEND +"telephone="+telephone+"&content_id="+content_id+"&Content="+Content;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "  学术圈详情评论");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "  学术圈详情评论" + s);
                try {
                    com.doctor.yuyi.bean.SubmitComment.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.SubmitComment.Root.class);
                    Message m = new Message();
                    m.what = 12;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(112);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", " 学术圈详情评论" + strMsg.toString());
                handler.sendEmptyMessage(112);
            }
        });
    }

    /**
     * 学术圈详情评论点赞  id=1&token=CEDA9F4E7D5FEC556E1BB035FA18E54E
     */
    public void circleCommendPrise(final Handler handler,long id,String token) {
        String url = UrlTools.BASE + UrlTools.URL_CIRCLE_COMMEND_PRAISE +"id="+id+"&token="+token;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "  学术圈详情评论点赞");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "  学术圈详情评论点赞" + s);
                try {
                    com.doctor.yuyi.bean.InformationPraise.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.InformationPraise.Root.class);
                    Message m = new Message();
                    m.what = 13;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(113);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", " 学术圈详情评论点赞" + strMsg.toString());
                handler.sendEmptyMessage(113);
            }
        });
    }

    /**
     * 我的患者接口  token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5
     */
    public void myPatient(final Handler handler,String token,int strat,int limit) {
        String url = UrlTools.BASE + UrlTools.URL_MY_PATIENT +"token="+token+"&start="+strat+"&limit="+limit;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "  我的患者接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "  我的患者接口" + s);
                try {
                    com.doctor.yuyi.bean.PatientList.Root root = mGson.fromJson(s, com.doctor.yuyi.bean.PatientList.Root.class);
                    Message m = new Message();
                    m.what = 14;
                    m.obj = root;
                    handler.sendMessage(m);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    handler.sendEmptyMessage(114);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure", " 我的患者接口" + strMsg.toString());
                handler.sendEmptyMessage(114);
            }
        });
    }

}
