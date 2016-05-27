/**************************************************************************
 * NpcstoBadgesContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;



import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcstoBadgesContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class NpcstoBadgesContractBase {


        /** NpcsInternalId_id. */
    public static final String COL_NPCSINTERNALID_ID =
            "NpcsInternalId_id";
    /** Alias. */
    public static final String ALIASED_COL_NPCSINTERNALID_ID =
            NpcstoBadgesContract.TABLE_NAME + "." + COL_NPCSINTERNALID_ID;

    /** badge_id. */
    public static final String COL_BADGE_ID =
            "badge_id";
    /** Alias. */
    public static final String ALIASED_COL_BADGE_ID =
            NpcstoBadgesContract.TABLE_NAME + "." + COL_BADGE_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "NpcstoBadges";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "NpcstoBadges";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        NpcstoBadgesContract.COL_NPCSINTERNALID_ID,
        
        NpcstoBadgesContract.COL_BADGE_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        NpcstoBadgesContract.ALIASED_COL_NPCSINTERNALID_ID,
        
        NpcstoBadgesContract.ALIASED_COL_BADGE_ID
    };

}
