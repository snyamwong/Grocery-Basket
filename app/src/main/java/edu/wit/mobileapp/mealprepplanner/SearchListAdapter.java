package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
 * Class for SearchListAdapter, since Meal class and Recipe class both share different attributes
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder>
{

    private static final String TAG = "SearchListAdapter";

    private Context context;
    // List of recipe(s)
    private List<Recipe> recipeArrayList;
    private ArrayList<Recipe> mRecipeArrayList;

    private MainActivity main;

    /**
     * Constructor
     *
     * @param context
     * @param recipeArrayList
     */
    public SearchListAdapter(Context context, List<Recipe> recipeArrayList)
    {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
        main = ((MainActivity) (context));
        this.mRecipeArrayList = main.getmRecipeList();
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
     * NOTE: Using layout search_list rather than meal_list
     * <p>
     * Inflate layout here
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Here, using search_list instead of meal_list so I avoid using the
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_list, null);

        return new SearchListAdapter.ViewHolder(view);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Recipe recipe = recipeArrayList.get(position);
        holder.name.setText(recipe.getName());
        if (recipe.getImage() != null)
        {
            holder.image.setImageBitmap(BitmapFactory.decodeByteArray(recipe.getImage(), 0, recipe.getImage().length));
        }
        else
        {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_app_icon));
        }

        holder.foreground.setOnClickListener(v ->
        {
            if (!mRecipeArrayList.contains(recipe))
            {
                Toast.makeText(context, "Meal:" + recipe.getName() + " added to meal list", Toast.LENGTH_LONG).show();
                holder.foreground.setBackgroundColor(Color.GREEN);
                mRecipeArrayList.add(recipe);
                main.setmRecipeList(mRecipeArrayList);
            }
        });

        if (mRecipeArrayList.contains(recipe))
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
