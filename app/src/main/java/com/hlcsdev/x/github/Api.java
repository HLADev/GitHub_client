package com.hlcsdev.x.github;

import com.hlcsdev.x.github.models.commits.Commits;
import com.hlcsdev.x.github.models.repos.Repos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface Api {

    @GET("user/repos")
    Call<List<Repos>> getAuthentication(@Header("Authorization") String auth);

    @GET("repos/{login}/{name}/commits")
    Call<List<Commits>> getCommits(@Path("login") String login, @Path("name") String name);

}
