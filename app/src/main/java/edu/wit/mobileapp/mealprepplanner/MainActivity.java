package edu.wit.mobileapp.mealprepplanner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    //class vars for nav bar and frame
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    //both fragments
    private MealsFragment mealsFragment;
    private ShoppingListFragment shoppingListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init nav bar and frame
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        //init both fragments
        mealsFragment = new MealsFragment();
        shoppingListFragment = new ShoppingListFragment();

        //set init fragment
        setFragment(mealsFragment);
        //event listener, on nav bar click
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.nav_meals:
                        setFragment(mealsFragment);
                        return true;

                    case R.id.nav_shopping_list:
                        setFragment(shoppingListFragment);
                        return true;

                        default:
                            return false;
                }

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}
