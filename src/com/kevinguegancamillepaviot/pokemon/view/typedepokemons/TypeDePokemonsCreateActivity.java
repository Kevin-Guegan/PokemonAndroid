/**************************************************************************
 * TypeDePokemonsCreateActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.typedepokemons;

import com.kevinguegancamillepaviot.pokemon.R;

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * TypeDePokemons create Activity.
 *
 * This only contains a TypeDePokemonsCreateFragment.
 *
 * @see android.app.Activity
 */
public class TypeDePokemonsCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_typedepokemons_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
