/**************************************************************************
 * NpcsProviderAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider;

import com.kevinguegancamillepaviot.pokemon.provider.base.NpcsProviderAdapterBase;
import com.kevinguegancamillepaviot.pokemon.provider.base.PokemonProviderBase;

/**
 * NpcsProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class NpcsProviderAdapter
                    extends NpcsProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public NpcsProviderAdapter(
            final PokemonProviderBase provider) {
        super(provider);
    }
}

