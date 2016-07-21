/**************************************************************************
 * AttaquesEditActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.attaques;

import com.kevinguegancamillepaviot.pokemon.R;

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Attaques edit Activity.
 *
 * This only contains a AttaquesEditFragment.
 *
 * @see android.app.Activity
 */
public class AttaquesEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_attaques_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
