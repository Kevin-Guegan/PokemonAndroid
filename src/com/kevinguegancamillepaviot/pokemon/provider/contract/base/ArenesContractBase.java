/**************************************************************************
 * ArenesContractBase.java, pokemon Android
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

import com.kevinguegancamillepaviot.pokemon.entity.Arenes;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;



import com.kevinguegancamillepaviot.pokemon.provider.contract.ArenesContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ArenesContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ArenesContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            ArenesContract.TABLE_NAME + "." + COL_NOM;

    /** badge_id. */
    public static final String COL_BADGE_ID =
            "badge_id";
    /** Alias. */
    public static final String ALIASED_COL_BADGE_ID =
            ArenesContract.TABLE_NAME + "." + COL_BADGE_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Arenes";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Arenes";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        ArenesContract.COL_ID,
        
        ArenesContract.COL_NOM,
        
        ArenesContract.COL_BADGE_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        ArenesContract.ALIASED_COL_ID,
        
        ArenesContract.ALIASED_COL_NOM,
        
        ArenesContract.ALIASED_COL_BADGE_ID
    };


    /**
     * Converts a Arenes into a content values.
     *
     * @param item The Arenes to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Arenes item) {
        final ContentValues result = new ContentValues();

             result.put(ArenesContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(ArenesContract.COL_NOM,
                    item.getNom());
            }

             if (item.getBadge() != null) {
                result.put(ArenesContract.COL_BADGE_ID,
                    item.getBadge().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Arenes.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Arenes
     */
    public static Arenes cursorToItem(final android.database.Cursor cursor) {
        Arenes result = new Arenes();
        ArenesContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Arenes entity.
     * @param cursor Cursor object
     * @param result Arenes entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Arenes result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(ArenesContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(ArenesContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            if (result.getBadge() == null) {
                final Badges badge = new Badges();
                index = cursor.getColumnIndex(ArenesContract.COL_BADGE_ID);

                if (index > -1) {
                    badge.setId(cursor.getInt(index));
                    result.setBadge(badge);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Arenes entity.
     * @param cursor Cursor object
     * @return Array of Arenes entity
     */
    public static ArrayList<Arenes> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Arenes> result = new ArrayList<Arenes>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Arenes item;
            do {
                item = ArenesContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
