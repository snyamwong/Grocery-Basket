package edu.wit.mobileapp.mealprepplanner;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Database extends SQLiteOpenHelper
{
//    private String recipeTable;
//    private String ingredientTable;
//    private String recipeIngredientTable;

    private static final String LOGTAG = "DATABASE_LOG";

    private Context context;
    private static String DB_PATH = "/data/data/edu.wit.mobileapp.mealprepplanner/";
    private static String DB_NAME = "meal_prep_db.db";
    private SQLiteDatabase db;

    public Database(Context context)
    {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

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

    private boolean checkDataBase()
    {
        File dbFile = context.getDatabasePath(DB_NAME);

        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled
     */
    private void copyDataBase() throws IOException
    {
        //Open your local db as the input stream
        InputStream inputStream = context.getAssets().open(DB_NAME);

        //Open the empty db as the output stream
        OutputStream outputStream = new FileOutputStream(DB_PATH + DB_NAME);

        //transfer bytes from the inputfile to the outputfile
        int length;
        byte[] buffer = new byte[1024];
        while ((length = inputStream.read(buffer)) > 0)
        {
            outputStream.write(buffer, 0, length);
        }

        //Close the streams
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDataBase() throws SQLException
    {
        //Open the database
        String path = DB_PATH + DB_NAME;

        db = SQLiteDatabase.openOrCreateDatabase(path, null);
    }

    @Override
    public synchronized void close()
    {
        if (db != null)
        {
            db.close();
        }

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
//        DB_PATH = "/data/data/edu.wit.mobileapp.mealprepplanner/meal_prep_db.db";
//
//        recipeTable =
//                "CREATE TABLE IF NOT EXISTS recipe" +
//                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                        "name TEXT, " +
//                        "description TEXT," +
//                        "instructions String" +
//                        ");";
//
//        recipeIngredientTable =
//                "CREATE TABLE IF NOT EXISTS recipe_ingredient" +
//                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                        "RecipeID INTEGER, " +
//                        "IngredientID INTEGER, " +
//                        "measurement FLOAT, " +
//                        "unit TEXT" +
//                        ");";
//
//        ingredientTable =
//                "CREATE TABLE IF NOT EXISTS ingredient" +
//                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                        "name TEXT, " +
//                        "category TEXT" +
//                        ");";
//
//        db.execSQL(recipeTable);
//        db.execSQL(recipeIngredientTable);
//        db.execSQL(ingredientTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
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
