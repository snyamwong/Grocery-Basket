package edu.wit.mobileapp.mealprepplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity{

    private ArrayList<Meal> mMealsList;
    private  MealListAdapter adapter;
    private ListView listView;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = (EditText) findViewById(R.id.searchInput);
        adapter = new MealListAdapter(this, mMealsList);

        //Generate sample data
        if(mMealsList == null) {
            mMealsList = new ArrayList<>();

            mMealsList.add(new Meal(1, R.drawable.food, "Chicken", 1));
            mMealsList.add(new Meal(2, R.drawable.food, "Pizza", 1));
            mMealsList.add(new Meal(3, R.drawable.food, "Steak", 1));
            mMealsList.add(new Meal(4, R.drawable.food, "Pasta", 1));
            mMealsList.add(new Meal(5, R.drawable.food, "Tacos", 1));
            mMealsList.add(new Meal(1, R.drawable.food, "Chicken", 1));
            mMealsList.add(new Meal(2, R.drawable.food, "Pizza", 1));
            mMealsList.add(new Meal(3, R.drawable.food, "Steak", 1));
            mMealsList.add(new Meal(4, R.drawable.food, "Pasta", 1));
            mMealsList.add(new Meal(5, R.drawable.food, "Tacos", 1));
        }

        listView = (ListView)findViewById(R.id.searchListView);
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
