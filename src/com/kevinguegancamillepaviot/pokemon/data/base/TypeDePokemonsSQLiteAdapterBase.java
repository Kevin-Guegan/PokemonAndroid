
/**************************************************************************
 * TypeDePokemonsSQLiteAdapterBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.TypesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonstoTypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.PokedexsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonstoTypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokedexsContract;
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Types;
import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;


import com.kevinguegancamillepaviot.pokemon.PokemonApplication;



/** TypeDePokemons adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypeDePokemonsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypeDePokemonsSQLiteAdapterBase
                        extends SQLiteAdapter<TypeDePokemons> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonsDBAdapter";


    /**
     * Get the table name used in DB for your TypeDePokemons entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypeDePokemonsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your TypeDePokemons entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypeDePokemonsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the TypeDePokemons entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypeDePokemonsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypeDePokemonsContract.TABLE_NAME    + " ("
        
         + TypeDePokemonsContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + TypeDePokemonsContract.COL_NOM    + " VARCHAR NOT NULL,"
         + TypeDePokemonsContract.COL_ATTAQUE    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_ATTAQUE_SPE    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_DEFENCE    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_DEFENCE_SPE    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_VITESSE    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_PV    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_TYPE_ID    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_POKEDEX_ID    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_TYPESTYPEDEPOKEMONINTERNAL_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + TypeDePokemonsContract.COL_TYPE_ID + ") REFERENCES " 
             + TypesContract.TABLE_NAME 
                + " (" + TypesContract.COL_ID + "),"
         + "FOREIGN KEY(" + TypeDePokemonsContract.COL_POKEDEX_ID + ") REFERENCES " 
             + PokedexsContract.TABLE_NAME 
                + " (" + PokedexsContract.COL_ID + "),"
         + "FOREIGN KEY(" + TypeDePokemonsContract.COL_TYPESTYPEDEPOKEMONINTERNAL_ID + ") REFERENCES " 
             + TypesContract.TABLE_NAME 
                + " (" + TypesContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert TypeDePokemons entity to Content Values for database.
     * @param item TypeDePokemons entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final TypeDePokemons item) {
        return TypeDePokemonsContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to TypeDePokemons entity.
     * @param cursor android.database.Cursor object
     * @return TypeDePokemons entity
     */
    public TypeDePokemons cursorToItem(final android.database.Cursor cursor) {
        return TypeDePokemonsContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to TypeDePokemons entity.
     * @param cursor android.database.Cursor object
     * @param result TypeDePokemons entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final TypeDePokemons result) {
        TypeDePokemonsContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read TypeDePokemons by id in database.
     *
     * @param id Identify of TypeDePokemons
     * @return TypeDePokemons entity
     */
    public TypeDePokemons getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final TypeDePokemons result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getType() != null) {
            final TypesSQLiteAdapter typeAdapter =
                    new TypesSQLiteAdapter(this.ctx);
            typeAdapter.open(this.mDatabase);

            result.setType(typeAdapter.getByID(
                            result.getType().getId()));
        }
        TypeDePokemonstoTypeDePokemonsSQLiteAdapter typedepokemonstotypedepokemonsAdapter =
                new TypeDePokemonstoTypeDePokemonsSQLiteAdapter(this.ctx);
        typedepokemonstotypedepokemonsAdapter.open(this.mDatabase);
        android.database.Cursor typedepokemonCursor = typedepokemonstotypedepokemonsAdapter.getByTypeDePokemonsInternalId(
                            result.getId(),
                            TypeDePokemonsContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setTypeDePokemon(new TypeDePokemonsSQLiteAdapter(ctx).cursorToItems(typedepokemonCursor));

        typedepokemonCursor.close();
        if (result.getPokedex() != null) {
            final PokedexsSQLiteAdapter pokedexAdapter =
                    new PokedexsSQLiteAdapter(this.ctx);
            pokedexAdapter.open(this.mDatabase);

            result.setPokedex(pokedexAdapter.getByID(
                            result.getPokedex().getId()));
        }
        return result;
    }

    /**
     * Find & read TypeDePokemons by type.
     * @param typeId typeId
     * @param orderBy Order by string (can be null)
     * @return List of TypeDePokemons entities
     */
     public android.database.Cursor getByType(final int typeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = TypeDePokemonsContract.COL_TYPE_ID + "= ?";
        String idSelectionArgs = String.valueOf(typeId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read TypeDePokemons by pokedex.
     * @param pokedexId pokedexId
     * @param orderBy Order by string (can be null)
     * @return List of TypeDePokemons entities
     */
     public android.database.Cursor getByPokedex(final int pokedexId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = TypeDePokemonsContract.COL_POKEDEX_ID + "= ?";
        String idSelectionArgs = String.valueOf(pokedexId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read TypeDePokemons by TypestypeDePokemonInternal.
     * @param typestypedepokemoninternalId typestypedepokemoninternalId
     * @param orderBy Order by string (can be null)
     * @return List of TypeDePokemons entities
     */
     public android.database.Cursor getByTypestypeDePokemonInternal(final int typestypedepokemoninternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = TypeDePokemonsContract.COL_TYPESTYPEDEPOKEMONINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(typestypedepokemoninternalId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }

    /**
     * Read All TypeDePokemonss entities.
     *
     * @return List of TypeDePokemons entities
     */
    public ArrayList<TypeDePokemons> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<TypeDePokemons> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a TypeDePokemons entity into database.
     *
     * @param item The TypeDePokemons entity to persist
     * @return Id of the TypeDePokemons entity
     */
    public long insert(final TypeDePokemons item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeDePokemonsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeDePokemonsContract.itemToContentValues(item, 0);
        values.remove(TypeDePokemonsContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    TypeDePokemonsContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getTypeDePokemon() != null) {
            TypeDePokemonstoTypeDePokemonsSQLiteAdapterBase typeDePokemonAdapter =
                    new TypeDePokemonstoTypeDePokemonsSQLiteAdapter(this.ctx);
            typeDePokemonAdapter.open(this.mDatabase);
            for (TypeDePokemons i : item.getTypeDePokemon()) {
                typeDePokemonAdapter.insert(insertResult,
                        i.getId());
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a TypeDePokemons entity into database whether.
     * it already exists or not.
     *
     * @param item The TypeDePokemons entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final TypeDePokemons item) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.update(item);
        } else {
            // Item doesn't exist => create it
            final long id = this.insert(item);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * Update a TypeDePokemons entity into database.
     *
     * @param item The TypeDePokemons entity to persist
     * @return count of updated entities
     */
    public int update(final TypeDePokemons item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypeDePokemonsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeDePokemonsContract.itemToContentValues(item, 0);
        final String whereClause =
                 TypeDePokemonsContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a TypeDePokemons entity into database.
     *
     * @param item The TypeDePokemons entity to persist
     * @param typesId The types id
     * @return count of updated entities
     */
    public int updateWithTypesTypeDePokemon(
                    TypeDePokemons item,
                    int typestypeDePokemonInternalId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypeDePokemonsContract.TABLE_NAME + ")");
        }

        ContentValues values =
                TypeDePokemonsContract.itemToContentValues(item);
        values.put(
                TypeDePokemonsContract.COL_TYPESTYPEDEPOKEMONINTERNAL_ID,
                typestypeDePokemonInternalId);
        String whereClause =
                 TypeDePokemonsContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a TypeDePokemons entity into database whether.
     * it already exists or not.
     *
     * @param item The TypeDePokemons entity to persist
     * @param typesId The types id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithTypesTypeDePokemon(
            TypeDePokemons item, int typesId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithTypesTypeDePokemon(item,
                    typesId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithTypesTypeDePokemon(item,
                    typesId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a TypeDePokemons entity into database.
     *
     * @param item The TypeDePokemons entity to persist
     * @param typesId The types id
     * @return Id of the TypeDePokemons entity
     */
    public long insertWithTypesTypeDePokemon(
            TypeDePokemons item, int typesId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeDePokemonsContract.TABLE_NAME + ")");
        }

        ContentValues values = TypeDePokemonsContract.itemToContentValues(item,
                typesId);
        values.remove(TypeDePokemonsContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        TypeDePokemonstoTypeDePokemonsSQLiteAdapter typeDePokemonAdapter =
                new TypeDePokemonstoTypeDePokemonsSQLiteAdapter(this.ctx);
        for (TypeDePokemons i : item.getTypeDePokemon()) {
            typeDePokemonAdapter.insert(newid, i.getId());
        }

        return newid;
    }


    /**
     * Delete a TypeDePokemons entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + TypeDePokemonsContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                TypeDePokemonsContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param typeDePokemons The entity to delete
     * @return count of updated entities
     */
    public int delete(final TypeDePokemons typeDePokemons) {
        return this.remove(typeDePokemons.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the TypeDePokemons corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                TypeDePokemonsContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                TypeDePokemonsContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a TypeDePokemons entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = TypeDePokemonsContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                TypeDePokemonsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

