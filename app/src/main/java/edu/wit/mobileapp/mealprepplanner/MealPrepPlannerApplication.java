package edu.wit.mobileapp.mealprepplanner;

import android.app.Application;

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
}
