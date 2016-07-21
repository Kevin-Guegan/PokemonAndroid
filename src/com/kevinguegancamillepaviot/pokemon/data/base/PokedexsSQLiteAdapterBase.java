
/**************************************************************************
 * PokedexsSQLiteAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.kevinguegancamillepaviot.pokemon.data.SQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.PokedexsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokedexsContract;
import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;


import com.kevinguegancamillepaviot.pokemon.PokemonApplication;



/** Pokedexs adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokedexsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokedexsSQLiteAdapterBase
                        extends SQLiteAdapter<Pokedexs> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokedexsDBAdapter";


    /**
     * Get the table name used in DB for your Pokedexs entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokedexsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Pokedexs entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokedexsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Pokedexs entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokedexsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokedexsContract.TABLE_NAME    + " ("
        
         + PokedexsContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokedexsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Pokedexs entity to Content Values for database.
     * @param item Pokedexs entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Pokedexs item) {
        return PokedexsContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Pokedexs entity.
     * @param cursor android.database.Cursor object
     * @return Pokedexs entity
     */
    public Pokedexs cursorToItem(final android.database.Cursor cursor) {
        return PokedexsContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Pokedexs entity.
     * @param cursor android.database.Cursor object
     * @param result Pokedexs entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Pokedexs result) {
        PokedexsContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Pokedexs by id in database.
     *
     * @param id Identify of Pokedexs
     * @return Pokedexs entity
     */
    public Pokedexs getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Pokedexs result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All Pokedexss entities.
     *
     * @return List of Pokedexs entities
     */
    public ArrayList<Pokedexs> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Pokedexs> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Pokedexs entity into database.
     *
     * @param item The Pokedexs entity to persist
     * @return Id of the Pokedexs entity
     */
    public long insert(final Pokedexs item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokedexsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokedexsContract.itemToContentValues(item);
        values.remove(PokedexsContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokedexsContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Pokedexs entity into database whether.
     * it already exists or not.
     *
     * @param item The Pokedexs entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Pokedexs item) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.update(item);
        } else {
            // Item doesn't exist => create it
            final long id = this.insert(item);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * Update a Pokedexs entity into database.
     *
     * @param item The Pokedexs entity to persist
     * @return count of updated entities
     */
    public int update(final Pokedexs item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokedexsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokedexsContract.itemToContentValues(item);
        final String whereClause =
                 PokedexsContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Pokedexs entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokedexsContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokedexsContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokedexs The entity to delete
     * @return count of updated entities
     */
    public int delete(final Pokedexs pokedexs) {
        return this.remove(pokedexs.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Pokedexs corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokedexsContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokedexsContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Pokedexs entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokedexsContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokedexsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

