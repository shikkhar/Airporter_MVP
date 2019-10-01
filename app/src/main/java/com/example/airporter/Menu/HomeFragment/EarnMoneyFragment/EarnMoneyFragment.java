package com.example.airporter.Menu.HomeFragment.EarnMoneyFragment;


import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airporter.R;
import com.example.airporter.data.Order;
import com.example.airporter.helper.ApiRequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarnMoneyFragment extends Fragment implements EarnMoneyContract.EarnMoneyView, SubmitOfferDialogFragment.SubmitOfferDialogCallback {
    private RecyclerView recyclerView;
    private List<Order> orderList;
    private EarnMoneyRecyclerViewAdapter mAdapter;
    private EarnMoneyPresenter mPresenter;
    private SubmitOfferDialogFragment submitOfferDialogFragment;
    private Context appContext;
    private SwipeRefreshLayout refreshOrderList;

    public EarnMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appContext = context.getApplicationContext();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderList = new ArrayList<>();
        mPresenter = new EarnMoneyPresenter(this, ApiRequestManager.getInstance());
        mAdapter = new EarnMoneyRecyclerViewAdapter(orderList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_earn_money, container, false);

        recyclerView = view.findViewById(R.id.earnMoneyRecyclerId);
        refreshOrderList = view.findViewById(R.id.refreshOrderListId);
        refreshOrderList.setOnRefreshListener(new RefreshOrderListListener());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.fetchOrderList();
    }

    private  class RefreshOrderListListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            orderList.clear();
            mPresenter.fetchOrderList();
        }
    }
    public void onLoadMoreClick(){
        String lastOrderIdFetched = orderList.get(orderList.size()-1).getOrderId();
        mPresenter.fetchMoreOrders(lastOrderIdFetched);
    }

    public void onSubmitClick(String price, String reward) {
        FragmentManager fm = getChildFragmentManager();
        Fragment fragment = fm.findFragmentByTag("dialog");
        if (fragment != null)
            fm.beginTransaction().remove(fragment).commit();
        submitOfferDialogFragment = new SubmitOfferDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("price", price);
        bundle.putString("reward", reward);
        submitOfferDialogFragment.setArguments(bundle);
        submitOfferDialogFragment.show(fm, "dialog");
    }

    @Override
    public void onOrderListFetched(List<Order> orderList) {
        refreshOrderList.setRefreshing(false);
        this.orderList.addAll(orderList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSubmitDialogButtonClick(int whichButton) {
        submitOfferDialogFragment.dismiss();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
