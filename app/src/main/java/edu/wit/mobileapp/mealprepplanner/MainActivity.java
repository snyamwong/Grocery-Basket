package edu.wit.mobileapp.mealprepplanner;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private final String LOGTAG = "MYAPP";

    //global lists
    private ArrayList<Recipe> mRecipeList;
    private HashMap<String, Integer> mSelectedIngredients;

    //class vars for nav bar and frame
    private BottomNavigationView navigationView;

    // Both fragments (MealListFragment,
    private MealListFragment mealListFragment;
    private ShoppingListFragment shoppingListFragment;
    private SearchFragment searchFragment;

    //Preferences for json storage
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set preferences
        mPrefs = getPreferences(MODE_PRIVATE);
        preferenceEditor = mPrefs.edit();

        //Get global lists from last time list was destroyed
        retrieveGlobalDataFromStorage();


        //init nav bar and frame
        navigationView = findViewById(R.id.main_nav);

        //init both fragments
        mealListFragment = new MealListFragment();
        shoppingListFragment = new ShoppingListFragment();
        searchFragment = new SearchFragment();

        // Set init fragment (if-else statement required as changing the portrait orientation changes onCreate / onDestroy)
        // Default fragment is the MealList (if it is null)
        if (MealPrepPlannerApplication.getMainActivityFragment() == null)
        {
            Log.v(LOGTAG, "Main Activity Fragment - NULL\n");
            setFragment(mealListFragment);
        }
        if (MealPrepPlannerApplication.getMainActivityFragment().getId() == R.id.nav_meals)
        {
            Log.v(LOGTAG, "Main Activity Fragment - MEAL\n");
            setFragment(mealListFragment);
        }
        else if (MealPrepPlannerApplication.getMainActivityFragment().getId() == R.id.nav_shopping_list)
        {
            Log.v(LOGTAG, "Main Activity Fragment - SHOPPING LIST\n");
            setFragment(shoppingListFragment);
        }


        // Event listener on nav bar click (either MealsList, or ShoppingList)
        navigationView.setOnNavigationItemSelectedListener(listener ->
        {
            if(listener.getItemId() == R.id.nav_meals && !mealListFragment.isVisible()){
                setFragment(mealListFragment);
                return true;
            }else if (listener.getItemId() == R.id.nav_shopping_list && !shoppingListFragment.isVisible()){
                setFragment(shoppingListFragment);
                return true;
            }
            return true;
        });
    }

    //Store meals list and selected map's current state
    @Override
    protected void onPause() {
        storeGlobalData();
        super.onPause();
    }

    // Sets fragment null case is for add buttons call of this method
    public void setFragment(Fragment fragment)
    {
        MealPrepPlannerApplication.setMainActivityFragment(fragment);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //meal list -> json
    //Selected Items -> json
    public void storeGlobalData(){
        Gson gson = new Gson();
        //Transform the ArrayLists into JSON Data.
        String recipeJSON = gson.toJson(mRecipeList);
        preferenceEditor.putString("mealsJSONData", recipeJSON);

        //selected ==> jason
        String selectedJSON = gson.toJson(mSelectedIngredients);
        preferenceEditor.putString("selectedJSONData", selectedJSON);

        //Commit the changes.
        preferenceEditor.commit();
    }

    //json -> array list
    //json -> selected items
    public void retrieveGlobalDataFromStorage(){
        Gson gson = new Gson();
        if(mPrefs.contains("mealsJSONData")){
            String mealsJSON = mPrefs.getString("mealsJSONData", "");
            Type mealType = new TypeToken<Collection<Meal>>() {}.getType();
            mRecipeList = gson.fromJson(mealsJSON, mealType);
        }else {
            mRecipeList = new ArrayList<>();
        }

        if(mPrefs.contains("selectedJSONData")){
            String selectedJSON = mPrefs.getString("selectedJSONData", "");
            Type selectedType = new TypeToken<HashMap<String, Integer>>() {}.getType();
            mSelectedIngredients = gson.fromJson(selectedJSON, selectedType);
        }else{
            mSelectedIngredients = new HashMap<>();
        }
    }


    // back button pressed = trace back
    // on first fragment = close app
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            Log.v(LOGTAG,"onBackPressed......Called       COUNT = " + count);
            this.finishAffinity();
        } else {
            Log.v(LOGTAG,"onBackPressed......Called       COUNT = " + count);
            getSupportFragmentManager().popBackStack();
        }
    }

    public ArrayList<Recipe> getmRecipeList() {
        return mRecipeList;
    }

    public void setmRecipeList(ArrayList<Recipe> mMealsList) {
        this.mRecipeList = mMealsList;
    }

    public HashMap<String, Integer> getmSelectedIngredients() {
        return mSelectedIngredients;
    }

    public void setmSelectedIngredients(HashMap<String, Integer> mSelectedIngredients) {
        this.mSelectedIngredients = mSelectedIngredients;
    }

    public SearchFragment getSearchFragment() {
        return searchFragment;
    }
}
