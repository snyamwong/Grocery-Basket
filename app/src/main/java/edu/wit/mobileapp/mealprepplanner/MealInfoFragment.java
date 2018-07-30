package edu.wit.mobileapp.mealprepplanner;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * TODO: documentation
 * TODO: change font sizes
 * TODO: refactor - separation of concerns, very bad code design atm
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
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

            // add or change
            button.setOnClickListener(v ->
            {
                // TODO get recipe from ArrayList
            });
        }
        // if the previous Fragment is MealListFragment, then the user will "Add Meal"
        else if (MealPrepPlannerApplication.peekMainActivityFragmentStack() instanceof SearchFragment)
        {
            button.setText("Add Meal");

            MealPrepPlannerApplication.pushMainActivityFragmentStack(temp);

            // add or change
            button.setOnClickListener(v ->
            {
                showDialog(recipe);
            });
        }

        // Get the Drawable of the Recipe's image
        Drawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(recipe.getImage(), 0, recipe.getImage().length));
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        // SpannableString of the Recipe's Name
        SpannableString recipeName = new SpannableString(recipe.getName());
        // SpannableString of the Recipe's Description
        SpannableString recipeDescription = new SpannableString(recipe.getDescription());

        // The StringBuilder here is to build a presentable and readable display of the Recipe's ingredients
        StringBuilder builder = new StringBuilder();

        for (RecipeIngredient r : recipe.getIngredients())
        {
            // \u2022 is unicode for a bullet
            builder.append("\u2022 " + r.getIngredientName() + " - " + r.getQuantity() + " " + r.getUnit() + "\n");
        }

        // SpannableString of the Recipe's Ingredient, built from the previous loop
        SpannableString recipeIngredients = new SpannableString(builder.toString());
        // SpannableString of the Recipe's instructions
        SpannableString recipeInstruction = new SpannableString(recipe.getInstruction());
        // SpannableString of the chef
        SpannableString recipeChef = new SpannableString(recipe.getChef());

        // Spans
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);

        RelativeSizeSpan titleSpan = new RelativeSizeSpan(2f);

        AlignmentSpan centerAlignment = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
        AlignmentSpan leftAlignment = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL);

        // recipe name
        recipeName.setSpan(boldSpan, 0, recipeName.length(), 0);
        recipeName.setSpan(titleSpan, 0, recipeName.length(), 0);
        recipeName.setSpan(centerAlignment, 0, recipeName.length(), 0);

        // recipe description
        recipeDescription.setSpan(italicSpan, 0, recipeDescription.length(), 0);
        recipeDescription.setSpan(centerAlignment, 0, recipeDescription.length(), 0);

        // recipe ingredients
        recipeIngredients.setSpan(leftAlignment, 0, recipeIngredients.length(), 0);

        // recipe instruction
        recipeInstruction.setSpan(leftAlignment, 0, recipeInstruction.length(), 0);

        // recipe chef
        recipeChef.setSpan(italicSpan, 0, recipeChef.length(), 0);
        recipeChef.setSpan(centerAlignment, 0, recipeChef.length(), 0);

        imageView.setImageDrawable(drawable);

        // Clears the TextView first
        textView.setText(null);

        // Setting TextView to be multiline
        textView.setSingleLine(false);

        // Append the text into TextView
        textView.append(recipeName);
        textView.append("\n\n\n");
        textView.append(recipeDescription);
        textView.append("\n\n\n");
        textView.append(recipeIngredients);
        textView.append("\n\n\n");
        textView.append(recipeInstruction);
        textView.append("\n\n\n");
        textView.append(recipeChef);

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

    public void showDialog(Recipe recipe)
    {

        final Dialog dialog = new Dialog(getContext());
        dialog.setTitle("NumberPicker");
        dialog.setContentView(R.layout.number_picker_dialog);
        Button setButton = dialog.findViewById(R.id.number_picker_dialog_set_button);
        final NumberPicker numberPicker = dialog.findViewById(R.id.numberPicker1);
        numberPicker.setMaxValue(20); // max value 20
        numberPicker.setMinValue(1);   // min value 1
        numberPicker.setWrapSelectorWheel(false);
        // numberPicker.setOnValueChangedListener(getContext());
        setButton.setOnClickListener(v ->
        {
            dialog.dismiss();

            recipe.setMultiplier(numberPicker.getValue());

            mRecipeArrayList.add(recipe);
            mainActivity.setmRecipeList(mRecipeArrayList);

            mainActivity.onBackPressed();
        });

        dialog.show();
    }
}
