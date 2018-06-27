package edu.wit.mobileapp.mealprepplanner;

import android.app.Application;
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

}
