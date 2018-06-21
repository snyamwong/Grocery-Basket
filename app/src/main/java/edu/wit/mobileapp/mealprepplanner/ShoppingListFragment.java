package edu.wit.mobileapp.mealprepplanner;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    private ListView shoppingListView;
    private ArrayList<Ingredient> mShoppingList;
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
            adapter = new ShoppingListAdapter(getActivity().getApplicationContext(), mShoppingList); //object to update fragment

            for (int i = 0; i <= 11; i++) {
                mShoppingList.add(new Ingredient("Ingredient " + Integer.toString(i), Integer.toString(i) + "oz"));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

}
