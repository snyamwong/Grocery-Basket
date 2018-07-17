package edu.wit.mobileapp.mealprepplanner;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";

    //List of search results
    private ArrayList<Meal> mSearchList;
    //updates search results
    private SearchListAdapter adapter;
    //views
    private RecyclerView listView;
    private EditText searchField;

    private MainActivity main;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //hide bottom nav bar, make frame fill that space
        main = (MainActivity)(getActivity());
        main.findViewById(R.id.main_nav).setVisibility(View.INVISIBLE);
        FrameLayout fl = getActivity().findViewById(R.id.main_frame);
        fl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        //Generate sample data
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        int n = 2;
        for(int i = 0; i < n; i++){
            ingredients.add(new Ingredient("Produce Ingredient " + i, i, "oz", "Produce"));
            ingredients.add(new Ingredient("Bakery Ingredient " + (i+n), (i+n), "oz", "Bakery"));
            ingredients.add(new Ingredient("Deli Ingredient " + (i+n*2), (i+n*2), "oz", "Deli"));
            ingredients.add(new Ingredient("Meat Ingredient " + (i+n*3), (i+n*3), "oz", "Meat"));
            ingredients.add(new Ingredient("Seafood Ingredient " + (i+n*4), (i+n*4), "oz", "Seafood"));
            ingredients.add(new Ingredient("Grocery Ingredient " + (i+n*5), (i+n*5), "oz", "Grocery"));
            ingredients.add(new Ingredient("Dairy Ingredient " + (i+n*6), (i+n*6), "oz", "Dairy"));
        }


        if(mSearchList == null) {
            mSearchList = new ArrayList<>();
            for(int i = 0; i < 25; i++) {
                mSearchList.add(new Meal(i, R.drawable.food, "Generic Meal #" + Integer.toString(i), 1, ingredients));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //inflate layout
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchField = view.findViewById(R.id.searchInput);
        listView = view.findViewById(R.id.searchListView);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set adapter
        adapter = new SearchListAdapter(getContext(), mSearchList);
        listView.setAdapter(adapter);

        //top bar setup
        Toolbar toolbar = view.findViewById(R.id.searchTopBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener((View v) -> onBackPressed());


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
    public void onDestroy() {
        super.onDestroy();
        //Show bottom nav bar / change frame to only fill space above it
        main.findViewById(R.id.main_nav).setVisibility(View.VISIBLE);
        LayoutParams params= new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, R.id.main_nav);
        main.findViewById(R.id.main_frame).setLayoutParams(params);
    }

    void filter(String text) {
        List<Meal> temp = new ArrayList<>();
        for(Meal meal : mSearchList) {
            if(meal.getName().toLowerCase().contains(text)) {
                temp.add(meal);
            }
        }
        adapter.updateList(temp);
    }

    //pass onto main activity on back press
    public void onBackPressed() {
        Log.v(TAG,"onBackPressed......Called");
        main.onBackPressed();
    }
}
