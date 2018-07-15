package edu.wit.mobileapp.mealprepplanner;


import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.HashMap;


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

    //main activity
    private MainActivity main;

    public MealListFragment() {
        // Required empty public constructor
    }

    //init data
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = (MainActivity)getActivity();

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
        Toolbar topBar = view.findViewById(R.id.mealsTopBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(topBar);
        setHasOptionsMenu(true);

        //Infinite List View
        mealListView = view.findViewById(R.id.mealsListView);
        mealListView.setHasFixedSize(true);
        mealListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Add touch listener for left swipe
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, MealListFragment.this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mealListView);

        //Add button Setup
        FloatingActionButton btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener((View v) -> {
            //set fragment null will set fragment to SearchFragment
            main.setFragment(main.getSearchFragment());

        });

        return view;
    }

    @Override
    public void onStart() {
        MainActivity main = (MainActivity)getActivity();
        mMealsList = main.getmMealsList();
        //Retrieve saved array list then set adapter
        adapter = new MealListAdapter(getActivity().getApplicationContext(), mMealsList); //object to update fragment
        //update list
        mealListView.setAdapter(adapter); //Update display with new list
        mealListView.getLayoutManager().scrollToPosition(mMealsList.size() - 1); //Nav to end of list
        //toggle empty text visibility
        toggleEmptyTextVisibility();


        BottomNavigationView bot = main.findViewById(R.id.main_nav);
        bot.setVisibility(View.VISIBLE);
        bot.setSelectedItemId(R.id.nav_meals);
        super.onStart();
        Log.v(LOGTAG, "onStart.....finished");
    }

    @Override
    public void onPause() {
        main.setmMealsList(mMealsList);
        super.onPause();
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
                            HashMap<String, Integer> selected = main.getmSelectedIngredients();
                            selected.clear();
                            main.setmSelectedIngredients(selected);

                            //toggle empty text visibility
                            TextView emptyTxt = getActivity().findViewById(R.id.emptyMealsList);
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

    //toggle the empty list text
    private void toggleEmptyTextVisibility(){
        //toggle empty text visibility
        TextView emptyTxt = relativeLayout.findViewById(R.id.emptyMealsList);
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
            snackbar.setAction("UNDO", (View v) -> {
                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                    toggleEmptyTextVisibility();
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
        //toggle empty text visibility
        toggleEmptyTextVisibility();
    }

}