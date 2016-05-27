/**************************************************************************
 * NpcsListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.npcs;


import com.kevinguegancamillepaviot.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyViewHolder;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.DresseursContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcstoBadgesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.BadgesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetstoNpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetsContract;

/**
 * List adapter for Npcs entity.
 */
public class NpcsListAdapter extends HarmonyCursorAdapter<Npcs> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public NpcsListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public NpcsListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Npcs cursorToItem(Cursor cursor) {
        return NpcsContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return NpcsContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Npcs> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Npcs> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_npcs);
        }

        /**
         * Populate row with a {@link Npcs}.
         *
         * @param model {@link Npcs} data
         */
        public void populate(final Npcs model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_npcs_nom);
                    
            TextView professionView = (TextView) this.getView().findViewById(
                    R.id.row_npcs_profession);
                    
            TextView texteView = (TextView) this.getView().findViewById(
                    R.id.row_npcs_texte);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            if (model.getProfession() != null) {
                professionView.setText(model.getProfession());
            }
            if (model.getTexte() != null) {
                texteView.setText(model.getTexte());
            }
        }
    }
}
