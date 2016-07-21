/**************************************************************************
 * ObjetstoNpcsProviderAdapterBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetstoNpcsContract;
import com.kevinguegancamillepaviot.pokemon.data.ObjetstoNpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.NpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.ObjetsSQLiteAdapter;

/**
 * ObjetstoNpcsProviderAdapterBase.
 */
public abstract class ObjetstoNpcsProviderAdapterBase
                extends ProviderAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ObjetstoNpcsProviderAdapter";

    /** OBJETSTONPCS_URI. */
    public      static Uri OBJETSTONPCS_URI;

    /** objetstoNpcs type. */
    protected static final String objetstoNpcsType =
            "objetstonpcs";

    /** OBJETSTONPCS_ALL. */
    protected static final int OBJETSTONPCS_ALL =
            1502639494;
    /** OBJETSTONPCS_ONE. */
    protected static final int OBJETSTONPCS_ONE =
            1502639495;

    /** OBJETSTONPCS_NPCSINTERNALID. */
    protected static final int OBJETSTONPCS_NPCSINTERNALID =
            1502639496;
    /** OBJETSTONPCS_OBJET. */
    protected static final int OBJETSTONPCS_OBJET =
            1502639497;

    /**
     * Static constructor.
     */
    static {
        OBJETSTONPCS_URI =
                PokemonProvider.generateUri(
                        objetstoNpcsType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetstoNpcsType,
                OBJETSTONPCS_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetstoNpcsType + "/#" + "/#",
                OBJETSTONPCS_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetstoNpcsType + "/#" + "/#" + "/npcsinternalid",
                OBJETSTONPCS_NPCSINTERNALID);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetstoNpcsType + "/#" + "/#" + "/objet",
                OBJETSTONPCS_OBJET);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ObjetstoNpcsProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new ObjetstoNpcsSQLiteAdapter(provider.getContext()));

        this.uriIds.add(OBJETSTONPCS_ALL);
        this.uriIds.add(OBJETSTONPCS_ONE);
        this.uriIds.add(OBJETSTONPCS_NPCSINTERNALID);
        this.uriIds.add(OBJETSTONPCS_OBJET);
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
            case OBJETSTONPCS_ONE:
                String NpcsInternalIdId = uri.getPathSegments().get(1);
                String objetId = uri.getPathSegments().get(2);
                selection = ObjetstoNpcsContract.COL_NPCSINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + ObjetstoNpcsContract.COL_OBJET_ID
                        + " = ?";
                selectionArgs = new String[2];
                selectionArgs[0] = NpcsInternalIdId;
                selectionArgs[1] = objetId;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case OBJETSTONPCS_ALL:
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
            case OBJETSTONPCS_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ObjetstoNpcsContract.COL_NPCSINTERNALID_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            OBJETSTONPCS_URI,
                            values.getAsString(ObjetstoNpcsContract.COL_NPCSINTERNALID_ID)
                            + "/"
                            + values.getAsString(ObjetstoNpcsContract.COL_OBJET_ID));
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
        android.database.Cursor objetstoNpcsCursor;

        switch (matchedUri) {

            case OBJETSTONPCS_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case OBJETSTONPCS_ONE:
                result = this.queryById(uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));
                break;

            case OBJETSTONPCS_NPCSINTERNALID:
                objetstoNpcsCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (objetstoNpcsCursor.getCount() > 0) {
                    objetstoNpcsCursor.moveToFirst();
                    int NpcsInternalIdId = objetstoNpcsCursor.getInt(
                            objetstoNpcsCursor.getColumnIndex(
                                    ObjetstoNpcsContract.COL_NPCSINTERNALID_ID));

                    NpcsSQLiteAdapter npcsAdapter = new NpcsSQLiteAdapter(this.ctx);
                    npcsAdapter.open(this.getDb());
                    result = npcsAdapter.query(NpcsInternalIdId);
                }
                break;

            case OBJETSTONPCS_OBJET:
                objetstoNpcsCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (objetstoNpcsCursor.getCount() > 0) {
                    objetstoNpcsCursor.moveToFirst();
                    int objetId = objetstoNpcsCursor.getInt(
                            objetstoNpcsCursor.getColumnIndex(
                                    ObjetstoNpcsContract.COL_OBJET_ID));

                    ObjetsSQLiteAdapter objetsAdapter = new ObjetsSQLiteAdapter(this.ctx);
                    objetsAdapter.open(this.getDb());
                    result = objetsAdapter.query(objetId);
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
            case OBJETSTONPCS_ONE:
                selectionArgs = new String[2];
                selection = ObjetstoNpcsContract.COL_NPCSINTERNALID_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                selection += " AND " + ObjetstoNpcsContract.COL_OBJET_ID + " = ?";
                selectionArgs[1] = uri.getPathSegments().get(2);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case OBJETSTONPCS_ALL:
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
        return OBJETSTONPCS_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String NpcsInternalIdId, String objetId) {
        android.database.Cursor result = null;
        String selection = ObjetstoNpcsContract.ALIASED_COL_NPCSINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + ObjetstoNpcsContract.ALIASED_COL_OBJET_ID
                        + " = ?";

        String[] selectionArgs = new String[2];
        selectionArgs[0] = NpcsInternalIdId;
        selectionArgs[1] = objetId;
        
        

        result = this.adapter.query(
                    ObjetstoNpcsContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

