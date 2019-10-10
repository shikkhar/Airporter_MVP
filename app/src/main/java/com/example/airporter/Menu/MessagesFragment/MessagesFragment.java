package com.example.airporter.Menu.MessagesFragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airporter.Menu.MessagesFragment.ChatMessagesActivity.ChatMessagesActivity;
import com.example.airporter.R;
import com.example.airporter.data.Messages;
import com.example.airporter.helper.ApiRequestManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment implements MessagesMvpContract.MessagesView {

    private RecyclerView recyclerView;
    private MessagesRecyclerViewAdapter mAdapter;
    private ArrayList<Messages> messageList;
    private MessagesPresenter mPresenter;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageList = new ArrayList<>();
        mPresenter = new MessagesPresenter(this, ApiRequestManager.getInstance());
        mAdapter = new MessagesRecyclerViewAdapter(messageList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_messages, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMessagesId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.fetchMessages();
    }

    public void onRecyclerViewItemClicked(int position) {
        Messages message = messageList.get(position);
        Intent intent = new Intent(getActivity(), ChatMessagesActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);

        /*Intent intent = new Intent(getActivity(),ChatActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("orderId",orderMessages.get(position).getOrderId());
        intent.putExtra("shopperId", orderMessages.get(position).getShopperId());
        intent.putExtra("bidderId", orderMessages.get(position).getBidderId());

        if(userId.equals(orderMessages.get(position).getBidderId()))
        {
            intent.putExtra("receiverName", orderMessages.get(position).getShopperName());
            intent.putExtra("receiverId", orderMessages.get(position).getShopperId());
            intent.putExtra("senderName", orderMessages.get(position).getBidderName());
            intent.putExtra("senderId", orderMessages.get(position).getBidderId());
        }
        else if(userId.equals(orderMessages.get(position).getShopperId()))
        {
            intent.putExtra("receiverName", orderMessages.get(position).getBidderName());
            intent.putExtra("receiverId", orderMessages.get(position).getBidderId());
            intent.putExtra("senderName", orderMessages.get(position).getShopperName());
            intent.putExtra("senderId", orderMessages.get(position).getShopperId());
        }*/
    }

    @Override
    public void onMessagesFetched(ArrayList<Messages> messageList) {
        this.messageList.addAll(messageList);
        mAdapter.notifyDataSetChanged();
    }
}
