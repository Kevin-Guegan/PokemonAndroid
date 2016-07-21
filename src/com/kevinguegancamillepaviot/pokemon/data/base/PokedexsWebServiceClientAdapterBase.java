/**************************************************************************
 * PokedexsWebServiceClientAdapterBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;
import com.kevinguegancamillepaviot.pokemon.data.RestClient.Verb;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokedexsContract;



/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokedexsWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokedexsWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Pokedexs> {
    /** PokedexsWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokedexsWSClientAdapter";

    /** JSON Object Pokedexs pattern. */
    protected static String JSON_OBJECT_POKEDEXS = "Pokedexs";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Pokedexs REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokedexsContract.COL_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokedexsWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokedexsWebServiceClientAdapterBase(Context context,
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
    public PokedexsWebServiceClientAdapterBase(Context context,
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
    public PokedexsWebServiceClientAdapterBase(Context context,
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
    public PokedexsWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Pokedexss in the given list. Uses the route : Pokedexs.
     * @param pokedexss : The list in which the Pokedexss will be returned
     * @return The number of Pokedexss returned
     */
    public int getAll(List<Pokedexs> pokedexss) {
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
                result = extractItems(json, "Pokedexss", pokedexss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokedexss = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokedexs";
    }

    /**
     * Retrieve one Pokedexs. Uses the route : Pokedexs/%id%.
     * @param pokedexs : The Pokedexs to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Pokedexs pokedexs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokedexs.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, pokedexs)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokedexs = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Pokedexs. Uses the route : Pokedexs/%id%.
     * @param pokedexs : The Pokedexs to retrieve (set the  ID)
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
     * Update a Pokedexs. Uses the route : Pokedexs/%id%.
     * @param pokedexs : The Pokedexs to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Pokedexs pokedexs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokedexs.getId(),
                        REST_FORMAT),
                    itemToJson(pokedexs));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokedexs);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Pokedexs. Uses the route : Pokedexs/%id%.
     * @param pokedexs : The Pokedexs to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Pokedexs pokedexs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokedexs.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }


    /**
     * Tests if the json is a valid Pokedexs Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokedexsWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Pokedexs from a JSONObject describing a Pokedexs.
     * @param json The JSONObject describing the Pokedexs
     * @param pokedexs The returned Pokedexs
     * @return true if a Pokedexs was found. false if not
     */
    public boolean extract(JSONObject json, Pokedexs pokedexs) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokedexsWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokedexsWebServiceClientAdapter.JSON_ID)) {
                    pokedexs.setId(
                            json.getInt(PokedexsWebServiceClientAdapter.JSON_ID));
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
        String id = json.optString(PokedexsWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[1];
                if (json.has(PokedexsWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokedexsWebServiceClientAdapter.JSON_ID);
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
     * Convert a Pokedexs to a JSONObject.
     * @param pokedexs The Pokedexs to convert
     * @return The converted Pokedexs
     */
    public JSONObject itemToJson(Pokedexs pokedexs) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokedexsWebServiceClientAdapter.JSON_ID,
                    pokedexs.getId());
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
    public JSONObject itemIdToJson(Pokedexs item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokedexsWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Pokedexs to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokedexsWebServiceClientAdapter.JSON_ID,
                    values.get(PokedexsContract.COL_ID));
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
            List<Pokedexs> items,
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
                Pokedexs item = new Pokedexs();
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
            List<Pokedexs> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
