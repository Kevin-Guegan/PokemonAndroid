
/**************************************************************************
 * NpcstoBadgesSQLiteAdapterBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.data.NpcstoBadgesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.NpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.BadgesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcstoBadgesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.BadgesContract;
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;


import com.kevinguegancamillepaviot.pokemon.PokemonApplication;

import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion.Type;
import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression.GroupType;
import com.kevinguegancamillepaviot.pokemon.criterias.base.value.SelectValue;


/** NpcstoBadges adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit NpcstoBadgesAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class NpcstoBadgesSQLiteAdapterBase
                        extends SQLiteAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "NpcstoBadgesDBAdapter";


    /**
     * Get the table name used in DB for your NpcstoBadges entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return NpcstoBadgesContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your NpcstoBadges entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = NpcstoBadgesContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the NpcstoBadges entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return NpcstoBadgesContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + NpcstoBadgesContract.TABLE_NAME    + " ("
        
         + NpcstoBadgesContract.COL_NPCSINTERNALID_ID    + " INTEGER NOT NULL,"
         + NpcstoBadgesContract.COL_BADGE_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + NpcstoBadgesContract.COL_NPCSINTERNALID_ID + ") REFERENCES " 
             + NpcsContract.TABLE_NAME 
                + " (" + NpcsContract.COL_ID + "),"
         + "FOREIGN KEY(" + NpcstoBadgesContract.COL_BADGE_ID + ") REFERENCES " 
             + BadgesContract.TABLE_NAME 
                + " (" + BadgesContract.COL_ID + ")"
        + ", PRIMARY KEY (" + NpcstoBadgesContract.COL_NPCSINTERNALID_ID + "," + NpcstoBadgesContract.COL_BADGE_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public NpcstoBadgesSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }


    /**
     * Query the DB to find a NpcstoBadges entity.
     *
     * @param NpcsInternalId The NpcsInternalId of the entity to get from the DB
     * @param badge The badge of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final Npcs NpcsInternalId,
                final Badges badge) {

        String selection = NpcstoBadgesContract.ALIASED_COL_NPCSINTERNALID_ID + " = ?";
        selection += " AND ";
        selection += NpcstoBadgesContract.ALIASED_COL_BADGE_ID + " = ?";
        

        String[] selectionArgs = new String[2];
        selectionArgs[0] = String.valueOf(NpcsInternalId.getId());
        selectionArgs[1] = String.valueOf(badge.getId());

        return this.query(
                NpcstoBadgesContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }



    /**
     * Insert a NpcstoBadges entity into database.
     *
     * @param npcsinternalid npcsinternalid
     * @param badge badge
     * @return Id of the NpcstoBadges entity
     */
    public long insert(final int npcsInternalIdId,
            final int badgeId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + NpcstoBadgesContract.TABLE_NAME + ")");
        }

        ContentValues values = new ContentValues();
        values.put(NpcstoBadgesContract.COL_NPCSINTERNALID_ID,
                npcsInternalIdId);
        values.put(NpcstoBadgesContract.COL_BADGE_ID,
                badgeId);

        return this.mDatabase.insert(
                NpcstoBadgesContract.TABLE_NAME,
                null,
                values);
    }


    /**
     * Find & read NpcstoBadges by NpcsInternalId.
     * @param npcsinternalid badge
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Badges matching npcsinternalid
     */
    public android.database.Cursor getByNpcsInternalId(
            final int npcsInternalIdId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(NpcstoBadgesContract.COL_NPCSINTERNALID_ID,
                String.valueOf(npcsInternalIdId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(NpcstoBadgesContract.COL_BADGE_ID);
        value.setRefTable(NpcstoBadgesContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression badgesCrit = new CriteriaExpression(GroupType.AND);
        Criterion badgesSelectCrit = new Criterion();
        badgesSelectCrit.setKey(BadgesContract.ALIASED_COL_ID);
        badgesSelectCrit.setType(Type.IN);
        badgesSelectCrit.addValue(value);
        badgesCrit.add(badgesSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = badgesCrit.toSQLiteSelection();
            selectionArgs = badgesCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + badgesCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        badgesCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(BadgesContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }

    /**
     * Find & read NpcstoBadges by badge.
     * @param badge npcsinternalid
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Npcs matching badge
     */
    public android.database.Cursor getByBadge(
            final int badgeId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(NpcstoBadgesContract.COL_BADGE_ID,
                String.valueOf(badgeId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(NpcstoBadgesContract.COL_NPCSINTERNALID_ID);
        value.setRefTable(NpcstoBadgesContract.TABLE_NAME);
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

