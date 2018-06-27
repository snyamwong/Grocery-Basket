package edu.wit.mobileapp.mealprepplanner;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

            for (int i = 0; i <= 5; i++) {
                addIngredientToSubList(mProduceList, new Ingredient("Ingredient " + Integer.toString(i), i, "OZ"));
                addIngredientToSubList(mBakeryList, new Ingredient("Ingredient " + Integer.toString(i+6), i, "OZ"));
                addIngredientToSubList(mDeliList, new Ingredient("Ingredient " + Integer.toString(i+12), i, "OZ"));
                addIngredientToSubList(mMeatList, new Ingredient("Ingredient " + Integer.toString(i+18), i, "OZ"));
                addIngredientToSubList(mSeafoodList, new Ingredient("Ingredient " + Integer.toString(i+24), i, "OZ"));
                addIngredientToSubList(mGroceryList, new Ingredient("Ingredient " + Integer.toString(i+30), i, "OZ"));
                addIngredientToSubList(mDairyList, new Ingredient("Ingredient " + Integer.toString(i+36), i, "OZ"));

                //Add dup items to test combination
                addIngredientToSubList(mProduceList, new Ingredient("Ingredient " + Integer.toString(i), i, "OZ"));
                addIngredientToSubList(mBakeryList, new Ingredient("Ingredient " + Integer.toString(i+6), i, "OZ"));
                addIngredientToSubList(mDeliList, new Ingredient("Ingredient " + Integer.toString(i+12), i, "OZ"));
                addIngredientToSubList(mMeatList, new Ingredient("Ingredient " + Integer.toString(i+18), i, "OZ"));
                addIngredientToSubList(mSeafoodList, new Ingredient("Ingredient " + Integer.toString(i+24), i, "OZ"));
                addIngredientToSubList(mGroceryList, new Ingredient("Ingredient " + Integer.toString(i+30), i, "OZ"));
                addIngredientToSubList(mDairyList, new Ingredient("Ingredient " + Integer.toString(i+36), i, "OZ"));
            }

            buildShoppingList();
        }
    }

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        adapter = new ShoppingListAdapter(getActivity().getApplicationContext(), mShoppingList); //object to update fragment
        mShoppingListView = (ListView) view.findViewById(R.id.shoppingListView);
        mShoppingListView.setAdapter(adapter);
        return view;
    }

    private void addIngredientToSubList(ArrayList<Ingredient> list, Ingredient ingredient){
        if(list.contains(ingredient)){
            Ingredient found = list.get(list.indexOf(ingredient));
            found.setAmount(combineAmounts(found, ingredient));
        }else {
            list.add(ingredient);
        }
    }

    //TODO: convert measurements to be the same as well
    private int combineAmounts(Ingredient i1, Ingredient i2){
        return  i1.getAmount() + i2.getAmount();
    }

    private void buildShoppingList(){
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
