package edu.wit.mobileapp.grocerybasket;

import android.app.Application;
import android.support.v4.app.Fragment;

import java.util.LinkedList;

/**
 * This class is mainly used for keeping track of the current fragment
 * It fills the void of how getSupportFragmentManager() has a void return
 */
public class GroceryBasketApplication extends Application
{
    private static LinkedList<Fragment> MainActivityFragmentStack = new LinkedList<>();

    public static Fragment peekMainActivityFragmentStack()
    {
        return MainActivityFragmentStack.peek();
    }

    public static void pushMainActivityFragmentStack(Fragment mainActivityFragment)
    {
        MainActivityFragmentStack.push(mainActivityFragment);
    }

    public static Fragment popPrevMainActivityFragmentStack()
    {
        return MainActivityFragmentStack.pop();
    }

    public static void clearMainActivityFragmentStack()
    {
        MainActivityFragmentStack.clear();
    }
}
