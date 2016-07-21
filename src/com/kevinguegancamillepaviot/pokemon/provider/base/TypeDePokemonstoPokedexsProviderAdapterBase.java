/**************************************************************************
 * TypeDePokemonstoPokedexsProviderAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.kevinguegancamillepaviot.pokemon.provider.ProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonstoPokedexsContract;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonstoPokedexsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.PokedexsSQLiteAdapter;

/**
 * TypeDePokemonstoPokedexsProviderAdapterBase.
 */
public abstract class TypeDePokemonstoPokedexsProviderAdapterBase
                extends ProviderAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonstoPokedexsProviderAdapter";

    /** TYPEDEPOKEMONSTOPOKEDEXS_URI. */
    public      static Uri TYPEDEPOKEMONSTOPOKEDEXS_URI;

    /** typeDePokemonstoPokedexs type. */
    protected static final String typeDePokemonstoPokedexsType =
            "typedepokemonstopokedexs";

    /** TYPEDEPOKEMONSTOPOKEDEXS_ALL. */
    protected static final int TYPEDEPOKEMONSTOPOKEDEXS_ALL =
            1200937163;
    /** TYPEDEPOKEMONSTOPOKEDEXS_ONE. */
    protected static final int TYPEDEPOKEMONSTOPOKEDEXS_ONE =
            1200937164;

    /** TYPEDEPOKEMONSTOPOKEDEXS_TYPEDEPOKEMONSINTERNALID. */
    protected static final int TYPEDEPOKEMONSTOPOKEDEXS_TYPEDEPOKEMONSINTERNALID =
            1200937165;
    /** TYPEDEPOKEMONSTOPOKEDEXS_POKEDEX. */
    protected static final int TYPEDEPOKEMONSTOPOKEDEXS_POKEDEX =
            1200937166;

    /**
     * Static constructor.
     */
    static {
        TYPEDEPOKEMONSTOPOKEDEXS_URI =
                PokemonProvider.generateUri(
                        typeDePokemonstoPokedexsType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonstoPokedexsType,
                TYPEDEPOKEMONSTOPOKEDEXS_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonstoPokedexsType + "/#" + "/#",
                TYPEDEPOKEMONSTOPOKEDEXS_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonstoPokedexsType + "/#" + "/#" + "/typedepokemonsinternalid",
                TYPEDEPOKEMONSTOPOKEDEXS_TYPEDEPOKEMONSINTERNALID);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonstoPokedexsType + "/#" + "/#" + "/pokedex",
                TYPEDEPOKEMONSTOPOKEDEXS_POKEDEX);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public TypeDePokemonstoPokedexsProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new TypeDePokemonstoPokedexsSQLiteAdapter(provider.getContext()));

        this.uriIds.add(TYPEDEPOKEMONSTOPOKEDEXS_ALL);
        this.uriIds.add(TYPEDEPOKEMONSTOPOKEDEXS_ONE);
        this.uriIds.add(TYPEDEPOKEMONSTOPOKEDEXS_TYPEDEPOKEMONSINTERNALID);
        this.uriIds.add(TYPEDEPOKEMONSTOPOKEDEXS_POKEDEX);
    }

    @Override
    public String getType(final Uri uri) {
        return null;
    }

    @Override
    public int delete(
            final Uri uri,
            String selection,
            String[] selectionArgs) {
        int matchedUri = PokemonProviderBase
                    .getUriMatcher().match(uri);
        int result = -1;
        switch (matchedUri) {
            case TYPEDEPOKEMONSTOPOKEDEXS_ONE:
                String TypeDePokemonsInternalIdId = uri.getPathSegments().get(1);
                String pokedexId = uri.getPathSegments().get(2);
                selection = TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID
                        + " = ?";
                selectionArgs = new String[2];
                selectionArgs[0] = TypeDePokemonsInternalIdId;
                selectionArgs[1] = pokedexId;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case TYPEDEPOKEMONSTOPOKEDEXS_ALL:
                result = this.adapter.delete(
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        int matchedUri = PokemonProviderBase
                .getUriMatcher().match(uri);
                Uri result = null;
        int id = 0;
        switch (matchedUri) {
            case TYPEDEPOKEMONSTOPOKEDEXS_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            TYPEDEPOKEMONSTOPOKEDEXS_URI,
                            values.getAsString(TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID)
                            + "/"
                            + values.getAsString(TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID));
                }
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    public android.database.Cursor query(final Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        final String sortOrder) {

        int matchedUri = PokemonProviderBase.getUriMatcher()
                .match(uri);
        android.database.Cursor result = null;
        android.database.Cursor typeDePokemonstoPokedexsCursor;

        switch (matchedUri) {

            case TYPEDEPOKEMONSTOPOKEDEXS_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case TYPEDEPOKEMONSTOPOKEDEXS_ONE:
                result = this.queryById(uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));
                break;

            case TYPEDEPOKEMONSTOPOKEDEXS_TYPEDEPOKEMONSINTERNALID:
                typeDePokemonstoPokedexsCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (typeDePokemonstoPokedexsCursor.getCount() > 0) {
                    typeDePokemonstoPokedexsCursor.moveToFirst();
                    int TypeDePokemonsInternalIdId = typeDePokemonstoPokedexsCursor.getInt(
                            typeDePokemonstoPokedexsCursor.getColumnIndex(
                                    TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID));

                    TypeDePokemonsSQLiteAdapter typeDePokemonsAdapter = new TypeDePokemonsSQLiteAdapter(this.ctx);
                    typeDePokemonsAdapter.open(this.getDb());
                    result = typeDePokemonsAdapter.query(TypeDePokemonsInternalIdId);
                }
                break;

            case TYPEDEPOKEMONSTOPOKEDEXS_POKEDEX:
                typeDePokemonstoPokedexsCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (typeDePokemonstoPokedexsCursor.getCount() > 0) {
                    typeDePokemonstoPokedexsCursor.moveToFirst();
                    int pokedexId = typeDePokemonstoPokedexsCursor.getInt(
                            typeDePokemonstoPokedexsCursor.getColumnIndex(
                                    TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID));

                    PokedexsSQLiteAdapter pokedexsAdapter = new PokedexsSQLiteAdapter(this.ctx);
                    pokedexsAdapter.open(this.getDb());
                    result = pokedexsAdapter.query(pokedexId);
                }
                break;

            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int update(
            final Uri uri,
            final ContentValues values,
            String selection,
            String[] selectionArgs) {

        
        int matchedUri = PokemonProviderBase.getUriMatcher()
                .match(uri);
        int result = -1;
        switch (matchedUri) {
            case TYPEDEPOKEMONSTOPOKEDEXS_ONE:
                selectionArgs = new String[2];
                selection = TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                selection += " AND " + TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID + " = ?";
                selectionArgs[1] = uri.getPathSegments().get(2);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case TYPEDEPOKEMONSTOPOKEDEXS_ALL:
                result = this.adapter.update(
                            values,
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }



    /**
     * Get the entity URI.
     * @return The URI
     */
    @Override
    public Uri getUri() {
        return TYPEDEPOKEMONSTOPOKEDEXS_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String TypeDePokemonsInternalIdId, String pokedexId) {
        android.database.Cursor result = null;
        String selection = TypeDePokemonstoPokedexsContract.ALIASED_COL_TYPEDEPOKEMONSINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + TypeDePokemonstoPokedexsContract.ALIASED_COL_POKEDEX_ID
                        + " = ?";

        String[] selectionArgs = new String[2];
        selectionArgs[0] = TypeDePokemonsInternalIdId;
        selectionArgs[1] = pokedexId;
        
        

        result = this.adapter.query(
                    TypeDePokemonstoPokedexsContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

