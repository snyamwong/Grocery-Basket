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
 * Class for RecipeListAdapter, since Meal class and Recipe class both share different attributes
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder>
{

    private static final String TAG = "RecipeListAdapter";

    private Context context;
    // List of recipe(s)
    private List<Recipe> recipeArrayList;

    /**
     * Constructor
     *
     * @param context
     * @param recipeArrayList
     */
    public RecipeListAdapter(Context context, List<Recipe> recipeArrayList)
    {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
    }

    /**
     * ViewHolder inner class
     */
    class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        TextView name;
        RelativeLayout foreground, background;

        ViewHolder(View itemView)
        {
            super(itemView);

            image = itemView.findViewById(R.id.meal_picture);
            name = itemView.findViewById(R.id.meal_name);

            foreground = itemView.findViewById(R.id.view_foreground);
            background = itemView.findViewById(R.id.view_background);
        }
    }

    /**
     * NOTE: Using layout recipe_list rather than meals_list
     * <p>
     * Inflate layout here
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Here, using recipe_list instead of meals_list so I avoid using the
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_list, null);

        return new RecipeListAdapter.ViewHolder(view);
    }

    /**
     * At the moment, only two things are certained with SearchActivity / RecipeList
     * <p>
     * 1) image
     * 2) name
     * <p>
     * If needed, add attributes here.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.ViewHolder holder, int position)
    {
        Recipe recipe = recipeArrayList.get(position);
        holder.name.setText(recipe.getName());
        holder.image.setImageBitmap(recipe.getImage());

        holder.foreground.setOnClickListener(v ->
        {
            // TODO this should transition into MealInfoFragment
            Toast.makeText(context, String.format("Clicked on %s", recipe.getName()), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount()
    {
        return recipeArrayList.size();
    }

    /**
     * Used to remove recipe
     *
     * @param position
     */
    public void removeRecipe(int position)
    {
        recipeArrayList.remove(position);

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    /**
     * Used to restore recipe
     *
     * @param item
     * @param position
     */
    public void restoreRecipe(Recipe item, int position)
    {
        recipeArrayList.add(position, item);

        // notify item added by position
        notifyItemInserted(position);
    }

    /**
     * Used to update recipe
     *
     * @param list
     */
    public void updateRecipeList(List<Recipe> list)
    {
        recipeArrayList = list;

        notifyDataSetChanged();
    }

}
