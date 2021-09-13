package com.example.appapi;

import static com.example.appapi.Others.ShowNotifyUser.dismissProgressDialog;
import static com.example.appapi.Others.ShowNotifyUser.showProgressDialog;
import static com.example.appapi.Service.ServiceAPI.BASE_Service;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.appapi.Adapter.MathAdapter;
import com.example.appapi.Adapter.RankAdapter;
import com.example.appapi.Adapter.TrollAdapter;
import com.example.appapi.Models.Game1;
import com.example.appapi.Models.Math1;
import com.example.appapi.Models.Message;
import com.example.appapi.Models.Score;
import com.example.appapi.Models.Trolls;
import com.example.appapi.Service.ServiceAPI;
import com.example.appapi.Token.DataToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MathFragment extends Fragment {
    private RecyclerView rclMath;
    private DataToken dataToken;
    private Button btnSub;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_math, container, false);
        rclMath = view.findViewById(R.id.rclMath);
        rclMath.setHasFixedSize(true);
        btnSub = view.findViewById(R.id.btnSub);
        dataToken = new DataToken(getContext());
        showProgressDialog(getContext(), "Đang tải dữ liệu");
        getMath();



        return view;

    }
    private void getMath() {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        String tokenCode =dataToken.getToken();
        new CompositeDisposable().add(requestInterface.GetGame(tokenCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(Game1 game1) {
        try {
            dismissProgressDialog();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rclMath.setLayoutManager(linearLayoutManager);
            List<Math1> maths = game1.getQuiz().getMaths();
            MathAdapter mathAdapter = new MathAdapter(maths,getContext());
            rclMath.setAdapter(mathAdapter);
            btnSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences preferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                    int finalScore = preferences.getInt("score",0);
                    int ms = preferences.getInt("id",0);
                    Toast.makeText(getActivity().getApplicationContext(), "Bạn được "+finalScore+ "điểm", Toast.LENGTH_SHORT).show();
                    Score score = new Score(String.valueOf(ms),finalScore);
                    udtScore(score);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void udtScore(Score score) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        String tokenCode =dataToken.getToken();
        new CompositeDisposable().add(requestInterface.Update(tokenCode,score)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(Message message) {
        try {
            dismissProgressDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Thông báo:");
            builder.setMessage(message.getNotification());
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleError(Throwable throwable) {
        dismissProgressDialog();
        Toast.makeText(getActivity().getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();

    }
}