/**************************************************************************
 * TypeDePokemonsShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.typedepokemons;


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
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;
import com.kevinguegancamillepaviot.pokemon.harmony.view.DeleteDialog;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragment;
import com.kevinguegancamillepaviot.pokemon.harmony.view.MultiLoader;
import com.kevinguegancamillepaviot.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.kevinguegancamillepaviot.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.kevinguegancamillepaviot.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.kevinguegancamillepaviot.pokemon.provider.utils.TypeDePokemonsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokedexsContract;

/** TypeDePokemons show fragment.
 *
 * This fragment gives you an interface to show a TypeDePokemons.
 * 
 * @see android.app.Fragment
 */
public class TypeDePokemonsShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected TypeDePokemons model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** attaque View. */
    protected TextView attaqueView;
    /** attaque_spe View. */
    protected TextView attaque_speView;
    /** defence View. */
    protected TextView defenceView;
    /** defence_spe View. */
    protected TextView defence_speView;
    /** vitesse View. */
    protected TextView vitesseView;
    /** pv View. */
    protected TextView pvView;
    /** type View. */
    protected TextView typeView;
    /** typeDePokemon View. */
    protected TextView typeDePokemonView;
    /** pokedex View. */
    protected TextView pokedexView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no TypeDePokemons. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_nom);
        this.attaqueView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_attaque);
        this.attaque_speView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_attaque_spe);
        this.defenceView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_defence);
        this.defence_speView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_defence_spe);
        this.vitesseView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_vitesse);
        this.pvView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_pv);
        this.typeView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_type);
        this.typeDePokemonView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_typedepokemon);
        this.pokedexView =
            (TextView) view.findViewById(
                    R.id.typedepokemons_pokedex);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.typedepokemons_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.typedepokemons_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.attaqueView.setText(String.valueOf(this.model.getAttaque()));
        this.attaque_speView.setText(String.valueOf(this.model.getAttaque_spe()));
        this.defenceView.setText(String.valueOf(this.model.getDefence()));
        this.defence_speView.setText(String.valueOf(this.model.getDefence_spe()));
        this.vitesseView.setText(String.valueOf(this.model.getVitesse()));
        this.pvView.setText(String.valueOf(this.model.getPv()));
        if (this.model.getType() != null) {
            this.typeView.setText(
                    String.valueOf(this.model.getType().getId()));
        }
        if (this.model.getTypeDePokemon() != null) {
            String typeDePokemonValue = "";
            for (TypeDePokemons item : this.model.getTypeDePokemon()) {
                typeDePokemonValue += item.getId() + ",";
            }
            this.typeDePokemonView.setText(typeDePokemonValue);
        }
        if (this.model.getPokedex() != null) {
            String pokedexValue = "";
            for (Pokedexs item : this.model.getPokedex()) {
                pokedexValue += item.getId() + ",";
            }
            this.pokedexView.setText(pokedexValue);
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
                        R.layout.fragment_typedepokemons_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((TypeDePokemons) intent.getParcelableExtra(TypeDePokemonsContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The TypeDePokemons to get the data from.
     */
    public void update(TypeDePokemons item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    TypeDePokemonsShowFragment.this.onTypeDePokemonsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/type"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    TypeDePokemonsShowFragment.this.onTypeLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/typedepokemon"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    TypeDePokemonsShowFragment.this.onTypeDePokemonLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/pokedex"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    TypeDePokemonsShowFragment.this.onPokedexLoaded(c);
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
    public void onTypeDePokemonsLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            TypeDePokemonsContract.cursorToItem(
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
    public void onTypeLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setType(TypesContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setType(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onTypeDePokemonLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setTypeDePokemon(TypeDePokemonsContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setTypeDePokemon(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onPokedexLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setPokedex(PokedexsContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setPokedex(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the TypeDePokemonsEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    TypeDePokemonsEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(TypeDePokemonsContract.PARCEL, this.model);
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
        private TypeDePokemons item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build TypeDePokemonsSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final TypeDePokemons item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new TypeDePokemonsProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                TypeDePokemonsShowFragment.this.onPostDelete();
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

