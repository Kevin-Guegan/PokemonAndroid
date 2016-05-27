
/**************************************************************************
 * TypeDePokemonstoTypeDePokemonsSQLiteAdapterBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonstoTypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonstoTypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;


import com.kevinguegancamillepaviot.pokemon.PokemonApplication;

import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion.Type;
import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression.GroupType;
import com.kevinguegancamillepaviot.pokemon.criterias.base.value.SelectValue;


/** TypeDePokemonstoTypeDePokemons adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypeDePokemonstoTypeDePokemonsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypeDePokemonstoTypeDePokemonsSQLiteAdapterBase
                        extends SQLiteAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonstoTypeDePokemonsDBAdapter";


    /**
     * Get the table name used in DB for your TypeDePokemonstoTypeDePokemons entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypeDePokemonstoTypeDePokemonsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your TypeDePokemonstoTypeDePokemons entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypeDePokemonstoTypeDePokemonsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the TypeDePokemonstoTypeDePokemons entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypeDePokemonstoTypeDePokemonsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypeDePokemonstoTypeDePokemonsContract.TABLE_NAME    + " ("
        
         + TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID    + " INTEGER NOT NULL,"
         + TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID + ") REFERENCES " 
             + TypeDePokemonsContract.TABLE_NAME 
                + " (" + TypeDePokemonsContract.COL_ID + "),"
         + "FOREIGN KEY(" + TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID + ") REFERENCES " 
             + TypeDePokemonsContract.TABLE_NAME 
                + " (" + TypeDePokemonsContract.COL_ID + ")"
        + ", PRIMARY KEY (" + TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID + "," + TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonstoTypeDePokemonsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }


    /**
     * Query the DB to find a TypeDePokemonstoTypeDePokemons entity.
     *
     * @param TypeDePokemonsInternalId The TypeDePokemonsInternalId of the entity to get from the DB
     * @param typeDePokemon The typeDePokemon of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final TypeDePokemons TypeDePokemonsInternalId,
                final TypeDePokemons typeDePokemon) {

        String selection = TypeDePokemonstoTypeDePokemonsContract.ALIASED_COL_TYPEDEPOKEMONSINTERNALID_ID + " = ?";
        selection += " AND ";
        selection += TypeDePokemonstoTypeDePokemonsContract.ALIASED_COL_TYPEDEPOKEMON_ID + " = ?";
        

        String[] selectionArgs = new String[2];
        selectionArgs[0] = String.valueOf(TypeDePokemonsInternalId.getId());
        selectionArgs[1] = String.valueOf(typeDePokemon.getId());

        return this.query(
                TypeDePokemonstoTypeDePokemonsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }



    /**
     * Insert a TypeDePokemonstoTypeDePokemons entity into database.
     *
     * @param typedepokemonsinternalid typedepokemonsinternalid
     * @param typedepokemon typedepokemon
     * @return Id of the TypeDePokemonstoTypeDePokemons entity
     */
    public long insert(final int typeDePokemonsInternalIdId,
            final int typeDePokemonId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeDePokemonstoTypeDePokemonsContract.TABLE_NAME + ")");
        }

        ContentValues values = new ContentValues();
        values.put(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID,
                typeDePokemonsInternalIdId);
        values.put(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID,
                typeDePokemonId);

        return this.mDatabase.insert(
                TypeDePokemonstoTypeDePokemonsContract.TABLE_NAME,
                null,
                values);
    }


    /**
     * Find & read TypeDePokemonstoTypeDePokemons by TypeDePokemonsInternalId.
     * @param typedepokemonsinternalid typedepokemon
     * @param orderBy Order by string (can be null)
     * @return ArrayList of TypeDePokemons matching typedepokemonsinternalid
     */
    public android.database.Cursor getByTypeDePokemonsInternalId(
            final int typeDePokemonsInternalIdId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID,
                String.valueOf(typeDePokemonsInternalIdId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID);
        value.setRefTable(TypeDePokemonstoTypeDePokemonsContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression typedepokemonsCrit = new CriteriaExpression(GroupType.AND);
        Criterion typedepokemonsSelectCrit = new Criterion();
        typedepokemonsSelectCrit.setKey(TypeDePokemonsContract.ALIASED_COL_ID);
        typedepokemonsSelectCrit.setType(Type.IN);
        typedepokemonsSelectCrit.addValue(value);
        typedepokemonsCrit.add(typedepokemonsSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = typedepokemonsCrit.toSQLiteSelection();
            selectionArgs = typedepokemonsCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + typedepokemonsCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        typedepokemonsCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(TypeDePokemonsContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }

    /**
     * Find & read TypeDePokemonstoTypeDePokemons by typeDePokemon.
     * @param typedepokemon typedepokemonsinternalid
     * @param orderBy Order by string (can be null)
     * @return ArrayList of TypeDePokemons matching typedepokemon
     */
    public android.database.Cursor getByTypeDePokemon(
            final int typeDePokemonId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID,
                String.valueOf(typeDePokemonId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID);
        value.setRefTable(TypeDePokemonstoTypeDePokemonsContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression typedepokemonsCrit = new CriteriaExpression(GroupType.AND);
        Criterion typedepokemonsSelectCrit = new Criterion();
        typedepokemonsSelectCrit.setKey(TypeDePokemonsContract.ALIASED_COL_ID);
        typedepokemonsSelectCrit.setType(Type.IN);
        typedepokemonsSelectCrit.addValue(value);
        typedepokemonsCrit.add(typedepokemonsSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = typedepokemonsCrit.toSQLiteSelection();
            selectionArgs = typedepokemonsCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + typedepokemonsCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        typedepokemonsCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(TypeDePokemonsContract.TABLE_NAME,
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

