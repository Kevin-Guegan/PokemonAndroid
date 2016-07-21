/**************************************************************************
 * ArenesProviderAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider;

import com.kevinguegancamillepaviot.pokemon.provider.base.ArenesProviderAdapterBase;
import com.kevinguegancamillepaviot.pokemon.provider.base.PokemonProviderBase;

/**
 * ArenesProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class ArenesProviderAdapter
                    extends ArenesProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public ArenesProviderAdapter(
            final PokemonProviderBase provider) {
        super(provider);
    }
}

