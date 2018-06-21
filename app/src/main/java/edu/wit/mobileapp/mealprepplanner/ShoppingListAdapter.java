package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ShoppingListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Ingredient> mIngredientList;

    public ShoppingListAdapter(Context mContext, ArrayList<Ingredient> mIngredientList) {
        this.mContext = mContext;
        this.mIngredientList = mIngredientList;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
