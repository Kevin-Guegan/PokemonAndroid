/**************************************************************************
 * TypesUtilsBase.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.entity.Types;



import com.kevinguegancamillepaviot.pokemon.test.utils.TestUtils;
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.fixture.TypeDePokemonsDataLoader;

import com.kevinguegancamillepaviot.pokemon.entity.Attaques;
import com.kevinguegancamillepaviot.pokemon.fixture.AttaquesDataLoader;


import java.util.ArrayList;

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
        ArrayList<TypeDePokemons> typeDePokemons =
            new ArrayList<TypeDePokemons>();
        typeDePokemons.addAll(TypeDePokemonsDataLoader.getInstance(ctx).getMap().values());
        ArrayList<TypeDePokemons> relatedTypeDePokemons = new ArrayList<TypeDePokemons>();
        if (!typeDePokemons.isEmpty()) {
            relatedTypeDePokemons.add(typeDePokemons.get(TestUtils.generateRandomInt(0, typeDePokemons.size())));
            types.setTypeDePokemon(relatedTypeDePokemons);
        }
        ArrayList<Attaques> attaques =
            new ArrayList<Attaques>();
        attaques.addAll(AttaquesDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Attaques> relatedAttaques = new ArrayList<Attaques>();
        if (!attaques.isEmpty()) {
            relatedAttaques.add(attaques.get(TestUtils.generateRandomInt(0, attaques.size())));
            types.setAttaque(relatedAttaques);
        }

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
            if (types1.getTypeDePokemon() != null
                    && types2.getTypeDePokemon() != null) {
                Assert.assertEquals(types1.getTypeDePokemon().size(),
                    types2.getTypeDePokemon().size());
                if (checkRecursiveId) {
                    for (TypeDePokemons typeDePokemon1 : types1.getTypeDePokemon()) {
                        boolean found = false;
                        for (TypeDePokemons typeDePokemon2 : types2.getTypeDePokemon()) {
                            if (typeDePokemon1.getId() == typeDePokemon2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated typeDePokemon (id = %s) in Types (id = %s)",
                                        typeDePokemon1.getId(),
                                        types1.getId()),
                                found);
                    }
                }
            }
            if (types1.getAttaque() != null
                    && types2.getAttaque() != null) {
                Assert.assertEquals(types1.getAttaque().size(),
                    types2.getAttaque().size());
                if (checkRecursiveId) {
                    for (Attaques attaque1 : types1.getAttaque()) {
                        boolean found = false;
                        for (Attaques attaque2 : types2.getAttaque()) {
                            if (attaque1.getId() == attaque2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated attaque (id = %s) in Types (id = %s)",
                                        attaque1.getId(),
                                        types1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

