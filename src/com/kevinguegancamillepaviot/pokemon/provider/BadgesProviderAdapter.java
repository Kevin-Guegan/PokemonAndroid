/**************************************************************************
 * BadgesProviderAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider;

import com.kevinguegancamillepaviot.pokemon.provider.base.BadgesProviderAdapterBase;
import com.kevinguegancamillepaviot.pokemon.provider.base.PokemonProviderBase;

/**
 * BadgesProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class BadgesProviderAdapter
                    extends BadgesProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public BadgesProviderAdapter(
            final PokemonProviderBase provider) {
        super(provider);
    }
}

