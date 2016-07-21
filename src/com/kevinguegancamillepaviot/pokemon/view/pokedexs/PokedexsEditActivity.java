/**************************************************************************
 * PokedexsEditActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.pokedexs;

import com.kevinguegancamillepaviot.pokemon.R;

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Pokedexs edit Activity.
 *
 * This only contains a PokedexsEditFragment.
 *
 * @see android.app.Activity
 */
public class PokedexsEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokedexs_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
