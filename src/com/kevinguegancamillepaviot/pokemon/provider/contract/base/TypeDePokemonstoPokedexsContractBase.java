/**************************************************************************
 * TypeDePokemonstoPokedexsContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;



import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonstoPokedexsContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypeDePokemonstoPokedexsContractBase {


        /** TypeDePokemonsInternalId_id. */
    public static final String COL_TYPEDEPOKEMONSINTERNALID_ID =
            "TypeDePokemonsInternalId_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPEDEPOKEMONSINTERNALID_ID =
            TypeDePokemonstoPokedexsContract.TABLE_NAME + "." + COL_TYPEDEPOKEMONSINTERNALID_ID;

    /** pokedex_id. */
    public static final String COL_POKEDEX_ID =
            "pokedex_id";
    /** Alias. */
    public static final String ALIASED_COL_POKEDEX_ID =
            TypeDePokemonstoPokedexsContract.TABLE_NAME + "." + COL_POKEDEX_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "TypeDePokemonstoPokedexs";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "TypeDePokemonstoPokedexs";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypeDePokemonstoPokedexsContract.COL_TYPEDEPOKEMONSINTERNALID_ID,
        
        TypeDePokemonstoPokedexsContract.COL_POKEDEX_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypeDePokemonstoPokedexsContract.ALIASED_COL_TYPEDEPOKEMONSINTERNALID_ID,
        
        TypeDePokemonstoPokedexsContract.ALIASED_COL_POKEDEX_ID
    };

}
