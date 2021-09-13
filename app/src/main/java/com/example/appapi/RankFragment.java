package com.example.appapi;

import static com.example.appapi.Others.ShowNotifyUser.dismissProgressDialog;
import static com.example.appapi.Others.ShowNotifyUser.showProgressDialog;
import static com.example.appapi.Service.ServiceAPI.BASE_Service;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appapi.Adapter.RankAdapter;
import com.example.appapi.Models.Message;
import com.example.appapi.Models.Token;
import com.example.appapi.Models.User;
import com.example.appapi.Models.UserRank;
import com.example.appapi.Service.ServiceAPI;
import com.example.appapi.Token.DataToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RankFragment extends Fragment {
    public RecyclerView rclRank;

    private DataToken dataToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        rclRank = view.findViewById(R.id.rclRank);
        dataToken = new DataToken(getContext());
        showProgressDialog(getContext(), "Đang tải dữ liệu");
        getRank();


        return view;
    }
    private void getRank() {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
            String tokenCode =dataToken.getToken();
        new CompositeDisposable().add(requestInterface.GetRank(tokenCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(List<UserRank> userRankList) {
        try {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rclRank.setLayoutManager(linearLayoutManager);
            RankAdapter rankAdapter = new RankAdapter(userRankList, getContext());
            rclRank.setAdapter(rankAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dismissProgressDialog();

    }


    private void handleError(Throwable throwable) {
        dismissProgressDialog();
        Toast.makeText(getActivity().getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();

    }
}