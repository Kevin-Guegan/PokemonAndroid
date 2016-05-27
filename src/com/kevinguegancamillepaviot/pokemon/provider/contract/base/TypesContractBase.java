/**************************************************************************
 * TypesContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.kevinguegancamillepaviot.pokemon.entity.Types;
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Attaques;



import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypesContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            TypesContract.TABLE_NAME + "." + COL_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Types";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Types";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypesContract.COL_ID,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypesContract.ALIASED_COL_ID,
        
        
    };


    /**
     * Converts a Types into a content values.
     *
     * @param item The Types to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Types item) {
        final ContentValues result = new ContentValues();

             result.put(TypesContract.COL_ID,
                String.valueOf(item.getId()));

  
        return result;
    }

    /**
     * Converts a Cursor into a Types.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Types
     */
    public static Types cursorToItem(final android.database.Cursor cursor) {
        Types result = new Types();
        TypesContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Types entity.
     * @param cursor Cursor object
     * @param result Types entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Types result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(TypesContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Types entity.
     * @param cursor Cursor object
     * @return Array of Types entity
     */
    public static ArrayList<Types> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Types> result = new ArrayList<Types>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Types item;
            do {
                item = TypesContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
