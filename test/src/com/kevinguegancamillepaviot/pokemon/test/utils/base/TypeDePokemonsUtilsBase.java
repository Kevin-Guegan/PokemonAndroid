/**************************************************************************
 * TypeDePokemonsUtilsBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;



import com.kevinguegancamillepaviot.pokemon.test.utils.TestUtils;

import com.kevinguegancamillepaviot.pokemon.test.utils.TypesUtils;
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.test.utils.TypeDePokemonsUtils;

import com.kevinguegancamillepaviot.pokemon.test.utils.PokedexsUtils;

import java.util.ArrayList;

public abstract class TypeDePokemonsUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static TypeDePokemons generateRandom(android.content.Context ctx){
        TypeDePokemons typeDePokemons = new TypeDePokemons();

        typeDePokemons.setId(TestUtils.generateRandomInt(0,100) + 1);
        typeDePokemons.setNom("nom_"+TestUtils.generateRandomString(10));
        typeDePokemons.setAttaque(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setAttaque_spe(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setDefence(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setDefence_spe(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setVitesse(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setPv(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setType(TypesUtils.generateRandom(ctx));
        ArrayList<TypeDePokemons> relatedTypeDePokemons = new ArrayList<TypeDePokemons>();
        relatedTypeDePokemons.add(TypeDePokemonsUtils.generateRandom(ctx));
        typeDePokemons.setTypeDePokemon(relatedTypeDePokemons);
        typeDePokemons.setPokedex(PokedexsUtils.generateRandom(ctx));

        return typeDePokemons;
    }

    public static boolean equals(TypeDePokemons typeDePokemons1,
            TypeDePokemons typeDePokemons2){
        return equals(typeDePokemons1, typeDePokemons2, true);
    }
    
    public static boolean equals(TypeDePokemons typeDePokemons1,
            TypeDePokemons typeDePokemons2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(typeDePokemons1);
        Assert.assertNotNull(typeDePokemons2);
        if (typeDePokemons1!=null && typeDePokemons2 !=null){
            Assert.assertEquals(typeDePokemons1.getId(), typeDePokemons2.getId());
            Assert.assertEquals(typeDePokemons1.getNom(), typeDePokemons2.getNom());
            Assert.assertEquals(typeDePokemons1.getAttaque(), typeDePokemons2.getAttaque());
            Assert.assertEquals(typeDePokemons1.getAttaque_spe(), typeDePokemons2.getAttaque_spe());
            Assert.assertEquals(typeDePokemons1.getDefence(), typeDePokemons2.getDefence());
            Assert.assertEquals(typeDePokemons1.getDefence_spe(), typeDePokemons2.getDefence_spe());
            Assert.assertEquals(typeDePokemons1.getVitesse(), typeDePokemons2.getVitesse());
            Assert.assertEquals(typeDePokemons1.getPv(), typeDePokemons2.getPv());
            if (typeDePokemons1.getType() != null
                    && typeDePokemons2.getType() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(typeDePokemons1.getType().getId(),
                            typeDePokemons2.getType().getId());
                }
            }
            if (typeDePokemons1.getTypeDePokemon() != null
                    && typeDePokemons2.getTypeDePokemon() != null) {
                Assert.assertEquals(typeDePokemons1.getTypeDePokemon().size(),
                    typeDePokemons2.getTypeDePokemon().size());
                if (checkRecursiveId) {
                    for (TypeDePokemons typeDePokemon1 : typeDePokemons1.getTypeDePokemon()) {
                        boolean found = false;
                        for (TypeDePokemons typeDePokemon2 : typeDePokemons2.getTypeDePokemon()) {
                            if (typeDePokemon1.getId() == typeDePokemon2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated typeDePokemon (id = %s) in TypeDePokemons (id = %s)",
                                        typeDePokemon1.getId(),
                                        typeDePokemons1.getId()),
                                found);
                    }
                }
            }
            if (typeDePokemons1.getPokedex() != null
                    && typeDePokemons2.getPokedex() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(typeDePokemons1.getPokedex().getId(),
                            typeDePokemons2.getPokedex().getId());
                }
            }
        }

        return ret;
    }
}

