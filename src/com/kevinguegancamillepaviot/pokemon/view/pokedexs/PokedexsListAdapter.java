/**************************************************************************
 * PokedexsListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.pokedexs;


import com.kevinguegancamillepaviot.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyViewHolder;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokedexsContract;

/**
 * List adapter for Pokedexs entity.
 */
public class PokedexsListAdapter extends HarmonyCursorAdapter<Pokedexs> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokedexsListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokedexsListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Pokedexs cursorToItem(Cursor cursor) {
        return PokedexsContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokedexsContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Pokedexs> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Pokedexs> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokedexs);
        }

        /**
         * Populate row with a {@link Pokedexs}.
         *
         * @param model {@link Pokedexs} data
         */
        public void populate(final Pokedexs model) {

        }
    }
}
