/**************************************************************************
 * NpcsCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.npcs;

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.kevinguegancamillepaviot.pokemon.R;
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Pokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;
import com.kevinguegancamillepaviot.pokemon.entity.Objets;

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragment;
import com.kevinguegancamillepaviot.pokemon.harmony.widget.MultiEntityWidget;
import com.kevinguegancamillepaviot.pokemon.harmony.widget.SingleEntityWidget;
import com.kevinguegancamillepaviot.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.kevinguegancamillepaviot.pokemon.provider.utils.NpcsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.PokemonsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.BadgesProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.ObjetsProviderUtils;

/**
 * Npcs create fragment.
 *
 * This fragment gives you an interface to create a Npcs.
 */
public class NpcsCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Npcs model = new Npcs();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** profession View. */
    protected EditText professionView;
    /** texte View. */
    protected EditText texteView;
    /** The pokemon chooser component. */
    protected MultiEntityWidget pokemonWidget;
    /** The pokemon Adapter. */
    protected MultiEntityWidget.EntityAdapter<Pokemons> 
                pokemonAdapter;
    /** The badge chooser component. */
    protected MultiEntityWidget badgeWidget;
    /** The badge Adapter. */
    protected MultiEntityWidget.EntityAdapter<Badges> 
                badgeAdapter;
    /** The objet chooser component. */
    protected MultiEntityWidget objetWidget;
    /** The objet Adapter. */
    protected MultiEntityWidget.EntityAdapter<Objets> 
                objetAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.npcs_nom);
        this.professionView =
            (EditText) view.findViewById(R.id.npcs_profession);
        this.texteView =
            (EditText) view.findViewById(R.id.npcs_texte);
        this.pokemonAdapter = 
                new MultiEntityWidget.EntityAdapter<Pokemons>() {
            @Override
            public String entityToString(Pokemons item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemonWidget =
            (MultiEntityWidget) view.findViewById(R.id.npcs_pokemon_button);
        this.pokemonWidget.setAdapter(this.pokemonAdapter);
        this.pokemonWidget.setTitle(R.string.npcs_pokemon_dialog_title);
        this.badgeAdapter = 
                new MultiEntityWidget.EntityAdapter<Badges>() {
            @Override
            public String entityToString(Badges item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgeWidget =
            (MultiEntityWidget) view.findViewById(R.id.npcs_badge_button);
        this.badgeWidget.setAdapter(this.badgeAdapter);
        this.badgeWidget.setTitle(R.string.npcs_badge_dialog_title);
        this.objetAdapter = 
                new MultiEntityWidget.EntityAdapter<Objets>() {
            @Override
            public String entityToString(Objets item) {
                return String.valueOf(item.getId());
            }
        };
        this.objetWidget =
            (MultiEntityWidget) view.findViewById(R.id.npcs_objet_button);
        this.objetWidget.setAdapter(this.objetAdapter);
        this.objetWidget.setTitle(R.string.npcs_objet_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getProfession() != null) {
            this.professionView.setText(this.model.getProfession());
        }
        if (this.model.getTexte() != null) {
            this.texteView.setText(this.model.getTexte());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setProfession(this.professionView.getEditableText().toString());

        this.model.setTexte(this.texteView.getEditableText().toString());

        this.model.setPokemon(this.pokemonAdapter.getCheckedItems());

        this.model.setBadge(this.badgeAdapter.getCheckedItems());

        this.model.setObjet(this.objetAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.npcs_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.professionView.getText().toString().trim())) {
            error = R.string.npcs_profession_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.texteView.getText().toString().trim())) {
            error = R.string.npcs_texte_invalid_field_error;
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
                R.layout.fragment_npcs_create,
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
        private final Npcs entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final NpcsCreateFragment fragment,
                final Npcs entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.npcs_progress_save_title),
                    this.ctx.getString(
                            R.string.npcs_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new NpcsProviderUtils(this.ctx).insert(
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
                                R.string.npcs_error_create));
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
        private NpcsCreateFragment fragment;
        /** pokemon list. */
        private ArrayList<Pokemons> pokemonList;
        /** badge list. */
        private ArrayList<Badges> badgeList;
        /** objet list. */
        private ArrayList<Objets> objetList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final NpcsCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.npcs_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.npcs_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.pokemonList = 
                new PokemonsProviderUtils(this.ctx).queryAll();
            this.badgeList = 
                new BadgesProviderUtils(this.ctx).queryAll();
            this.objetList = 
                new ObjetsProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.pokemonAdapter.loadData(this.pokemonList);
            this.fragment.badgeAdapter.loadData(this.badgeList);
            this.fragment.objetAdapter.loadData(this.objetList);
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
