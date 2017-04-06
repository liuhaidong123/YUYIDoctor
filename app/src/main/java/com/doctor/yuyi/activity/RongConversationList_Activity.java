package com.doctor.yuyi.activity;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.R;

import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

import static android.R.attr.fragment;

public class RongConversationList_Activity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rong_conversation_list_);
        ConversationListFragment fragment = (ConversationListFragment) getSupportFragmentManager().findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")
                .build();
        fragment.setUri(uri);
    }

    public void reBack(View view) {
        finish();
    }
}
