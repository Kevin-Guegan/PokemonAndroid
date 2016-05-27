/**************************************************************************
 * DresseursUtilsBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.entity.Dresseurs;



import com.kevinguegancamillepaviot.pokemon.test.utils.TestUtils;
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.fixture.NpcsDataLoader;


import java.util.ArrayList;

public abstract class DresseursUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Dresseurs generateRandom(android.content.Context ctx){
        Dresseurs dresseurs = new Dresseurs();

        dresseurs.setId(TestUtils.generateRandomInt(0,100) + 1);
        dresseurs.setNom("nom_"+TestUtils.generateRandomString(10));
        dresseurs.setLogin("login_"+TestUtils.generateRandomString(10));
        dresseurs.setPassword("password_"+TestUtils.generateRandomString(10));
        ArrayList<Npcs> npcs =
            new ArrayList<Npcs>();
        npcs.addAll(NpcsDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Npcs> relatedNpcs = new ArrayList<Npcs>();
        if (!npcs.isEmpty()) {
            relatedNpcs.add(npcs.get(TestUtils.generateRandomInt(0, npcs.size())));
            dresseurs.setNpc(relatedNpcs);
        }

        return dresseurs;
    }

    public static boolean equals(Dresseurs dresseurs1,
            Dresseurs dresseurs2){
        return equals(dresseurs1, dresseurs2, true);
    }
    
    public static boolean equals(Dresseurs dresseurs1,
            Dresseurs dresseurs2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(dresseurs1);
        Assert.assertNotNull(dresseurs2);
        if (dresseurs1!=null && dresseurs2 !=null){
            Assert.assertEquals(dresseurs1.getId(), dresseurs2.getId());
            Assert.assertEquals(dresseurs1.getNom(), dresseurs2.getNom());
            Assert.assertEquals(dresseurs1.getLogin(), dresseurs2.getLogin());
            Assert.assertEquals(dresseurs1.getPassword(), dresseurs2.getPassword());
            if (dresseurs1.getNpc() != null
                    && dresseurs2.getNpc() != null) {
                Assert.assertEquals(dresseurs1.getNpc().size(),
                    dresseurs2.getNpc().size());
                if (checkRecursiveId) {
                    for (Npcs npc1 : dresseurs1.getNpc()) {
                        boolean found = false;
                        for (Npcs npc2 : dresseurs2.getNpc()) {
                            if (npc1.getId() == npc2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated npc (id = %s) in Dresseurs (id = %s)",
                                        npc1.getId(),
                                        dresseurs1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

