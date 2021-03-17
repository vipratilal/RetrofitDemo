package com.app.retrofitdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.retrofitdemo.R;
import com.app.retrofitdemo.database.DatabaseClient;
import com.app.retrofitdemo.database.DatabaseHandler;
import com.app.retrofitdemo.database.MyDatabase;
import com.app.retrofitdemo.databinding.ActivityMainBinding;
import com.app.retrofitdemo.model.GitHubModel;
import com.app.retrofitdemo.model.Hero;
import com.app.retrofitdemo.model.User;
import com.app.retrofitdemo.network.RetrofitClient;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

       //getHeros();

       getGithubData();
    }

    private void getGithubData() {

        Call<GitHubModel> call = RetrofitClient.getInstance().getMyApi().getGithub();

        call.enqueue(new Callback<GitHubModel>() {
            @Override
            public void onResponse(Call<GitHubModel> call, Response<GitHubModel> response) {

                Log.e("Data",":"+ response.body().getData());
                Log.e("Data",":"+ response.toString());
                Log.e("Data",":"+ response.body().getMessage());
            }

            @Override
            public void onFailure(Call<GitHubModel> call, Throwable t) {

            }
        });

    }

    private void getHeros() {

        Call<List<Hero>> call = RetrofitClient.getInstance().getMyApi().getHeroes();

        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> heroList = response.body();

                //Creating an String array for the ListView
                String[] heroes = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    heroes[i] = heroList.get(i).getName();

                    Log.e("Value",":" + heroList.get(i).getName());
                    Log.e("Image",":" + heroList.get(i).getImageurl());

                    User mUser = new User();
                    mUser.setContactName(heroList.get(i).getName());
                    mUser.setContactNumber(heroList.get(i).getImageurl());
                    mUser.setUserId(String.valueOf(i));

                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .userDao()
                            .insert(mUser);
                    //DatabaseHandler.insertUser(MyDatabase.getAppDatabase(MainActivity.this), mUser);
                }


                List<User> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .userDao()
                        .getAll();

                for (int i = 0; i < taskList.size(); i++) {
                    Log.e("1",""+ taskList.get(i).getUid());
                    Log.e("2",""+ taskList.get(i).getContactName());
                    Log.e("3",""+ taskList.get(i).getContactNumber());
                    Log.e("4",""+ taskList.get(i).getUserId());
                }

                Log.e("Size",":"+taskList.size());



                //displaying the string array into listview
                //listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}