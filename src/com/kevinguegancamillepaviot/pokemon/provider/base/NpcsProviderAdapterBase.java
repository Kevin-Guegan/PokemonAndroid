/**************************************************************************
 * NpcsProviderAdapterBase.java, pokemon Android
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



import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.provider.ProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.BadgesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetsContract;
import com.kevinguegancamillepaviot.pokemon.data.NpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.PokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.NpcstoBadgesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.BadgesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.ObjetstoNpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.ObjetsSQLiteAdapter;

/**
 * NpcsProviderAdapterBase.
 */
public abstract class NpcsProviderAdapterBase
                extends ProviderAdapter<Npcs> {

    /** TAG for debug purpose. */
    protected static final String TAG = "NpcsProviderAdapter";

    /** NPCS_URI. */
    public      static Uri NPCS_URI;

    /** npcs type. */
    protected static final String npcsType =
            "npcs";

    /** NPCS_ALL. */
    protected static final int NPCS_ALL =
            2434514;
    /** NPCS_ONE. */
    protected static final int NPCS_ONE =
            2434515;

    /** NPCS_POKEMON. */
    protected static final int NPCS_POKEMON =
            2434516;
    /** NPCS_BADGE. */
    protected static final int NPCS_BADGE =
            2434517;
    /** NPCS_OBJET. */
    protected static final int NPCS_OBJET =
            2434518;

    /**
     * Static constructor.
     */
    static {
        NPCS_URI =
                PokemonProvider.generateUri(
                        npcsType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcsType,
                NPCS_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcsType + "/#",
                NPCS_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcsType + "/#" + "/pokemon",
                NPCS_POKEMON);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcsType + "/#" + "/badge",
                NPCS_BADGE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcsType + "/#" + "/objet",
                NPCS_OBJET);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public NpcsProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new NpcsSQLiteAdapter(provider.getContext()));

        this.uriIds.add(NPCS_ALL);
        this.uriIds.add(NPCS_ONE);
        this.uriIds.add(NPCS_POKEMON);
        this.uriIds.add(NPCS_BADGE);
        this.uriIds.add(NPCS_OBJET);
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
            case NPCS_ALL:
                result = collection + "npcs";
                break;
            case NPCS_ONE:
                result = single + "npcs";
                break;
            case NPCS_POKEMON:
                result = collection + "npcs";
                break;
            case NPCS_BADGE:
                result = collection + "npcs";
                break;
            case NPCS_OBJET:
                result = collection + "npcs";
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
            case NPCS_ONE:
                String id = uri.getPathSegments().get(1);
                selection = NpcsContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case NPCS_ALL:
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
            case NPCS_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(NpcsContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            NPCS_URI,
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
        android.database.Cursor npcsCursor;
        int npcsId;

        switch (matchedUri) {

            case NPCS_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case NPCS_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case NPCS_POKEMON:
                npcsId = Integer.parseInt(uri.getPathSegments().get(1));
                PokemonsSQLiteAdapter pokemonAdapter = new PokemonsSQLiteAdapter(this.ctx);
                pokemonAdapter.open(this.getDb());
                result = pokemonAdapter.getByNpcspokemonInternal(npcsId, PokemonsContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case NPCS_BADGE:
                npcsId = Integer.parseInt(uri.getPathSegments().get(1));
                NpcstoBadgesSQLiteAdapter badgeAdapter = new NpcstoBadgesSQLiteAdapter(this.ctx);
                badgeAdapter.open(this.getDb());
                result = badgeAdapter.getByNpcsInternalId(npcsId, BadgesContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case NPCS_OBJET:
                npcsId = Integer.parseInt(uri.getPathSegments().get(1));
                ObjetstoNpcsSQLiteAdapter objetAdapter = new ObjetstoNpcsSQLiteAdapter(this.ctx);
                objetAdapter.open(this.getDb());
                result = objetAdapter.getByNpcsInternalId(npcsId, ObjetsContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case NPCS_ONE:
                selectionArgs = new String[1];
                selection = NpcsContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case NPCS_ALL:
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
        return NPCS_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = NpcsContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    NpcsContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

