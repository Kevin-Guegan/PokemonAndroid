/**************************************************************************
 * DresseursProviderAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider;

import com.kevinguegancamillepaviot.pokemon.provider.base.DresseursProviderAdapterBase;
import com.kevinguegancamillepaviot.pokemon.provider.base.PokemonProviderBase;

/**
 * DresseursProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class DresseursProviderAdapter
                    extends DresseursProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public DresseursProviderAdapter(
            final PokemonProviderBase provider) {
        super(provider);
    }
}

