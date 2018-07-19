package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * RESPONSIBLE FOR UPDATING mMealList FOR MEAL LIST FRAGMENT
 *
 * @author: Jason Fagerberg
 */

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.ViewHolder>{
    private static final String TAG = "MealListAdapter";

    //vars
    private List<Recipe> mealsList;
    private Context mContext;

    //constructor
    public MealListAdapter(Context mContext, ArrayList<Recipe> mealsList) {
        this.mealsList = mealsList;
        this.mContext = mContext;
    }

    //set layout inflater & inflate layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meal_list, null);
        return new ViewHolder(view);
    }

    //When view is rendered bind the correct holder to it
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe meal = mealsList.get(position);
        //holder.image.setImageResource(meal.getImage());
        holder.image.setImageBitmap(meal.getImage());
        holder.name.setText(meal.getName());
        String amount = "x" + Integer.toString(meal.getMultiplier());
        holder.amount.setText(amount);

        holder.foreground.setOnClickListener((View v) -> {
                //Meal clicked = mealsList.get(holder.getAdapterPosition());
                Toast.makeText(mContext, "Clicked Meal:    " + meal.getName() + " x" + meal.getMultiplier(), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    //ViewHolder for each item in list
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name, amount;
        RelativeLayout foreground, background;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.meal_picture);
            name = itemView.findViewById(R.id.meal_name);
            amount = itemView.findViewById(R.id.meal_amount);

            foreground = itemView.findViewById(R.id.view_foreground);
            background= itemView.findViewById(R.id.view_background);
        }

    }

    //swipe remove
    public void removeItem(int position) {
        mealsList.remove(position);
        notifyItemRemoved(position);
    }

    //undo swipe remove
    public void restoreItem(Recipe item, int position) {
        mealsList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
