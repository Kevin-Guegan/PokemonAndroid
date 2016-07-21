/**************************************************************************
 * TypeDePokemonsProviderAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider;

import com.kevinguegancamillepaviot.pokemon.provider.base.TypeDePokemonsProviderAdapterBase;
import com.kevinguegancamillepaviot.pokemon.provider.base.PokemonProviderBase;

/**
 * TypeDePokemonsProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class TypeDePokemonsProviderAdapter
                    extends TypeDePokemonsProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonsProviderAdapter(
            final PokemonProviderBase provider) {
        super(provider);
    }
}

