package edu.wit.mobileapp.mealprepplanner;

import android.app.Application;
<<<<<<< HEAD

import io.realm.Realm;

/*
    Used to set a global access to our Realm database
 */
public class MealPrepPlannerApplication extends Application
{
    @Override
    public void onCreate ()
    {
        super.onCreate();
        Realm.init(this);
    }
=======
import android.support.v4.app.Fragment;

public class MealPrepPlannerApplication extends Application
{
    private static Fragment MainActivityFragment;

    public static Fragment getMainActivityFragment()
    {
        return MainActivityFragment;
    }

    public static void setMainActivityFragment(Fragment mainActivityFragment)
    {
        MainActivityFragment = mainActivityFragment;
    }

>>>>>>> shopping-cart-activity
}
