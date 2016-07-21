/**************************************************************************
 * TypeObjetsUtilsBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.entity.TypeObjets;



import com.kevinguegancamillepaviot.pokemon.test.utils.TestUtils;


public abstract class TypeObjetsUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static TypeObjets generateRandom(android.content.Context ctx){
        TypeObjets typeObjets = new TypeObjets();

        typeObjets.setId(TestUtils.generateRandomInt(0,100) + 1);
        typeObjets.setNom("nom_"+TestUtils.generateRandomString(10));

        return typeObjets;
    }

    public static boolean equals(TypeObjets typeObjets1,
            TypeObjets typeObjets2){
        return equals(typeObjets1, typeObjets2, true);
    }
    
    public static boolean equals(TypeObjets typeObjets1,
            TypeObjets typeObjets2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(typeObjets1);
        Assert.assertNotNull(typeObjets2);
        if (typeObjets1!=null && typeObjets2 !=null){
            Assert.assertEquals(typeObjets1.getId(), typeObjets2.getId());
            Assert.assertEquals(typeObjets1.getNom(), typeObjets2.getNom());
        }

        return ret;
    }
}

