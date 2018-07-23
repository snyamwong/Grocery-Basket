package edu.wit.mobileapp.mealprepplanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.wit.mobileapp.mealprepplanner.adapter.ShoppingListAdapter;
import edu.wit.mobileapp.mealprepplanner.helper.Recipe;
import edu.wit.mobileapp.mealprepplanner.helper.RecipeIngredient;

/**
 *
 */
public class ShoppingListFragment extends Fragment
{
    //Log tag
    private static final String TAG = "ShoppingListFragment";

    //List view
    private RecyclerView mShoppingListView;

    //Main list
    private ArrayList<Object> mShoppingList;

    //Sub lists
    private ArrayList<RecipeIngredient> mProduceList;
    private ArrayList<RecipeIngredient> mBakeryList;
    private ArrayList<RecipeIngredient> mDeliList;
    private ArrayList<RecipeIngredient> mMeatList;
    private ArrayList<RecipeIngredient> mSeafoodList;
    private ArrayList<RecipeIngredient> mGroceryList;
    private ArrayList<RecipeIngredient> mDairyList;

    //Meal list from meal fragment
    private ArrayList<Recipe> mRecipeList;

    // Adapter
    private ShoppingListAdapter adapter;

    private MainActivity main;

    public ShoppingListFragment()
    {
        // Required empty public constructor
    }

    /**
     * Init data
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        main = (MainActivity) getActivity();
        //Log.v("Meals Fragment", "onCreate called");
        super.onCreate(savedInstanceState);
    }

    /**
     * Renders view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        mShoppingList = new ArrayList<>();
        mProduceList = new ArrayList<>();
        mBakeryList = new ArrayList<>();
        mDeliList = new ArrayList<>();
        mMeatList = new ArrayList<>();
        mSeafoodList = new ArrayList<>();
        mGroceryList = new ArrayList<>();
        mDairyList = new ArrayList<>();

        mRecipeList = main.getmRecipeList();
        setShoppingList();

        adapter = new ShoppingListAdapter(mShoppingList, getContext()); //object to update fragment
        mShoppingListView = view.findViewById(R.id.shoppingListView);
        mShoppingListView.setLayoutManager(new LinearLayoutManager(getContext()));

        mShoppingListView.setAdapter(adapter);

        buildShoppingList();

        //toggle empty text visibility
        TextView emptyTxt = view.findViewById(R.id.emptyShoppingList);
        if (!mShoppingList.isEmpty())
        {
            emptyTxt.setVisibility(View.INVISIBLE);
        }
        else
        {
            emptyTxt.setVisibility(View.VISIBLE);
        }
        //Log.v("Meals Fragment", "onCreateView called");
        return view;
    }

    @Override
    public void onStart()
    {
        BottomNavigationView bot = main.findViewById(R.id.main_nav);
        bot.setVisibility(View.VISIBLE);
        bot.setSelectedItemId(R.id.nav_shopping_list);
        super.onStart();
        Log.v(TAG, "onStart.....finished");
    }

    @Override
    public void onPause()
    {
        main.setmRecipeList(mRecipeList);
        super.onPause();
    }

    /**
     * Takes recipe list and call addIngredientToSubList w/ correct parameters
     */
    public void setShoppingList()
    {
        // For each recipe in recipe list
        for (Recipe recipe : mRecipeList)
        {
            // For each ingredient in recipe
            for (RecipeIngredient recipeIngredient : recipe.getIngredients())
            {
                // Copies ingredient to preserve mMealList
                RecipeIngredient copy = new RecipeIngredient(recipeIngredient.getRecipeID(),
                        recipeIngredient.getRecipeName(),
                        recipeIngredient.getIngredientName(),
                        recipeIngredient.getIngredientCategory(),
                        recipeIngredient.getQuantity(),
                        recipeIngredient.getUnit());

                // Adds to appropriate sub list
                switch (recipeIngredient.getIngredientCategory())
                {
                    case "Produce":
                        addIngredientToSubList(mProduceList, copy);
                        break;
                    case "Bakery":
                        addIngredientToSubList(mBakeryList, copy);
                        break;
                    case "Deli":
                        addIngredientToSubList(mDeliList, copy);
                        break;
                    case "Meat":
                        addIngredientToSubList(mMeatList, copy);
                        break;
                    case "Seafood":
                        addIngredientToSubList(mSeafoodList, copy);
                        break;
                    case "Grocery":
                        addIngredientToSubList(mGroceryList, copy);
                        break;
                    case "Dairy":
                        addIngredientToSubList(mDairyList, copy);
                        break;
                }
            }
        }
    }

    /**
     * Adds ingredient to sublist, add amounts if ingredient is already there
     *
     * @param list
     * @param recipeIngredient
     */
    private void addIngredientToSubList(ArrayList<RecipeIngredient> list, RecipeIngredient recipeIngredient)
    {
        if (list.contains(recipeIngredient))
        {
            RecipeIngredient found = list.get(list.indexOf(recipeIngredient));
            found.setQuantity(combineAmounts(found, recipeIngredient));
        }
        else
        {
            list.add(recipeIngredient);
        }
    }

    /**
     * Combines listed ingredient amount with new ingredient amount
     * <p>
     * TODO: convert measurements to be the same as well, might not need to
     *
     * @param i1
     * @param i2
     * @return
     */
    private double combineAmounts(RecipeIngredient i1, RecipeIngredient i2)
    {
        return i1.getQuantity() + i2.getQuantity();
    }

    /**
     * Builds shopping list based on sub lists
     */
    private void buildShoppingList()
    {
        if (!mProduceList.isEmpty())
        {
            mShoppingList.add("Produce");
            mShoppingList.addAll(mProduceList);
        }
        if (!mBakeryList.isEmpty())
        {
            mShoppingList.add("Bakery");
            mShoppingList.addAll(mBakeryList);
        }
        if (!mDeliList.isEmpty())
        {
            mShoppingList.add("Deli");
            mShoppingList.addAll(mDeliList);
        }
        if (!mMeatList.isEmpty())
        {
            mShoppingList.add("Meat");
            mShoppingList.addAll(mMeatList);
        }
        if (!mSeafoodList.isEmpty())
        {
            mShoppingList.add("Seafood");
            mShoppingList.addAll(mSeafoodList);
        }
        if (!mGroceryList.isEmpty())
        {
            mShoppingList.add("Grocery");
            mShoppingList.addAll(mGroceryList);
        }
        if (!mDairyList.isEmpty())
        {
            mShoppingList.add("Dairy");
            mShoppingList.addAll(mDairyList);
        }
    }

}
