/**************************************************************************
 * NpcstoBadgesProviderAdapterBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcstoBadgesContract;
import com.kevinguegancamillepaviot.pokemon.data.NpcstoBadgesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.NpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.BadgesSQLiteAdapter;

/**
 * NpcstoBadgesProviderAdapterBase.
 */
public abstract class NpcstoBadgesProviderAdapterBase
                extends ProviderAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "NpcstoBadgesProviderAdapter";

    /** NPCSTOBADGES_URI. */
    public      static Uri NPCSTOBADGES_URI;

    /** npcstoBadges type. */
    protected static final String npcstoBadgesType =
            "npcstobadges";

    /** NPCSTOBADGES_ALL. */
    protected static final int NPCSTOBADGES_ALL =
            123010909;
    /** NPCSTOBADGES_ONE. */
    protected static final int NPCSTOBADGES_ONE =
            123010910;

    /** NPCSTOBADGES_NPCSINTERNALID. */
    protected static final int NPCSTOBADGES_NPCSINTERNALID =
            123010911;
    /** NPCSTOBADGES_BADGE. */
    protected static final int NPCSTOBADGES_BADGE =
            123010912;

    /**
     * Static constructor.
     */
    static {
        NPCSTOBADGES_URI =
                PokemonProvider.generateUri(
                        npcstoBadgesType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcstoBadgesType,
                NPCSTOBADGES_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcstoBadgesType + "/#" + "/#",
                NPCSTOBADGES_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcstoBadgesType + "/#" + "/#" + "/npcsinternalid",
                NPCSTOBADGES_NPCSINTERNALID);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcstoBadgesType + "/#" + "/#" + "/badge",
                NPCSTOBADGES_BADGE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public NpcstoBadgesProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new NpcstoBadgesSQLiteAdapter(provider.getContext()));

        this.uriIds.add(NPCSTOBADGES_ALL);
        this.uriIds.add(NPCSTOBADGES_ONE);
        this.uriIds.add(NPCSTOBADGES_NPCSINTERNALID);
        this.uriIds.add(NPCSTOBADGES_BADGE);
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
            case NPCSTOBADGES_ONE:
                String NpcsInternalIdId = uri.getPathSegments().get(1);
                String badgeId = uri.getPathSegments().get(2);
                selection = NpcstoBadgesContract.COL_NPCSINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + NpcstoBadgesContract.COL_BADGE_ID
                        + " = ?";
                selectionArgs = new String[2];
                selectionArgs[0] = NpcsInternalIdId;
                selectionArgs[1] = badgeId;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case NPCSTOBADGES_ALL:
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
            case NPCSTOBADGES_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(NpcstoBadgesContract.COL_NPCSINTERNALID_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            NPCSTOBADGES_URI,
                            values.getAsString(NpcstoBadgesContract.COL_NPCSINTERNALID_ID)
                            + "/"
                            + values.getAsString(NpcstoBadgesContract.COL_BADGE_ID));
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
        android.database.Cursor npcstoBadgesCursor;

        switch (matchedUri) {

            case NPCSTOBADGES_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case NPCSTOBADGES_ONE:
                result = this.queryById(uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));
                break;

            case NPCSTOBADGES_NPCSINTERNALID:
                npcstoBadgesCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (npcstoBadgesCursor.getCount() > 0) {
                    npcstoBadgesCursor.moveToFirst();
                    int NpcsInternalIdId = npcstoBadgesCursor.getInt(
                            npcstoBadgesCursor.getColumnIndex(
                                    NpcstoBadgesContract.COL_NPCSINTERNALID_ID));

                    NpcsSQLiteAdapter npcsAdapter = new NpcsSQLiteAdapter(this.ctx);
                    npcsAdapter.open(this.getDb());
                    result = npcsAdapter.query(NpcsInternalIdId);
                }
                break;

            case NPCSTOBADGES_BADGE:
                npcstoBadgesCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (npcstoBadgesCursor.getCount() > 0) {
                    npcstoBadgesCursor.moveToFirst();
                    int badgeId = npcstoBadgesCursor.getInt(
                            npcstoBadgesCursor.getColumnIndex(
                                    NpcstoBadgesContract.COL_BADGE_ID));

                    BadgesSQLiteAdapter badgesAdapter = new BadgesSQLiteAdapter(this.ctx);
                    badgesAdapter.open(this.getDb());
                    result = badgesAdapter.query(badgeId);
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
            case NPCSTOBADGES_ONE:
                selectionArgs = new String[2];
                selection = NpcstoBadgesContract.COL_NPCSINTERNALID_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                selection += " AND " + NpcstoBadgesContract.COL_BADGE_ID + " = ?";
                selectionArgs[1] = uri.getPathSegments().get(2);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case NPCSTOBADGES_ALL:
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
        return NPCSTOBADGES_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String NpcsInternalIdId, String badgeId) {
        android.database.Cursor result = null;
        String selection = NpcstoBadgesContract.ALIASED_COL_NPCSINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + NpcstoBadgesContract.ALIASED_COL_BADGE_ID
                        + " = ?";

        String[] selectionArgs = new String[2];
        selectionArgs[0] = NpcsInternalIdId;
        selectionArgs[1] = badgeId;
        
        

        result = this.adapter.query(
                    NpcstoBadgesContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

