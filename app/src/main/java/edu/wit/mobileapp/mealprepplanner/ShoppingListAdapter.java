package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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

    private class ViewHolder{
        CheckBox cb;
        TextView sName;
        TextView sAmount;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch (getItemViewType(i)){

                case INGREDIENT:
                    convertView = inflater.inflate(R.layout.shopping_list, null);
                    holder.cb = convertView.findViewById(R.id.ingredient_chk_box);
                    holder.sName = convertView.findViewById(R.id.ingredient_name);
                    holder.sAmount = convertView.findViewById(R.id.ingredient_amount);

                    holder.cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckBox cb = (CheckBox) v;
                            Ingredient ingredient = (Ingredient)cb.getTag();
                            Toast.makeText(mContext, "cb clicked: " + ingredient.toString(), Toast.LENGTH_SHORT).show();
                            ingredient.setSelected(cb.isChecked());
                        }
                    });
                    break;
                case HEADER:
                    convertView = inflater.inflate(R.layout.shopping_list_section_headers, null);
                    break;
            }
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        switch (getItemViewType(i)){
            case INGREDIENT:
                Ingredient ingredient = (Ingredient) mShoppingList.get(i);
                Log.d("SLAdapter", "Position: " + i);
                //initial 9 or so rows
                if(holder != null){
                    holder.sName.setText(ingredient.getName());
                    String measurement = Integer.toString(ingredient.getAmount()) + " " + ingredient.getMeasurement();
                    holder.sAmount.setText(measurement);
                    holder.cb.setChecked(ingredient.isSelected());

                    holder.cb.setTag(ingredient);
                //when rows start to be reused
                }else{
                    //new holder
                    holder = new ViewHolder();
                    holder.cb = convertView.findViewById(R.id.ingredient_chk_box);
                    holder.sName = convertView.findViewById(R.id.ingredient_name);
                    holder.sAmount = convertView.findViewById(R.id.ingredient_amount);

                    holder.sName.setText(ingredient.getName());
                    String measurement = Integer.toString(ingredient.getAmount()) + " " + ingredient.getMeasurement();
                    holder.sAmount.setText(measurement);
                    holder.cb.setChecked(ingredient.isSelected());

                    holder.cb.setTag(ingredient);

                }


//                final CheckBox cb = holder.cb;
//                final TextView ingredient_name = holder.sName
//                final TextView ingredient_amount = holder.sAmount;
//
//                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
//                {
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
//                    {
//                        if(cb.isChecked()){
//                            ingredient_amount.setPaintFlags(ingredient_amount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                            ingredient_name.setPaintFlags(ingredient_name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                        }else{
//                            ingredient_amount.setPaintFlags(ingredient_amount.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                            ingredient_name.setPaintFlags(ingredient_name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                        }
//
//                    }
//                });
//
//                Ingredient ingredient = ((Ingredient) mShoppingList.get(i));
//                String measurement = Integer.toString(ingredient.getAmount()) + " " + ingredient.getMeasurement();
//                ingredient_amount.setText(measurement);
//                ingredient_name.setText(ingredient.getName());
                break;
            case HEADER:
                TextView ingredient_type_header = convertView.findViewById(R.id.ingredient_type_header);
                ingredient_type_header.setText(mShoppingList.get(i).toString());
                break;
        }
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
