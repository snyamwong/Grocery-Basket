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
    private ArrayList<Recipe> mMealsList;

    // Adapter
    private ShoppingListAdapter adapter;

    private MainActivity main;

    public ShoppingListFragment()
    {
        // Required empty public constructor
    }

    /**
     * Init data
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

        mMealsList = main.getmMealsList();
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
        main.setmMealsList(mMealsList);
        super.onPause();
    }


    //take meals list and call addIngredientToSubList w/ correct parameters
    public void setShoppingList()
    {
        //for each ingredient in each meal
        for (Meal meal : mMealsList)
        {
            for (Ingredient ingredient : meal.getIngredients())
            {
                //copy ingredient to preserve mMealList
                Ingredient copy = new Ingredient(ingredient.getName(), ingredient.getAmount(), ingredient.getMeasurement(), ingredient.getCategory());
                //add to appropriate sub list
                switch (ingredient.getCategory())
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

    //Add ingredient to sublist, add amounts if ingredient is already there
    private void addIngredientToSubList(ArrayList<Ingredient> list, Ingredient ingredient)
    {
        if (list.contains(ingredient))
        {
            Ingredient found = list.get(list.indexOf(ingredient));
            found.setAmount(combineAmounts(found, ingredient));
        }
        else
        {
            list.add(ingredient);
        }
    }

    //combine listed ingredient amount with new ingredient amount
    //TODO: convert measurements to be the same as well, might not need to
    private int combineAmounts(Ingredient i1, Ingredient i2)
    {
        return i1.getAmount() + i2.getAmount();
    }

    //builds shopping list based on sub lists
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
