/**************************************************************************
 * NpcsTestProviderBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.kevinguegancamillepaviot.pokemon.provider.NpcsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.utils.NpcsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;

import com.kevinguegancamillepaviot.pokemon.data.NpcsSQLiteAdapter;

import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;
import com.kevinguegancamillepaviot.pokemon.entity.Objets;


import java.util.ArrayList;
import com.kevinguegancamillepaviot.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Npcs database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit NpcsTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class NpcsTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected NpcsSQLiteAdapter adapter;

    protected Npcs entity;
    protected ContentResolver provider;
    protected NpcsProviderUtils providerUtils;

    protected ArrayList<Npcs> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new NpcsSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new NpcsProviderUtils(this.getContext());
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /********** Direct Provider calls. *******/

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        Uri result = null;
        if (this.entity != null) {
            Npcs npcs = NpcsUtils.generateRandom(this.ctx);

            try {
                ContentValues values = NpcsContract.itemToContentValues(npcs, 0);
                values.remove(NpcsContract.COL_ID);
                result = this.provider.insert(NpcsProviderAdapter.NPCS_URI, values);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertNotNull(result);
            Assert.assertTrue(Integer.parseInt(result.getPathSegments().get(1)) > 0);        
            
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Npcs result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        NpcsProviderAdapter.NPCS_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = NpcsContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            NpcsUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Npcs> result = null;
        try {
            android.database.Cursor c = this.provider.query(NpcsProviderAdapter.NPCS_URI, this.adapter.getCols(), null, null, null);
            result = NpcsContract.cursorToItems(c);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            Npcs npcs = NpcsUtils.generateRandom(this.ctx);

            try {
                npcs.setId(this.entity.getId());
                if (this.entity.getBadge() != null) {
                    npcs.getBadge().addAll(this.entity.getBadge());
                }
                if (this.entity.getObjet() != null) {
                    npcs.getObjet().addAll(this.entity.getObjet());
                }

                ContentValues values = NpcsContract.itemToContentValues(npcs, 0);
                result = this.provider.update(
                    Uri.parse(NpcsProviderAdapter.NPCS_URI
                        + "/"
                        + npcs.getId()),
                    values,
                    null,
                    null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertTrue(result > 0);
        }
    }

    /** Test case UpdateAll Entity */
    @SmallTest
    public void testUpdateAll() {
        int result = -1;
        if (this.entities != null) {
            if (this.entities.size() > 0) {
                Npcs npcs = NpcsUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = NpcsContract.itemToContentValues(npcs, 0);
                    values.remove(NpcsContract.COL_ID);
    
                    result = this.provider.update(NpcsProviderAdapter.NPCS_URI, values, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                Assert.assertEquals(result, this.nbEntities);
            }
        }
    }

    /** Test case Delete Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            try {
                result = this.provider.delete(
                        Uri.parse(NpcsProviderAdapter.NPCS_URI
                            + "/" 
                            + this.entity.getId()),
                        null,
                        null);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Assert.assertTrue(result >= 0);
        }

    }

    /** Test case DeleteAll Entity */
    @SmallTest
    public void testDeleteAll() {
        int result = -1;
        if (this.entities != null) {
            if (this.entities.size() > 0) {
    
                try {
                    result = this.provider.delete(NpcsProviderAdapter.NPCS_URI, null, null);
    
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                Assert.assertEquals(result, this.nbEntities);
            }
        }
    }

    /****** Provider Utils calls ********/

    /** Test case Read Entity by provider utils. */
    @SmallTest
    public void testUtilsRead() {
        Npcs result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            NpcsUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Npcs> result = null;
        result = this.providerUtils.queryAll();

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity by provider utils. */
    @SmallTest
    public void testUtilsUpdate() {
        int result = -1;
        if (this.entity != null) {
            Npcs npcs = NpcsUtils.generateRandom(this.ctx);

            npcs.setId(this.entity.getId());
            if (this.entity.getBadge() != null) {
                for (Badges badge : this.entity.getBadge()) {
                    boolean found = false;
                    for (Badges badge2 : npcs.getBadge()) {
                        if (badge.getId() == badge2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        npcs.getBadge().add(badge);
                    }
                }
            }
            if (this.entity.getObjet() != null) {
                for (Objets objet : this.entity.getObjet()) {
                    boolean found = false;
                    for (Objets objet2 : npcs.getObjet()) {
                        if (objet.getId() == objet2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        npcs.getObjet().add(objet);
                    }
                }
            }
            result = this.providerUtils.update(npcs);

            Assert.assertTrue(result > 0);
        }
    }


    /** Test case Delete Entity by provider utils. */
    @SmallTest
    public void testUtilsDelete() {
        int result = -1;
        if (this.entity != null) {
            result = this.providerUtils.delete(this.entity);
            Assert.assertTrue(result >= 0);
        }

    }
}
