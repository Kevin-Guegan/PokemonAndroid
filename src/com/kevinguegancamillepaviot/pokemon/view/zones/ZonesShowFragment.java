/**************************************************************************
 * ZonesShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.zones;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kevinguegancamillepaviot.pokemon.R;
import com.kevinguegancamillepaviot.pokemon.entity.Zones;
import com.kevinguegancamillepaviot.pokemon.harmony.view.DeleteDialog;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragment;
import com.kevinguegancamillepaviot.pokemon.harmony.view.MultiLoader;
import com.kevinguegancamillepaviot.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.kevinguegancamillepaviot.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.kevinguegancamillepaviot.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.kevinguegancamillepaviot.pokemon.provider.utils.ZonesProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.ZonesProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ZonesContract;

/** Zones show fragment.
 *
 * This fragment gives you an interface to show a Zones.
 * 
 * @see android.app.Fragment
 */
public class ZonesShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Zones model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Zones. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.zones_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.zones_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        } else {
            this.dataLayout.setVisibility(View.GONE);
            this.emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(
                        R.layout.fragment_zones_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Zones) intent.getParcelableExtra(ZonesContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Zones to get the data from.
     */
    public void update(Zones item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    ZonesProviderAdapter.ZONES_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ZonesShowFragment.this.onZonesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.init();
        }
    }

    /**
     * Called when the entity has been loaded.
     * 
     * @param c The cursor of this entity
     */
    public void onZonesLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            ZonesContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }

    /**
     * Calls the ZonesEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    ZonesEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(ZonesContract.PARCEL, this.model);
        intent.putExtras(extras);

        this.getActivity().startActivity(intent);
    }
    /**
     * Shows a confirmation dialog.
     */
    @Override
    public void onClickDelete() {
        new DeleteDialog(this.getActivity(), this).show();
    }

    @Override
    public void onDeleteDialogClose(boolean ok) {
        if (ok) {
            new DeleteTask(this.getActivity(), this.model).execute();
        }
    }
    
    /** 
     * Called when delete task is done.
     */    
    public void onPostDelete() {
        if (this.deleteCallback != null) {
            this.deleteCallback.onItemDeleted();
        }
    }

    /**
     * This class will remove the entity into the DB.
     * It runs asynchronously.
     */
    private class DeleteTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private android.content.Context ctx;
        /** Entity to delete. */
        private Zones item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build ZonesSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Zones item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new ZonesProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                ZonesShowFragment.this.onPostDelete();
            }
            super.onPostExecute(result);
        }
        
        

    }

    /**
     * Callback for item deletion.
     */ 
    public interface DeleteCallback {
        /** Called when current item has been deleted. */
        void onItemDeleted();
    }
}

