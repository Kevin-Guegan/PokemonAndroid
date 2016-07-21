/**************************************************************************
 * TypeDePokemonsWebServiceClientAdapterBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.data.RestClient.Verb;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;

import com.kevinguegancamillepaviot.pokemon.entity.Types;
import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeDePokemonsWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class TypeDePokemonsWebServiceClientAdapterBase
        extends WebServiceClientAdapter<TypeDePokemons> {
    /** TypeDePokemonsWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "TypeDePokemonsWSClientAdapter";

    /** JSON Object TypeDePokemons pattern. */
    protected static String JSON_OBJECT_TYPEDEPOKEMONS = "TypeDePokemons";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_ATTAQUE attributes. */
    protected static String JSON_ATTAQUE = "attaque";
    /** JSON_ATTAQUE_SPE attributes. */
    protected static String JSON_ATTAQUE_SPE = "attaque_spe";
    /** JSON_DEFENCE attributes. */
    protected static String JSON_DEFENCE = "defence";
    /** JSON_DEFENCE_SPE attributes. */
    protected static String JSON_DEFENCE_SPE = "defence_spe";
    /** JSON_VITESSE attributes. */
    protected static String JSON_VITESSE = "vitesse";
    /** JSON_PV attributes. */
    protected static String JSON_PV = "pv";
    /** JSON_TYPE attributes. */
    protected static String JSON_TYPE = "type";
    /** JSON_TYPEDEPOKEMON attributes. */
    protected static String JSON_TYPEDEPOKEMON = "typeDePokemon";
    /** JSON_POKEDEX attributes. */
    protected static String JSON_POKEDEX = "pokedex";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** TypeDePokemons REST Columns. */
    public static String[] REST_COLS = new String[]{
            TypeDePokemonsContract.COL_ID,
            TypeDePokemonsContract.COL_NOM,
            TypeDePokemonsContract.COL_ATTAQUE,
            TypeDePokemonsContract.COL_ATTAQUE_SPE,
            TypeDePokemonsContract.COL_DEFENCE,
            TypeDePokemonsContract.COL_DEFENCE_SPE,
            TypeDePokemonsContract.COL_VITESSE,
            TypeDePokemonsContract.COL_PV,
            TypeDePokemonsContract.COL_TYPE_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public TypeDePokemonsWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public TypeDePokemonsWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonsWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonsWebServiceClientAdapterBase(Context context,
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
    public TypeDePokemonsWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the TypeDePokemonss in the given list. Uses the route : TypeDePokemons.
     * @param typeDePokemonss : The list in which the TypeDePokemonss will be returned
     * @return The number of TypeDePokemonss returned
     */
    public int getAll(List<TypeDePokemons> typeDePokemonss) {
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
                result = extractItems(json, "TypeDePokemonss", typeDePokemonss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonss = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "typedepokemons";
    }

    /**
     * Retrieve one TypeDePokemons. Uses the route : TypeDePokemons/%id%.
     * @param typeDePokemons : The TypeDePokemons to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(TypeDePokemons typeDePokemons) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemons.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, typeDePokemons)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemons = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one TypeDePokemons. Uses the route : TypeDePokemons/%id%.
     * @param typeDePokemons : The TypeDePokemons to retrieve (set the  ID)
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
     * Update a TypeDePokemons. Uses the route : TypeDePokemons/%id%.
     * @param typeDePokemons : The TypeDePokemons to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(TypeDePokemons typeDePokemons) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemons.getId(),
                        REST_FORMAT),
                    itemToJson(typeDePokemons));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, typeDePokemons);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a TypeDePokemons. Uses the route : TypeDePokemons/%id%.
     * @param typeDePokemons : The TypeDePokemons to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(TypeDePokemons typeDePokemons) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemons.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the TypeDePokemonss associated with a Types. Uses the route : types/%Types_id%/typedepokemons.
     * @param typeDePokemonss : The list in which the TypeDePokemonss will be returned
     * @param types : The associated types
     * @return The number of TypeDePokemonss returned
     */
    public int getByType(List<TypeDePokemons> typeDePokemonss, Types types) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        types.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "TypeDePokemonss", typeDePokemonss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonss = null;
            }
        }

        return result;
    }

    /**
     * Get the TypeDePokemonss associated with a TypeDePokemons. Uses the route : typedepokemons/%TypeDePokemons_id%/typedepokemons.
     * @param typeDePokemonss : The list in which the TypeDePokemonss will be returned
     * @param typedepokemons : The associated typedepokemons
     * @return The number of TypeDePokemonss returned
     */
    public int getByTypeDePokemon(List<TypeDePokemons> typeDePokemonss, TypeDePokemons typeDePokemons) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemons.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "TypeDePokemonss", typeDePokemonss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonss = null;
            }
        }

        return result;
    }

    /**
     * Get the TypeDePokemonss associated with a Pokedexs. Uses the route : pokedexs/%Pokedexs_id%/typedepokemons.
     * @param typeDePokemonss : The list in which the TypeDePokemonss will be returned
     * @param pokedexs : The associated pokedexs
     * @return The number of TypeDePokemonss returned
     */
    public int getByPokedex(List<TypeDePokemons> typeDePokemonss, Pokedexs pokedexs) {
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
                result = this.extractItems(json, "TypeDePokemonss", typeDePokemonss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeDePokemonss = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid TypeDePokemons Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(TypeDePokemonsWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a TypeDePokemons from a JSONObject describing a TypeDePokemons.
     * @param json The JSONObject describing the TypeDePokemons
     * @param typeDePokemons The returned TypeDePokemons
     * @return true if a TypeDePokemons was found. false if not
     */
    public boolean extract(JSONObject json, TypeDePokemons typeDePokemons) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_ID)) {
                    typeDePokemons.setId(
                            json.getInt(TypeDePokemonsWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_NOM)) {
                    typeDePokemons.setNom(
                            json.getString(TypeDePokemonsWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE)) {
                    typeDePokemons.setAttaque(
                            json.getInt(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE));
                }

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE_SPE)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE_SPE)) {
                    typeDePokemons.setAttaque_spe(
                            json.getInt(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE_SPE));
                }

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE)) {
                    typeDePokemons.setDefence(
                            json.getInt(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE));
                }

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE_SPE)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE_SPE)) {
                    typeDePokemons.setDefence_spe(
                            json.getInt(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE_SPE));
                }

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_VITESSE)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_VITESSE)) {
                    typeDePokemons.setVitesse(
                            json.getInt(TypeDePokemonsWebServiceClientAdapter.JSON_VITESSE));
                }

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_PV)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_PV)) {
                    typeDePokemons.setPv(
                            json.getInt(TypeDePokemonsWebServiceClientAdapter.JSON_PV));
                }

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_TYPE)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_TYPE)) {

                    try {
                        TypesWebServiceClientAdapter typeAdapter =
                                new TypesWebServiceClientAdapter(this.context);
                        Types type =
                                new Types();

                        if (typeAdapter.extract(
                                json.optJSONObject(
                                        TypeDePokemonsWebServiceClientAdapter.JSON_TYPE),
                                        type)) {
                            typeDePokemons.setType(type);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Types data");
                    }
                }

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_TYPEDEPOKEMON)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_TYPEDEPOKEMON)) {
                    ArrayList<TypeDePokemons> typeDePokemon =
                            new ArrayList<TypeDePokemons>();
                    TypeDePokemonsWebServiceClientAdapter typeDePokemonAdapter =
                            new TypeDePokemonsWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(TypeDePokemonsWebServiceClientAdapter.JSON_TYPEDEPOKEMON);
                        typeDePokemonAdapter.extractItems(
                                json, TypeDePokemonsWebServiceClientAdapter.JSON_TYPEDEPOKEMON,
                                typeDePokemon);
                        typeDePokemons.setTypeDePokemon(typeDePokemon);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_POKEDEX)
                        && !json.isNull(TypeDePokemonsWebServiceClientAdapter.JSON_POKEDEX)) {
                    ArrayList<Pokedexs> pokedex =
                            new ArrayList<Pokedexs>();
                    PokedexsWebServiceClientAdapter pokedexAdapter =
                            new PokedexsWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(TypeDePokemonsWebServiceClientAdapter.JSON_POKEDEX);
                        pokedexAdapter.extractItems(
                                json, TypeDePokemonsWebServiceClientAdapter.JSON_POKEDEX,
                                pokedex);
                        typeDePokemons.setPokedex(pokedex);
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
        String id = json.optString(TypeDePokemonsWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[9];
                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(TypeDePokemonsWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(TypeDePokemonsWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE)) {
                    row[2] = json.getString(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE);
                }
                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE_SPE)) {
                    row[3] = json.getString(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE_SPE);
                }
                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE)) {
                    row[4] = json.getString(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE);
                }
                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE_SPE)) {
                    row[5] = json.getString(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE_SPE);
                }
                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_VITESSE)) {
                    row[6] = json.getString(TypeDePokemonsWebServiceClientAdapter.JSON_VITESSE);
                }
                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_PV)) {
                    row[7] = json.getString(TypeDePokemonsWebServiceClientAdapter.JSON_PV);
                }
                if (json.has(TypeDePokemonsWebServiceClientAdapter.JSON_TYPE)) {
                    JSONObject typeJson = json.getJSONObject(
                            TypeDePokemonsWebServiceClientAdapter.JSON_TYPE);
                    row[8] = typeJson.getString(
                            TypesWebServiceClientAdapter.JSON_ID);
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
     * Convert a TypeDePokemons to a JSONObject.
     * @param typeDePokemons The TypeDePokemons to convert
     * @return The converted TypeDePokemons
     */
    public JSONObject itemToJson(TypeDePokemons typeDePokemons) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_ID,
                    typeDePokemons.getId());
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_NOM,
                    typeDePokemons.getNom());
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE,
                    typeDePokemons.getAttaque());
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE_SPE,
                    typeDePokemons.getAttaque_spe());
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE,
                    typeDePokemons.getDefence());
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE_SPE,
                    typeDePokemons.getDefence_spe());
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_VITESSE,
                    typeDePokemons.getVitesse());
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_PV,
                    typeDePokemons.getPv());

            if (typeDePokemons.getType() != null) {
                TypesWebServiceClientAdapter typeAdapter =
                        new TypesWebServiceClientAdapter(this.context);

                params.put(TypeDePokemonsWebServiceClientAdapter.JSON_TYPE,
                        typeAdapter.itemIdToJson(typeDePokemons.getType()));
            }

            if (typeDePokemons.getTypeDePokemon() != null) {
                TypeDePokemonsWebServiceClientAdapter typeDePokemonAdapter =
                        new TypeDePokemonsWebServiceClientAdapter(this.context);

                params.put(TypeDePokemonsWebServiceClientAdapter.JSON_TYPEDEPOKEMON,
                        typeDePokemonAdapter.itemsIdToJson(typeDePokemons.getTypeDePokemon()));
            }

            if (typeDePokemons.getPokedex() != null) {
                PokedexsWebServiceClientAdapter pokedexAdapter =
                        new PokedexsWebServiceClientAdapter(this.context);

                params.put(TypeDePokemonsWebServiceClientAdapter.JSON_POKEDEX,
                        pokedexAdapter.itemsIdToJson(typeDePokemons.getPokedex()));
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
    public JSONObject itemIdToJson(TypeDePokemons item) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a TypeDePokemons to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_ID,
                    values.get(TypeDePokemonsContract.COL_ID));
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_NOM,
                    values.get(TypeDePokemonsContract.COL_NOM));
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE,
                    values.get(TypeDePokemonsContract.COL_ATTAQUE));
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_ATTAQUE_SPE,
                    values.get(TypeDePokemonsContract.COL_ATTAQUE_SPE));
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE,
                    values.get(TypeDePokemonsContract.COL_DEFENCE));
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_DEFENCE_SPE,
                    values.get(TypeDePokemonsContract.COL_DEFENCE_SPE));
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_VITESSE,
                    values.get(TypeDePokemonsContract.COL_VITESSE));
            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_PV,
                    values.get(TypeDePokemonsContract.COL_PV));
            TypesWebServiceClientAdapter typeAdapter =
                    new TypesWebServiceClientAdapter(this.context);

            params.put(TypeDePokemonsWebServiceClientAdapter.JSON_TYPE,
                    typeAdapter.contentValuesToJson(values));
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
            List<TypeDePokemons> items,
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
                TypeDePokemons item = new TypeDePokemons();
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
            List<TypeDePokemons> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
