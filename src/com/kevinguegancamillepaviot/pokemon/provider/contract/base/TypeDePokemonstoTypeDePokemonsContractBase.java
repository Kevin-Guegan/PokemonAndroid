/**************************************************************************
 * TypeDePokemonstoTypeDePokemonsContractBase.java, pokemon Android
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



import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonstoTypeDePokemonsContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypeDePokemonstoTypeDePokemonsContractBase {


        /** TypeDePokemonsInternalId_id. */
    public static final String COL_TYPEDEPOKEMONSINTERNALID_ID =
            "TypeDePokemonsInternalId_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPEDEPOKEMONSINTERNALID_ID =
            TypeDePokemonstoTypeDePokemonsContract.TABLE_NAME + "." + COL_TYPEDEPOKEMONSINTERNALID_ID;

    /** typeDePokemon_id. */
    public static final String COL_TYPEDEPOKEMON_ID =
            "typeDePokemon_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPEDEPOKEMON_ID =
            TypeDePokemonstoTypeDePokemonsContract.TABLE_NAME + "." + COL_TYPEDEPOKEMON_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "TypeDePokemonstoTypeDePokemons";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "TypeDePokemonstoTypeDePokemons";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMONSINTERNALID_ID,
        
        TypeDePokemonstoTypeDePokemonsContract.COL_TYPEDEPOKEMON_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypeDePokemonstoTypeDePokemonsContract.ALIASED_COL_TYPEDEPOKEMONSINTERNALID_ID,
        
        TypeDePokemonstoTypeDePokemonsContract.ALIASED_COL_TYPEDEPOKEMON_ID
    };

}
