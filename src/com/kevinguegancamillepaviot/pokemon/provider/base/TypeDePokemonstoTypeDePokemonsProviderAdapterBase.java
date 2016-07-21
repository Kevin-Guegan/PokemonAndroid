/**************************************************************************
 * TypeDePokemonstoTypeDePokemonsProviderAdapterBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonstoTypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonstoTypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonsSQLiteAdapter;

/**
 * TypeDePokemonstoTypeDePokemonsProviderAdapterBase.
 */
public abstract class TypeDePokemonstoTypeDePokemonsProviderAdapterBase
                extends ProviderAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonstoTypeDePokemonsProviderAdapter";

    /** TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI. */
    public      static Uri TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI;

    /** typeDePokemonstoTypeDePokemons type. */
    protected static final String typeDePokemonstoTypeDePokemonsType =
            "typedepokemonstotypedepokemons";

    /** TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ALL. */
    protected static final int TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ALL =
            1600885135;
    /** TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ONE. */
    protected static final int TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ONE =
            1600885136;

    /** TYPEDEPOKEMONSTOTYPEDEPOKEMONS_TYPEDEPOKEMONSINTERNALID. */
    protected static final int TYPEDEPOKEMONSTOTYPEDEPOKEMONS_TYPEDEPOKEMONSINTERNALID =
            1600885137;
    /** TYPEDEPOKEMONSTOTYPEDEPOKEMONS_TYPEDEPOKEMON. */
    protected static final int TYPEDEPOKEMONSTOTYPEDEPOKEMONS_TYPEDEPOKEMON =
            1600885138;

    /**
     * Static constructor.
     */
    static {
        TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI =
                PokemonProvider.generateUri(
                        typeDePokemonstoTypeDePokemonsType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonstoTypeDePokemonsType,
                TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonstoTypeDePokemonsType + "/#" + "/#",
                TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonstoTypeDePokemonsType + "/#" + "/#" + "/typedepokemonsinternalid",
                TYPEDEPOKEMONSTOTYPEDEPOKEMONS_TYPEDEPOKEMONSINTERNALID);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonstoTypeDePokemonsType + "/#" + "/#" + "/typedepokemon",
                TYPEDEPOKEMONSTOTYPEDEPOKEMONS_TYPEDEPOKEMON);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public TypeDePokemonstoTypeDePokemonsProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new TypeDePokemonstoTypeDePokemonsSQLiteAdapter(provider.getContext()));

        this.uriIds.add(TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ALL);
        this.uriIds.add(TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ONE);
        this.uriIds.add(TYPEDEPOKEMONSTOTYPEDEPOKEMONS_TYPEDEPOKEMONSINTERNALID);
        this.uriIds.add(TYPEDEPOKEMONSTOTYPEDEPOKEMONS_TYPEDEPOKEMON);
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
            case TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ONE:
                String TypeDePokemonsInternalIdId = uri.getPathSegments().get(1);
                String typeDePokemonId = uri.getPathSegments().get(2);
                selection = TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID
                        + " = ?";
                selectionArgs = new String[2];
                selectionArgs[0] = TypeDePokemonsInternalIdId;
                selectionArgs[1] = typeDePokemonId;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ALL:
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
            case TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI,
                            values.getAsString(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID)
                            + "/"
                            + values.getAsString(TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID));
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
        android.database.Cursor typeDePokemonstoTypeDePokemonsCursor;

        switch (matchedUri) {

            case TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ONE:
                result = this.queryById(uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));
                break;

            case TYPEDEPOKEMONSTOTYPEDEPOKEMONS_TYPEDEPOKEMONSINTERNALID:
                typeDePokemonstoTypeDePokemonsCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (typeDePokemonstoTypeDePokemonsCursor.getCount() > 0) {
                    typeDePokemonstoTypeDePokemonsCursor.moveToFirst();
                    int TypeDePokemonsInternalIdId = typeDePokemonstoTypeDePokemonsCursor.getInt(
                            typeDePokemonstoTypeDePokemonsCursor.getColumnIndex(
                                    TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID));

                    TypeDePokemonsSQLiteAdapter typeDePokemonsAdapter = new TypeDePokemonsSQLiteAdapter(this.ctx);
                    typeDePokemonsAdapter.open(this.getDb());
                    result = typeDePokemonsAdapter.query(TypeDePokemonsInternalIdId);
                }
                break;

            case TYPEDEPOKEMONSTOTYPEDEPOKEMONS_TYPEDEPOKEMON:
                typeDePokemonstoTypeDePokemonsCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (typeDePokemonstoTypeDePokemonsCursor.getCount() > 0) {
                    typeDePokemonstoTypeDePokemonsCursor.moveToFirst();
                    int typeDePokemonId = typeDePokemonstoTypeDePokemonsCursor.getInt(
                            typeDePokemonstoTypeDePokemonsCursor.getColumnIndex(
                                    TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID));

                    TypeDePokemonsSQLiteAdapter typeDePokemonsAdapter = new TypeDePokemonsSQLiteAdapter(this.ctx);
                    typeDePokemonsAdapter.open(this.getDb());
                    result = typeDePokemonsAdapter.query(typeDePokemonId);
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
            case TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ONE:
                selectionArgs = new String[2];
                selection = TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                selection += " AND " + TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID + " = ?";
                selectionArgs[1] = uri.getPathSegments().get(2);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case TYPEDEPOKEMONSTOTYPEDEPOKEMONS_ALL:
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
        return TYPEDEPOKEMONSTOTYPEDEPOKEMONS_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String TypeDePokemonsInternalIdId, String typeDePokemonId) {
        android.database.Cursor result = null;
        String selection = TypeDePokemonstoTypeDePokemonsContract.ALIASED_COL_TYPEDEPOKEMONSINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + TypeDePokemonstoTypeDePokemonsContract.ALIASED_COL_TYPEDEPOKEMON_ID
                        + " = ?";

        String[] selectionArgs = new String[2];
        selectionArgs[0] = TypeDePokemonsInternalIdId;
        selectionArgs[1] = typeDePokemonId;
        
        

        result = this.adapter.query(
                    TypeDePokemonstoTypeDePokemonsContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

