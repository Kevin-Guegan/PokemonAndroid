/**************************************************************************
 * TypesCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.types;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.kevinguegancamillepaviot.pokemon.R;
import com.kevinguegancamillepaviot.pokemon.entity.Types;
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Attaques;

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragment;
import com.kevinguegancamillepaviot.pokemon.harmony.widget.MultiEntityWidget;
import com.kevinguegancamillepaviot.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.kevinguegancamillepaviot.pokemon.provider.utils.TypesProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.TypeDePokemonsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.AttaquesProviderUtils;

/**
 * Types create fragment.
 *
 * This fragment gives you an interface to create a Types.
 */
public class TypesCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Types model = new Types();

    /** Fields View. */
    /** The typeDePokemon chooser component. */
    protected MultiEntityWidget typeDePokemonWidget;
    /** The typeDePokemon Adapter. */
    protected MultiEntityWidget.EntityAdapter<TypeDePokemons> 
                typeDePokemonAdapter;
    /** The attaque chooser component. */
    protected MultiEntityWidget attaqueWidget;
    /** The attaque Adapter. */
    protected MultiEntityWidget.EntityAdapter<Attaques> 
                attaqueAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.typeDePokemonAdapter = 
                new MultiEntityWidget.EntityAdapter<TypeDePokemons>() {
            @Override
            public String entityToString(TypeDePokemons item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeDePokemonWidget =
            (MultiEntityWidget) view.findViewById(R.id.types_typedepokemon_button);
        this.typeDePokemonWidget.setAdapter(this.typeDePokemonAdapter);
        this.typeDePokemonWidget.setTitle(R.string.types_typedepokemon_dialog_title);
        this.attaqueAdapter = 
                new MultiEntityWidget.EntityAdapter<Attaques>() {
            @Override
            public String entityToString(Attaques item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaqueWidget =
            (MultiEntityWidget) view.findViewById(R.id.types_attaque_button);
        this.attaqueWidget.setAdapter(this.attaqueAdapter);
        this.attaqueWidget.setTitle(R.string.types_attaque_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {


        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setTypeDePokemon(this.typeDePokemonAdapter.getCheckedItems());

        this.model.setAttaque(this.attaqueAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (this.typeDePokemonAdapter.getCheckedItems().isEmpty()) {
            error = R.string.types_typedepokemon_invalid_field_error;
        }
        if (this.attaqueAdapter.getCheckedItems().isEmpty()) {
            error = R.string.types_attaque_invalid_field_error;
        }
    
        if (error > 0) {
            Toast.makeText(this.getActivity(),
                this.getActivity().getString(error),
                Toast.LENGTH_SHORT).show();
        }
        return error == 0;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(
                R.layout.fragment_types_create,
                container,
                false);

        this.initializeComponent(view);
        this.loadData();
        return view;
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class CreateTask extends AsyncTask<Void, Void, Uri> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to persist. */
        private final Types entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final TypesCreateFragment fragment,
                final Types entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.types_progress_save_title),
                    this.ctx.getString(
                            R.string.types_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new TypesProviderUtils(this.ctx).insert(
                        this.entity);

            return result;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (result != null) {
                final HarmonyFragmentActivity activity =
                                         (HarmonyFragmentActivity) this.ctx;
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.types_error_create));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {

                            }
                        });
                builder.show();
            }

            this.progress.dismiss();
        }
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class LoadTask extends AsyncTask<Void, Void, Void> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Progress Dialog. */
        private ProgressDialog progress;
        /** Fragment. */
        private TypesCreateFragment fragment;
        /** typeDePokemon list. */
        private ArrayList<TypeDePokemons> typeDePokemonList;
        /** attaque list. */
        private ArrayList<Attaques> attaqueList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final TypesCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.types_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.types_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.typeDePokemonList = 
                new TypeDePokemonsProviderUtils(this.ctx).queryAll();
            this.attaqueList = 
                new AttaquesProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.typeDePokemonAdapter.loadData(this.typeDePokemonList);
            this.fragment.attaqueAdapter.loadData(this.attaqueList);
            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new CreateTask(this, this.model).execute();
        }
    }
}
