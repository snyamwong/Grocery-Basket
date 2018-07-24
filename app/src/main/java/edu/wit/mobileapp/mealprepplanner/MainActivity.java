package edu.wit.mobileapp.mealprepplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private final String LOGTAG = "MYAPP";

    // global lists
    private ArrayList<Recipe> mRecipeList;
    private HashMap<String, Double> mSelectedIngredients;

    // class vars for nav bar and frame
    private BottomNavigationView navigationView;

    // fragments
    private MealListFragment mealListFragment;
    private ShoppingListFragment shoppingListFragment;
    private SearchFragment searchFragment;
    private MealInfoFragment mealInfoFragment;

    // preferences for json storage
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor preferenceEditor;

    // database of recipe
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up database
        database = new Database(getApplicationContext());
        database.open();

        // sets preferences
        mPrefs = getPreferences(MODE_PRIVATE);
        preferenceEditor = mPrefs.edit();

        // gets global lists from last time list was destroyed
        retrieveGlobalDataFromStorage();

        //todo: BEFORE YOU RUN MAKE SURE YOU CLEAR YOUR LISTS AS THE STORED DATA HAS CHANGED
        //mRecipeList = new ArrayList<>();
        //mSelectedIngredients = new HashMap<>();

        // init nav bar and frame
        navigationView = findViewById(R.id.main_nav);

        // init all four fragments
        mealListFragment = new MealListFragment();
        shoppingListFragment = new ShoppingListFragment();
        searchFragment = new SearchFragment();
        mealInfoFragment = new MealInfoFragment();

        // event listener on nav bar click (either MealsList, or ShoppingList)
        navigationView.setOnNavigationItemSelectedListener(listener ->
        {
            if (listener.getItemId() == R.id.nav_meals && !mealListFragment.isVisible())
            {
                setFragment(mealListFragment);
                return true;
            }
            else if (listener.getItemId() == R.id.nav_shopping_list && !shoppingListFragment.isVisible())
            {
                setFragment(shoppingListFragment);
                return true;
            }
            return true;
        });
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        Log.v(LOGTAG, "onPaused.....Called");
        //Store meals list and selected map's current state
        storeGlobalData();
    }

    @Override
    protected void onResume()
    {
        //Log.v(LOGTAG, "getMAFrag = " + MealPrepPlannerApplication.getMainActivityFragment().toString());
        if (MealPrepPlannerApplication.getMainActivityFragment() == null)
        {
            Log.v(LOGTAG, "Main Activity Fragment - NULL\n");
            setFragment(mealListFragment);
        }
        if (MealPrepPlannerApplication.getMainActivityFragment() instanceof MealListFragment)
        {
            Log.v(LOGTAG, "Main Activity Fragment - MEAL\n");
            setFragment(mealListFragment);
        }
        else if (MealPrepPlannerApplication.getMainActivityFragment() instanceof ShoppingListFragment)
        {
            Log.v(LOGTAG, "Main Activity Fragment - SHOPPING LIST\n");
            setFragment(shoppingListFragment);
        }
        Log.v(LOGTAG, "onResume......called");
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        MealPrepPlannerApplication.setMainActivityFragment(mealListFragment);
        super.onDestroy();
    }

    /**
     * Sets fragment null case is for add buttons call of this method
     *
     * @param fragment
     */
    public void setFragment(Fragment fragment)
    {
        MealPrepPlannerApplication.setMainActivityFragment(fragment);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setFragment(Fragment fragment, Recipe recipe)
    {

    }

    /**
     * Stores meal list into JSON
     * Stores selected items into JSON
     */
    public void storeGlobalData()
    {
        Gson gson = new Gson();
        ArrayList<Integer> recipeIDs = new ArrayList<>();
        for(Recipe r : mRecipeList){
            recipeIDs.add(r.getRecipeID());
        }

        // transforms the ArrayLists into JSON Data.
        String recipeIDsJSON = gson.toJson(recipeIDs);
        preferenceEditor.putString("recipeIDsJSONData", recipeIDsJSON);

        // selected ==> jason (lol leaving this typo here - Tin)
        String selectedJSON = gson.toJson(mSelectedIngredients);
        preferenceEditor.putString("selectedJSONData", selectedJSON);

        // commits the changes
        preferenceEditor.commit();
        database.close();
        preferenceEditor.apply();
    }

    /**
     * Retrieves ArrayList from JSON
     * Retrieves selected items from JSON
     */
    public void retrieveGlobalDataFromStorage()
    {
        Gson gson = new Gson();

        if (mPrefs.contains("recipeIDsJSONData"))
        {
            String recipeIDsJSON = mPrefs.getString("recipeIDsJSONData", "");
            Type idType = new TypeToken<ArrayList<Integer>>() {}.getType();
            ArrayList<Integer> recipeIds = gson.fromJson(recipeIDsJSON, idType);

            mRecipeList = new ArrayList<>();
            for(Integer id: recipeIds){
                mRecipeList.add(database.getRecipeByID(id));
            }
        }
        else
        {
            mRecipeList = new ArrayList<>();
        }

        if (mPrefs.contains("selectedJSONData"))
        {
            String selectedJSON = mPrefs.getString("selectedJSONData", "");
            Type selectedType = new TypeToken<HashMap<String, Double>>() {}.getType();
            mSelectedIngredients = gson.fromJson(selectedJSON, selectedType);
        }
        else
        {
            mSelectedIngredients = new HashMap<>();
        }
    }


    // back button pressed = trace back
    // on first fragment = close app
    @Override
    public void onBackPressed()
    {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count <= 2)
        {
            Log.v(LOGTAG, "onBackPressed......Called       COUNT = " + count);
            this.finish();
        }
        else
        {
            Log.v(LOGTAG, "onBackPressed......Called       COUNT = " + count);
            getSupportFragmentManager().popBackStack();
        }
    }

    public ArrayList<Recipe> getmRecipeList()
    {
        return mRecipeList;
    }

    public void setmRecipeList(ArrayList<Recipe> mMealsList)
    {
        this.mRecipeList = mMealsList;
    }

    public HashMap<String, Double> getmSelectedIngredients()
    {
        return mSelectedIngredients;
    }

    public void setmSelectedIngredients(HashMap<String, Double> mSelectedIngredients)
    {
        this.mSelectedIngredients = mSelectedIngredients;
    }

    public SearchFragment getSearchFragment()
    {
        return searchFragment;
    }
    
    public MealInfoFragment getMealInfoFragment()
    {
        return mealInfoFragment;
    }
    
    public Database getDatabase()
    {
        return database;
    }
}
