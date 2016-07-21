/**************************************************************************
 * TypesProviderUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
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
import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression;
import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression.GroupType;

import com.kevinguegancamillepaviot.pokemon.entity.Types;

import com.kevinguegancamillepaviot.pokemon.provider.TypesProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;

/**
 * Types Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class TypesProviderUtilsBase
            extends ProviderUtils<Types> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "TypesProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public TypesProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Types item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = TypesContract.itemToContentValues(item);
        itemValues.remove(TypesContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                TypesProviderAdapter.TYPES_URI)
                        .withValues(itemValues)
                        .build());


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
     * @param item Types
     * @return number of row affected
     */
    public int delete(final Types item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = TypesProviderAdapter.TYPES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Types
     */
    public Types query(final Types item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Types
     */
    public Types query(final int id) {
        Types result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(TypesContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            TypesProviderAdapter.TYPES_URI,
            TypesContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = TypesContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Types>
     */
    public ArrayList<Types> queryAll() {
        ArrayList<Types> result =
                    new ArrayList<Types>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypesProviderAdapter.TYPES_URI,
                TypesContract.ALIASED_COLS,
                null,
                null,
                null);

        result = TypesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Types>
     */
    public ArrayList<Types> query(CriteriaExpression expression) {
        ArrayList<Types> result =
                    new ArrayList<Types>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypesProviderAdapter.TYPES_URI,
                TypesContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = TypesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Types
     
     * @return number of rows updated
     */
    public int update(final Types item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = TypesContract.itemToContentValues(
                item);

        Uri uri = TypesProviderAdapter.TYPES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());



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

    
}
