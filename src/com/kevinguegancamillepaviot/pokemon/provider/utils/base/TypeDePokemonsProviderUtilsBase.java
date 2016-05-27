/**************************************************************************
 * TypeDePokemonsProviderUtilsBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Types;
import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;

import com.kevinguegancamillepaviot.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.TypesProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.TypeDePokemonstoTypeDePokemonsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokedexsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonstoTypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokedexsContract;

/**
 * TypeDePokemons Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class TypeDePokemonsProviderUtilsBase
            extends ProviderUtils<TypeDePokemons> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "TypeDePokemonsProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public TypeDePokemonsProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final TypeDePokemons item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = TypeDePokemonsContract.itemToContentValues(item);
        itemValues.remove(TypeDePokemonsContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getTypeDePokemon() != null && item.getTypeDePokemon().size() > 0) {
            for (TypeDePokemons typeDePokemons : item.getTypeDePokemon()) {
                ContentValues typeDePokemonsValues = new ContentValues();
                typeDePokemonsValues.put(
                        TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID,
                        typeDePokemons.getId());

                operations.add(ContentProviderOperation.newInsert(
                    TypeDePokemonstoTypeDePokemonsProviderAdapter.TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI)
                        .withValues(typeDePokemonsValues)
                        .withValueBackReference(
                                TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID,
                                0)
                        .build());

            }
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
     * Insert into DB.
     * @param item TypeDePokemons to insert
     * @param typestypeDePokemonInternalId typestypeDePokemonInternal Id
     * @return number of rows affected
     */
    public Uri insert(final TypeDePokemons item,
                             final int typestypeDePokemonInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = TypeDePokemonsContract.itemToContentValues(item,
                    typestypeDePokemonInternalId);
        itemValues.remove(TypeDePokemonsContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI)
                    .withValues(itemValues)
                    .build());


        if (item.getTypeDePokemon() != null && item.getTypeDePokemon().size() > 0) {
            for (TypeDePokemons typeDePokemons : item.getTypeDePokemon()) {
                ContentValues typeDePokemonsValues = new ContentValues();
                typeDePokemonsValues.put(
                        TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID,
                        typeDePokemons.getId());

                operations.add(ContentProviderOperation.newInsert(
                    TypeDePokemonstoTypeDePokemonsProviderAdapter.TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI)
                        .withValues(typeDePokemonsValues)
                        .withValueBackReference(
                                TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID,
                                0)
                        .build());

            }
        }

        try {
            ContentProviderResult[] results =
                prov.applyBatch(PokemonProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getLastPathSegment()));
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
     * @param item TypeDePokemons
     * @return number of row affected
     */
    public int delete(final TypeDePokemons item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return TypeDePokemons
     */
    public TypeDePokemons query(final TypeDePokemons item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return TypeDePokemons
     */
    public TypeDePokemons query(final int id) {
        TypeDePokemons result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(TypeDePokemonsContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
            TypeDePokemonsContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = TypeDePokemonsContract.cursorToItem(cursor);

            if (result.getType() != null) {
                result.setType(
                    this.getAssociateType(result));
            }
            result.setTypeDePokemon(
                this.getAssociateTypeDePokemon(result));
            if (result.getPokedex() != null) {
                result.setPokedex(
                    this.getAssociatePokedex(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<TypeDePokemons>
     */
    public ArrayList<TypeDePokemons> queryAll() {
        ArrayList<TypeDePokemons> result =
                    new ArrayList<TypeDePokemons>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
                TypeDePokemonsContract.ALIASED_COLS,
                null,
                null,
                null);

        result = TypeDePokemonsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<TypeDePokemons>
     */
    public ArrayList<TypeDePokemons> query(CriteriaExpression expression) {
        ArrayList<TypeDePokemons> result =
                    new ArrayList<TypeDePokemons>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
                TypeDePokemonsContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = TypeDePokemonsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item TypeDePokemons
     * @return number of rows updated
     */
    public int update(final TypeDePokemons item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = TypeDePokemonsContract.itemToContentValues(item);

        Uri uri = TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        operations.add(ContentProviderOperation.newDelete(TypeDePokemonstoTypeDePokemonsProviderAdapter.TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI)
                .withSelection(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (TypeDePokemons typeDePokemons : item.getTypeDePokemon()) {
            ContentValues typeDePokemonsValues = new ContentValues();
            typeDePokemonsValues.put(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID,
                    typeDePokemons.getId());
            typeDePokemonsValues.put(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(TypeDePokemonstoTypeDePokemonsProviderAdapter.TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI)
                    .withValues(typeDePokemonsValues)
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

    /**
     * Updates the DB.
     * @param item TypeDePokemons
     * @param typestypeDePokemonInternalId typestypeDePokemonInternal Id
     * @return number of rows updated
     */
    public int update(final TypeDePokemons item,
                             final int typestypeDePokemonInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = TypeDePokemonsContract.itemToContentValues(
                item,
                typestypeDePokemonInternalId);

        Uri uri = TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        operations.add(ContentProviderOperation.newDelete(TypeDePokemonstoTypeDePokemonsProviderAdapter.TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI)
                .withSelection(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (TypeDePokemons typeDePokemons : item.getTypeDePokemon()) {
            ContentValues typeDePokemonsValues = new ContentValues();
            typeDePokemonsValues.put(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID,
                    typeDePokemons.getId());
            typeDePokemonsValues.put(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(TypeDePokemonstoTypeDePokemonsProviderAdapter.TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI)
                    .withValues(typeDePokemonsValues)
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
     * Get associate Type.
     * @param item TypeDePokemons
     * @return Types
     */
    public Types getAssociateType(
            final TypeDePokemons item) {
        Types result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typesCursor = prov.query(
                TypesProviderAdapter.TYPES_URI,
                TypesContract.ALIASED_COLS,
                TypesContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getType().getId())},
                null);

        if (typesCursor.getCount() > 0) {
            typesCursor.moveToFirst();
            result = TypesContract.cursorToItem(typesCursor);
        } else {
            result = null;
        }
        typesCursor.close();

        return result;
    }

    /**
     * Get associate TypeDePokemon.
     * @param item TypeDePokemons
     * @return TypeDePokemons
     */
    public ArrayList<TypeDePokemons> getAssociateTypeDePokemon(
            final TypeDePokemons item) {
        ArrayList<TypeDePokemons> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typeDePokemonstoTypeDePokemonsCursor = prov.query(
                TypeDePokemonstoTypeDePokemonsProviderAdapter.TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI,
                TypeDePokemonstoTypeDePokemonsContract.ALIASED_COLS,
                TypeDePokemonstoTypeDePokemonsContract.ALIASED_COL_TYPEDEPOKEMONSINTERNALID_ID 
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        if (typeDePokemonstoTypeDePokemonsCursor.getCount() > 0) {
            CriteriaExpression typeDePokemonsCrits =
                    new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            ArrayValue arrayValue = new ArrayValue();
            inCrit.setKey(TypeDePokemonsContract.ALIASED_COL_ID);
            inCrit.setType(Type.IN);
            inCrit.addValue(arrayValue);
            typeDePokemonsCrits.add(inCrit);

            while (typeDePokemonstoTypeDePokemonsCursor.moveToNext()) {
                int index = typeDePokemonstoTypeDePokemonsCursor.getColumnIndex(
                        TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID);
                String typeDePokemonsId = typeDePokemonstoTypeDePokemonsCursor.getString(index);

                arrayValue.addValue(typeDePokemonsId);
            }
            typeDePokemonstoTypeDePokemonsCursor.close();
            android.database.Cursor typeDePokemonsCursor = prov.query(
                    TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
                    TypeDePokemonsContract.ALIASED_COLS,
                    typeDePokemonsCrits.toSQLiteSelection(),
                    typeDePokemonsCrits.toSQLiteSelectionArgs(),
                    null);

            result = TypeDePokemonsContract.cursorToItems(typeDePokemonsCursor);
            typeDePokemonsCursor.close();
        } else {
            result = new ArrayList<TypeDePokemons>();
        }

        return result;
    }

    /**
     * Get associate Pokedex.
     * @param item TypeDePokemons
     * @return Pokedexs
     */
    public Pokedexs getAssociatePokedex(
            final TypeDePokemons item) {
        Pokedexs result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokedexsCursor = prov.query(
                PokedexsProviderAdapter.POKEDEXS_URI,
                PokedexsContract.ALIASED_COLS,
                PokedexsContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getPokedex().getId())},
                null);

        if (pokedexsCursor.getCount() > 0) {
            pokedexsCursor.moveToFirst();
            result = PokedexsContract.cursorToItem(pokedexsCursor);
        } else {
            result = null;
        }
        pokedexsCursor.close();

        return result;
    }

}
