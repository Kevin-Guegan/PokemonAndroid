/**************************************************************************
 * TypeObjetsWebServiceClientAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
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
import com.kevinguegancamillepaviot.pokemon.entity.TypeObjets;
import com.kevinguegancamillepaviot.pokemon.data.RestClient.Verb;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeObjetsContract;



/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeObjetsWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class TypeObjetsWebServiceClientAdapterBase
        extends WebServiceClientAdapter<TypeObjets> {
    /** TypeObjetsWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "TypeObjetsWSClientAdapter";

    /** JSON Object TypeObjets pattern. */
    protected static String JSON_OBJECT_TYPEOBJETS = "TypeObjets";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** TypeObjets REST Columns. */
    public static String[] REST_COLS = new String[]{
            TypeObjetsContract.COL_ID,
            TypeObjetsContract.COL_NOM
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public TypeObjetsWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public TypeObjetsWebServiceClientAdapterBase(Context context,
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
    public TypeObjetsWebServiceClientAdapterBase(Context context,
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
    public TypeObjetsWebServiceClientAdapterBase(Context context,
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
    public TypeObjetsWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the TypeObjetss in the given list. Uses the route : TypeObjets.
     * @param typeObjetss : The list in which the TypeObjetss will be returned
     * @return The number of TypeObjetss returned
     */
    public int getAll(List<TypeObjets> typeObjetss) {
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
                result = extractItems(json, "TypeObjetss", typeObjetss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeObjetss = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "typeobjets";
    }

    /**
     * Retrieve one TypeObjets. Uses the route : TypeObjets/%id%.
     * @param typeObjets : The TypeObjets to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(TypeObjets typeObjets) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeObjets.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, typeObjets)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeObjets = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one TypeObjets. Uses the route : TypeObjets/%id%.
     * @param typeObjets : The TypeObjets to retrieve (set the  ID)
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
     * Update a TypeObjets. Uses the route : TypeObjets/%id%.
     * @param typeObjets : The TypeObjets to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(TypeObjets typeObjets) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeObjets.getId(),
                        REST_FORMAT),
                    itemToJson(typeObjets));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, typeObjets);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a TypeObjets. Uses the route : TypeObjets/%id%.
     * @param typeObjets : The TypeObjets to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(TypeObjets typeObjets) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeObjets.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }


    /**
     * Tests if the json is a valid TypeObjets Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(TypeObjetsWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a TypeObjets from a JSONObject describing a TypeObjets.
     * @param json The JSONObject describing the TypeObjets
     * @param typeObjets The returned TypeObjets
     * @return true if a TypeObjets was found. false if not
     */
    public boolean extract(JSONObject json, TypeObjets typeObjets) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(TypeObjetsWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(TypeObjetsWebServiceClientAdapter.JSON_ID)) {
                    typeObjets.setId(
                            json.getInt(TypeObjetsWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(TypeObjetsWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(TypeObjetsWebServiceClientAdapter.JSON_NOM)) {
                    typeObjets.setNom(
                            json.getString(TypeObjetsWebServiceClientAdapter.JSON_NOM));
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
        String id = json.optString(TypeObjetsWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[2];
                if (json.has(TypeObjetsWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(TypeObjetsWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(TypeObjetsWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(TypeObjetsWebServiceClientAdapter.JSON_NOM);
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
     * Convert a TypeObjets to a JSONObject.
     * @param typeObjets The TypeObjets to convert
     * @return The converted TypeObjets
     */
    public JSONObject itemToJson(TypeObjets typeObjets) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeObjetsWebServiceClientAdapter.JSON_ID,
                    typeObjets.getId());
            params.put(TypeObjetsWebServiceClientAdapter.JSON_NOM,
                    typeObjets.getNom());
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
    public JSONObject itemIdToJson(TypeObjets item) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeObjetsWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a TypeObjets to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(TypeObjetsWebServiceClientAdapter.JSON_ID,
                    values.get(TypeObjetsContract.COL_ID));
            params.put(TypeObjetsWebServiceClientAdapter.JSON_NOM,
                    values.get(TypeObjetsContract.COL_NOM));
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
            List<TypeObjets> items,
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
                TypeObjets item = new TypeObjets();
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
            List<TypeObjets> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
