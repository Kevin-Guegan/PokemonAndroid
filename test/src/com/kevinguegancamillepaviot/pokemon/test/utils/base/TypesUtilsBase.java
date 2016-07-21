/**************************************************************************
 * TypesUtilsBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.entity.Types;



import com.kevinguegancamillepaviot.pokemon.test.utils.TestUtils;


public abstract class TypesUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Types generateRandom(android.content.Context ctx){
        Types types = new Types();

        types.setId(TestUtils.generateRandomInt(0,100) + 1);
        types.setNom("nom_"+TestUtils.generateRandomString(10));

        return types;
    }

    public static boolean equals(Types types1,
            Types types2){
        return equals(types1, types2, true);
    }
    
    public static boolean equals(Types types1,
            Types types2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(types1);
        Assert.assertNotNull(types2);
        if (types1!=null && types2 !=null){
            Assert.assertEquals(types1.getId(), types2.getId());
            Assert.assertEquals(types1.getNom(), types2.getNom());
        }

        return ret;
    }
}

