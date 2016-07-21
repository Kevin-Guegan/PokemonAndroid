/**************************************************************************
 * PokemonsSQLiteAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.data;

import com.kevinguegancamillepaviot.pokemon.data.base.PokemonsSQLiteAdapterBase;


/**
 * Pokemons adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class PokemonsSQLiteAdapter extends PokemonsSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PokemonsSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
