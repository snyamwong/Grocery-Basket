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

import java.util.ArrayList;
import java.util.List;

/**
 * Class for RecipeListAdapter, since Meal class and Recipe class both share different attributes
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder>
{

    private static final String TAG = "RecipeListAdapter";

    private Context context;
    private List<Recipe> recipeArrayList;

    // Constructor
    public RecipeListAdapter(Context context, List<Recipe> recipeArrayList)
    {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
    }

    // Viewholder
    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        TextView name;
        RelativeLayout foreground, background;

        public ViewHolder(View itemView)
        {
            super(itemView);

            image = itemView.findViewById(R.id.meal_picture);
            name = itemView.findViewById(R.id.meal_name);

            foreground = itemView.findViewById(R.id.view_foreground);
            background = itemView.findViewById(R.id.view_background);
        }
    }

    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meals_list, null);

        return new RecipeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.ViewHolder holder, int position)
    {
        Recipe recipe = recipeArrayList.get(position);
        holder.name.setText(recipe.getName());
        holder.image.setImageBitmap(recipe.getImage());

//        holder.foreground.setOnClickListener(v ->
//        {
//             Recipe recipe = recipeArrayList.get(holder.getAdapterPosition());
//             Toast.makeText(context, "Clicked Meal:    " + meal.getName() + " x" + meal.getAmount(), Toast.LENGTH_LONG).show();
//        });
    }

    @Override
    public int getItemCount()
    {
        return recipeArrayList.size();
    }

    public void removeItem(int position)
    {
        recipeArrayList.remove(position);

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Recipe item, int position)
    {
        recipeArrayList.add(position, item);

        // notify item added by position
        notifyItemInserted(position);
    }

    public void updateList(List<Recipe> list)
    {
        recipeArrayList = list;

        notifyDataSetChanged();
    }

}
