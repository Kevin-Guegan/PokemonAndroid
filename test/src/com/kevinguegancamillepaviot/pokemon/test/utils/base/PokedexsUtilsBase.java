/**************************************************************************
 * PokedexsUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.test.utils.base;


import junit.framework.Assert;
import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;



import com.kevinguegancamillepaviot.pokemon.test.utils.TestUtils;


public abstract class PokedexsUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Pokedexs generateRandom(android.content.Context ctx){
        Pokedexs pokedexs = new Pokedexs();

        pokedexs.setId(TestUtils.generateRandomInt(0,100) + 1);

        return pokedexs;
    }

    public static boolean equals(Pokedexs pokedexs1,
            Pokedexs pokedexs2){
        return equals(pokedexs1, pokedexs2, true);
    }
    
    public static boolean equals(Pokedexs pokedexs1,
            Pokedexs pokedexs2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokedexs1);
        Assert.assertNotNull(pokedexs2);
        if (pokedexs1!=null && pokedexs2 !=null){
            Assert.assertEquals(pokedexs1.getId(), pokedexs2.getId());
        }

        return ret;
    }
}

