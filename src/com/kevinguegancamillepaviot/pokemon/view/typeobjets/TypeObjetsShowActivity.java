/**************************************************************************
 * TypeObjetsShowActivity.java, pokemon Android
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

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kevinguegancamillepaviot.pokemon.view.typeobjets.TypeObjetsShowFragment.DeleteCallback;
import android.os.Bundle;

/** TypeObjets show Activity.
 *
 * This only contains a TypeObjetsShowFragment.
 *
 * @see android.app.Activity
 */
public class TypeObjetsShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_typeobjets_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
