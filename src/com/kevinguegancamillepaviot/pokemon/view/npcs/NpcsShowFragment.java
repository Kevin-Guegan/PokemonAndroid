/**************************************************************************
 * NpcsShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.npcs;


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
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Pokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;
import com.kevinguegancamillepaviot.pokemon.entity.Objets;
import com.kevinguegancamillepaviot.pokemon.harmony.view.DeleteDialog;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragment;
import com.kevinguegancamillepaviot.pokemon.harmony.view.MultiLoader;
import com.kevinguegancamillepaviot.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.kevinguegancamillepaviot.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.kevinguegancamillepaviot.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.kevinguegancamillepaviot.pokemon.provider.utils.NpcsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.NpcsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.DresseursContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.BadgesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetsContract;

/** Npcs show fragment.
 *
 * This fragment gives you an interface to show a Npcs.
 * 
 * @see android.app.Fragment
 */
public class NpcsShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Npcs model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** profession View. */
    protected TextView professionView;
    /** texte View. */
    protected TextView texteView;
    /** pokemon View. */
    protected TextView pokemonView;
    /** badge View. */
    protected TextView badgeView;
    /** objet View. */
    protected TextView objetView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Npcs. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.npcs_nom);
        this.professionView =
            (TextView) view.findViewById(
                    R.id.npcs_profession);
        this.texteView =
            (TextView) view.findViewById(
                    R.id.npcs_texte);
        this.pokemonView =
            (TextView) view.findViewById(
                    R.id.npcs_pokemon);
        this.badgeView =
            (TextView) view.findViewById(
                    R.id.npcs_badge);
        this.objetView =
            (TextView) view.findViewById(
                    R.id.npcs_objet);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.npcs_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.npcs_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getProfession() != null) {
            this.professionView.setText(this.model.getProfession());
        }
        if (this.model.getTexte() != null) {
            this.texteView.setText(this.model.getTexte());
        }
        if (this.model.getPokemon() != null) {
            String pokemonValue = "";
            for (Pokemons item : this.model.getPokemon()) {
                pokemonValue += item.getId() + ",";
            }
            this.pokemonView.setText(pokemonValue);
        }
        if (this.model.getBadge() != null) {
            String badgeValue = "";
            for (Badges item : this.model.getBadge()) {
                badgeValue += item.getId() + ",";
            }
            this.badgeView.setText(badgeValue);
        }
        if (this.model.getObjet() != null) {
            String objetValue = "";
            for (Objets item : this.model.getObjet()) {
                objetValue += item.getId() + ",";
            }
            this.objetView.setText(objetValue);
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
                        R.layout.fragment_npcs_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Npcs) intent.getParcelableExtra(NpcsContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Npcs to get the data from.
     */
    public void update(Npcs item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    NpcsProviderAdapter.NPCS_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    NpcsShowFragment.this.onNpcsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/pokemon"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    NpcsShowFragment.this.onPokemonLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/badge"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    NpcsShowFragment.this.onBadgeLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/objet"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    NpcsShowFragment.this.onObjetLoaded(c);
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
    public void onNpcsLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            NpcsContract.cursorToItem(
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
    public void onPokemonLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setPokemon(PokemonsContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setPokemon(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onBadgeLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setBadge(BadgesContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setBadge(null);
                    this.loadData();
            }
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
            this.model.setObjet(ObjetsContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setObjet(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the NpcsEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    NpcsEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(NpcsContract.PARCEL, this.model);
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
        private Npcs item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build NpcsSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Npcs item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new NpcsProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                NpcsShowFragment.this.onPostDelete();
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

