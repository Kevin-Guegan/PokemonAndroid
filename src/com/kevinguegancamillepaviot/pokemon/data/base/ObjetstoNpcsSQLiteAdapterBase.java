
/**************************************************************************
 * ObjetstoNpcsSQLiteAdapterBase.java, pokemon Android
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


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.kevinguegancamillepaviot.pokemon.data.SQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.ObjetstoNpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.NpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.ObjetsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetstoNpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetsContract;
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Objets;


import com.kevinguegancamillepaviot.pokemon.PokemonApplication;

import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion.Type;
import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression.GroupType;
import com.kevinguegancamillepaviot.pokemon.criterias.base.value.SelectValue;


/** ObjetstoNpcs adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ObjetstoNpcsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ObjetstoNpcsSQLiteAdapterBase
                        extends SQLiteAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ObjetstoNpcsDBAdapter";


    /**
     * Get the table name used in DB for your ObjetstoNpcs entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ObjetstoNpcsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your ObjetstoNpcs entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ObjetstoNpcsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the ObjetstoNpcs entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ObjetstoNpcsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ObjetstoNpcsContract.TABLE_NAME    + " ("
        
         + ObjetstoNpcsContract.COL_NPCSINTERNALID_ID    + " INTEGER NOT NULL,"
         + ObjetstoNpcsContract.COL_OBJET_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + ObjetstoNpcsContract.COL_NPCSINTERNALID_ID + ") REFERENCES " 
             + NpcsContract.TABLE_NAME 
                + " (" + NpcsContract.COL_ID + "),"
         + "FOREIGN KEY(" + ObjetstoNpcsContract.COL_OBJET_ID + ") REFERENCES " 
             + ObjetsContract.TABLE_NAME 
                + " (" + ObjetsContract.COL_ID + ")"
        + ", PRIMARY KEY (" + ObjetstoNpcsContract.COL_NPCSINTERNALID_ID + "," + ObjetstoNpcsContract.COL_OBJET_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ObjetstoNpcsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }


    /**
     * Query the DB to find a ObjetstoNpcs entity.
     *
     * @param NpcsInternalId The NpcsInternalId of the entity to get from the DB
     * @param objet The objet of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final Npcs NpcsInternalId,
                final Objets objet) {

        String selection = ObjetstoNpcsContract.ALIASED_COL_NPCSINTERNALID_ID + " = ?";
        selection += " AND ";
        selection += ObjetstoNpcsContract.ALIASED_COL_OBJET_ID + " = ?";
        

        String[] selectionArgs = new String[2];
        selectionArgs[0] = String.valueOf(NpcsInternalId.getId());
        selectionArgs[1] = String.valueOf(objet.getId());

        return this.query(
                ObjetstoNpcsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }



    /**
     * Insert a ObjetstoNpcs entity into database.
     *
     * @param npcsinternalid npcsinternalid
     * @param objet objet
     * @return Id of the ObjetstoNpcs entity
     */
    public long insert(final int npcsInternalIdId,
            final int objetId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ObjetstoNpcsContract.TABLE_NAME + ")");
        }

        ContentValues values = new ContentValues();
        values.put(ObjetstoNpcsContract.COL_NPCSINTERNALID_ID,
                npcsInternalIdId);
        values.put(ObjetstoNpcsContract.COL_OBJET_ID,
                objetId);

        return this.mDatabase.insert(
                ObjetstoNpcsContract.TABLE_NAME,
                null,
                values);
    }


    /**
     * Find & read ObjetstoNpcs by NpcsInternalId.
     * @param npcsinternalid objet
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Objets matching npcsinternalid
     */
    public android.database.Cursor getByNpcsInternalId(
            final int npcsInternalIdId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(ObjetstoNpcsContract.COL_NPCSINTERNALID_ID,
                String.valueOf(npcsInternalIdId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(ObjetstoNpcsContract.COL_OBJET_ID);
        value.setRefTable(ObjetstoNpcsContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression objetsCrit = new CriteriaExpression(GroupType.AND);
        Criterion objetsSelectCrit = new Criterion();
        objetsSelectCrit.setKey(ObjetsContract.ALIASED_COL_ID);
        objetsSelectCrit.setType(Type.IN);
        objetsSelectCrit.addValue(value);
        objetsCrit.add(objetsSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = objetsCrit.toSQLiteSelection();
            selectionArgs = objetsCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + objetsCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        objetsCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(ObjetsContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }

    /**
     * Find & read ObjetstoNpcs by objet.
     * @param objet npcsinternalid
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Npcs matching objet
     */
    public android.database.Cursor getByObjet(
            final int objetId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(ObjetstoNpcsContract.COL_OBJET_ID,
                String.valueOf(objetId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(ObjetstoNpcsContract.COL_NPCSINTERNALID_ID);
        value.setRefTable(ObjetstoNpcsContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression npcsCrit = new CriteriaExpression(GroupType.AND);
        Criterion npcsSelectCrit = new Criterion();
        npcsSelectCrit.setKey(NpcsContract.ALIASED_COL_ID);
        npcsSelectCrit.setType(Type.IN);
        npcsSelectCrit.addValue(value);
        npcsCrit.add(npcsSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = npcsCrit.toSQLiteSelection();
            selectionArgs = npcsCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + npcsCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        npcsCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(NpcsContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }


    @Override
    public Void cursorToItem(android.database.Cursor c) {
        return null;
    }

    @Override
    public ContentValues itemToContentValues(Void item) {
        return null;
    }

    @Override
    public long insert(Void item) {
        return -1;
    }

    @Override
    public int update(Void item) {
        return 0;
    }

    @Override
    public int delete(Void item) {
        return 0;
    }

}

