package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
    private static final String LOGTAG = "Database";

    private static final String TABLE_NAME = "meal_prep_planner";
    private static String recipeTable;
    private static String ingredientTable;
    private static String recipeIngredientTable;

    public Database(Context context)
    {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        recipeTable =
                "CREATE TABLE IF NOT EXISTS recipe" +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT, " +
                        "description TEXT," +
                        "instructions String" +
                        ");";

        recipeIngredientTable =
                "CREATE TABLE IF NOT EXISTS recipe_ingredient" +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "RecipeID INTEGER, " +
                        "IngredientID INTEGER, " +
                        "measurement FLOAT, " +
                        "unit TEXT" +
                        ");";

        ingredientTable =
                "CREATE TABLE IF NOT EXISTS ingredient" +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT, " +
                        "category TEXT" +
                        ");";

        db.execSQL(recipeTable);
        db.execSQL(recipeIngredientTable);
        db.execSQL(ingredientTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
