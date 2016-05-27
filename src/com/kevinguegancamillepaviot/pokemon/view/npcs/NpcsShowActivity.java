/**************************************************************************
 * NpcsShowActivity.java, pokemon Android
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

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kevinguegancamillepaviot.pokemon.view.npcs.NpcsShowFragment.DeleteCallback;
import android.os.Bundle;

/** Npcs show Activity.
 *
 * This only contains a NpcsShowFragment.
 *
 * @see android.app.Activity
 */
public class NpcsShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_npcs_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
