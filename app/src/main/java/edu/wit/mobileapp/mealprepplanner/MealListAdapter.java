package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 * RESPONSIBLE FOR UPDATING mMealList FOR MEAL LIST FRAGMENT
 *
 * @author: Jason Fagerberg
 */


public class MealListAdapter extends BaseAdapter {

    //activity and corresponding list
    private Context mContext;
    private ArrayList<Meal> mMealsList;

    public MealListAdapter(Context mContext, ArrayList<Meal> mMeals) {
        this.mContext = mContext;
        this.mMealsList = mMeals;
    }

    @Override
    public int getCount() {
        return mMealsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMealsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * get the new view with the updated List
     * @param position = List position
     * @param convertView = not used
     * @param parent = not used
     * @return = new view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //New view based on the list layout
        View v = View.inflate(mContext, R.layout.meals_list, null);
        /*
            PLACEHOLDER MEAL UPDATE
         */
        TextView mealName = (TextView) v.findViewById(R.id.meal_name);
        TextView mealAmount = (TextView) v.findViewById(R.id.meal_amount);
        ImageView mealPicture = (ImageView) v.findViewById(R.id.meal_picture);

        //Change the three objects in the meals_list layout to the variables we just created
        mealName.setText(mMealsList.get(position).getName());
        mealAmount.setText("x" + Integer.toString(mMealsList.get(position).getAmount()));
        mealPicture.setImageResource(mMealsList.get(position).getImage());

        //set tag to the ID
        //USED FOR THE TOAST PRINT DEBUGGING TO BE REMOVED LATER?
        v.setTag(mMealsList.get(position).getId());

        //Return new view
        return v;
    }
}
