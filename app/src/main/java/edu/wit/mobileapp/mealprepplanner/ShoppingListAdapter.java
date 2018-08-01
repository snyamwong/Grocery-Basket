package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;


public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>
{
    private static final String TAG = "ShoppingListAdapter";
    private List<Object> mShoppingList;
    private Context mContext;

    private static final int INGREDIENT = 0;
    private static final int HEADER = 1;

    private MainActivity main;

    private HashMap<String, Double> selected;

    public ShoppingListAdapter(List<Object> mShoppingList, Context mContext)
    {
        main = ((MainActivity) (mContext));
        this.mShoppingList = mShoppingList;
        this.mContext = mContext;

        MainActivity main = ((MainActivity) (mContext));
        selected = main.getmSelectedIngredients();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView header, ingredientName, ingredientAmount;
        CheckBox cb;
        boolean checked;

        public ViewHolder(View itemView)
        {
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
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //Log.v(TAG, "onCreateViewHolder called....... viewType = " + viewType);
        View view = null;
        switch (viewType)
        {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        int viewType = getItemViewType(position);

        switch (viewType)
        {
            case INGREDIENT:
                RecipeIngredient ingredient = (RecipeIngredient) mShoppingList.get(position);

                String amountString = ingredient.getQuantity() + " " + ingredient.getUnit();

                holder.checked = selected.containsKey(ingredient.getIngredientName());
                holder.ingredientName.setText(ingredient.getIngredientName());
                holder.ingredientAmount.setText(amountString);

                //if marked as selected and amount is unchanged ==> keep checked
                if (holder.checked && selected.get(ingredient.getIngredientName()) == ingredient.getQuantity())
                {
                    holder.cb.setChecked(true);
                    setStrikeThrough(holder.ingredientName, true);
                    setStrikeThrough(holder.ingredientAmount, true);
                    //marked as selected but amount has changed = remove from selected
                }
                else
                {
                    holder.cb.setChecked(false);
                    setStrikeThrough(holder.ingredientName, false);
                    setStrikeThrough(holder.ingredientAmount, false);
                    selected.remove(ingredient.getIngredientName());
                }

                holder.cb.setOnClickListener((View v) ->
                {
                    CheckBox cb = (CheckBox) v;
                    //get the layout
                    RelativeLayout r = (RelativeLayout) v.getParent();
                    //get the two text views
                    TextView name = r.findViewById(R.id.ingredient_name);
                    TextView amount = r.findViewById(R.id.ingredient_amount);

                    //Toast.makeText(mContext, "cb clicked: " + ingredient.toString(), Toast.LENGTH_SHORT).show();
                    //change ingredient set value and change text views accordingly
                    //if unchecked -> checked
                    if (cb.isChecked())
                    {
                        selected.put(ingredient.getIngredientName(), ingredient.getQuantity());
                        setStrikeThrough(name, true);
                        setStrikeThrough(amount, true);
                        //if checked -> unchecked
                    }
                    else
                    {
                        selected.remove(ingredient.getIngredientName());
                        setStrikeThrough(name, false);
                        setStrikeThrough(amount, false);

                        main.setmSelectedIngredients(selected);
                    }
                });
                main.setmSelectedIngredients(selected);
                break;

            case HEADER:
                String header = (String) mShoppingList.get(position);
                holder.header.setText(header);
                break;
        }
    }

    /**
     * Strikes through a text view if true un-strike through if false
     *
     * @param text
     * @param doStrike
     */
    private void setStrikeThrough(TextView text, boolean doStrike)
    {
        if (doStrike)
        {
            text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            text.setPaintFlags(text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public int getItemCount()
    {
        return mShoppingList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (mShoppingList.get(position) instanceof RecipeIngredient)
        {
            return INGREDIENT;
        }
        else
        {
            return HEADER;
        }
    }
}
