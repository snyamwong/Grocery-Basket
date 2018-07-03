package edu.wit.mobileapp.mealprepplanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

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

    private ArrayList<Meal> mMealsList;

    //Preferences for json storage
    public SharedPreferences mPrefs;
    public Editor preferenceEditor;

    //Debug log tag
    private final String LOGTAG = "ShoppingListFragment";

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    //init data
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //Log.v("Meals Fragment", "onCreate called");
        super.onCreate(savedInstanceState);

        //set preferences
        mPrefs = getActivity().getPreferences(MODE_PRIVATE);
        preferenceEditor = mPrefs.edit();

        mShoppingList = new ArrayList<>();
        mProduceList = new ArrayList<>();
        mBakeryList = new ArrayList<>();
        mDeliList = new ArrayList<>();
        mMeatList = new ArrayList<>();
        mSeafoodList = new ArrayList<>();
        mGroceryList = new ArrayList<>();
        mDairyList = new ArrayList<>();

    }

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        adapter = new ShoppingListAdapter(getActivity().getApplicationContext(), mShoppingList); //object to update fragment
        mShoppingListView = (ListView) view.findViewById(R.id.shoppingListView);
        mShoppingListView.setAdapter(adapter);

        retrieveGlobalDataFromStorage();
        buildShoppingList();
        return view;
    }

    //save array list
    @Override
    public void onPause() {
        super.onPause();
        storeGlobalData();
        //Log.v(LOGTAG, "onPause.....finished");
    }

    //array list -> json
    public void storeGlobalData(){
        Gson gson = new Gson();
        //Transform the ArrayLists into JSON Data.
        String mealsJSON = gson.toJson(mMealsList);
        String selectedJSON = gson.toJson(adapter.selected);
        preferenceEditor.putString("mealsJSONData", mealsJSON);
        preferenceEditor.putString("selectedJSONData", selectedJSON);
        //Commit the changes.
        preferenceEditor.commit();
    }

    //json -> array list
    public void retrieveGlobalDataFromStorage(){
        Gson gson = new Gson();
        if(mPrefs.contains("mealsJSONData")){
            String mealsJSON = mPrefs.getString("mealsJSONData", "");
            Type mealType = new TypeToken<Collection<Meal>>() {}.getType();
            mMealsList = gson.fromJson(mealsJSON, mealType);
            setShoppingList();
        }

        if(mPrefs.contains("selectedJSONData")){
            String selectedJSON = mPrefs.getString("selectedJSONData", "");
            Type selectedType = new TypeToken<HashMap<String, Integer>>() {}.getType();
            adapter.selected = gson.fromJson(selectedJSON, selectedType);
        }else{
            adapter.selected = new HashMap<>();
        }
    }

    public void setShoppingList() {
        for(Meal meal : mMealsList){
            for(Ingredient ingredient : meal.getIngredients()){
                switch (ingredient.getCategory()){
                    case "Produce":
                        addIngredientToSubList(mProduceList, ingredient);
                        break;
                    case "Bakery":
                        addIngredientToSubList(mBakeryList, ingredient);
                        break;
                    case "Deli":
                        addIngredientToSubList(mDeliList, ingredient);
                        break;
                    case "Meat":
                        addIngredientToSubList(mMeatList, ingredient);
                        break;
                    case "Seafood":
                        addIngredientToSubList(mSeafoodList, ingredient);
                        break;
                    case "Grocery":
                        addIngredientToSubList(mGroceryList, ingredient);
                        break;
                    case "Dairy":
                        addIngredientToSubList(mDairyList, ingredient);
                        break;
                }
            }
        }
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

    //TODO take ingredient list from mealList and build based on that
    private void buildShoppingList(){
        if(!mProduceList.isEmpty()){
            mShoppingList.add("Produce");
            mShoppingList.addAll(mProduceList);
        }
        if(!mBakeryList.isEmpty()){
            mShoppingList.add("Bakery");
            mShoppingList.addAll(mBakeryList);
        }
        if(!mDeliList.isEmpty()){
            mShoppingList.add("Deli");
            mShoppingList.addAll(mDeliList);
        }
        if(!mMeatList.isEmpty()){
            mShoppingList.add("Meat");
            mShoppingList.addAll(mMeatList);
        }
        if(!mSeafoodList.isEmpty()){
            mShoppingList.add("Seafood");
            mShoppingList.addAll(mSeafoodList);
        }
        if(!mGroceryList.isEmpty()){
            mShoppingList.add("Grocery");
            mShoppingList.addAll(mGroceryList);
        }
        if(!mDairyList.isEmpty()){
            mShoppingList.add("Dairy");
            mShoppingList.addAll(mDairyList);}
    }

}
