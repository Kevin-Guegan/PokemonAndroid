/**************************************************************************
 * ObjetsShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.objets;


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
import com.kevinguegancamillepaviot.pokemon.entity.Objets;
import com.kevinguegancamillepaviot.pokemon.harmony.view.DeleteDialog;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragment;
import com.kevinguegancamillepaviot.pokemon.harmony.view.MultiLoader;
import com.kevinguegancamillepaviot.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.kevinguegancamillepaviot.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.kevinguegancamillepaviot.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.kevinguegancamillepaviot.pokemon.provider.utils.ObjetsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.ObjetsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeObjetsContract;

/** Objets show fragment.
 *
 * This fragment gives you an interface to show a Objets.
 * 
 * @see android.app.Fragment
 */
public class ObjetsShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Objets model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** quantite View. */
    protected TextView quantiteView;
    /** objet View. */
    protected TextView objetView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Objets. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.objets_nom);
        this.quantiteView =
            (TextView) view.findViewById(
                    R.id.objets_quantite);
        this.objetView =
            (TextView) view.findViewById(
                    R.id.objets_objet);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.objets_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.objets_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.quantiteView.setText(String.valueOf(this.model.getQuantite()));
        if (this.model.getObjet() != null) {
            this.objetView.setText(
                    String.valueOf(this.model.getObjet().getId()));
        }
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
                        R.layout.fragment_objets_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Objets) intent.getParcelableExtra(ObjetsContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Objets to get the data from.
     */
    public void update(Objets item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    ObjetsProviderAdapter.OBJETS_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ObjetsShowFragment.this.onObjetsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/objet"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ObjetsShowFragment.this.onObjetLoaded(c);
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
    public void onObjetsLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            ObjetsContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onObjetLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setObjet(TypeObjetsContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setObjet(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the ObjetsEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    ObjetsEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(ObjetsContract.PARCEL, this.model);
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
        private Objets item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build ObjetsSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Objets item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new ObjetsProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                ObjetsShowFragment.this.onPostDelete();
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

