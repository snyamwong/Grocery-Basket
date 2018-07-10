package edu.wit.mobileapp.mealprepplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity{

    private ArrayList<Meal> mMealsList;
    private  MealListAdapter adapter;
    private RecyclerView listView;
    private EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = (EditText) findViewById(R.id.searchInput);
        adapter = new MealListAdapter(this, mMealsList);

        //Generate sample data
        if(mMealsList == null) {
            mMealsList = new ArrayList<>();
            ArrayList<Ingredient> ingredients = new ArrayList<>();

            int rand = (int) (Math.random()*100);
            Meal toAdd = new Meal(rand, R.drawable.food, "Generic Meal #" + Integer.toString(rand), 1,ingredients);
            mMealsList.add(toAdd);
        }

        listView = (RecyclerView) findViewById(R.id.searchListView);
        adapter = new MealListAdapter(this, mMealsList);

        listView.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
            }
        });

    }
}
