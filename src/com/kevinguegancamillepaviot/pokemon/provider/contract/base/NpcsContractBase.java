/**************************************************************************
 * NpcsContractBase.java, pokemon Android
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

import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Pokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;
import com.kevinguegancamillepaviot.pokemon.entity.Objets;



import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class NpcsContractBase {


        /** DresseursnpcInternal_id. */
    public static final String COL_DRESSEURSNPCINTERNAL_ID =
            "Dresseurs_npc_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_DRESSEURSNPCINTERNAL_ID =
            NpcsContract.TABLE_NAME + "." + COL_DRESSEURSNPCINTERNAL_ID;

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            NpcsContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            NpcsContract.TABLE_NAME + "." + COL_NOM;

    /** profession. */
    public static final String COL_PROFESSION =
            "profession";
    /** Alias. */
    public static final String ALIASED_COL_PROFESSION =
            NpcsContract.TABLE_NAME + "." + COL_PROFESSION;

    /** texte. */
    public static final String COL_TEXTE =
            "texte";
    /** Alias. */
    public static final String ALIASED_COL_TEXTE =
            NpcsContract.TABLE_NAME + "." + COL_TEXTE;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Npcs";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Npcs";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        NpcsContract.COL_DRESSEURSNPCINTERNAL_ID,
        
        NpcsContract.COL_ID,
        
        NpcsContract.COL_NOM,
        
        NpcsContract.COL_PROFESSION,
        
        NpcsContract.COL_TEXTE,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        NpcsContract.ALIASED_COL_DRESSEURSNPCINTERNAL_ID,
        
        NpcsContract.ALIASED_COL_ID,
        
        NpcsContract.ALIASED_COL_NOM,
        
        NpcsContract.ALIASED_COL_PROFESSION,
        
        NpcsContract.ALIASED_COL_TEXTE,
        
        
        
    };

    /** Convert Npcs entity to Content Values for database.
     *
     * @param item Npcs entity object
     * @param dresseursId dresseurs id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final Npcs item,
                final int dresseursnpcInternalId) {
        final ContentValues result = NpcsContract.itemToContentValues(item);
        result.put(NpcsContract.COL_DRESSEURSNPCINTERNAL_ID,
                String.valueOf(dresseursnpcInternalId));
        return result;
    }

    /**
     * Converts a Npcs into a content values.
     *
     * @param item The Npcs to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Npcs item) {
        final ContentValues result = new ContentValues();

              result.put(NpcsContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(NpcsContract.COL_NOM,
                    item.getNom());
            }

             if (item.getProfession() != null) {
                result.put(NpcsContract.COL_PROFESSION,
                    item.getProfession());
            }

             if (item.getTexte() != null) {
                result.put(NpcsContract.COL_TEXTE,
                    item.getTexte());
            }

   
        return result;
    }

    /**
     * Converts a Cursor into a Npcs.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Npcs
     */
    public static Npcs cursorToItem(final android.database.Cursor cursor) {
        Npcs result = new Npcs();
        NpcsContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Npcs entity.
     * @param cursor Cursor object
     * @param result Npcs entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Npcs result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(NpcsContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(NpcsContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(NpcsContract.COL_PROFESSION);

            if (index > -1) {
                result.setProfession(cursor.getString(index));
            }
            index = cursor.getColumnIndex(NpcsContract.COL_TEXTE);

            if (index > -1) {
                result.setTexte(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Npcs entity.
     * @param cursor Cursor object
     * @return Array of Npcs entity
     */
    public static ArrayList<Npcs> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Npcs> result = new ArrayList<Npcs>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Npcs item;
            do {
                item = NpcsContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
