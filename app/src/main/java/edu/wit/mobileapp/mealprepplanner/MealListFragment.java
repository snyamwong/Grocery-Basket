package edu.wit.mobileapp.mealprepplanner;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import static android.content.Context.MODE_PRIVATE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

//object to json packages
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 *
 * MEALS FRAGMENT
 *
 *@author: Jason Fagerberg
 */

public class MealListFragment extends Fragment {

    //Objects in fragment
    private ListView mealListView;
    private MealListAdapter adapter;
    private ArrayList<Meal> mMealsList;

    //Preferences for json storage
    public SharedPreferences mPrefs;
    public Editor preferenceEditor;

    //Debug log tag
    private final String LOGTAG = "MealsListFragment";

    public MealListFragment() {
        // Required empty public constructor
    }

    //init data
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set preferences
        mPrefs = getActivity().getPreferences(MODE_PRIVATE);
        preferenceEditor = mPrefs.edit();

        //Retrieve saved array list then set adapter
        retrieveGlobalDataFromStorage();
        adapter = new MealListAdapter(getActivity().getApplicationContext(), mMealsList); //object to update fragment

        //Log.v(LOGTAG, "onCreate.....finished");
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
        preferenceEditor.putString("mealsJSONData", mealsJSON);
        //Commit the changes.
        preferenceEditor.commit();
    }

    //json -> array list
    public void retrieveGlobalDataFromStorage(){
        if(mPrefs.contains("mealsJSONData")){
            Gson gson = new Gson();
            String mealsJSON = mPrefs.getString("mealsJSONData", "");
            Type mealType = new TypeToken<Collection<Meal>>() {}.getType();
            setMealsList(gson.fromJson(mealsJSON, mealType));
        }else {
            mMealsList = new ArrayList<>();
        }
    }

    public void setMealsList(ArrayList<Meal> mealsList) {
        this.mMealsList = mealsList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        //inflate fragment
        view = inflater.inflate(R.layout.fragment_meals, container, false);

        //create toolbar named 'topBar'
        Toolbar topBar = (Toolbar) view.findViewById(R.id.mealsTopBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(topBar);
        setHasOptionsMenu(true);

        //Infinite List View
        mealListView = (ListView)  view.findViewById(R.id.mealsListView);

        //update list
        mealListView.setAdapter(adapter); //Update display with new list
        mealListView.setSelection(mealListView.getCount() - 1); //Nav to end of list

        //On click List Item
        mealListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                //Debugging message
                Toast.makeText(getActivity().getApplicationContext(), "Clicked Meal ID: " + v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            ingredients.add(new Ingredient("Produce Ingredient " + i, i, "oz", "Produce"));
            ingredients.add(new Ingredient("Bakery Ingredient " + (i+4), (i+4), "oz", "Bakery"));
            ingredients.add(new Ingredient("Deli Ingredient " + (i+8), (i+8), "oz", "Deli"));
            ingredients.add(new Ingredient("Meat Ingredient " + (i+12), (i+12), "oz", "Meat"));
            ingredients.add(new Ingredient("Seafood Ingredient " + (i+16), (i+16), "oz", "Seafood"));
            ingredients.add(new Ingredient("Grocery Ingredient " + (i+20), (i+20), "oz", "Grocery"));
            ingredients.add(new Ingredient("Dairy Ingredient " + (i+24), (i+24), "oz", "Dairy"));
        }

        //TODO: build better generic food
        Meal generic = new Meal(1, R.drawable.food , "Food Name", 1, ingredients);

        //Add button Setup
        //TODO: make button navigate to search activity
        FloatingActionButton btnAdd = (FloatingActionButton) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Placeholder Action to add placeholder meal
                mMealsList.add(generic);
                mealListView.setAdapter(adapter);
                mealListView.setSelection(mealListView.getCount() - 1);
                Toast.makeText(getActivity().getApplicationContext(), "Placeholder Food added", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    //Create menu where delete button sits
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_meals, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //On delete button press
    /*
        FULLY IMPLEMENTED
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mMealsList.size() == 0){return false;} //No items to delete, do nothing
        int res_id = item.getItemId(); //get ID of pressed button (Only one button so this is redundant)
        //Redundant, only button is delete button
        if(res_id == R.id.deleteAll){

            //Are you sure? box class
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes is pressed
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "All Meals Deleted " , Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            //clear meal list
                            mMealsList.clear();
                            mealListView.setAdapter(adapter);
                            mealListView.setSelection(mealListView.getCount() - 1);

                            //Clear selected options
                            Gson gson = new Gson();
                            //Transform the ArrayLists into JSON Data.
                            String selectedJSON = gson.toJson(new HashMap<String,Integer>());
                            preferenceEditor.putString("selectedJSONData", selectedJSON);
                            //Commit the changes.
                            preferenceEditor.commit();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            //do nothing
                            break;
                    }
                }
            };

            //Build Actual box
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Delete All Your Meals?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }
        return true;
    }


    public ArrayList<Meal> getmMealsList() {
        return mMealsList;
    }
}
