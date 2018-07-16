package edu.wit.mobileapp.mealprepplanner;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    private final String LOGTAG = "MYAPP";

    //class vars for nav bar and frame
    private BottomNavigationView navigationView;

    // Both fragments (MealListFragment,
    private MealListFragment mealListFragment;
    private ShoppingListFragment shoppingListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init nav bar and frame
        navigationView = findViewById(R.id.main_nav);

        //init both fragments
        mealListFragment = new MealListFragment();
        shoppingListFragment = new ShoppingListFragment();

        // XXX START - database sample code

        /*
            this technically should be in SearchActivity - not MainActivity
            this is again, just a sample

            create a Database object
            createDatabase (copies local db to system db)
            openDatabase (opens system db)

            then query as you please!

            also, remember -

            onPause() - db.close
            onResume() - db.open
            
            to prevent memory leak and such
        */

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
