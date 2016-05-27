/**************************************************************************
 * NpcsUtilsBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;



import com.kevinguegancamillepaviot.pokemon.test.utils.TestUtils;
import com.kevinguegancamillepaviot.pokemon.entity.Pokemons;
import com.kevinguegancamillepaviot.pokemon.fixture.PokemonsDataLoader;

import com.kevinguegancamillepaviot.pokemon.entity.Badges;
import com.kevinguegancamillepaviot.pokemon.fixture.BadgesDataLoader;

import com.kevinguegancamillepaviot.pokemon.entity.Objets;
import com.kevinguegancamillepaviot.pokemon.fixture.ObjetsDataLoader;


import java.util.ArrayList;

public abstract class NpcsUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Npcs generateRandom(android.content.Context ctx){
        Npcs npcs = new Npcs();

        npcs.setId(TestUtils.generateRandomInt(0,100) + 1);
        npcs.setNom("nom_"+TestUtils.generateRandomString(10));
        npcs.setProfession("profession_"+TestUtils.generateRandomString(10));
        npcs.setTexte("texte_"+TestUtils.generateRandomString(10));
        ArrayList<Pokemons> pokemons =
            new ArrayList<Pokemons>();
        pokemons.addAll(PokemonsDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Pokemons> relatedPokemons = new ArrayList<Pokemons>();
        if (!pokemons.isEmpty()) {
            relatedPokemons.add(pokemons.get(TestUtils.generateRandomInt(0, pokemons.size())));
            npcs.setPokemon(relatedPokemons);
        }
        ArrayList<Badges> badges =
            new ArrayList<Badges>();
        badges.addAll(BadgesDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Badges> relatedBadges = new ArrayList<Badges>();
        if (!badges.isEmpty()) {
            relatedBadges.add(badges.get(TestUtils.generateRandomInt(0, badges.size())));
            npcs.setBadge(relatedBadges);
        }
        ArrayList<Objets> objets =
            new ArrayList<Objets>();
        objets.addAll(ObjetsDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Objets> relatedObjets = new ArrayList<Objets>();
        if (!objets.isEmpty()) {
            relatedObjets.add(objets.get(TestUtils.generateRandomInt(0, objets.size())));
            npcs.setObjet(relatedObjets);
        }

        return npcs;
    }

    public static boolean equals(Npcs npcs1,
            Npcs npcs2){
        return equals(npcs1, npcs2, true);
    }
    
    public static boolean equals(Npcs npcs1,
            Npcs npcs2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(npcs1);
        Assert.assertNotNull(npcs2);
        if (npcs1!=null && npcs2 !=null){
            Assert.assertEquals(npcs1.getId(), npcs2.getId());
            Assert.assertEquals(npcs1.getNom(), npcs2.getNom());
            Assert.assertEquals(npcs1.getProfession(), npcs2.getProfession());
            Assert.assertEquals(npcs1.getTexte(), npcs2.getTexte());
            if (npcs1.getPokemon() != null
                    && npcs2.getPokemon() != null) {
                Assert.assertEquals(npcs1.getPokemon().size(),
                    npcs2.getPokemon().size());
                if (checkRecursiveId) {
                    for (Pokemons pokemon1 : npcs1.getPokemon()) {
                        boolean found = false;
                        for (Pokemons pokemon2 : npcs2.getPokemon()) {
                            if (pokemon1.getId() == pokemon2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated pokemon (id = %s) in Npcs (id = %s)",
                                        pokemon1.getId(),
                                        npcs1.getId()),
                                found);
                    }
                }
            }
            if (npcs1.getBadge() != null
                    && npcs2.getBadge() != null) {
                Assert.assertEquals(npcs1.getBadge().size(),
                    npcs2.getBadge().size());
                if (checkRecursiveId) {
                    for (Badges badge1 : npcs1.getBadge()) {
                        boolean found = false;
                        for (Badges badge2 : npcs2.getBadge()) {
                            if (badge1.getId() == badge2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated badge (id = %s) in Npcs (id = %s)",
                                        badge1.getId(),
                                        npcs1.getId()),
                                found);
                    }
                }
            }
            if (npcs1.getObjet() != null
                    && npcs2.getObjet() != null) {
                Assert.assertEquals(npcs1.getObjet().size(),
                    npcs2.getObjet().size());
                if (checkRecursiveId) {
                    for (Objets objet1 : npcs1.getObjet()) {
                        boolean found = false;
                        for (Objets objet2 : npcs2.getObjet()) {
                            if (objet1.getId() == objet2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated objet (id = %s) in Npcs (id = %s)",
                                        objet1.getId(),
                                        npcs1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

