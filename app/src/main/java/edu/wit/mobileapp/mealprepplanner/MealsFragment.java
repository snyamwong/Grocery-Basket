package edu.wit.mobileapp.mealprepplanner;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MealsFragment extends Fragment {

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
        //create toolbar
        Toolbar topBar = (Toolbar) view.findViewById(R.id.mealsTopBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(topBar);
        setHasOptionsMenu(true);

        //Infinite List setup
        mealListView = (ListView)  view.findViewById(R.id.mealsListView);
        mMealsList = new ArrayList<>();
        //sample data
        for(int i = 0; i < 11; i++){
            mMealsList.add(new Meal(i, R.drawable.food , "Food Name", i));
        }


        //on Item click
        adapter = new MealsListAdapter(getActivity().getApplicationContext(), mMealsList);
        mealListView.setAdapter(adapter);

        mealListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                Toast.makeText(getActivity().getApplicationContext(), "Clicked Meal ID: " + v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        //Add button Setup
        FloatingActionButton btnAdd = (FloatingActionButton) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_meals, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mMealsList.size() == 0){return false;}
        int res_id = item.getItemId();
        if(res_id == R.id.deleteAll){

            //Are you sure about that?
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
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            //do nothing
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Delete All Your Meals?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }
        return true;
    }
}
