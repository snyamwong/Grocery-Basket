package edu.wit.mobileapp.mealprepplanner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
<<<<<<< HEAD
import android.view.MenuItem;
import android.widget.FrameLayout;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
=======

public class MainActivity extends AppCompatActivity
{
    private final String LOGTAG = "MYAPP";
>>>>>>> shopping-cart-activity

    // NOTE - realm.getPath() = /data/data/edu.wit.mobileapp.mealprepplanner/files/default.realm

    private final String LOGTAG = "myApp";

    //class vars for nav bar and frame
    private BottomNavigationView navigationView;

    // Both fragments (MealListFragment,
    private MealListFragment mealListFragment;
    private ShoppingListFragment shoppingListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Setting up Realm database
        Realm realm = Realm.getDefaultInstance();

        // set up view
        setContentView(R.layout.activity_main);

        //init nav bar and frame
        navigationView = findViewById(R.id.main_nav);

        //init both fragments
        mealListFragment = new MealListFragment();
        shoppingListFragment = new ShoppingListFragment();

        // Set init fragment (if-else statement required as changing the portrait orientation changes onCreate / onDestroy)
        // Default fragment is the MealList (if it is null)
        if (MealPrepPlannerApplication.getMainActivityFragment() == null)
        {
            Log.v(LOGTAG, "NULL");
            setFragment(mealListFragment);
        }
        if (MealPrepPlannerApplication.getMainActivityFragment().getId() == R.id.nav_meals)
        {
            Log.v(LOGTAG, "MEAL");
            setFragment(mealListFragment);
        }
        else if (MealPrepPlannerApplication.getMainActivityFragment().getId() == R.id.nav_shopping_list)
        {
            Log.v(LOGTAG, "SHOPPING LIST");
            setFragment(shoppingListFragment);
        }

        // Event listener on nav bar click (either MealsList, or ShoppingList)
        navigationView.setOnNavigationItemSelectedListener(listener ->
        {

            switch (listener.getItemId())
            {
                case R.id.nav_meals:
                    setFragment(mealListFragment);
                    return true;

                case R.id.nav_shopping_list:
                    setFragment(shoppingListFragment);
                    return true;

                default:
                    return false;
            }

        });
    }

    // Sets fragment
    private void setFragment(Fragment fragment)
    {
        MealPrepPlannerApplication.setMainActivityFragment(fragment);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
