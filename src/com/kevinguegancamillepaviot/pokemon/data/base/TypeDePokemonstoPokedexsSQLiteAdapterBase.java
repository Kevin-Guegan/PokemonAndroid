
/**************************************************************************
 * TypeDePokemonstoPokedexsSQLiteAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.kevinguegancamillepaviot.pokemon.data.SQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonstoPokedexsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.PokedexsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonstoPokedexsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokedexsContract;
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;


import com.kevinguegancamillepaviot.pokemon.PokemonApplication;

import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion.Type;
import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression.GroupType;
import com.kevinguegancamillepaviot.pokemon.criterias.base.value.SelectValue;


/** TypeDePokemonstoPokedexs adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypeDePokemonstoPokedexsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypeDePokemonstoPokedexsSQLiteAdapterBase
                        extends SQLiteAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonstoPokedexsDBAdapter";


    /**
     * Get the table name used in DB for your TypeDePokemonstoPokedexs entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypeDePokemonstoPokedexsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your TypeDePokemonstoPokedexs entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypeDePokemonstoPokedexsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the TypeDePokemonstoPokedexs entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypeDePokemonstoPokedexsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypeDePokemonstoPokedexsContract.TABLE_NAME    + " ("
        
         + TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID    + " INTEGER NOT NULL,"
         + TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID + ") REFERENCES " 
             + TypeDePokemonsContract.TABLE_NAME 
                + " (" + TypeDePokemonsContract.COL_ID + "),"
         + "FOREIGN KEY(" + TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID + ") REFERENCES " 
             + PokedexsContract.TABLE_NAME 
                + " (" + PokedexsContract.COL_ID + ")"
        + ", PRIMARY KEY (" + TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID + "," + TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonstoPokedexsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }


    /**
     * Query the DB to find a TypeDePokemonstoPokedexs entity.
     *
     * @param TypeDePokemonsInternalId The TypeDePokemonsInternalId of the entity to get from the DB
     * @param pokedex The pokedex of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final TypeDePokemons TypeDePokemonsInternalId,
                final Pokedexs pokedex) {

        String selection = TypeDePokemonstoPokedexsContract.ALIASED_COL_TYPEDEPOKEMONSINTERNALID_ID + " = ?";
        selection += " AND ";
        selection += TypeDePokemonstoPokedexsContract.ALIASED_COL_POKEDEX_ID + " = ?";
        

        String[] selectionArgs = new String[2];
        selectionArgs[0] = String.valueOf(TypeDePokemonsInternalId.getId());
        selectionArgs[1] = String.valueOf(pokedex.getId());

        return this.query(
                TypeDePokemonstoPokedexsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }



    /**
     * Insert a TypeDePokemonstoPokedexs entity into database.
     *
     * @param typedepokemonsinternalid typedepokemonsinternalid
     * @param pokedex pokedex
     * @return Id of the TypeDePokemonstoPokedexs entity
     */
    public long insert(final int typeDePokemonsInternalIdId,
            final int pokedexId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeDePokemonstoPokedexsContract.TABLE_NAME + ")");
        }

        ContentValues values = new ContentValues();
        values.put(TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID,
                typeDePokemonsInternalIdId);
        values.put(TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID,
                pokedexId);

        return this.mDatabase.insert(
                TypeDePokemonstoPokedexsContract.TABLE_NAME,
                null,
                values);
    }


    /**
     * Find & read TypeDePokemonstoPokedexs by TypeDePokemonsInternalId.
     * @param typedepokemonsinternalid pokedex
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Pokedexs matching typedepokemonsinternalid
     */
    public android.database.Cursor getByTypeDePokemonsInternalId(
            final int typeDePokemonsInternalIdId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID,
                String.valueOf(typeDePokemonsInternalIdId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID);
        value.setRefTable(TypeDePokemonstoPokedexsContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression pokedexsCrit = new CriteriaExpression(GroupType.AND);
        Criterion pokedexsSelectCrit = new Criterion();
        pokedexsSelectCrit.setKey(PokedexsContract.ALIASED_COL_ID);
        pokedexsSelectCrit.setType(Type.IN);
        pokedexsSelectCrit.addValue(value);
        pokedexsCrit.add(pokedexsSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = pokedexsCrit.toSQLiteSelection();
            selectionArgs = pokedexsCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + pokedexsCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        pokedexsCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(PokedexsContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }

    /**
     * Find & read TypeDePokemonstoPokedexs by pokedex.
     * @param pokedex typedepokemonsinternalid
     * @param orderBy Order by string (can be null)
     * @return ArrayList of TypeDePokemons matching pokedex
     */
    public android.database.Cursor getByPokedex(
            final int pokedexId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID,
                String.valueOf(pokedexId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID);
        value.setRefTable(TypeDePokemonstoPokedexsContract.TABLE_NAME);
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

