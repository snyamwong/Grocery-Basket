package edu.wit.mobileapp.mealprepplanner;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * MEALS FRAGMENT
 *
 *@author: Jason Fagerberg
 */

public class MealsFragment extends Fragment {

    //Objects in fragment
    private ListView mealListView;
    private MealsListAdapter adapter;
    private List<Meal> mMealsList;

    public MealsFragment() {
        // Required empty public constructor
    }

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate fragment
        view = inflater.inflate(R.layout.fragment_meals, container, false);

        //create toolbar named 'topBar'
        Toolbar topBar = (Toolbar) view.findViewById(R.id.mealsTopBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(topBar);
        setHasOptionsMenu(true);

        //Infinite List View / init arrayList
        mealListView = (ListView)  view.findViewById(R.id.mealsListView);
        mMealsList = new ArrayList<>();

        //Add Sample Data for display purposes
        for(int i = 0; i < 11; i++){
            mMealsList.add(new Meal(i, R.drawable.food , "Food Name", i));
        }

        adapter = new MealsListAdapter(getActivity().getApplicationContext(), mMealsList); //object to update fragment
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

        //Add button Setup
        FloatingActionButton btnAdd = (FloatingActionButton) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Placeholder Action to add placeholder meal
                mMealsList.add(new Meal(1, R.drawable.food , "Food Name", 1));
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
}
