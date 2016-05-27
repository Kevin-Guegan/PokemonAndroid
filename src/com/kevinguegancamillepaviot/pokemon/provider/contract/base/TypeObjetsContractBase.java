/**************************************************************************
 * TypeObjetsContractBase.java, pokemon Android
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

import com.kevinguegancamillepaviot.pokemon.entity.TypeObjets;



import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeObjetsContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypeObjetsContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            TypeObjetsContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            TypeObjetsContract.TABLE_NAME + "." + COL_NOM;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "TypeObjets";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "TypeObjets";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypeObjetsContract.COL_ID,
        
        TypeObjetsContract.COL_NOM
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypeObjetsContract.ALIASED_COL_ID,
        
        TypeObjetsContract.ALIASED_COL_NOM
    };


    /**
     * Converts a TypeObjets into a content values.
     *
     * @param item The TypeObjets to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final TypeObjets item) {
        final ContentValues result = new ContentValues();

             result.put(TypeObjetsContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(TypeObjetsContract.COL_NOM,
                    item.getNom());
            }


        return result;
    }

    /**
     * Converts a Cursor into a TypeObjets.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted TypeObjets
     */
    public static TypeObjets cursorToItem(final android.database.Cursor cursor) {
        TypeObjets result = new TypeObjets();
        TypeObjetsContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to TypeObjets entity.
     * @param cursor Cursor object
     * @param result TypeObjets entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final TypeObjets result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(TypeObjetsContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeObjetsContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of TypeObjets entity.
     * @param cursor Cursor object
     * @return Array of TypeObjets entity
     */
    public static ArrayList<TypeObjets> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<TypeObjets> result = new ArrayList<TypeObjets>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            TypeObjets item;
            do {
                item = TypeObjetsContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
