package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Object> mShoppingList;
    private static final int INGREDIENT = 0;
    private static final int HEADER =1;
    private LayoutInflater inflater;

    //Sets context, Shopping List, & inflater
    public ShoppingListAdapter(Context mContext, ArrayList<Object> mIngredientList) {
        this.mContext = mContext;
        this.mShoppingList = mIngredientList;
        inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    //get if view is header or ingredient
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

    //strike through a text view if true un-strike through if false
    private void setStrikeThrough(TextView text, boolean doStrike){
        if(doStrike){
            text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            text.setPaintFlags(text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

    }

    //render view
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        //if header or ingredient
        switch (getItemViewType(i)){
            case INGREDIENT:
                //attach shopping list layout
                convertView = inflater.inflate(R.layout.shopping_list, null);

                //Vars: base ingredient, cb & text views in row, amount string
                Ingredient ingredient = (Ingredient) mShoppingList.get(i);
                CheckBox cb = convertView.findViewById(R.id.ingredient_chk_box);
                TextView name = convertView.findViewById(R.id.ingredient_name);
                TextView amount = convertView.findViewById(R.id.ingredient_amount);
                String measurement = Integer.toString(ingredient.getAmount()) + " " + ingredient.getMeasurement();

                //render info based on ingredient stored values
                name.setText(ingredient.getName());
                amount.setText(measurement);
                //needed because views are recycled after first 10
                //basically restoring checked state
                cb.setChecked(ingredient.isSelected());
                setStrikeThrough(name, ingredient.isSelected());
                setStrikeThrough(amount, ingredient.isSelected());
                //attach ingredient to cb for onClick action
                cb.setTag(ingredient);

                //On checkbox click
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //passed checkbox
                        CheckBox cb = (CheckBox) v;
                        //get the layout
                        RelativeLayout r = (RelativeLayout) v.getParent();
                        //get the two text views
                        TextView name = r.findViewById(R.id.ingredient_name);
                        TextView amount = r.findViewById(R.id.ingredient_amount);

                        //Ingredient is attached to cb so that ingredient cant store if it is checked
                        Ingredient ingredient = (Ingredient)cb.getTag();
                        Toast.makeText(mContext, "cb clicked: " + ingredient.toString(), Toast.LENGTH_SHORT).show();
                        //change ingredient set value and change text views accordingly
                        ingredient.setSelected(cb.isChecked());
                        setStrikeThrough(name, ingredient.isSelected());
                        setStrikeThrough(amount, ingredient.isSelected());
                    }
                });

                break;
            case HEADER:
                //attach header layout
                convertView = inflater.inflate(R.layout.shopping_list_section_headers, null);
                //header text init
                TextView ingredient_type_header = convertView.findViewById(R.id.ingredient_type_header);
                ingredient_type_header.setText(mShoppingList.get(i).toString());
                break;
        }
        //return newly rendered view
        return convertView;
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
}
