/**************************************************************************
 * PokedexsSQLiteAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.data;

import com.kevinguegancamillepaviot.pokemon.data.base.PokedexsSQLiteAdapterBase;


/**
 * Pokedexs adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class PokedexsSQLiteAdapter extends PokedexsSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PokedexsSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
