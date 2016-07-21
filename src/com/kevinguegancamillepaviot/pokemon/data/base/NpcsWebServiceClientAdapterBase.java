/**************************************************************************
 * NpcsWebServiceClientAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/

package com.kevinguegancamillepaviot.pokemon.data.base;

import java.util.List;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.kevinguegancamillepaviot.pokemon.data.*;
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.data.RestClient.Verb;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;

import com.kevinguegancamillepaviot.pokemon.entity.Dresseurs;
import com.kevinguegancamillepaviot.pokemon.entity.Pokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;
import com.kevinguegancamillepaviot.pokemon.entity.Objets;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit NpcsWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class NpcsWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Npcs> {
    /** NpcsWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "NpcsWSClientAdapter";

    /** JSON Object Npcs pattern. */
    protected static String JSON_OBJECT_NPCS = "Npcs";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_PROFESSION attributes. */
    protected static String JSON_PROFESSION = "profession";
    /** JSON_TEXTE attributes. */
    protected static String JSON_TEXTE = "texte";
    /** JSON_POKEMON attributes. */
    protected static String JSON_POKEMON = "pokemon";
    /** JSON_BADGE attributes. */
    protected static String JSON_BADGE = "badge";
    /** JSON_OBJET attributes. */
    protected static String JSON_OBJET = "objet";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Npcs REST Columns. */
    public static String[] REST_COLS = new String[]{
            NpcsContract.COL_ID,
            NpcsContract.COL_NOM,
            NpcsContract.COL_PROFESSION,
            NpcsContract.COL_TEXTE
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public NpcsWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public NpcsWebServiceClientAdapterBase(Context context,
        Integer port) {
        this(context, null, port);
    }

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     */
    public NpcsWebServiceClientAdapterBase(Context context,
            String host, Integer port) {
        this(context, host, port, null);
    }

    /**
     * Constructor with overriden port, host and scheme.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     */
    public NpcsWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme) {
        this(context, host, port, scheme, null);
    }

    /**
     * Constructor with overriden port, host, scheme and prefix.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     * @param prefix The overriden prefix
     */
    public NpcsWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Npcss in the given list. Uses the route : Npcs.
     * @param npcss : The list in which the Npcss will be returned
     * @return The number of Npcss returned
     */
    public int getAll(List<Npcs> npcss) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "%s",
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = extractItems(json, "Npcss", npcss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                npcss = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "npcs";
    }

    /**
     * Retrieve one Npcs. Uses the route : Npcs/%id%.
     * @param npcs : The Npcs to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Npcs npcs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        npcs.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, npcs)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                npcs = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Npcs. Uses the route : Npcs/%id%.
     * @param npcs : The Npcs to retrieve (set the  ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public Cursor query(final int id) {
        MatrixCursor result = new MatrixCursor(REST_COLS);
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        id,
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extractCursor(json, result);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                result = null;
            }
        }

        return result;
    }

    /**
     * Update a Npcs. Uses the route : Npcs/%id%.
     * @param npcs : The Npcs to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Npcs npcs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        npcs.getId(),
                        REST_FORMAT),
                    itemToJson(npcs));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, npcs);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Npcs. Uses the route : Npcs/%id%.
     * @param npcs : The Npcs to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Npcs npcs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        npcs.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Npcss associated with a Dresseurs. Uses the route : dresseurs/%Dresseurs_id%/npcs.
     * @param npcss : The list in which the Npcss will be returned
     * @param dresseurs : The associated dresseurs
     * @return The number of Npcss returned
     */
    public int getByDresseursnpcInternal(List<Npcs> npcss, Dresseurs dresseurs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        dresseurs.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Npcss", npcss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                npcss = null;
            }
        }

        return result;
    }


    /**
     * Get the Npcss associated with a Badges. Uses the route : badges/%Badges_id%/npcs.
     * @param npcss : The list in which the Npcss will be returned
     * @param badges : The associated badges
     * @return The number of Npcss returned
     */
    public int getByBadge(List<Npcs> npcss, Badges badges) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        badges.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Npcss", npcss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                npcss = null;
            }
        }

        return result;
    }

    /**
     * Get the Npcss associated with a Objets. Uses the route : objets/%Objets_id%/npcs.
     * @param npcss : The list in which the Npcss will be returned
     * @param objets : The associated objets
     * @return The number of Npcss returned
     */
    public int getByObjet(List<Npcs> npcss, Objets objets) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        objets.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Npcss", npcss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                npcss = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Npcs Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(NpcsWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Npcs from a JSONObject describing a Npcs.
     * @param json The JSONObject describing the Npcs
     * @param npcs The returned Npcs
     * @return true if a Npcs was found. false if not
     */
    public boolean extract(JSONObject json, Npcs npcs) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(NpcsWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(NpcsWebServiceClientAdapter.JSON_ID)) {
                    npcs.setId(
                            json.getInt(NpcsWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(NpcsWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(NpcsWebServiceClientAdapter.JSON_NOM)) {
                    npcs.setNom(
                            json.getString(NpcsWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(NpcsWebServiceClientAdapter.JSON_PROFESSION)
                        && !json.isNull(NpcsWebServiceClientAdapter.JSON_PROFESSION)) {
                    npcs.setProfession(
                            json.getString(NpcsWebServiceClientAdapter.JSON_PROFESSION));
                }

                if (json.has(NpcsWebServiceClientAdapter.JSON_TEXTE)
                        && !json.isNull(NpcsWebServiceClientAdapter.JSON_TEXTE)) {
                    npcs.setTexte(
                            json.getString(NpcsWebServiceClientAdapter.JSON_TEXTE));
                }

                if (json.has(NpcsWebServiceClientAdapter.JSON_POKEMON)
                        && !json.isNull(NpcsWebServiceClientAdapter.JSON_POKEMON)) {
                    ArrayList<Pokemons> pokemon =
                            new ArrayList<Pokemons>();
                    PokemonsWebServiceClientAdapter pokemonAdapter =
                            new PokemonsWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(NpcsWebServiceClientAdapter.JSON_POKEMON);
                        pokemonAdapter.extractItems(
                                json, NpcsWebServiceClientAdapter.JSON_POKEMON,
                                pokemon);
                        npcs.setPokemon(pokemon);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(NpcsWebServiceClientAdapter.JSON_BADGE)
                        && !json.isNull(NpcsWebServiceClientAdapter.JSON_BADGE)) {
                    ArrayList<Badges> badge =
                            new ArrayList<Badges>();
                    BadgesWebServiceClientAdapter badgeAdapter =
                            new BadgesWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(NpcsWebServiceClientAdapter.JSON_BADGE);
                        badgeAdapter.extractItems(
                                json, NpcsWebServiceClientAdapter.JSON_BADGE,
                                badge);
                        npcs.setBadge(badge);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(NpcsWebServiceClientAdapter.JSON_OBJET)
                        && !json.isNull(NpcsWebServiceClientAdapter.JSON_OBJET)) {
                    ArrayList<Objets> objet =
                            new ArrayList<Objets>();
                    ObjetsWebServiceClientAdapter objetAdapter =
                            new ObjetsWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(NpcsWebServiceClientAdapter.JSON_OBJET);
                        objetAdapter.extractItems(
                                json, NpcsWebServiceClientAdapter.JSON_OBJET,
                                objet);
                        npcs.setObjet(objet);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    @Override
    public boolean extractCursor(JSONObject json, MatrixCursor cursor) {
        boolean result = false;
        String id = json.optString(NpcsWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[4];
                if (json.has(NpcsWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(NpcsWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(NpcsWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(NpcsWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(NpcsWebServiceClientAdapter.JSON_PROFESSION)) {
                    row[2] = json.getString(NpcsWebServiceClientAdapter.JSON_PROFESSION);
                }
                if (json.has(NpcsWebServiceClientAdapter.JSON_TEXTE)) {
                    row[3] = json.getString(NpcsWebServiceClientAdapter.JSON_TEXTE);
                }

                cursor.addRow(row);
                result = true;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Convert a Npcs to a JSONObject.
     * @param npcs The Npcs to convert
     * @return The converted Npcs
     */
    public JSONObject itemToJson(Npcs npcs) {
        JSONObject params = new JSONObject();
        try {
            params.put(NpcsWebServiceClientAdapter.JSON_ID,
                    npcs.getId());
            params.put(NpcsWebServiceClientAdapter.JSON_NOM,
                    npcs.getNom());
            params.put(NpcsWebServiceClientAdapter.JSON_PROFESSION,
                    npcs.getProfession());
            params.put(NpcsWebServiceClientAdapter.JSON_TEXTE,
                    npcs.getTexte());

            if (npcs.getPokemon() != null) {
                PokemonsWebServiceClientAdapter pokemonAdapter =
                        new PokemonsWebServiceClientAdapter(this.context);

                params.put(NpcsWebServiceClientAdapter.JSON_POKEMON,
                        pokemonAdapter.itemsIdToJson(npcs.getPokemon()));
            }

            if (npcs.getBadge() != null) {
                BadgesWebServiceClientAdapter badgeAdapter =
                        new BadgesWebServiceClientAdapter(this.context);

                params.put(NpcsWebServiceClientAdapter.JSON_BADGE,
                        badgeAdapter.itemsIdToJson(npcs.getBadge()));
            }

            if (npcs.getObjet() != null) {
                ObjetsWebServiceClientAdapter objetAdapter =
                        new ObjetsWebServiceClientAdapter(this.context);

                params.put(NpcsWebServiceClientAdapter.JSON_OBJET,
                        objetAdapter.itemsIdToJson(npcs.getObjet()));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Convert a <T> to a JSONObject.
     * @param item The <T> to convert
     * @return The converted <T>
     */
    public JSONObject itemIdToJson(Npcs item) {
        JSONObject params = new JSONObject();
        try {
            params.put(NpcsWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Npcs to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(NpcsWebServiceClientAdapter.JSON_ID,
                    values.get(NpcsContract.COL_ID));
            params.put(NpcsWebServiceClientAdapter.JSON_NOM,
                    values.get(NpcsContract.COL_NOM));
            params.put(NpcsWebServiceClientAdapter.JSON_PROFESSION,
                    values.get(NpcsContract.COL_PROFESSION));
            params.put(NpcsWebServiceClientAdapter.JSON_TEXTE,
                    values.get(NpcsContract.COL_TEXTE));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Extract a list of <T> from a JSONObject describing an array of <T> given the array name.
     * @param json The JSONObject describing the array of <T>
     * @param items The returned list of <T>
     * @param paramName The name of the array
     * @param limit Limit the number of items to parse
     * @return The number of <T> found in the JSON
     */
    public int extractItems(JSONObject json,
            String paramName,
            List<Npcs> items,
            int limit) throws JSONException {

        JSONArray itemArray = json.optJSONArray(paramName);

        int result = -1;

        if (itemArray != null) {
            int count = itemArray.length();

            if (limit > 0 && count > limit) {
                count = limit;
            }

            for (int i = 0; i < count; i++) {
                JSONObject jsonItem = itemArray.getJSONObject(i);
                Npcs item = new Npcs();
                this.extract(jsonItem, item);
                if (item!=null) {
                    synchronized (items) {
                        items.add(item);
                    }
                }
            }
        }

        if (!json.isNull("Meta")) {
            JSONObject meta = json.optJSONObject("Meta");
            result = meta.optInt("nbt",0);
        }

        return result;
    }

    /**
     * Extract a list of <T> from a JSONObject describing an array of <T> given the array name.
     * @param json The JSONObject describing the array of <T>
     * @param items The returned list of <T>
     * @param paramName The name of the array
     * @return The number of <T> found in the JSON
     */
    public int extractItems(JSONObject json,
            String paramName,
            List<Npcs> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
