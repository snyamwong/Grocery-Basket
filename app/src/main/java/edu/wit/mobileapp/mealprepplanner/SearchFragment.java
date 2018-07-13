package edu.wit.mobileapp.mealprepplanner;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";

    private ArrayList<Meal> mMealsList;
    private  MealListAdapter adapter;
    private RecyclerView listView;
    private EditText searchField;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MainActivity main = (MainActivity)(getActivity());
        main.findViewById(R.id.main_nav).setVisibility(View.INVISIBLE);

        //Generate sample data
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        if(mMealsList == null) {
            mMealsList = new ArrayList<Meal>();
            for(int i = 0; i < 25; i++) {
                int rand = (int) (Math.random()*100);
                mMealsList.add(new Meal(i, R.drawable.food, "Generic Meal #" + Integer.toString(rand), 1, ingredients));
            }
        }

        adapter = new MealListAdapter(getContext(), mMealsList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchField = (EditText) view.findViewById(R.id.searchInput);
        searchField.requestFocus();
        adapter = new MealListAdapter(getContext(), mMealsList);

        listView = view.findViewById(R.id.searchListView);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        listView.setAdapter(adapter);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.searchTopBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity main = (MainActivity)getActivity();
        main.findViewById(R.id.main_nav).setVisibility(View.VISIBLE);
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

    public void onBackPressed() {
        Log.v(TAG,"onBackPressed......Called");
        MainActivity main = (MainActivity)getActivity();
        main.onBackPressed();
    }
}
