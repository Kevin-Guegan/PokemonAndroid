/**************************************************************************
 * TypeObjetsProviderAdapterBase.java, pokemon Android
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



import com.kevinguegancamillepaviot.pokemon.entity.TypeObjets;
import com.kevinguegancamillepaviot.pokemon.provider.ProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeObjetsContract;
import com.kevinguegancamillepaviot.pokemon.data.TypeObjetsSQLiteAdapter;

/**
 * TypeObjetsProviderAdapterBase.
 */
public abstract class TypeObjetsProviderAdapterBase
                extends ProviderAdapter<TypeObjets> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeObjetsProviderAdapter";

    /** TYPEOBJETS_URI. */
    public      static Uri TYPEOBJETS_URI;

    /** typeObjets type. */
    protected static final String typeObjetsType =
            "typeobjets";

    /** TYPEOBJETS_ALL. */
    protected static final int TYPEOBJETS_ALL =
            439363911;
    /** TYPEOBJETS_ONE. */
    protected static final int TYPEOBJETS_ONE =
            439363912;


    /**
     * Static constructor.
     */
    static {
        TYPEOBJETS_URI =
                PokemonProvider.generateUri(
                        typeObjetsType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeObjetsType,
                TYPEOBJETS_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeObjetsType + "/#",
                TYPEOBJETS_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public TypeObjetsProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new TypeObjetsSQLiteAdapter(provider.getContext()));

        this.uriIds.add(TYPEOBJETS_ALL);
        this.uriIds.add(TYPEOBJETS_ONE);
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
            case TYPEOBJETS_ALL:
                result = collection + "typeobjets";
                break;
            case TYPEOBJETS_ONE:
                result = single + "typeobjets";
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
            case TYPEOBJETS_ONE:
                String id = uri.getPathSegments().get(1);
                selection = TypeObjetsContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case TYPEOBJETS_ALL:
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
            case TYPEOBJETS_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(TypeObjetsContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            TYPEOBJETS_URI,
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

            case TYPEOBJETS_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case TYPEOBJETS_ONE:
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
            case TYPEOBJETS_ONE:
                selectionArgs = new String[1];
                selection = TypeObjetsContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case TYPEOBJETS_ALL:
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
        return TYPEOBJETS_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = TypeObjetsContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    TypeObjetsContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

