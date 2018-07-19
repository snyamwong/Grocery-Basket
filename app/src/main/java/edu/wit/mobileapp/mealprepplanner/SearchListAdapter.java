package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder>
{
    //vars
    private List<Recipe> searchList;
    private ArrayList<Recipe> mMealsList;
    private Context mContext;

    private MainActivity main;

    public SearchListAdapter(Context mContext, List<Recipe> searchList)
    {
        this.searchList = searchList;
        this.mContext = mContext;
        main = ((MainActivity) (mContext));
        mMealsList = main.getmRecipeList();
    }

    @NonNull
    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.search_list, null);
        return new SearchListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.ViewHolder holder, int position)
    {
        Recipe recipe = searchList.get(position);
        holder.image.setImageBitmap(recipe.getImage());
        holder.name.setText(recipe.getName());

        holder.foreground.setBackgroundColor(Color.WHITE);

        holder.foreground.setOnClickListener((View v) ->
        {
            if (!mMealsList.contains(recipe))
            {
                Toast.makeText(mContext, "Meal:" + recipe.getName() + " added to meal list", Toast.LENGTH_LONG).show();
                holder.foreground.setBackgroundColor(Color.GREEN);
                mMealsList.add(recipe);
                main.setmRecipeList(mMealsList);
            }
        });

        if (mMealsList.contains(recipe))
        {
            holder.foreground.setBackgroundColor(Color.GREEN);
        }
        else
        {
            holder.foreground.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount()
    {
        return searchList.size();
    }

    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        TextView name;
        RelativeLayout foreground;

        public ViewHolder(View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.meal_picture);
            name = itemView.findViewById(R.id.meal_name);

            foreground = itemView.findViewById(R.id.view_foreground);
        }
    }

    public void updateList(List<Recipe> list)
    {
        searchList = list;
        notifyDataSetChanged();
    }
}
