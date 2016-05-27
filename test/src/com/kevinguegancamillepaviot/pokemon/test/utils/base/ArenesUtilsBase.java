/**************************************************************************
 * ArenesUtilsBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.entity.Arenes;



import com.kevinguegancamillepaviot.pokemon.test.utils.TestUtils;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;
import com.kevinguegancamillepaviot.pokemon.fixture.BadgesDataLoader;


import java.util.ArrayList;

public abstract class ArenesUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Arenes generateRandom(android.content.Context ctx){
        Arenes arenes = new Arenes();

        arenes.setId(TestUtils.generateRandomInt(0,100) + 1);
        arenes.setNom("nom_"+TestUtils.generateRandomString(10));
        ArrayList<Badges> badges =
            new ArrayList<Badges>();
        badges.addAll(BadgesDataLoader.getInstance(ctx).getMap().values());
        if (!badges.isEmpty()) {
            arenes.setBadge(badges.get(TestUtils.generateRandomInt(0, badges.size())));
        }

        return arenes;
    }

    public static boolean equals(Arenes arenes1,
            Arenes arenes2){
        return equals(arenes1, arenes2, true);
    }
    
    public static boolean equals(Arenes arenes1,
            Arenes arenes2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(arenes1);
        Assert.assertNotNull(arenes2);
        if (arenes1!=null && arenes2 !=null){
            Assert.assertEquals(arenes1.getId(), arenes2.getId());
            Assert.assertEquals(arenes1.getNom(), arenes2.getNom());
            if (arenes1.getBadge() != null
                    && arenes2.getBadge() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(arenes1.getBadge().getId(),
                            arenes2.getBadge().getId());
                }
            }
        }

        return ret;
    }
}

