
/**************************************************************************
 * NpcsSQLiteAdapterBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.data.NpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.PokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.NpcstoBadgesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.BadgesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.ObjetstoNpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.ObjetsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.DresseursContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcstoBadgesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.BadgesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetstoNpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetsContract;
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Pokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;
import com.kevinguegancamillepaviot.pokemon.entity.Objets;


import com.kevinguegancamillepaviot.pokemon.PokemonApplication;



/** Npcs adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit NpcsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class NpcsSQLiteAdapterBase
                        extends SQLiteAdapter<Npcs> {

    /** TAG for debug purpose. */
    protected static final String TAG = "NpcsDBAdapter";


    /**
     * Get the table name used in DB for your Npcs entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return NpcsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Npcs entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = NpcsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Npcs entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return NpcsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + NpcsContract.TABLE_NAME    + " ("
        
         + NpcsContract.COL_DRESSEURSNPCINTERNAL_ID    + " INTEGER,"
         + NpcsContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + NpcsContract.COL_NOM    + " VARCHAR NOT NULL,"
         + NpcsContract.COL_PROFESSION    + " VARCHAR NOT NULL,"
         + NpcsContract.COL_TEXTE    + " VARCHAR NOT NULL,"

        
         + "FOREIGN KEY(" + NpcsContract.COL_DRESSEURSNPCINTERNAL_ID + ") REFERENCES " 
             + DresseursContract.TABLE_NAME 
                + " (" + DresseursContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public NpcsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Npcs entity to Content Values for database.
     * @param item Npcs entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Npcs item) {
        return NpcsContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Npcs entity.
     * @param cursor android.database.Cursor object
     * @return Npcs entity
     */
    public Npcs cursorToItem(final android.database.Cursor cursor) {
        return NpcsContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Npcs entity.
     * @param cursor android.database.Cursor object
     * @param result Npcs entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Npcs result) {
        NpcsContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Npcs by id in database.
     *
     * @param id Identify of Npcs
     * @return Npcs entity
     */
    public Npcs getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Npcs result = this.cursorToItem(cursor);
        cursor.close();

        final PokemonsSQLiteAdapter pokemonAdapter =
                new PokemonsSQLiteAdapter(this.ctx);
        pokemonAdapter.open(this.mDatabase);
        android.database.Cursor pokemonCursor = pokemonAdapter
                    .getByNpcspokemonInternal(
                            result.getId(),
                            PokemonsContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setPokemon(pokemonAdapter.cursorToItems(pokemonCursor));

        pokemonCursor.close();
        NpcstoBadgesSQLiteAdapter npcstobadgesAdapter =
                new NpcstoBadgesSQLiteAdapter(this.ctx);
        npcstobadgesAdapter.open(this.mDatabase);
        android.database.Cursor badgeCursor = npcstobadgesAdapter.getByNpcsInternalId(
                            result.getId(),
                            BadgesContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setBadge(new BadgesSQLiteAdapter(ctx).cursorToItems(badgeCursor));

        badgeCursor.close();
        ObjetstoNpcsSQLiteAdapter objetstonpcsAdapter =
                new ObjetstoNpcsSQLiteAdapter(this.ctx);
        objetstonpcsAdapter.open(this.mDatabase);
        android.database.Cursor objetCursor = objetstonpcsAdapter.getByNpcsInternalId(
                            result.getId(),
                            ObjetsContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setObjet(new ObjetsSQLiteAdapter(ctx).cursorToItems(objetCursor));

        objetCursor.close();
        return result;
    }

    /**
     * Find & read Npcs by DresseursnpcInternal.
     * @param dresseursnpcinternalId dresseursnpcinternalId
     * @param orderBy Order by string (can be null)
     * @return List of Npcs entities
     */
     public android.database.Cursor getByDresseursnpcInternal(final int dresseursnpcinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = NpcsContract.COL_DRESSEURSNPCINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(dresseursnpcinternalId);
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
     * Read All Npcss entities.
     *
     * @return List of Npcs entities
     */
    public ArrayList<Npcs> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Npcs> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Npcs entity into database.
     *
     * @param item The Npcs entity to persist
     * @return Id of the Npcs entity
     */
    public long insert(final Npcs item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + NpcsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                NpcsContract.itemToContentValues(item, 0);
        values.remove(NpcsContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    NpcsContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getPokemon() != null) {
            PokemonsSQLiteAdapterBase pokemonAdapter =
                    new PokemonsSQLiteAdapter(this.ctx);
            pokemonAdapter.open(this.mDatabase);
            for (Pokemons pokemons
                        : item.getPokemon()) {
                pokemonAdapter.insertOrUpdateWithNpcsPokemon(
                                    pokemons,
                                    insertResult);
            }
        }
        if (item.getBadge() != null) {
            NpcstoBadgesSQLiteAdapterBase badgeAdapter =
                    new NpcstoBadgesSQLiteAdapter(this.ctx);
            badgeAdapter.open(this.mDatabase);
            for (Badges i : item.getBadge()) {
                badgeAdapter.insert(insertResult,
                        i.getId());
            }
        }
        if (item.getObjet() != null) {
            ObjetstoNpcsSQLiteAdapterBase objetAdapter =
                    new ObjetstoNpcsSQLiteAdapter(this.ctx);
            objetAdapter.open(this.mDatabase);
            for (Objets i : item.getObjet()) {
                objetAdapter.insert(insertResult,
                        i.getId());
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Npcs entity into database whether.
     * it already exists or not.
     *
     * @param item The Npcs entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Npcs item) {
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
     * Update a Npcs entity into database.
     *
     * @param item The Npcs entity to persist
     * @return count of updated entities
     */
    public int update(final Npcs item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + NpcsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                NpcsContract.itemToContentValues(item, 0);
        final String whereClause =
                 NpcsContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a Npcs entity into database.
     *
     * @param item The Npcs entity to persist
     * @param dresseursId The dresseurs id
     * @return count of updated entities
     */
    public int updateWithDresseursNpc(
                    Npcs item,
                    int dresseursnpcInternalId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + NpcsContract.TABLE_NAME + ")");
        }

        ContentValues values =
                NpcsContract.itemToContentValues(item);
        values.put(
                NpcsContract.COL_DRESSEURSNPCINTERNAL_ID,
                dresseursnpcInternalId);
        String whereClause =
                 NpcsContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a Npcs entity into database whether.
     * it already exists or not.
     *
     * @param item The Npcs entity to persist
     * @param dresseursId The dresseurs id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithDresseursNpc(
            Npcs item, int dresseursId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithDresseursNpc(item,
                    dresseursId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithDresseursNpc(item,
                    dresseursId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a Npcs entity into database.
     *
     * @param item The Npcs entity to persist
     * @param dresseursId The dresseurs id
     * @return Id of the Npcs entity
     */
    public long insertWithDresseursNpc(
            Npcs item, int dresseursId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + NpcsContract.TABLE_NAME + ")");
        }

        ContentValues values = NpcsContract.itemToContentValues(item,
                dresseursId);
        values.remove(NpcsContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);

        PokemonsSQLiteAdapter pokemonAdapter =
                new PokemonsSQLiteAdapter(this.ctx);
        pokemonAdapter.open(this.mDatabase);
        if (item.getPokemon() != null) {
            for (Pokemons pokemons : item.getPokemon()) {
                pokemonAdapter.updateWithNpcsPokemon(
                        pokemons, newid);
            }
        }

        NpcstoBadgesSQLiteAdapter badgeAdapter =
                new NpcstoBadgesSQLiteAdapter(this.ctx);
        for (Badges i : item.getBadge()) {
            badgeAdapter.insert(newid, i.getId());
        }

        ObjetstoNpcsSQLiteAdapter objetAdapter =
                new ObjetstoNpcsSQLiteAdapter(this.ctx);
        for (Objets i : item.getObjet()) {
            objetAdapter.insert(newid, i.getId());
        }

        return newid;
    }


    /**
     * Delete a Npcs entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + NpcsContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                NpcsContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param npcs The entity to delete
     * @return count of updated entities
     */
    public int delete(final Npcs npcs) {
        return this.remove(npcs.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Npcs corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                NpcsContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                NpcsContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Npcs entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = NpcsContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                NpcsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

