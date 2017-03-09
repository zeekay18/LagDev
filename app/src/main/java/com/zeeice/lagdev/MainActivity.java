package com.zeeice.lagdev;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.SearchResultAdapter;
import Model.UserObject;
import Service.LagDevClient;
import Service.LagDevServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchResultAdapter mAdapter;
    ArrayList<UserObject> searchResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchResults = new ArrayList<>();
        mAdapter = new SearchResultAdapter(this);

        recyclerView = (RecyclerView)findViewById(R.id.searchRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);

            populateList(query);
        }
    }

    private void populateList(String q)
    {
        if(!TextUtils.isEmpty(q))
        {
        String query =  q+"+language:java+location:lagos+in:fullname|username|email";

        LagDevClient client = LagDevServiceGenerator.createService(LagDevClient.class);
        Call<UserObject.SearchResult> call = client.searchUser(query);
        call.enqueue(new Callback<UserObject.SearchResult>() {
            @Override
            public void onResponse(Call<UserObject.SearchResult> call, Response<UserObject.SearchResult> response) {

                if (response.isSuccessful()) {
                    searchResults = response.body().getItems();
                    mAdapter.setResultLists(searchResults);
                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG)
                    .show();
                }
            }

            @Override
            public void onFailure(Call<UserObject.SearchResult> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(),Toast.LENGTH_LONG)
                .show();
            }
        });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search,menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =(SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                populateList(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
