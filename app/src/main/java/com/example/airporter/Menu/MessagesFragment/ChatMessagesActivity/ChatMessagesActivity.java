package com.example.airporter.Menu.MessagesFragment.ChatMessagesActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.airporter.AppController;
import com.example.airporter.R;
import com.example.airporter.data.ChatMessage;
import com.example.airporter.data.Messages;
import com.example.airporter.helper.ApiRequestManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class ChatMessagesActivity extends AppCompatActivity implements ChatMessagesMvpContract.ChatMessagesView {
    private Messages message;
    private String orderId;
    private String bidderId;
    private String shopperId;
    private String receiverName;
    private String userId;
    private String senderName;
    private String receiverId;
    private String messageToSend;
    private String fcmToken;
    private RecyclerView recyclerView;
    private ImageButton sendMessageButton;
    private EditText messageToSendEditText;
    private ChatMessagesRecyclerViewAdapter mAdapter;
    private ArrayList<ChatMessage> chatMessageList;
    private ChatMessagesPresenter mPresenter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChatMessage chatMessage) {
        chatMessageList.add(chatMessage);
        mAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatMessageList.size()-1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_messages);
        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey("message"))
            message = bundle.getParcelable("message");
        parseMessage(message);

        chatMessageList = new ArrayList<>();
        mAdapter = new ChatMessagesRecyclerViewAdapter(chatMessageList);
        mPresenter = new ChatMessagesPresenter(this, ApiRequestManager.getInstance());
        mPresenter.fetchFCMToken(receiverId);

        sendMessageButton = findViewById(R.id.imageButtonSendMessageId);
        messageToSendEditText = findViewById(R.id.editTextChatMessageId);
        recyclerView = findViewById(R.id.recyclerViewChatMessagesId);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);

        sendMessageButton.setOnClickListener(new OnSendMessageClickListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        chatMessageList.clear();
        mPresenter.fetchChatMessages(orderId, bidderId);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onChatMessagesFetched(ArrayList<ChatMessage> chatMessageList) {
        this.chatMessageList.addAll(chatMessageList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChatMessageUploaded(ChatMessage chatMessage) {
        this.chatMessageList.add(chatMessage);
        mAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatMessageList.size()-1);
        messageToSendEditText.setText("");
        mPresenter.sendChatMessage(fcmToken, senderName, chatMessage.getChatMessage(), bidderId, shopperId);
    }

    @Override
    public void onFcmTokenFetched(String token) {
        this.fcmToken = token;
    }

    @Override
    public void onChatMessageSent() {

    }

    private class OnSendMessageClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(chatMessageList.isEmpty() && userId.equals(bidderId))
                Toast.makeText(getApplicationContext(), "Please wait for the shopper to initiate a conversation", Toast.LENGTH_SHORT).show();
            else{
                mPresenter.uploadChatMessage(orderId, messageToSendEditText.getText().toString(), bidderId);
            }
        }
    }

    private void parseMessage(Messages message) {
        userId = AppController.getInstance().getPreferenceManager().getUserId();
        orderId = message.getOrderId();
        bidderId = message.getBidderId();
        shopperId = message.getShopperId();

        if(userId.equals(bidderId)){
            senderName = message.getBidderName();
            receiverName = message.getShopperName();
            receiverId = message.getShopperId();
        } else if(userId.equals(shopperId)){
            senderName = message.getShopperName();
            receiverName = message.getBidderName();
            receiverId = message.getBidderId();
        }
    }
}
