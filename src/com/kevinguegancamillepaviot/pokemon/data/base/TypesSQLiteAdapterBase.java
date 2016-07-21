
/**************************************************************************
 * TypesSQLiteAdapterBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.data.TypesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;
import com.kevinguegancamillepaviot.pokemon.entity.Types;


import com.kevinguegancamillepaviot.pokemon.PokemonApplication;



/** Types adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypesAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypesSQLiteAdapterBase
                        extends SQLiteAdapter<Types> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypesDBAdapter";


    /**
     * Get the table name used in DB for your Types entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypesContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Types entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypesContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Types entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypesContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypesContract.TABLE_NAME    + " ("
        
         + TypesContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + TypesContract.COL_NOM    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypesSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Types entity to Content Values for database.
     * @param item Types entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Types item) {
        return TypesContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Types entity.
     * @param cursor android.database.Cursor object
     * @return Types entity
     */
    public Types cursorToItem(final android.database.Cursor cursor) {
        return TypesContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Types entity.
     * @param cursor android.database.Cursor object
     * @param result Types entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Types result) {
        TypesContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Types by id in database.
     *
     * @param id Identify of Types
     * @return Types entity
     */
    public Types getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Types result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All Typess entities.
     *
     * @return List of Types entities
     */
    public ArrayList<Types> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Types> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Types entity into database.
     *
     * @param item The Types entity to persist
     * @return Id of the Types entity
     */
    public long insert(final Types item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypesContract.itemToContentValues(item);
        values.remove(TypesContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    TypesContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Types entity into database whether.
     * it already exists or not.
     *
     * @param item The Types entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Types item) {
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
     * Update a Types entity into database.
     *
     * @param item The Types entity to persist
     * @return count of updated entities
     */
    public int update(final Types item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypesContract.itemToContentValues(item);
        final String whereClause =
                 TypesContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Types entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + TypesContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                TypesContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param types The entity to delete
     * @return count of updated entities
     */
    public int delete(final Types types) {
        return this.remove(types.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Types corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                TypesContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                TypesContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Types entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = TypesContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                TypesContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

