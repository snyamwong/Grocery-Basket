package edu.wit.mobileapp.mealprepplanner;

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
import android.widget.TextView;

/**
 * TODO: documentation
 * TODO: fix ImageSpan alignment
 * TODO: change font sizes
 * TODO: refactor - separation of concerns, very bad code design atm
 * TODO: figure out how to do the buttons (check MainActivity's current fragment)
 * <p>
 * TODO: not needed, but figure out why default image doesn't show up/work
 */
public class MealInfoFragment extends Fragment
{

    private MainActivity mainActivity;

    public MealInfoFragment()
    {
        // Required empty public constructor
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

        // Create SpannableString for every attributes in Recipe object
        // if the blob == null, it means there isn't an image in the database
        // therefore, it is then replaced with the app icon

        Drawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(recipe.getImage(), 0, recipe.getImage().length));
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        SpannableString recipeName = new SpannableString(recipe.getName());
        SpannableString recipeDescription = new SpannableString(recipe.getDescription());

        StringBuilder builder = new StringBuilder();

        for (RecipeIngredient r : recipe.getIngredients())
        {
            builder.append("\u2022 " + r.getIngredientName() + " - " + r.getQuantity() + " " + r.getUnit() + "\n");
        }

        SpannableString recipeIngredients = new SpannableString(builder.toString());
        SpannableString recipeInstruction = new SpannableString(recipe.getInstruction());
        SpannableString recipeChef = new SpannableString(recipe.getChef());

        // Apply XXX_Span to the SpannableString objects

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
        recipeIngredients.setSpan(centerAlignment, 0, recipeIngredients.length(), 0);

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

        // add or change
        button.setOnClickListener(v ->
        {

        });

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
}
