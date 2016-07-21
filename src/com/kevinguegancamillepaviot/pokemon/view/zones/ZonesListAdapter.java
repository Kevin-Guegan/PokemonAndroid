/**************************************************************************
 * ZonesListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.zones;


import com.kevinguegancamillepaviot.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinguegancamillepaviot.pokemon.entity.Zones;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyViewHolder;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ZonesContract;

/**
 * List adapter for Zones entity.
 */
public class ZonesListAdapter extends HarmonyCursorAdapter<Zones> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public ZonesListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public ZonesListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Zones cursorToItem(Cursor cursor) {
        return ZonesContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return ZonesContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Zones> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Zones> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_zones);
        }

        /**
         * Populate row with a {@link Zones}.
         *
         * @param model {@link Zones} data
         */
        public void populate(final Zones model) {

        }
    }
}
