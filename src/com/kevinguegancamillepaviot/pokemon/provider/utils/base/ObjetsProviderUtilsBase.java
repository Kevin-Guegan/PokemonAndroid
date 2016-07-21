/**************************************************************************
 * ObjetsProviderUtilsBase.java, pokemon Android
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

import com.kevinguegancamillepaviot.pokemon.entity.Objets;
import com.kevinguegancamillepaviot.pokemon.entity.TypeObjets;

import com.kevinguegancamillepaviot.pokemon.provider.ObjetsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.TypeObjetsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeObjetsContract;

/**
 * Objets Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ObjetsProviderUtilsBase
            extends ProviderUtils<Objets> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "ObjetsProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public ObjetsProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Objets item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = ObjetsContract.itemToContentValues(item);
        itemValues.remove(ObjetsContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                ObjetsProviderAdapter.OBJETS_URI)
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
     * @param item Objets
     * @return number of row affected
     */
    public int delete(final Objets item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = ObjetsProviderAdapter.OBJETS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Objets
     */
    public Objets query(final Objets item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Objets
     */
    public Objets query(final int id) {
        Objets result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(ObjetsContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            ObjetsProviderAdapter.OBJETS_URI,
            ObjetsContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = ObjetsContract.cursorToItem(cursor);

            if (result.getObjet() != null) {
                result.setObjet(
                    this.getAssociateObjet(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Objets>
     */
    public ArrayList<Objets> queryAll() {
        ArrayList<Objets> result =
                    new ArrayList<Objets>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ObjetsProviderAdapter.OBJETS_URI,
                ObjetsContract.ALIASED_COLS,
                null,
                null,
                null);

        result = ObjetsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Objets>
     */
    public ArrayList<Objets> query(CriteriaExpression expression) {
        ArrayList<Objets> result =
                    new ArrayList<Objets>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ObjetsProviderAdapter.OBJETS_URI,
                ObjetsContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = ObjetsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Objets
     
     * @return number of rows updated
     */
    public int update(final Objets item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = ObjetsContract.itemToContentValues(
                item);

        Uri uri = ObjetsProviderAdapter.OBJETS_URI;
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

    /** Relations operations. */
    /**
     * Get associate Objet.
     * @param item Objets
     * @return TypeObjets
     */
    public TypeObjets getAssociateObjet(
            final Objets item) {
        TypeObjets result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typeObjetsCursor = prov.query(
                TypeObjetsProviderAdapter.TYPEOBJETS_URI,
                TypeObjetsContract.ALIASED_COLS,
                TypeObjetsContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getObjet().getId())},
                null);

        if (typeObjetsCursor.getCount() > 0) {
            typeObjetsCursor.moveToFirst();
            result = TypeObjetsContract.cursorToItem(typeObjetsCursor);
        } else {
            result = null;
        }
        typeObjetsCursor.close();

        return result;
    }

}
