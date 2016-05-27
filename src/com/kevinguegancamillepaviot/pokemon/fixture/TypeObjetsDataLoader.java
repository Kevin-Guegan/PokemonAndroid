/**************************************************************************
 * TypeObjetsDataLoader.java, pokemon Android
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




import com.kevinguegancamillepaviot.pokemon.entity.TypeObjets;


/**
 * TypeObjetsDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class TypeObjetsDataLoader
                        extends FixtureBase<TypeObjets> {
    /** TypeObjetsDataLoader name. */
    private static final String FILE_NAME = "TypeObjets";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";


    /** TypeObjetsDataLoader instance (Singleton). */
    private static TypeObjetsDataLoader instance;

    /**
     * Get the TypeObjetsDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static TypeObjetsDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new TypeObjetsDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private TypeObjetsDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected TypeObjets extractItem(final Map<?, ?> columns) {
        final TypeObjets typeObjets =
                new TypeObjets();

        return this.extractItem(columns, typeObjets);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param typeObjets Entity to extract
     * @return A TypeObjets entity
     */
    protected TypeObjets extractItem(final Map<?, ?> columns,
                TypeObjets typeObjets) {
        typeObjets.setId(this.parseIntField(columns, ID));
        typeObjets.setNom(this.parseField(columns, NOM, String.class));

        return typeObjets;
    }
    /**
     * Loads TypeObjetss into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final TypeObjets typeObjets : this.items.values()) {
            int id = dataManager.persist(typeObjets);
            typeObjets.setId(id);

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
    protected TypeObjets get(final String key) {
        final TypeObjets result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
