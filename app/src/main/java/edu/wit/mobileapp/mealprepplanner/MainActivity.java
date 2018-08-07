package edu.wit.mobileapp.mealprepplanner;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
{
    private final String LOGTAG = "MYAPP";
    private final int DB_VERSION = 4;

    // global lists
    private ArrayList<Recipe> mRecipeList;
    private HashMap<String, Double> mSelectedIngredients;
    private ArrayList<Recipe> mAllRecipe;

    // class vars for nav bar and frame
    private BottomNavigationView navigationView;

    // fragments
    private MealListFragment mealListFragment;
    private ShoppingListFragment shoppingListFragment;
    private SearchFragment searchFragment;
    private MealInfoFragment mealInfoFragment;

    // temp reference to the Recipe in MealInfoFragment
    private Recipe mealInfoFragmentRecipe;

    // database of recipe
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Set up database
        database = new Database(getApplicationContext(), null, null, DB_VERSION);

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
    protected void onResume()
    {
        //Log.v(LOGTAG, "getMAFrag = " + MealPrepPlannerApplication.peekMainActivityFragmentStack().toString());
        if (MealPrepPlannerApplication.peekMainActivityFragmentStack() == null)
        {
            Log.v(LOGTAG, "Main Activity Fragment - NULL\n");
            setFragment(mealListFragment);
        }
        if (MealPrepPlannerApplication.peekMainActivityFragmentStack() instanceof MealListFragment)
        {
            Log.v(LOGTAG, "Main Activity Fragment - MEAL\n");
            setFragment(mealListFragment);
        }
        else if (MealPrepPlannerApplication.peekMainActivityFragmentStack() instanceof ShoppingListFragment)
        {
            Log.v(LOGTAG, "Main Activity Fragment - SHOPPING LIST\n");
            setFragment(shoppingListFragment);
        }
        Log.v(LOGTAG, "onResume......called");
        super.onResume();
    }

    @Override
    protected void onStart()
    {
        database.open();
        // gets global lists from last time list was destroyed
        mRecipeList = database.getUserRecipes();
        mAllRecipe = database.getRecipes("");
        mSelectedIngredients = database.getUserSelectedIngredients();
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        MealPrepPlannerApplication.pushMainActivityFragmentStack(mealListFragment);
        database.updateUserDB(mRecipeList, mSelectedIngredients);
        database.close();
        super.onStop();
    }

    protected void onDestroy()
    {
        MealPrepPlannerApplication.clearMainActivityFragmentStack();
        super.onDestroy();
    }

    /**
     * Sets fragment null case is for add buttons call of this method
     *
     * @param fragment
     */
    public void setFragment(Fragment fragment)
    {
        MealPrepPlannerApplication.pushMainActivityFragmentStack(fragment);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Overload previous setFragment method, same functionality
     * <p>
     * Recipe is for MealInfoFragment, to store the reference in MainActivity
     * <p>
     * TODO Bundle the Recipe into MealInfoFragment
     *
     * @param fragment
     * @param recipe
     */
    public void setFragment(Fragment fragment, Recipe recipe)
    {
        MealPrepPlannerApplication.pushMainActivityFragmentStack(fragment);
        this.setMealInfoFragmentRecipe(recipe);

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
        MealPrepPlannerApplication.popPrevMainActivityFragmentStack();

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

    public void hideNavigationBar()
    {
        this.findViewById(R.id.main_nav).setVisibility(View.INVISIBLE);
        FrameLayout fl = this.findViewById(R.id.main_frame);
        fl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
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

    public Recipe getMealInfoFragmentRecipe()
    {
        return mealInfoFragmentRecipe;
    }

    public void setMealInfoFragmentRecipe(Recipe mealInfoFragmentRecipe)
    {
        this.mealInfoFragmentRecipe = mealInfoFragmentRecipe;
    }

    public ArrayList<Recipe> getmAllRecipe(){return mAllRecipe;}
}
