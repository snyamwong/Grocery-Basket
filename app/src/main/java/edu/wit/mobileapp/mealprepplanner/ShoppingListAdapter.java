package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Object> mShoppingList;
    private static final int INGREDIENT = 0;
    private static final int HEADER =1;
    private LayoutInflater inflater;

    public ShoppingListAdapter(Context mContext, ArrayList<Object> mIngredientList) {
        this.mContext = mContext;
        this.mShoppingList = mIngredientList;
        inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position){
        if(mShoppingList.get(position) instanceof Ingredient){
            return INGREDIENT;
        }
        else
        {
            return HEADER;
        }
    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }

    @Override
    public int getCount() {
        return mShoppingList.size();
    }

    @Override
    public Object getItem(int i) {
        return mShoppingList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            switch (getItemViewType(i)){
                case INGREDIENT:
                    view = inflater.inflate(R.layout.shopping_list, null);
                    break;
                case HEADER:
                    view = inflater.inflate(R.layout.shopping_list_section_headers, null);
                    break;
            }
        }

        switch (getItemViewType(i)){
            case INGREDIENT:
                TextView ingredient_amount = view.findViewById(R.id.ingredient_amount);
                TextView ingredient_name = view.findViewById(R.id.ingredient_name);


                Ingredient ingredient = ((Ingredient) mShoppingList.get(i));
                String measurement = Integer.toString(ingredient.getAmount()) + " " + ingredient.getMeasurement();
                ingredient_amount.setText(measurement);
                ingredient_name.setText(ingredient.getName());
                break;
            case HEADER:
                TextView ingredient_type_header = view.findViewById(R.id.ingredient_type_header);
                ingredient_type_header.setText(mShoppingList.get(i).toString());
                break;
        }
        return view;
    }
}
