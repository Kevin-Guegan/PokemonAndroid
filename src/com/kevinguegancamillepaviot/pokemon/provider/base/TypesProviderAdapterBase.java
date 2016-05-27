/**************************************************************************
 * TypesProviderAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.kevinguegancamillepaviot.pokemon.entity.Types;
import com.kevinguegancamillepaviot.pokemon.provider.ProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.AttaquesContract;
import com.kevinguegancamillepaviot.pokemon.data.TypesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.AttaquesSQLiteAdapter;

/**
 * TypesProviderAdapterBase.
 */
public abstract class TypesProviderAdapterBase
                extends ProviderAdapter<Types> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypesProviderAdapter";

    /** TYPES_URI. */
    public      static Uri TYPES_URI;

    /** types type. */
    protected static final String typesType =
            "types";

    /** TYPES_ALL. */
    protected static final int TYPES_ALL =
            81291353;
    /** TYPES_ONE. */
    protected static final int TYPES_ONE =
            81291354;

    /** TYPES_TYPEDEPOKEMON. */
    protected static final int TYPES_TYPEDEPOKEMON =
            81291355;
    /** TYPES_ATTAQUE. */
    protected static final int TYPES_ATTAQUE =
            81291356;

    /**
     * Static constructor.
     */
    static {
        TYPES_URI =
                PokemonProvider.generateUri(
                        typesType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typesType,
                TYPES_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typesType + "/#",
                TYPES_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typesType + "/#" + "/typedepokemon",
                TYPES_TYPEDEPOKEMON);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typesType + "/#" + "/attaque",
                TYPES_ATTAQUE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public TypesProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new TypesSQLiteAdapter(provider.getContext()));

        this.uriIds.add(TYPES_ALL);
        this.uriIds.add(TYPES_ONE);
        this.uriIds.add(TYPES_TYPEDEPOKEMON);
        this.uriIds.add(TYPES_ATTAQUE);
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
            case TYPES_ALL:
                result = collection + "types";
                break;
            case TYPES_ONE:
                result = single + "types";
                break;
            case TYPES_TYPEDEPOKEMON:
                result = collection + "types";
                break;
            case TYPES_ATTAQUE:
                result = collection + "types";
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
            case TYPES_ONE:
                String id = uri.getPathSegments().get(1);
                selection = TypesContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case TYPES_ALL:
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
            case TYPES_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(TypesContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            TYPES_URI,
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
        int typesId;

        switch (matchedUri) {

            case TYPES_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case TYPES_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case TYPES_TYPEDEPOKEMON:
                typesId = Integer.parseInt(uri.getPathSegments().get(1));
                TypeDePokemonsSQLiteAdapter typeDePokemonAdapter = new TypeDePokemonsSQLiteAdapter(this.ctx);
                typeDePokemonAdapter.open(this.getDb());
                result = typeDePokemonAdapter.getByTypestypeDePokemonInternal(typesId, TypeDePokemonsContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case TYPES_ATTAQUE:
                typesId = Integer.parseInt(uri.getPathSegments().get(1));
                AttaquesSQLiteAdapter attaqueAdapter = new AttaquesSQLiteAdapter(this.ctx);
                attaqueAdapter.open(this.getDb());
                result = attaqueAdapter.getByTypesattaqueInternal(typesId, AttaquesContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case TYPES_ONE:
                selectionArgs = new String[1];
                selection = TypesContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case TYPES_ALL:
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
        return TYPES_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = TypesContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    TypesContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

