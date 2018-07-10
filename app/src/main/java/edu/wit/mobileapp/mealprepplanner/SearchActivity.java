package edu.wit.mobileapp.mealprepplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    private ArrayList<Meal> mMealsList;
    private  MealListAdapter adapter;
    private RecyclerView listView;
    private EditText searchField;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.searchTopBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchField = (EditText) findViewById(R.id.searchInput);
        adapter = new MealListAdapter(this, mMealsList);

        //Generate sample data
        ArrayList<Ingredient> ingredients = new ArrayList<>();


        if(mMealsList == null) {
            mMealsList = new ArrayList<Meal>();
            for(int i = 0; i < 25; i++) {
                int rand = (int) (Math.random()*100);
                mMealsList.add(new Meal(i, R.drawable.food, "Generic Meal #" + Integer.toString(rand), 1, ingredients));
            }
        }

        adapter = new MealListAdapter(this, mMealsList);
        listView = findViewById(R.id.searchListView);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    void filter(String text) {
        List<Meal> temp = new ArrayList<>();
        for(Meal meal : mMealsList) {
            if(meal.getName().toLowerCase().contains(text)) {
                temp.add(meal);
            }
        }
        adapter.updateList(temp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
