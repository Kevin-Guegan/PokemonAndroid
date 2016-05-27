/**************************************************************************
 * DresseursProviderUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.kevinguegancamillepaviot.pokemon.provider.utils.ProviderUtils;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion.Type;
import com.kevinguegancamillepaviot.pokemon.criterias.base.value.ArrayValue;
import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression;
import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression.GroupType;

import com.kevinguegancamillepaviot.pokemon.entity.Dresseurs;
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;

import com.kevinguegancamillepaviot.pokemon.provider.DresseursProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.NpcsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.DresseursContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;

/**
 * Dresseurs Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class DresseursProviderUtilsBase
            extends ProviderUtils<Dresseurs> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "DresseursProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public DresseursProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Dresseurs item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = DresseursContract.itemToContentValues(item);
        itemValues.remove(DresseursContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                DresseursProviderAdapter.DRESSEURS_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getNpc() != null && item.getNpc().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(NpcsContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getNpc().size(); i++) {
                inValue.addValue(String.valueOf(item.getNpc().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(NpcsProviderAdapter.NPCS_URI)
                    .withValueBackReference(
                            NpcsContract
                                    .COL_DRESSEURSNPCINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }

        try {
            ContentProviderResult[] results =
                    prov.applyBatch(PokemonProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }


    /**
     * Delete from DB.
     * @param item Dresseurs
     * @return number of row affected
     */
    public int delete(final Dresseurs item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = DresseursProviderAdapter.DRESSEURS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Dresseurs
     */
    public Dresseurs query(final Dresseurs item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Dresseurs
     */
    public Dresseurs query(final int id) {
        Dresseurs result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(DresseursContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            DresseursProviderAdapter.DRESSEURS_URI,
            DresseursContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = DresseursContract.cursorToItem(cursor);

            result.setNpc(
                this.getAssociateNpc(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Dresseurs>
     */
    public ArrayList<Dresseurs> queryAll() {
        ArrayList<Dresseurs> result =
                    new ArrayList<Dresseurs>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                DresseursProviderAdapter.DRESSEURS_URI,
                DresseursContract.ALIASED_COLS,
                null,
                null,
                null);

        result = DresseursContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Dresseurs>
     */
    public ArrayList<Dresseurs> query(CriteriaExpression expression) {
        ArrayList<Dresseurs> result =
                    new ArrayList<Dresseurs>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                DresseursProviderAdapter.DRESSEURS_URI,
                DresseursContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = DresseursContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Dresseurs
     
     * @return number of rows updated
     */
    public int update(final Dresseurs item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = DresseursContract.itemToContentValues(
                item);

        Uri uri = DresseursProviderAdapter.DRESSEURS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getNpc() != null && item.getNpc().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new npc for Dresseurs
            CriteriaExpression npcCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(NpcsContract.COL_ID);
            crit.addValue(values);
            npcCrit.add(crit);


            for (Npcs npc : item.getNpc()) {
                values.addValue(
                    String.valueOf(npc.getId()));
            }
            selection = npcCrit.toSQLiteSelection();
            selectionArgs = npcCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    NpcsProviderAdapter.NPCS_URI)
                    .withValue(
                            NpcsContract.COL_DRESSEURSNPCINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated npc
            crit.setType(Type.NOT_IN);
            npcCrit.add(NpcsContract.COL_DRESSEURSNPCINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    NpcsProviderAdapter.NPCS_URI)
                    .withValue(
                            NpcsContract.COL_DRESSEURSNPCINTERNAL_ID,
                            null)
                    .withSelection(
                            npcCrit.toSQLiteSelection(),
                            npcCrit.toSQLiteSelectionArgs())
                    .build());
        }


        try {
            ContentProviderResult[] results = prov.applyBatch(PokemonProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /** Relations operations. */
    /**
     * Get associate Npc.
     * @param item Dresseurs
     * @return Npcs
     */
    public ArrayList<Npcs> getAssociateNpc(
            final Dresseurs item) {
        ArrayList<Npcs> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor npcsCursor = prov.query(
                NpcsProviderAdapter.NPCS_URI,
                NpcsContract.ALIASED_COLS,
                NpcsContract.ALIASED_COL_DRESSEURSNPCINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = NpcsContract.cursorToItems(
                        npcsCursor);
        npcsCursor.close();

        return result;
    }

}
