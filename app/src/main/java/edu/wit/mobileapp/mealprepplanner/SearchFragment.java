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

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment
{
    private static final String TAG = "SearchFragment";

    // recipe list
    private List<Recipe> recipeArrayList;
    private SearchListAdapter recipeListAdapter;
    private RecyclerView recyclerView;

    // user input
    private EditText searchField;

    // database of recipe
    private Database database;

    private MainActivity main;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set up database
        database = new Database(getContext());
        database.open();

        //hide bottom nav bar, make frame fill that space
        main = (MainActivity) (getActivity());
        main.findViewById(R.id.main_nav).setVisibility(View.INVISIBLE);
        FrameLayout fl = getActivity().findViewById(R.id.main_frame);
        fl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        // inflates layout
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchField = view.findViewById(R.id.searchInput);
        recyclerView = view.findViewById(R.id.searchListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // initializes searchField // EditText
        searchField = view.findViewById(R.id.searchInput);

        // sets adapter
        // shows all the results in the database
        this.recipeArrayList = new ArrayList<>();
        recipeListAdapter = new SearchListAdapter(getContext(), recipeArrayList);
        recyclerView.setAdapter(recipeListAdapter);
        searchDatabase("");

        //top bar setup
        Toolbar toolbar = view.findViewById(R.id.searchTopBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener((View v) -> onBackPressed());

        // listens for text changed
        searchField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // After user finishes typing, filters
                String userInput = filter(s.toString());

                // Then searches database and return result
                searchDatabase(userInput);
            }
        });

        return view;
    }

    /**
     * At the moment, not too too useful other than trimming whitespaces.
     * <p>
     * LIKE clause in database already ignores case
     * <p>
     * Maybe have a more sophisticated filtering text.
     *
     * @param text
     * @return
     */
    String filter(String text)
    {
        return text.trim().toLowerCase();
    }

    /**
     * Given userInput, searches database for recipes with userInput
     * <p>
     * Updates SearchListAdapter with results from query
     *
     * @param userInput
     */
    void searchDatabase(String userInput)
    {
        List<Recipe> result = this.database.getRecipes(userInput);

        recipeListAdapter.updateRecipeList(result);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //Show bottom nav bar / change frame to only fill space above it
        main.findViewById(R.id.main_nav).setVisibility(View.VISIBLE);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, R.id.main_nav);
        main.findViewById(R.id.main_frame).setLayoutParams(params);

        // closes the database when not in search activity to prevent corruption
        database.close();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        // opens the database when navigating to search activity
        database.open();
    }

    //pass onto main activity on back press
    public void onBackPressed()
    {
        Log.v(TAG, "onBackPressed......Called");
        main.onBackPressed();
    }
}
