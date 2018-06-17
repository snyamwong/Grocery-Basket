package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MealsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Meal> mMealsList;

    public MealsListAdapter(Context mContext, List<Meal> mMeals) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.meals_list, null);
        TextView mealName = (TextView) v.findViewById(R.id.meal_name);
        TextView mealAmount = (TextView) v.findViewById(R.id.meal_amount);
        ImageView mealPicture = (ImageView) v.findViewById(R.id.meal_picture);

        //set
        mealName.setText(mMealsList.get(position).getName());
        mealAmount.setText("x" + Integer.toString(mMealsList.get(position).getAmount()));
        mealPicture = mMealsList.get(position).getImage();

        v.setTag(mMealsList.get(position).getId());

        return v;
    }
}
