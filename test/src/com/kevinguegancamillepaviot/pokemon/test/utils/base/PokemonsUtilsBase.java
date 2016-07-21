/**************************************************************************
 * PokemonsUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.test.utils.base;


import junit.framework.Assert;
import com.kevinguegancamillepaviot.pokemon.entity.Pokemons;



import com.kevinguegancamillepaviot.pokemon.test.utils.TestUtils;

import com.kevinguegancamillepaviot.pokemon.test.utils.TypeDePokemonsUtils;

import com.kevinguegancamillepaviot.pokemon.test.utils.AttaquesUtils;


public abstract class PokemonsUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Pokemons generateRandom(android.content.Context ctx){
        Pokemons pokemons = new Pokemons();

        pokemons.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokemons.setSurnom("surnom_"+TestUtils.generateRandomString(10));
        pokemons.setNiveau(TestUtils.generateRandomInt(0,100));
        pokemons.setCapture(TestUtils.generateRandomDateTime());
        pokemons.setTypePokemon(TypeDePokemonsUtils.generateRandom(ctx));
        pokemons.setAttaque1(AttaquesUtils.generateRandom(ctx));
        pokemons.setAttaque2(AttaquesUtils.generateRandom(ctx));
        pokemons.setAttaque3(AttaquesUtils.generateRandom(ctx));
        pokemons.setAttaque4(AttaquesUtils.generateRandom(ctx));

        return pokemons;
    }

    public static boolean equals(Pokemons pokemons1,
            Pokemons pokemons2){
        return equals(pokemons1, pokemons2, true);
    }
    
    public static boolean equals(Pokemons pokemons1,
            Pokemons pokemons2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokemons1);
        Assert.assertNotNull(pokemons2);
        if (pokemons1!=null && pokemons2 !=null){
            Assert.assertEquals(pokemons1.getId(), pokemons2.getId());
            Assert.assertEquals(pokemons1.getSurnom(), pokemons2.getSurnom());
            Assert.assertEquals(pokemons1.getNiveau(), pokemons2.getNiveau());
            Assert.assertEquals(pokemons1.getCapture(), pokemons2.getCapture());
            if (pokemons1.getTypePokemon() != null
                    && pokemons2.getTypePokemon() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemons1.getTypePokemon().getId(),
                            pokemons2.getTypePokemon().getId());
                }
            }
            if (pokemons1.getAttaque1() != null
                    && pokemons2.getAttaque1() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemons1.getAttaque1().getId(),
                            pokemons2.getAttaque1().getId());
                }
            }
            if (pokemons1.getAttaque2() != null
                    && pokemons2.getAttaque2() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemons1.getAttaque2().getId(),
                            pokemons2.getAttaque2().getId());
                }
            }
            if (pokemons1.getAttaque3() != null
                    && pokemons2.getAttaque3() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemons1.getAttaque3().getId(),
                            pokemons2.getAttaque3().getId());
                }
            }
            if (pokemons1.getAttaque4() != null
                    && pokemons2.getAttaque4() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemons1.getAttaque4().getId(),
                            pokemons2.getAttaque4().getId());
                }
            }
        }

        return ret;
    }
}

