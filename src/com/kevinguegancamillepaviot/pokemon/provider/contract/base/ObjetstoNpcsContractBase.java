/**************************************************************************
 * ObjetstoNpcsContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Objets;



import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetstoNpcsContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ObjetstoNpcsContractBase {


        /** NpcsInternalId_id. */
    public static final String COL_NPCSINTERNALID_ID =
            "NpcsInternalId_id";
    /** Alias. */
    public static final String ALIASED_COL_NPCSINTERNALID_ID =
            ObjetstoNpcsContract.TABLE_NAME + "." + COL_NPCSINTERNALID_ID;

    /** objet_id. */
    public static final String COL_OBJET_ID =
            "objet_id";
    /** Alias. */
    public static final String ALIASED_COL_OBJET_ID =
            ObjetstoNpcsContract.TABLE_NAME + "." + COL_OBJET_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "ObjetstoNpcs";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "ObjetstoNpcs";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        ObjetstoNpcsContract.COL_NPCSINTERNALID_ID,
        
        ObjetstoNpcsContract.COL_OBJET_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        ObjetstoNpcsContract.ALIASED_COL_NPCSINTERNALID_ID,
        
        ObjetstoNpcsContract.ALIASED_COL_OBJET_ID
    };

}
