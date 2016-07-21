/**************************************************************************
 * PokedexsProviderAdapterBase.java, pokemon Android
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



import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;
import com.kevinguegancamillepaviot.pokemon.provider.ProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokedexsContract;
import com.kevinguegancamillepaviot.pokemon.data.PokedexsSQLiteAdapter;

/**
 * PokedexsProviderAdapterBase.
 */
public abstract class PokedexsProviderAdapterBase
                extends ProviderAdapter<Pokedexs> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokedexsProviderAdapter";

    /** POKEDEXS_URI. */
    public      static Uri POKEDEXS_URI;

    /** pokedexs type. */
    protected static final String pokedexsType =
            "pokedexs";

    /** POKEDEXS_ALL. */
    protected static final int POKEDEXS_ALL =
            579241589;
    /** POKEDEXS_ONE. */
    protected static final int POKEDEXS_ONE =
            579241590;


    /**
     * Static constructor.
     */
    static {
        POKEDEXS_URI =
                PokemonProvider.generateUri(
                        pokedexsType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokedexsType,
                POKEDEXS_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokedexsType + "/#",
                POKEDEXS_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokedexsProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new PokedexsSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKEDEXS_ALL);
        this.uriIds.add(POKEDEXS_ONE);
    }

    @Override
    public String getType(final Uri uri) {
        String result;
        final String single =
                "vnc.android.cursor.item/"
                    + PokemonProvider.authority + ".";
        final String collection =
                "vnc.android.cursor.collection/"
                    + PokemonProvider.authority + ".";

        int matchedUri = PokemonProviderBase
                .getUriMatcher().match(uri);

        switch (matchedUri) {
            case POKEDEXS_ALL:
                result = collection + "pokedexs";
                break;
            case POKEDEXS_ONE:
                result = single + "pokedexs";
                break;
            default:
                result = null;
                break;
        }

        return result;
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
            case POKEDEXS_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokedexsContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKEDEXS_ALL:
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
            case POKEDEXS_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokedexsContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKEDEXS_URI,
                            String.valueOf(id));
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

        switch (matchedUri) {

            case POKEDEXS_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKEDEXS_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
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
            case POKEDEXS_ONE:
                selectionArgs = new String[1];
                selection = PokedexsContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKEDEXS_ALL:
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
        return POKEDEXS_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokedexsContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokedexsContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

