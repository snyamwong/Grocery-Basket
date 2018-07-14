package edu.wit.mobileapp.mealprepplanner;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>{
    private static final String TAG = "ShoppingListAdapter";
    private List<Object> mShoppingList;
    private Context mContext;

    private static final int INGREDIENT = 0;
    private static final int HEADER =1;

    private HashMap<String, Integer> selected;

    //Preferences for json storage
    public SharedPreferences mPrefs;
    public SharedPreferences.Editor preferenceEditor;

    public ShoppingListAdapter(List<Object> mShoppingList, Context mContext) {
        this.mShoppingList = mShoppingList;
        this.mContext = mContext;

        mPrefs = ((Activity)(mContext)).getPreferences(MODE_PRIVATE);
        preferenceEditor = mPrefs.edit();

        retrieveGlobalDataFromStorage();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView header, ingredientName, ingredientAmount;
        CheckBox cb;
        boolean checked;
        public ViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.ingredient_type_header);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            ingredientAmount = itemView.findViewById(R.id.ingredient_amount);
            cb = itemView.findViewById(R.id.ingredient_chk_box);
            checked = false;
        }
    }

    @NonNull
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        Log.v(TAG, "onCreateViewHolder called....... viewType = " + viewType);
        View view = null;
        switch (viewType){
            case INGREDIENT:
                view = inflater.inflate(R.layout.shopping_list, parent, false);
                break;
            case HEADER:
                view = inflater.inflate(R.layout.shopping_list_section_headers, parent, false);
                break;
        }

        return new ShoppingListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType){
            case INGREDIENT:
                Ingredient ingredient = (Ingredient) mShoppingList.get(position);
                //retrieveGlobalDataFromStorage();

                holder.checked = selected.containsKey(ingredient.getName());

                holder.ingredientName.setText(ingredient.getName());
                String amount = ingredient.getAmount() + " " + ingredient.getMeasurement();
                holder.ingredientAmount.setText(amount);

                //todo delete no longer needed, only for debug purpose
                holder.cb.setTag(ingredient);

                //if marked as selected and amount is unchanged ==> keep checked
                if(holder.checked && holder.ingredientAmount.getText().equals(amount)){
                    holder.cb.setChecked(true);
                    setStrikeThrough(holder.ingredientName, true);
                    setStrikeThrough(holder.ingredientAmount, true);
                    //marked as selected but amount has changed = remove from selected
                }else{
                    holder.cb.setChecked(false);
                    setStrikeThrough(holder.ingredientName, false);
                    setStrikeThrough(holder.ingredientAmount, false);
                    selected.remove(ingredient.getName());
                    //storeGlobalData();
                }

                holder.cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                        //if unchecked -> checked
                        if(cb.isChecked()){
                            selected.put(ingredient.getName(), ingredient.getAmount());
                            setStrikeThrough(name, true);
                            setStrikeThrough(amount, true);
                            //if checked -> unchecked
                        }else{
                            selected.remove(ingredient.getName());
                            setStrikeThrough(name, false);
                            setStrikeThrough(amount, false);
                        }
                        //storeGlobalData();
                    }
                });
                break;
            case HEADER:
                String header = (String) mShoppingList.get(position);
                holder.header.setText(header);
                break;
        }
    }

    //selected hash map ->json
    public void storeGlobalData(){
        Gson gson = new Gson();
        //Transform the ArrayLists into JSON Data.
        String selectedJSON = gson.toJson(selected);
        preferenceEditor.putString("selectedJSONData", selectedJSON);
        //Commit the changes.
        preferenceEditor.commit();
    }

    //json -> array list
    //json -> selected hash map
    public void retrieveGlobalDataFromStorage(){
        Gson gson = new Gson();
        if(mPrefs.contains("selectedJSONData")){
            String selectedJSON = mPrefs.getString("selectedJSONData", "");
            Type selectedType = new TypeToken<HashMap<String, Integer>>() {}.getType();
            selected = gson.fromJson(selectedJSON, selectedType);
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

    @Override
    public int getItemCount() {
        return mShoppingList.size();
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
}
