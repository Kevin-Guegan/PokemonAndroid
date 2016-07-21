/**************************************************************************
 * DresseursContractBase.java, pokemon Android
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

import com.kevinguegancamillepaviot.pokemon.entity.Dresseurs;
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;



import com.kevinguegancamillepaviot.pokemon.provider.contract.DresseursContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class DresseursContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            DresseursContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            DresseursContract.TABLE_NAME + "." + COL_NOM;

    /** login. */
    public static final String COL_LOGIN =
            "login";
    /** Alias. */
    public static final String ALIASED_COL_LOGIN =
            DresseursContract.TABLE_NAME + "." + COL_LOGIN;

    /** password. */
    public static final String COL_PASSWORD =
            "password";
    /** Alias. */
    public static final String ALIASED_COL_PASSWORD =
            DresseursContract.TABLE_NAME + "." + COL_PASSWORD;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Dresseurs";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Dresseurs";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        DresseursContract.COL_ID,
        
        DresseursContract.COL_NOM,
        
        DresseursContract.COL_LOGIN,
        
        DresseursContract.COL_PASSWORD,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        DresseursContract.ALIASED_COL_ID,
        
        DresseursContract.ALIASED_COL_NOM,
        
        DresseursContract.ALIASED_COL_LOGIN,
        
        DresseursContract.ALIASED_COL_PASSWORD,
        
    };


    /**
     * Converts a Dresseurs into a content values.
     *
     * @param item The Dresseurs to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Dresseurs item) {
        final ContentValues result = new ContentValues();

             result.put(DresseursContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(DresseursContract.COL_NOM,
                    item.getNom());
            }

             if (item.getLogin() != null) {
                result.put(DresseursContract.COL_LOGIN,
                    item.getLogin());
            }

             if (item.getPassword() != null) {
                result.put(DresseursContract.COL_PASSWORD,
                    item.getPassword());
            }

 
        return result;
    }

    /**
     * Converts a Cursor into a Dresseurs.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Dresseurs
     */
    public static Dresseurs cursorToItem(final android.database.Cursor cursor) {
        Dresseurs result = new Dresseurs();
        DresseursContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Dresseurs entity.
     * @param cursor Cursor object
     * @param result Dresseurs entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Dresseurs result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(DresseursContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(DresseursContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(DresseursContract.COL_LOGIN);

            if (index > -1) {
                result.setLogin(cursor.getString(index));
            }
            index = cursor.getColumnIndex(DresseursContract.COL_PASSWORD);

            if (index > -1) {
                result.setPassword(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Dresseurs entity.
     * @param cursor Cursor object
     * @return Array of Dresseurs entity
     */
    public static ArrayList<Dresseurs> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Dresseurs> result = new ArrayList<Dresseurs>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Dresseurs item;
            do {
                item = DresseursContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
