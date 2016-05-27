/**************************************************************************
 * NpcsDataLoader.java, pokemon Android
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
import java.util.ArrayList;



import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;
import com.kevinguegancamillepaviot.pokemon.entity.Objets;


/**
 * NpcsDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class NpcsDataLoader
                        extends FixtureBase<Npcs> {
    /** NpcsDataLoader name. */
    private static final String FILE_NAME = "Npcs";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for profession. */
    private static final String PROFESSION = "profession";
    /** Constant field for texte. */
    private static final String TEXTE = "texte";
    /** Constant field for pokemon. */
    private static final String POKEMON = "pokemon";
    /** Constant field for badge. */
    private static final String BADGE = "badge";
    /** Constant field for objet. */
    private static final String OBJET = "objet";


    /** NpcsDataLoader instance (Singleton). */
    private static NpcsDataLoader instance;

    /**
     * Get the NpcsDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static NpcsDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new NpcsDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private NpcsDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Npcs extractItem(final Map<?, ?> columns) {
        final Npcs npcs =
                new Npcs();

        return this.extractItem(columns, npcs);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param npcs Entity to extract
     * @return A Npcs entity
     */
    protected Npcs extractItem(final Map<?, ?> columns,
                Npcs npcs) {
        npcs.setId(this.parseIntField(columns, ID));
        npcs.setNom(this.parseField(columns, NOM, String.class));
        npcs.setProfession(this.parseField(columns, PROFESSION, String.class));
        npcs.setTexte(this.parseField(columns, TEXTE, String.class));
        npcs.setPokemon(this.parseMultiRelationField(columns, POKEMON, PokemonsDataLoader.getInstance(this.ctx)));
        npcs.setBadge(this.parseMultiRelationField(columns, BADGE, BadgesDataLoader.getInstance(this.ctx)));
        npcs.setObjet(this.parseMultiRelationField(columns, OBJET, ObjetsDataLoader.getInstance(this.ctx)));

        return npcs;
    }
    /**
     * Loads Npcss into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Npcs npcs : this.items.values()) {
            int id = dataManager.persist(npcs);
            npcs.setId(id);

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
    protected Npcs get(final String key) {
        final Npcs result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
