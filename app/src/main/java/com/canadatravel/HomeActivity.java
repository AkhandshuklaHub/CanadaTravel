package com.canadatravel;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.canadatravel.Adapter.DataAdapter;
import com.canadatravel.api.ApiServices;
import com.canadatravel.api.CounteryServices;
import com.canadatravel.model.DataModel;
import com.canadatravel.model.Rows;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Rows> logList;
    private  List<Rows> rowsData;
    private DataAdapter mAdapter;
    private String counteryTitleName;
    private TextView textViewTitle,txt_no_internet;
    private  SwipeRefreshLayout swiperefresh;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        inIt();
        UpdateUI();
        // pull to refresh
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               if(rowsData!=null){
                   rowsData.clear();
                   mAdapter.clear();
               }

                UpdateUI();
            }
        });

    }
   // update UI
    private void UpdateUI() {
        if(isNetworkConnected()){

            getSourceFeed();
        }
        else{
            swiperefresh.setRefreshing(false);
            recyclerView.setVisibility(View.GONE);
            txt_no_internet.setVisibility(View.VISIBLE);
        }
    }

    private void inIt() {
        txt_no_internet = (TextView) findViewById(R.id.txt_no_internet);
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        textViewTitle = (TextView) findViewById(R.id.text_country_title);
        textViewTitle.setText(counteryTitleName);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    // get parsed data from api
    private void getSourceFeed() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        ApiServices apiService = CounteryServices.getClient().create(ApiServices.class);
        Call<DataModel> call = apiService.getFeed();

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                counteryTitleName = response.body().getTitle();
                textViewTitle.setText(counteryTitleName);

                rowsData = response.body().getLsrows();
                for(int i =0 ; i<rowsData.size(); i++){
                    if(rowsData.get(i).getTitle() == null){
                        rowsData.remove(i);
                    }

                }
                swiperefresh.setRefreshing(false);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if(mAdapter!=null){
                    mAdapter.addAll(rowsData);

                }
                else {
                    mAdapter = new DataAdapter(rowsData, HomeActivity.this);
                    recyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Log.v("data",""+ t.getMessage());
                swiperefresh.setRefreshing(false);
                recyclerView.setVisibility(View.GONE);
                txt_no_internet.setVisibility(View.VISIBLE);
                txt_no_internet.setText(R.string.txt_problem);
            }
        });

    }
     // check internet connectivity
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
