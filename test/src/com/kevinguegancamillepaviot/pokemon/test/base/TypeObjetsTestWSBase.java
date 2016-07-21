/**************************************************************************
 * TypeObjetsTestWSBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.test.base;

import java.util.ArrayList;

import android.database.Cursor;

import com.kevinguegancamillepaviot.pokemon.data.TypeObjetsWebServiceClientAdapter;
import com.kevinguegancamillepaviot.pokemon.data.RestClient.RequestConstants;
import com.kevinguegancamillepaviot.pokemon.entity.TypeObjets;
import com.kevinguegancamillepaviot.pokemon.fixture.TypeObjetsDataLoader;
import com.kevinguegancamillepaviot.pokemon.test.utils.TypeObjetsUtils;
import com.kevinguegancamillepaviot.pokemon.test.utils.TestUtils;

import com.google.mockwebserver.MockResponse;

import junit.framework.Assert;

/** TypeObjets Web Service Test.
 * 
 * @see android.app.Fragment
 */
public abstract class TypeObjetsTestWSBase extends TestWSBase {
    /** model {@link TypeObjets}. */
    protected TypeObjets model;
    /** web {@link TypeObjetsWebServiceClientAdapter}. */
    protected TypeObjetsWebServiceClientAdapter web;
    /** entities ArrayList<TypeObjets>. */
    protected ArrayList<TypeObjets> entities;
    /** nbEntities Number of entities. */
    protected int nbEntities = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String host = this.server.getHostName();
        int port = this.server.getPort();

        this.web = new TypeObjetsWebServiceClientAdapter(
                this.ctx, host, port, RequestConstants.HTTP);
        
        this.entities = new ArrayList<TypeObjets>();        
        this.entities.addAll(TypeObjetsDataLoader.getInstance(this.ctx).getMap().values());
        
        if (entities.size() > 0) {
            this.model = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += TypeObjetsDataLoader.getInstance(this.ctx).getMap().size();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /** Test case Create Entity */
    public void testInsert() {
        this.server.enqueue(new MockResponse().setBody("{'result'='0'}"));

        int result = this.web.insert(this.model);

        Assert.assertTrue(result >= 0);
    }
    
    /** Test case Get Entity. */
    public void testGet() {
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        int result = this.web.get(this.model);

        Assert.assertTrue(result >= 0);
    }

    /** Test case Read Entity. */
    public void testQuery() {
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        Cursor result = this.web.query(this.model.getId());
        
        Assert.assertTrue(result.getCount() >= 0);
    }

    /** Test case get all Entity. */
    public void testGetAll() {
        this.server.enqueue(new MockResponse().setBody("{TypeObjetss :"
            + this.web.itemsToJson(this.entities).toString() + "}"));

        ArrayList<TypeObjets> typeObjetsList = 
                new ArrayList<TypeObjets>();

        int result = this.web.getAll(typeObjetsList);

        Assert.assertEquals(typeObjetsList.size(), this.entities.size());
    }
    
    /** Test case Update Entity. */
    public void testUpdate() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.update(this.model);

        Assert.assertTrue(result >= 0);
        
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        TypeObjets item = new TypeObjets();
        item.setId(this.model.getId());

        result = this.web.get(item);
        
        TypeObjetsUtils.equals(this.model, item);
    }
    
    /** Test case Delete Entity. */
    public void testDelete() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.delete(this.model);

        Assert.assertTrue(result == 0);

        this.server.enqueue(new MockResponse().setBody("{}"));

        result = this.web.get(this.model);

        Assert.assertTrue(result < 0);
    }
}
