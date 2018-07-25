package edu.wit.mobileapp.mealprepplanner;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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


    // database of recipe
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up database
        database = new Database(getApplicationContext());

        // init nav bar and frame
        navigationView = findViewById(R.id.main_nav);

        // init all three fragments
        mealListFragment = new MealListFragment();
        shoppingListFragment = new ShoppingListFragment();
        searchFragment = new SearchFragment();

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
    protected void onStart() {
        database.open();
        // gets global lists from last time list was destroyed
        mRecipeList = database.getUserRecipes();
        mSelectedIngredients = database.getUserSelectedIngredients();
        super.onStart();
    }

    @Override
    protected void onStop() {
        MealPrepPlannerApplication.setMainActivityFragment(mealListFragment);
        database.updateUserDB(mRecipeList, mSelectedIngredients);
        database.close();
        super.onStop();
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

    public Database getDatabase() {
        return database;
    }
}
