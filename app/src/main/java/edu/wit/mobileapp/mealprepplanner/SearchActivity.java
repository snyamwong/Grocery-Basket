package edu.wit.mobileapp.mealprepplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;

/**
 * (Replace with SearchFragment)
 *
 * The Activity that represents when user searches for a recipe in the database
 */
public class SearchActivity extends AppCompatActivity
{

    private static final String TAG = "SearchActivity";

    private List<Recipe> recipeArrayList;
    private RecipeListAdapter recipeListAdapter;
    private RecyclerView recyclerView;
    private EditText editText;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Set up database
        database = new Database(this);
        database.open();

        /*
         * TODO
         *
         * change meal list adapter
         * or
         * make recipe list adapter
         *
         * change all Meal generic to Recipe generic
         *
         * connect search and db
         */

        // initializing EditText
        editText = findViewById(R.id.searchInput);

        // initializing RecyclerView
        this.recipeArrayList = new ArrayList<>();
        recipeListAdapter = new RecipeListAdapter(this, recipeArrayList);
        recyclerView = findViewById(R.id.searchListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeListAdapter);

        editText.addTextChangedListener(new TextWatcher()
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

    }

    /**
     * At the moment, not too too useful other than trimming whitespaces.
     *
     * LIKE clause in database already ignores case
     *
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
     *
     * Updates RecipeListAdapter with results from query
     * @param userInput
     */
    void searchDatabase(String userInput)
    {
        List<Recipe> result = this.database.getRecipes(userInput);

        recipeListAdapter.updateList(result);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // closes the database when not in search activity to prevent corruption
        database.close();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // opens the database when navigating to search activity
        database.open();
    }


}
