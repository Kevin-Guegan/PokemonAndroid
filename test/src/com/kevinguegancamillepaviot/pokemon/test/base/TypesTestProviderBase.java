/**************************************************************************
 * TypesTestProviderBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.kevinguegancamillepaviot.pokemon.provider.TypesProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.utils.TypesProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;

import com.kevinguegancamillepaviot.pokemon.data.TypesSQLiteAdapter;

import com.kevinguegancamillepaviot.pokemon.entity.Types;

import com.kevinguegancamillepaviot.pokemon.fixture.TypesDataLoader;

import java.util.ArrayList;
import com.kevinguegancamillepaviot.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Types database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypesTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class TypesTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected TypesSQLiteAdapter adapter;

    protected Types entity;
    protected ContentResolver provider;
    protected TypesProviderUtils providerUtils;

    protected ArrayList<Types> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new TypesSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Types>();
        this.entities.addAll(TypesDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += TypesDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new TypesProviderUtils(this.getContext());
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
            Types types = TypesUtils.generateRandom(this.ctx);

            try {
                ContentValues values = TypesContract.itemToContentValues(types);
                values.remove(TypesContract.COL_ID);
                result = this.provider.insert(TypesProviderAdapter.TYPES_URI, values);

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
        Types result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        TypesProviderAdapter.TYPES_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = TypesContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            TypesUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Types> result = null;
        try {
            android.database.Cursor c = this.provider.query(TypesProviderAdapter.TYPES_URI, this.adapter.getCols(), null, null, null);
            result = TypesContract.cursorToItems(c);
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
            Types types = TypesUtils.generateRandom(this.ctx);

            try {
                types.setId(this.entity.getId());

                ContentValues values = TypesContract.itemToContentValues(types);
                result = this.provider.update(
                    Uri.parse(TypesProviderAdapter.TYPES_URI
                        + "/"
                        + types.getId()),
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
                Types types = TypesUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = TypesContract.itemToContentValues(types);
                    values.remove(TypesContract.COL_ID);
    
                    result = this.provider.update(TypesProviderAdapter.TYPES_URI, values, null, null);
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
                        Uri.parse(TypesProviderAdapter.TYPES_URI
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
                    result = this.provider.delete(TypesProviderAdapter.TYPES_URI, null, null);
    
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
        Types result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            TypesUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Types> result = null;
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
            Types types = TypesUtils.generateRandom(this.ctx);

            types.setId(this.entity.getId());
            result = this.providerUtils.update(types);

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
