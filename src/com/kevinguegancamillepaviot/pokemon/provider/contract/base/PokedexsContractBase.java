/**************************************************************************
 * PokedexsContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;



import com.kevinguegancamillepaviot.pokemon.provider.contract.PokedexsContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokedexsContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokedexsContract.TABLE_NAME + "." + COL_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Pokedexs";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Pokedexs";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokedexsContract.COL_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokedexsContract.ALIASED_COL_ID
    };


    /**
     * Converts a Pokedexs into a content values.
     *
     * @param item The Pokedexs to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Pokedexs item) {
        final ContentValues result = new ContentValues();

             result.put(PokedexsContract.COL_ID,
                String.valueOf(item.getId()));


        return result;
    }

    /**
     * Converts a Cursor into a Pokedexs.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Pokedexs
     */
    public static Pokedexs cursorToItem(final android.database.Cursor cursor) {
        Pokedexs result = new Pokedexs();
        PokedexsContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Pokedexs entity.
     * @param cursor Cursor object
     * @param result Pokedexs entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Pokedexs result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokedexsContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Pokedexs entity.
     * @param cursor Cursor object
     * @return Array of Pokedexs entity
     */
    public static ArrayList<Pokedexs> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Pokedexs> result = new ArrayList<Pokedexs>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Pokedexs item;
            do {
                item = PokedexsContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
