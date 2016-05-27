/**************************************************************************
 * TypeObjetsListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.typeobjets;


import com.kevinguegancamillepaviot.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinguegancamillepaviot.pokemon.entity.TypeObjets;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyViewHolder;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeObjetsContract;

/**
 * List adapter for TypeObjets entity.
 */
public class TypeObjetsListAdapter extends HarmonyCursorAdapter<TypeObjets> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public TypeObjetsListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public TypeObjetsListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected TypeObjets cursorToItem(Cursor cursor) {
        return TypeObjetsContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return TypeObjetsContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<TypeObjets> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<TypeObjets> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_typeobjets);
        }

        /**
         * Populate row with a {@link TypeObjets}.
         *
         * @param model {@link TypeObjets} data
         */
        public void populate(final TypeObjets model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_typeobjets_nom);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
        }
    }
}
