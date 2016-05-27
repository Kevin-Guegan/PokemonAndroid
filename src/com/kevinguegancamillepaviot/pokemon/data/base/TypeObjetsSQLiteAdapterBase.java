
/**************************************************************************
 * TypeObjetsSQLiteAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.kevinguegancamillepaviot.pokemon.data.SQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.TypeObjetsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeObjetsContract;
import com.kevinguegancamillepaviot.pokemon.entity.TypeObjets;


import com.kevinguegancamillepaviot.pokemon.PokemonApplication;



/** TypeObjets adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypeObjetsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypeObjetsSQLiteAdapterBase
                        extends SQLiteAdapter<TypeObjets> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeObjetsDBAdapter";


    /**
     * Get the table name used in DB for your TypeObjets entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypeObjetsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your TypeObjets entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypeObjetsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the TypeObjets entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypeObjetsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypeObjetsContract.TABLE_NAME    + " ("
        
         + TypeObjetsContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + TypeObjetsContract.COL_NOM    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeObjetsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert TypeObjets entity to Content Values for database.
     * @param item TypeObjets entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final TypeObjets item) {
        return TypeObjetsContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to TypeObjets entity.
     * @param cursor android.database.Cursor object
     * @return TypeObjets entity
     */
    public TypeObjets cursorToItem(final android.database.Cursor cursor) {
        return TypeObjetsContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to TypeObjets entity.
     * @param cursor android.database.Cursor object
     * @param result TypeObjets entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final TypeObjets result) {
        TypeObjetsContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read TypeObjets by id in database.
     *
     * @param id Identify of TypeObjets
     * @return TypeObjets entity
     */
    public TypeObjets getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final TypeObjets result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All TypeObjetss entities.
     *
     * @return List of TypeObjets entities
     */
    public ArrayList<TypeObjets> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<TypeObjets> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a TypeObjets entity into database.
     *
     * @param item The TypeObjets entity to persist
     * @return Id of the TypeObjets entity
     */
    public long insert(final TypeObjets item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeObjetsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeObjetsContract.itemToContentValues(item);
        values.remove(TypeObjetsContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    TypeObjetsContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a TypeObjets entity into database whether.
     * it already exists or not.
     *
     * @param item The TypeObjets entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final TypeObjets item) {
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
     * Update a TypeObjets entity into database.
     *
     * @param item The TypeObjets entity to persist
     * @return count of updated entities
     */
    public int update(final TypeObjets item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypeObjetsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeObjetsContract.itemToContentValues(item);
        final String whereClause =
                 TypeObjetsContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a TypeObjets entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + TypeObjetsContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                TypeObjetsContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param typeObjets The entity to delete
     * @return count of updated entities
     */
    public int delete(final TypeObjets typeObjets) {
        return this.remove(typeObjets.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the TypeObjets corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                TypeObjetsContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                TypeObjetsContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a TypeObjets entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = TypeObjetsContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                TypeObjetsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

