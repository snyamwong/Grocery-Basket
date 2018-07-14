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

import org.w3c.dom.Text;

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
    private List<Meal> mealsList;
    private Context mContext;

    public MealListAdapter(Context mContext, ArrayList<Meal> mealsList) {
        this.mealsList = mealsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meals_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = mealsList.get(position);
        holder.image.setImageResource(meal.getImage());
        holder.name.setText(meal.getName());
        String amount = "x" + Integer.toString(meal.getAmount());
        holder.amount.setText(amount);

        holder.foreground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meal meal = mealsList.get(holder.getAdapterPosition());
                Toast.makeText(mContext, "Clicked Meal:    " + meal.getName() + " x" + meal.getAmount(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    //ViewHolder
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

    public void removeItem(int position) {
        mealsList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Meal item, int position) {
        mealsList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
