package com.example.appapi;

import static com.example.appapi.Others.ShowNotifyUser.dismissProgressDialog;
import static com.example.appapi.Others.ShowNotifyUser.showProgressDialog;
import static com.example.appapi.Service.ServiceAPI.BASE_Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appapi.Models.Account;
import com.example.appapi.Models.Info;
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

public class TrangChu extends AppCompatActivity {
    private Button btnTroll, btnRank,btnMath;
    private TextView txtWel;
    private DataToken dataToken;
    Account account = null;

  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        btnMath = findViewById(R.id.btnMath);
        btnRank = findViewById(R.id.btnRank);
        btnTroll = findViewById(R.id.btnTroll);
        txtWel = findViewById(R.id.txtWel);
        dataToken = new DataToken(TrangChu.this);
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        txtWel.setText("Xin chào "+preferences.getString("FullName",""));


        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft_add = fm.beginTransaction();
                ft_add.replace(R.id.fragment,new RankFragment());

                ft_add.commit();
            }
        });
        btnMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft_add = fm.beginTransaction();
                ft_add.replace(R.id.fragment,new MathFragment());

                ft_add.commit();
            }
        });
        btnTroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft_add = fm.beginTransaction();
                ft_add.replace(R.id.fragment,new TrollFragment());

                ft_add.commit();
            }
        });


    }
///Option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnInf:
                SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
                 int id = preferences.getInt("id",0);
                AlertDialog.Builder builder = new AlertDialog.Builder(TrangChu.this);
                LayoutInflater inflater = TrangChu.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.doithongtin,null);
                builder.setView(dialogView);
                builder.setTitle("Thay đổi thông tin");
                AlertDialog b = builder.create();
                Button btnSave,btnCancel;
                EditText edtMs,edtName,edtMail,edtSdt;
                btnSave = dialogView.findViewById(R.id.btnSave);
                btnCancel = dialogView.findViewById(R.id.btnCancel);
                edtMs = dialogView.findViewById(R.id.edtMs);
                edtName = dialogView.findViewById(R.id.edtName);
                edtMail = dialogView.findViewById(R.id.edtMail);
                edtSdt = dialogView.findViewById(R.id.edtSdt);
                b.show();
                showProgressDialog(this,"Đang tải thông tin ");
                getInfo(id);
                edtMs.setText(String.valueOf(id));
                edtName.setText(preferences.getString("FullName",""));
                edtMail.setText(preferences.getString("mail",""));
                edtSdt.setText(preferences.getString("phone",""));
                User user = new User(edtMs.getText().toString(),edtName.getText().toString(),
                        edtMail.getText().toString(),edtSdt.getText().toString());
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showProgressDialog(TrangChu.this,"Đang thực hiện vui lòng chờ");
                        chgUser(user);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        b.cancel();
                    }
                });



            break;
            case R.id.mnExit:
                AlertDialog.Builder bu = new AlertDialog.Builder(TrangChu.this);
                bu.setTitle("Thông báo");
                bu.setMessage("Bạn có muốn thoát chương trình?");
                bu.setPositiveButton("Coá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                bu.setNegativeButton("Khum", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog al = bu.create();
                al.show();
                break;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }


    //doi info

    private void chgUser(User user) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.Change(dataToken.getToken(), user)
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
    private void getInfo(int id) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.GetInfo(dataToken.getToken(), id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(Info info) {
        dismissProgressDialog();

        try{
            if(info.getStatus()==1){
                 account  = info.getAccount();
                 String name = account.getFullName();
                String mail = account.getEmail();
                String sdt = account.getPhone();
                savePrefs(mail,"mail");
                savePrefs(sdt,"phone");



            }else
            {
                Toast.makeText(getApplicationContext(), "Không lấy được thông tin tài khoản", Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void savePrefs(String content,String name){
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString(name,content);
        editor.commit();
    }



    private void handleError(Throwable throwable) {
        dismissProgressDialog();
        Toast.makeText(TrangChu.this, "Lỗi", Toast.LENGTH_SHORT).show();

    }
}