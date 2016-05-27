/**************************************************************************
 * PokedexsDataLoader.java, pokemon Android
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




import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;


/**
 * PokedexsDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokedexsDataLoader
                        extends FixtureBase<Pokedexs> {
    /** PokedexsDataLoader name. */
    private static final String FILE_NAME = "Pokedexs";

    /** Constant field for id. */
    private static final String ID = "id";


    /** PokedexsDataLoader instance (Singleton). */
    private static PokedexsDataLoader instance;

    /**
     * Get the PokedexsDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokedexsDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokedexsDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokedexsDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Pokedexs extractItem(final Map<?, ?> columns) {
        final Pokedexs pokedexs =
                new Pokedexs();

        return this.extractItem(columns, pokedexs);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokedexs Entity to extract
     * @return A Pokedexs entity
     */
    protected Pokedexs extractItem(final Map<?, ?> columns,
                Pokedexs pokedexs) {
        pokedexs.setId(this.parseIntField(columns, ID));

        return pokedexs;
    }
    /**
     * Loads Pokedexss into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Pokedexs pokedexs : this.items.values()) {
            int id = dataManager.persist(pokedexs);
            pokedexs.setId(id);

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
    protected Pokedexs get(final String key) {
        final Pokedexs result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
