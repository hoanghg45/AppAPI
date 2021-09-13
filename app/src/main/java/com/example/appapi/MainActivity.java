package com.example.appapi;

import static com.example.appapi.Others.ShowNotifyUser.dismissProgressDialog;
import static com.example.appapi.Others.ShowNotifyUser.showProgressDialog;
import static com.example.appapi.Service.ServiceAPI.BASE_Service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appapi.Models.Login;
import com.example.appapi.Models.LoginInput;
import com.example.appapi.Models.Message;
import com.example.appapi.Models.Token;
import com.example.appapi.Models.User;
import com.example.appapi.Service.ServiceAPI;
import com.example.appapi.Token.DataToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button btnDk,btnDn;
    private EditText edtMs,edtMail;
    private DataToken dataToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDk = findViewById(R.id.btnDk);
        btnDn = findViewById(R.id.btnDn);
        edtMs = findViewById(R.id.edtMs);
        edtMail = findViewById(R.id.edtMail);


        dataToken = new DataToken(MainActivity.this);

        if(dataToken.getToken().equals("")){
            getTokenAPI();
        }

        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DangKy.class));
            }
        });
        btnDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog(MainActivity.this,"Đang thực hiện vui lòng chờ");
                LoginInput li = new LoginInput(edtMs.getText().toString(),edtMail.getText().toString());
                loginUser(li);

            }
        });
    }
    public void getTokenAPI() {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.GetToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(Token token) {
        dataToken.saveToken(token.getToken());
    }

    private void loginUser(LoginInput li) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.LoginUser(dataToken.getToken(),li)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }
    private void savePrefs(String content,String name){
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString(name,content);
        editor.commit();
    }
    private void savePrefs1(int content,String name){
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putInt(name,content);
        editor.commit();
    }
    private void handleResponse(Login login) {
        dismissProgressDialog();

        try{
            Toast.makeText(getApplicationContext(), login.getNotification(), Toast.LENGTH_SHORT).show();
            if(login.getStatus() == 1){
                Intent intent = new Intent(MainActivity.this,TrangChu.class);
                savePrefs(login.getFullName(),"FullName");
                savePrefs1(Integer.parseInt(login.getMssv()),"id");
                startActivity(intent);


            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void handleError(Throwable throwable) {
        dismissProgressDialog();
        Toast.makeText(MainActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();

    }
}