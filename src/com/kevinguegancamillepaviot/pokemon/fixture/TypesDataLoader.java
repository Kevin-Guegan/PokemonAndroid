/**************************************************************************
 * TypesDataLoader.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.fixture;

import java.util.Map;




import com.kevinguegancamillepaviot.pokemon.entity.Types;


/**
 * TypesDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class TypesDataLoader
                        extends FixtureBase<Types> {
    /** TypesDataLoader name. */
    private static final String FILE_NAME = "Types";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for typeDePokemon. */
    private static final String TYPEDEPOKEMON = "typeDePokemon";
    /** Constant field for attaque. */
    private static final String ATTAQUE = "attaque";


    /** TypesDataLoader instance (Singleton). */
    private static TypesDataLoader instance;

    /**
     * Get the TypesDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static TypesDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new TypesDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private TypesDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Types extractItem(final Map<?, ?> columns) {
        final Types types =
                new Types();

        return this.extractItem(columns, types);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param types Entity to extract
     * @return A Types entity
     */
    protected Types extractItem(final Map<?, ?> columns,
                Types types) {
        types.setId(this.parseIntField(columns, ID));
        types.setTypeDePokemon(this.parseMultiRelationField(columns, TYPEDEPOKEMON, TypeDePokemonsDataLoader.getInstance(this.ctx)));
        types.setAttaque(this.parseMultiRelationField(columns, ATTAQUE, AttaquesDataLoader.getInstance(this.ctx)));

        return types;
    }
    /**
     * Loads Typess into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Types types : this.items.values()) {
            int id = dataManager.persist(types);
            types.setId(id);

        }
        dataManager.flush();
    }

    /**
     * Give priority for fixtures insertion in database.
     * 0 is the first.
     * @return The order
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * Get the fixture file name.
     * @return A String representing the file name
     */
    @Override
    public String getFixtureFileName() {
        return FILE_NAME;
    }

    @Override
    protected Types get(final String key) {
        final Types result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
