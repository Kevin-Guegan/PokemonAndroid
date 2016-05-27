/**************************************************************************
 * TypesProviderUtilsBase.java, pokemon Android
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

import com.kevinguegancamillepaviot.pokemon.entity.Types;
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Attaques;

import com.kevinguegancamillepaviot.pokemon.provider.TypesProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.AttaquesProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.AttaquesContract;

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

        if (item.getTypeDePokemon() != null && item.getTypeDePokemon().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(TypeDePokemonsContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getTypeDePokemon().size(); i++) {
                inValue.addValue(String.valueOf(item.getTypeDePokemon().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI)
                    .withValueBackReference(
                            TypeDePokemonsContract
                                    .COL_TYPESTYPEDEPOKEMONINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getAttaque() != null && item.getAttaque().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(AttaquesContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getAttaque().size(); i++) {
                inValue.addValue(String.valueOf(item.getAttaque().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(AttaquesProviderAdapter.ATTAQUES_URI)
                    .withValueBackReference(
                            AttaquesContract
                                    .COL_TYPESATTAQUEINTERNAL_ID,
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

            result.setTypeDePokemon(
                this.getAssociateTypeDePokemon(result));
            result.setAttaque(
                this.getAssociateAttaque(result));
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


        if (item.getTypeDePokemon() != null && item.getTypeDePokemon().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new typeDePokemon for Types
            CriteriaExpression typeDePokemonCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(TypeDePokemonsContract.COL_ID);
            crit.addValue(values);
            typeDePokemonCrit.add(crit);


            for (TypeDePokemons typeDePokemon : item.getTypeDePokemon()) {
                values.addValue(
                    String.valueOf(typeDePokemon.getId()));
            }
            selection = typeDePokemonCrit.toSQLiteSelection();
            selectionArgs = typeDePokemonCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI)
                    .withValue(
                            TypeDePokemonsContract.COL_TYPESTYPEDEPOKEMONINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated typeDePokemon
            crit.setType(Type.NOT_IN);
            typeDePokemonCrit.add(TypeDePokemonsContract.COL_TYPESTYPEDEPOKEMONINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI)
                    .withValue(
                            TypeDePokemonsContract.COL_TYPESTYPEDEPOKEMONINTERNAL_ID,
                            null)
                    .withSelection(
                            typeDePokemonCrit.toSQLiteSelection(),
                            typeDePokemonCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getAttaque() != null && item.getAttaque().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new attaque for Types
            CriteriaExpression attaqueCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(AttaquesContract.COL_ID);
            crit.addValue(values);
            attaqueCrit.add(crit);


            for (Attaques attaque : item.getAttaque()) {
                values.addValue(
                    String.valueOf(attaque.getId()));
            }
            selection = attaqueCrit.toSQLiteSelection();
            selectionArgs = attaqueCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    AttaquesProviderAdapter.ATTAQUES_URI)
                    .withValue(
                            AttaquesContract.COL_TYPESATTAQUEINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated attaque
            crit.setType(Type.NOT_IN);
            attaqueCrit.add(AttaquesContract.COL_TYPESATTAQUEINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    AttaquesProviderAdapter.ATTAQUES_URI)
                    .withValue(
                            AttaquesContract.COL_TYPESATTAQUEINTERNAL_ID,
                            null)
                    .withSelection(
                            attaqueCrit.toSQLiteSelection(),
                            attaqueCrit.toSQLiteSelectionArgs())
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
     * Get associate TypeDePokemon.
     * @param item Types
     * @return TypeDePokemons
     */
    public ArrayList<TypeDePokemons> getAssociateTypeDePokemon(
            final Types item) {
        ArrayList<TypeDePokemons> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typeDePokemonsCursor = prov.query(
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
                TypeDePokemonsContract.ALIASED_COLS,
                TypeDePokemonsContract.ALIASED_COL_TYPESTYPEDEPOKEMONINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = TypeDePokemonsContract.cursorToItems(
                        typeDePokemonsCursor);
        typeDePokemonsCursor.close();

        return result;
    }

    /**
     * Get associate Attaque.
     * @param item Types
     * @return Attaques
     */
    public ArrayList<Attaques> getAssociateAttaque(
            final Types item) {
        ArrayList<Attaques> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor attaquesCursor = prov.query(
                AttaquesProviderAdapter.ATTAQUES_URI,
                AttaquesContract.ALIASED_COLS,
                AttaquesContract.ALIASED_COL_TYPESATTAQUEINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = AttaquesContract.cursorToItems(
                        attaquesCursor);
        attaquesCursor.close();

        return result;
    }

}
