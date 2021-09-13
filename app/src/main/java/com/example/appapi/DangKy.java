package com.example.appapi;

import static com.example.appapi.Others.ShowNotifyUser.dismissProgressDialog;
import static com.example.appapi.Others.ShowNotifyUser.showProgressDialog;
import static com.example.appapi.Service.ServiceAPI.BASE_Service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appapi.Models.Message;
import com.example.appapi.Models.User;
import com.example.appapi.Service.ServiceAPI;
import com.example.appapi.Token.DataToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangKy extends AppCompatActivity {
    public Button btnDk,btnDn;
    public EditText edtMs,edtName,edtMail,edtSdt;
    private DataToken dataToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        btnDk = findViewById(R.id.btnDk);
        btnDn = findViewById(R.id.btnDn);
        edtMs = findViewById(R.id.edtMs);
        edtName = findViewById(R.id.edtName);
        edtMail = findViewById(R.id.edtMail);
        edtSdt = findViewById(R.id.edtSdt);


        dataToken = new DataToken(DangKy.this);


        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog(DangKy.this,"Đang thực hiện vui lòng chờ");
                User user = new User(edtMs.getText().toString(),edtName.getText().toString(),edtMail.getText().toString(),edtSdt.getText().toString());
                addUser(user);
            }
        });
        btnDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangKy.this, MainActivity.class));
            }
        });

    }
    private void addUser(User user) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.AddUser(dataToken.getToken(), user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }
    private void handleResponse(Message message) {
        dismissProgressDialog();
        try{
            Toast.makeText(getApplicationContext(), message.getNotification(), Toast.LENGTH_SHORT).show();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleError(Throwable throwable) {
        dismissProgressDialog();
        Toast.makeText(DangKy.this, "Lỗi", Toast.LENGTH_SHORT).show();

    }


}