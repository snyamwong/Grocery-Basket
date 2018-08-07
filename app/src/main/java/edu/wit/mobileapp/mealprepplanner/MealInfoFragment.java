package edu.wit.mobileapp.mealprepplanner;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Fragment for MealInfo, which appears when user taps on a Recipe in either MealListFragment or SearchFragment
 * <p>
 * Allows the user to either add a recipe or update a recipe
 */
public class MealInfoFragment extends Fragment
{

    private ArrayList<Recipe> mRecipeArrayList;

    private MainActivity mainActivity;

    public MealInfoFragment()
    {
        // Required empty public constructor
        mRecipeArrayList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.setMainActivity((MainActivity) getActivity());

    }

    /**
     * FIXME really need to clean up code
     * TODO change the aesthetics (white background, better typography, etc).
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_meal_info, container, false);

        // Recipe's Image
        ImageView imageView = view.findViewById(R.id.meal_info_fragment_recipe_image);
        // Recipe's info
        TextView textView = view.findViewById(R.id.meal_info_fragment_recipe_textview);
        // Add the Recipe || Change Servings
        Button button = view.findViewById(R.id.meal_info_fragment_recipe_add_meal_change_serving_button);
        // Recipe from SearchFragment
        Recipe recipe = this.getMainActivity().getMealInfoFragmentRecipe();

        Fragment temp = MealPrepPlannerApplication.popPrevMainActivityFragmentStack();

        // if the previous Fragment is MealListFragment, then the user will "Change Servings"
        if (MealPrepPlannerApplication.peekMainActivityFragmentStack() instanceof MealListFragment)
        {
            button.setText("Change Servings");

            // Hide nav bar
            mainActivity.hideNavigationBar();

            MealPrepPlannerApplication.pushMainActivityFragmentStack(temp);

            // change servings
            button.setOnClickListener(v -> showChangeServingsDialog(recipe));
        }
        // if the previous Fragment is MealListFragment, then the user will "Add Meal"
        else if (MealPrepPlannerApplication.peekMainActivityFragmentStack() instanceof SearchFragment)
        {
            button.setText("Add Meal");

            MealPrepPlannerApplication.pushMainActivityFragmentStack(temp);

            // add meal
            button.setOnClickListener(v -> showAddMealDialog(recipe));
        }

        // Get the Drawable of the Recipe's image
        Drawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(recipe.getImage(), 0, recipe.getImage().length));
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        // SpannableString of the Recipe's Name
        SpannableString recipeName = new SpannableString(recipe.getName());
        SpannableString recipeServes = new SpannableString("Serves: " + recipe.getServes());
        // SpannableString of the Recipe's Description
        SpannableString recipeDescription = new SpannableString(recipe.getDescription());

        // The StringBuilder here is to build a presentable and readable display of the Recipe's ingredients
        StringBuilder builder = new StringBuilder();

        for (RecipeIngredient r : recipe.getIngredients())
        {
            // \u2022 is unicode for a bullet
            // Appending everything to StringBuilder to save memory
            builder.append("\u2022 ")
                    .append(r.getIngredientName())
                    .append(" - ")
                    .append(r.getQuantity())
                    .append(" ")
                    .append(r.getUnit())
                    .append("\n");
        }

        // SpannableString of the Recipe's Ingredient, built from the previous loop
        SpannableString recipeIngredients = new SpannableString(builder.toString());
        // SpannableString of the Recipe's instructions
        SpannableString recipeInstruction = new SpannableString(recipe.getInstruction());
        // SpannableString of the chef
        SpannableString recipeChef = new SpannableString(recipe.getChef());

        // recipe name
        recipeName.setSpan(new StyleSpan(Typeface.BOLD), 0, recipeName.length(), 0);
        recipeName.setSpan(new RelativeSizeSpan(2f), 0, recipeName.length(), 0);
        recipeName.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, recipeName.length(), 0);

        recipeServes.setSpan(new StyleSpan(Typeface.BOLD), 0, recipeServes.length(), 0);
        //recipeServes.setSpan(new RelativeSizeSpan(2f), 0, recipeServes.length(), 0);
        recipeServes.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, recipeServes.length(), 0);

        // recipe description
        recipeDescription.setSpan(new StyleSpan(Typeface.ITALIC), 0, recipeDescription.length(), 0);
        recipeDescription.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, recipeDescription.length(), 0);

        // recipe ingredients
        recipeIngredients.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, recipeIngredients.length(), 0);

        // recipe instruction
        recipeInstruction.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, recipeInstruction.length(), 0);

        // recipe chef
        recipeChef.setSpan(new StyleSpan(Typeface.ITALIC), 0, recipeChef.length(), 0);
        recipeChef.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, recipeChef.length(), 0);

        imageView.setImageDrawable(drawable);

        // Clears the TextView first
        textView.setText(null);

        // Setting TextView to be multiline
        textView.setSingleLine(false);

        // Append the text into TextView
        textView.append(recipeName);
        textView.append("\n");
        textView.append(recipeServes);
        textView.append("\n\n\n");
        textView.append(recipeDescription);
        textView.append("\n\n\n");
        textView.append(recipeIngredients);
        textView.append("\n\n\n");
        textView.append(recipeInstruction);
        textView.append("\n\n\n");
        textView.append(recipeChef);

        Toolbar toolbar = view.findViewById(R.id.mealInfoTopBar);
        mainActivity.setSupportActionBar(toolbar);

        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener((View v) -> onBackPressed());


        return view;
    }

    public MainActivity getMainActivity()
    {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    /**
     * Adds Recipe to Meal List
     * <p>
     * Difference between this and showChangeServingsDialog is that this listener adds recipe
     *
     * @param recipe
     */
    public void showAddMealDialog(Recipe recipe)
    {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.number_picker_dialog);
        dialog.setTitle("Servings Multiplier");

        Button setButton = dialog.findViewById(R.id.number_picker_dialog_set_button);

        final NumberPicker numberPicker = dialog.findViewById(R.id.number_pick_dialog);
        numberPicker.setMaxValue(100); // max value 100 (why?)
        numberPicker.setMinValue(1);   // min value 1
        numberPicker.setWrapSelectorWheel(false);

        // FIXME
        setButton.setOnClickListener(v ->
        {
            if (!mainActivity.getmRecipeList().contains(recipe))
            {
                recipe.setMultiplier(numberPicker.getValue());
                mRecipeArrayList = mainActivity.getmRecipeList();
                mRecipeArrayList.add(recipe);
                mainActivity.setmRecipeList(mRecipeArrayList);
            }
            else
            {
                for (Recipe target : mainActivity.getmRecipeList())
                {
                    if (recipe.equals(target))
                    {
                        target.setMultiplier(numberPicker.getValue() + target.getMultiplier());
                    }
                }
            }

            mainActivity.onBackPressed();
            dialog.dismiss();
        });

        dialog.show();
    }

    /**
     * Changes servings for the Recipe
     * <p>
     * Difference between this and showAddMealDialog is that this listeners updates recipe
     *
     * @param recipe
     */
    public void showChangeServingsDialog(Recipe recipe)
    {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.number_picker_dialog);
        dialog.setTitle("Servings");

        final NumberPicker numberPicker = dialog.findViewById(R.id.number_pick_dialog);
        numberPicker.setMaxValue(100); // max value 100 (why?)
        numberPicker.setMinValue(1);   // min value 1
        numberPicker.setWrapSelectorWheel(false);

        Button setButton = dialog.findViewById(R.id.number_picker_dialog_set_button);

        // FIXME Needs improvements big time
        setButton.setOnClickListener(v ->
        {
            for (Recipe target : mainActivity.getmRecipeList())
            {
                if (recipe.equals(target))
                {
                    target.setMultiplier(numberPicker.getValue());

                    mainActivity.onBackPressed();
                }
            }

            dialog.dismiss();
        });

        dialog.show();
    }
    //pass onto main activity on back press

    public void onBackPressed()
    {
        mainActivity.onBackPressed();
    }
}
