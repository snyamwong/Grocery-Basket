package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A helper class for the database being used in this app.
 * <p>
 * Provides an easy method to query something
 * <p>
 * Make sure to open/close accordingly (i.e open during search, close during meal list/shopping list)
 */
public class Database extends SQLiteOpenHelper
{
    private static final String LOGTAG = "DATABASE_LOG";
    private static String DB_PATH = "/data/data/edu.wit.mobileapp.mealprepplanner/";
    private static String DB_NAME = "meal_prep_db.db";

    private Context context;
    private SQLiteDatabase db;

    public Database(Context context)
    {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    /*
     * XXX
     * Creates database by copying local database to phone database
     *
     * @throws IOException
     */
    public void createDatabase() throws IOException
    {
        try
        {
            copyDataBase();
        }
        catch (IOException e)
        {
            throw new Error("Error copying database");
        }
    }

    /**
     * Checks if database exists
     *
     * @return if database exists
     */
    private boolean exists()
    {
        File dbFile = context.getDatabasePath(DB_NAME);

        return dbFile.exists();
    }

    /**
     * Copies database from local assets folder
     * to just created empty database in the system/emulator/phone folder
     *
     * @throws IOException
     */
    private void copyDataBase() throws IOException
    {
        // opens local db as the input stream
        // context.getAssets() returns the assets folder placed in the project
        InputStream inputStream = context.getAssets().open(DB_NAME);

        // opens empty db as the output stream
        OutputStream outputStream = new FileOutputStream(DB_PATH + DB_NAME);

        // transfers bytes from the inputfile to the outputfile
        // XXX no idea if the buffer still works for images, need to test
        int length;
        byte[] buffer = new byte[1024];
        while ((length = inputStream.read(buffer)) > 0)
        {
            outputStream.write(buffer, 0, length);
        }

        // closes the streams
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /**
     * Opens the database
     *
     * @throws SQLException
     */
    public void open() throws SQLException
    {
        // first, checks if database exists in local phone's storage
        if (!this.exists())
        {
            try
            {
                this.createDatabase();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        // opens the database
        String path = DB_PATH + DB_NAME;

        db = SQLiteDatabase.openOrCreateDatabase(path, null);
    }

    /**
     * Closes the database
     * synchronized is if we want to get fancy and do multithreading (not for this project lmao)
     */
    @Override
    public synchronized void close()
    {
        if (db != null)
        {
            db.close();
        }

        super.close();
    }

    /**
     * Gets recipes given the recipe's name
     * Then, gets recipeIngredients given the recipe's ID
     * <p>
     * i.e in the search activity:
     * user types in meal name of Bagel
     * searches for all name that have Bagel in the name
     * searches for all ingredients for each recipe that has Bagel in the name
     * returns an ArrayList<Recipe>, which contains all recipes with Bagel in the name
     * <p>
     * TODO
     * clean up code, separate operations in the method
     *
     * @param userInputRecipeName
     * @return ArrayList<Recipe>
     */
    public ArrayList<Recipe> getRecipes(String userInputRecipeName)
    {
        String[] recipeColumns = {"recipe_id, name, image, description, instruction, chef"};
        // using LIKE clause here so the user can just request for any recipe that has just the ingredient/name
        String where = "name LIKE ?";
        String[] where_args = new String[]{"%" + userInputRecipeName + "%"};
        String having = null;
        String group_by = null;
        String order_by = "name";

        ArrayList<Recipe> recipes = new ArrayList<>();
        Cursor cursor;

        // here, queries the Recipe database for all recipes that are LIKE userInputRecipeName
        cursor = this.getDb().query("Recipe", recipeColumns, where, where_args, group_by, having, order_by);
        while (cursor.moveToNext())
        {
            Integer recipeID = cursor.getInt(cursor.getColumnIndex("recipe_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("image"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String instruction = cursor.getString(cursor.getColumnIndex("instruction"));
            String chef = cursor.getString(cursor.getColumnIndex("chef"));
            Recipe recipe;

            // TODO SCALE DOWN ALL THE PHOTOS by checking its dimens before decoding
            if (blob != null)
            {
                Bitmap image = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                recipe = new Recipe(recipeID, name, image, description, instruction, chef);
            }
            else
            {
                Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_app_icon);

                recipe = new Recipe(recipeID, name, icon, description, instruction, chef);
            }

            recipes.add(recipe);
        }

        // here, queries the Recipe_Ingredient database for all the ingredients using the results of the previous query
        for (Recipe r : recipes)
        {
            String[] ingredientsColumns = {"recipe_id", "recipe_name", "ingredient_name", "ingredient_category", "quantity", "unit"};
            where = "recipe_id = ?";
            where_args = new String[]{Integer.toString(r.getRecipeID())};
            having = null;
            group_by = null;
            order_by = "recipe_name";

            // cursor will move to each ingredient with the name recipeID
            cursor = this.getDb().query("Recipe_Ingredient", ingredientsColumns, where, where_args, having, group_by, order_by);
            while (cursor.moveToNext())
            {
                Integer recipeID = cursor.getInt(cursor.getColumnIndex("recipe_id"));
                String recipeName = cursor.getString(cursor.getColumnIndex("recipe_name"));
                String ingredientName = cursor.getString(cursor.getColumnIndex("ingredient_name"));
                String ingredientCategory = cursor.getString(cursor.getColumnIndex("ingredient_category"));
                Double quantity = cursor.getDouble(cursor.getColumnIndex("quantity"));
                String unit = cursor.getString(cursor.getColumnIndex("unit"));

                RecipeIngredient recipeIngredient;
                recipeIngredient = new RecipeIngredient(recipeID, recipeName, ingredientName, ingredientCategory, quantity, unit);

                r.getIngredients().add(recipeIngredient);
            }
        }

        // XXX logging result of the recipe, delete during production / non testing
        Log.v(LOGTAG, recipes.toString());

        return recipes;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public SQLiteDatabase getDb()
    {
        return db;
    }

    public void setDb(SQLiteDatabase db)
    {
        this.db = db;
    }
}
