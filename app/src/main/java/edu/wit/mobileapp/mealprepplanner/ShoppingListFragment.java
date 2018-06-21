package edu.wit.mobileapp.mealprepplanner;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    private ListView mShoppingListView;
    private ArrayList<Object> mShoppingList;
    private ArrayList<Ingredient> mProduceList;
    private ArrayList<Ingredient> mBakeryList;
    private ArrayList<Ingredient> mDeliList;
    private ArrayList<Ingredient> mMeatList;
    private ArrayList<Ingredient> mSeafoodList;
    private ArrayList<Ingredient> mGroceryList;
    private ArrayList<Ingredient> mDairyList;
    private ShoppingListAdapter adapter;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    //init data
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //Log.v("Meals Fragment", "onCreate called");
        super.onCreate(savedInstanceState);

        //if list isn't initialized
        if(mShoppingList == null) {
            mShoppingList = new ArrayList<>();
            mProduceList = new ArrayList<>();
            mBakeryList = new ArrayList<>();
            mDeliList = new ArrayList<>();
            mMeatList = new ArrayList<>();
            mSeafoodList = new ArrayList<>();
            mGroceryList = new ArrayList<>();
            mDairyList = new ArrayList<>();

            for (int i = 0; i <= 11; i++) {
                mProduceList.add(new Ingredient("Ingredient " + Integer.toString(i), Integer.toString(i) + "oz"));
                mBakeryList.add(new Ingredient("Ingredient " + Integer.toString(i), Integer.toString(i) + "oz"));
                mDeliList.add(new Ingredient("Ingredient " + Integer.toString(i), Integer.toString(i) + "oz"));
                mMeatList.add(new Ingredient("Ingredient " + Integer.toString(i), Integer.toString(i) + "oz"));
                mSeafoodList.add(new Ingredient("Ingredient " + Integer.toString(i), Integer.toString(i) + "oz"));
                mGroceryList.add(new Ingredient("Ingredient " + Integer.toString(i), Integer.toString(i) + "oz"));
                mDairyList.add(new Ingredient("Ingredient " + Integer.toString(i), Integer.toString(i) + "oz"));
            }

            adapter = new ShoppingListAdapter(getActivity().getApplicationContext(), mShoppingList); //object to update fragment

            mShoppingList.add("Produce");
            mShoppingList.addAll(mProduceList);
            mShoppingList.add("Bakery");
            mShoppingList.addAll(mBakeryList);
            mShoppingList.add("Deli");
            mShoppingList.addAll(mDeliList);
            mShoppingList.add("Meat");
            mShoppingList.addAll(mMeatList);
            mShoppingList.add("Seafood");
            mShoppingList.addAll(mSeafoodList);
            mShoppingList.add("Grocery");
            mShoppingList.addAll(mGroceryList);
            mShoppingList.add("Dairy");
            mShoppingList.addAll(mDairyList);
        }
    }

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        mShoppingListView = (ListView) view.findViewById(R.id.shoppingListView);
        mShoppingListView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

}
