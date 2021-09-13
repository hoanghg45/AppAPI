package com.example.appapi.Service;

import com.example.appapi.Game;
import com.example.appapi.Models.Game1;
import com.example.appapi.Models.Info;
import com.example.appapi.Models.Login;
import com.example.appapi.Models.LoginInput;
import com.example.appapi.Models.Message;
import com.example.appapi.Models.Score;
import com.example.appapi.Models.Token;
import com.example.appapi.Models.User;
import com.example.appapi.Models.UserRank;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAPI {
    String BASE_Service = "https://hoccungminh.dinhnt.com/api/";

    @GET("token")
    Observable<Token> GetToken();
    @POST("register")
    Observable<Message> AddUser(@Header("Authorization") String token, @Body User user);
    @POST("login")
    Observable<Login> LoginUser(@Header("Authorization") String token,@Body LoginInput loginInput);
    @POST("change-info")
    Observable<Message> Change(@Header("Authorization") String token,@Body User user);
    @GET("quiz")
    Observable<Game1> GetGame(@Header("Authorization") String token);
    @GET("ranking")
    Observable<List<UserRank>> GetRank(@Header("Authorization") String token);
    @GET("details")
    Observable<Info>GetInfo(@Header("Authorization") String token, @Query("id") int id);
    @POST("update-score")
    Observable<Message> Update(@Header("Authorization") String token,@Body Score score);


}
