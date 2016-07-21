/**************************************************************************
 * NpcsCreateActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.npcs;

import com.kevinguegancamillepaviot.pokemon.R;

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * Npcs create Activity.
 *
 * This only contains a NpcsCreateFragment.
 *
 * @see android.app.Activity
 */
public class NpcsCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_npcs_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
