package edu.wit.mobileapp.mealprepplanner;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class MealListFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    //Debug log tag
    private static final String LOGTAG = "MealsListFragment";
    Activity context;

    //Objects in fragment
    private RecyclerView mealListView;
    private MealListAdapter adapter;
    private ArrayList<Meal> mMealsList;
    private RelativeLayout relativeLayout;

    //Preferences for json storage
    public SharedPreferences mPrefs;
    public Editor preferenceEditor;

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

        Log.v(LOGTAG, "onCreate.....finished");
    }

    //build view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();

        View view;

        //inflate fragment
        view = inflater.inflate(R.layout.fragment_meals, container, false);

        //getLayout
        relativeLayout = view.findViewById(R.id.meals_layout);

        //create toolbar named 'topBar'
        Toolbar topBar = (Toolbar) view.findViewById(R.id.mealsTopBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(topBar);
        setHasOptionsMenu(true);

        //Infinite List View
        mealListView = (RecyclerView)  view.findViewById(R.id.mealsListView);
        mealListView.setHasFixedSize(true);
        mealListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mealListView.setItemAnimator(new DefaultItemAnimator());
        mealListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, MealListFragment.this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mealListView);



        //update list
        mealListView.setAdapter(adapter); //Update display with new list
        mealListView.getLayoutManager().scrollToPosition(mMealsList.size() - 1); //Nav to end of list

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        int n = 2;
        for(int i = 0; i < n; i++){
            ingredients.add(new Ingredient("Produce Ingredient " + i, i, "oz", "Produce"));
            ingredients.add(new Ingredient("Bakery Ingredient " + (i+n), (i+n), "oz", "Bakery"));
            ingredients.add(new Ingredient("Deli Ingredient " + (i+n*2), (i+n*2), "oz", "Deli"));
            ingredients.add(new Ingredient("Meat Ingredient " + (i+n*3), (i+n*3), "oz", "Meat"));
            ingredients.add(new Ingredient("Seafood Ingredient " + (i+n*4), (i+n*4), "oz", "Seafood"));
            ingredients.add(new Ingredient("Grocery Ingredient " + (i+n*5), (i+n*5), "oz", "Grocery"));
            ingredients.add(new Ingredient("Dairy Ingredient " + (i+n*6), (i+n*6), "oz", "Dairy"));
        }

        //Add button Setup
        FloatingActionButton btnAdd = (FloatingActionButton) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //navigates to search activity
//                Intent intent = new Intent(context, SearchActivity.class);
//
//                startActivity(intent);
                MainActivity main = (MainActivity)getActivity();
                Fragment searchFragment = new SearchFragment();
                main.setFragment(searchFragment);

                //Placeholder Action to add placeholder meal
//                int rand = (int) (Math.random()*100);
//                Meal toAdd = new Meal(rand, R.drawable.food, "Generic Meal #" + Integer.toString(rand), 1,ingredients);
//                mMealsList.add(toAdd);
//
//                mealListView.setAdapter(adapter);
//                mealListView.getLayoutManager().scrollToPosition(mMealsList.size() - 1); //Nav to end of list
//
//                //TODO remove when search and add is working
//                TextView emptyTxt = (TextView) getActivity().findViewById(R.id.emptyMealsList);
//                emptyTxt.setVisibility(View.INVISIBLE);


            }
        });

        //toggle empty text visibility
        toggleEmptyTextVisibility();
        return view;
    }

    @Override
    public void onStart() {
        MainActivity main = (MainActivity)getActivity();
        BottomNavigationView bot = main.findViewById(R.id.main_nav);
        bot.setVisibility(View.VISIBLE);
        bot.setSelectedItemId(R.id.nav_meals);
        super.onStart();
        Log.v(LOGTAG, "onStart.....finished");
    }

    //Create menu where delete button sits
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_meals, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //On delete button press
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
                            mealListView.getLayoutManager().scrollToPosition(mMealsList.size() - 1); //Nav to end of list

                            //Clear selected options
                            Gson gson = new Gson();
                            //Transform the ArrayLists into JSON Data.
                            String selectedJSON = gson.toJson(new HashMap<String,Integer>());
                            preferenceEditor.putString("selectedJSONData", selectedJSON);
                            //Commit the changes.
                            preferenceEditor.commit();

                            //toggle empty text visibility
                            TextView emptyTxt = (TextView) getActivity().findViewById(R.id.emptyMealsList);
                            emptyTxt.setVisibility(View.VISIBLE);
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
            mMealsList = gson.fromJson(mealsJSON, mealType);
        }else {
            mMealsList = new ArrayList<>();
        }
    }

    private void toggleEmptyTextVisibility(){
        //toggle empty text visibility
        TextView emptyTxt = (TextView) relativeLayout.findViewById(R.id.emptyMealsList);
        if(!mMealsList.isEmpty()) {
            emptyTxt.setVisibility(View.INVISIBLE);
        }else {
            emptyTxt.setVisibility(View.VISIBLE);
        }
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MealListAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = mMealsList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final Meal deletedItem = mMealsList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar.make(relativeLayout, name + " removed from meal list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                    toggleEmptyTextVisibility();
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
        //toggle empty text visibility
        toggleEmptyTextVisibility();
    }

}