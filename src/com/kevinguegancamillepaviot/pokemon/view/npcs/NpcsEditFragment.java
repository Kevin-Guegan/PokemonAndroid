/**************************************************************************
 * NpcsEditFragment.java, pokemon Android
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
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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
import com.kevinguegancamillepaviot.pokemon.provider.NpcsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.utils.NpcsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.PokemonsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.BadgesProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.ObjetsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.data.PokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.BadgesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.ObjetsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.DresseursContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.BadgesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetsContract;

/** Npcs create fragment.
 *
 * This fragment gives you an interface to edit a Npcs.
 *
 * @see android.app.Fragment
 */
public class NpcsEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Npcs model = new Npcs();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.npcs_nom);
        this.professionView = (EditText) view.findViewById(
                R.id.npcs_profession);
        this.texteView = (EditText) view.findViewById(
                R.id.npcs_texte);
        this.pokemonAdapter =
                new MultiEntityWidget.EntityAdapter<Pokemons>() {
            @Override
            public String entityToString(Pokemons item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemonWidget = (MultiEntityWidget) view.findViewById(
                        R.id.npcs_pokemon_button);
        this.pokemonWidget.setAdapter(this.pokemonAdapter);
        this.pokemonWidget.setTitle(R.string.npcs_pokemon_dialog_title);
        this.badgeAdapter =
                new MultiEntityWidget.EntityAdapter<Badges>() {
            @Override
            public String entityToString(Badges item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgeWidget = (MultiEntityWidget) view.findViewById(
                        R.id.npcs_badge_button);
        this.badgeWidget.setAdapter(this.badgeAdapter);
        this.badgeWidget.setTitle(R.string.npcs_badge_dialog_title);
        this.objetAdapter =
                new MultiEntityWidget.EntityAdapter<Objets>() {
            @Override
            public String entityToString(Objets item) {
                return String.valueOf(item.getId());
            }
        };
        this.objetWidget = (MultiEntityWidget) view.findViewById(
                        R.id.npcs_objet_button);
        this.objetWidget.setAdapter(this.objetAdapter);
        this.objetWidget.setTitle(R.string.npcs_objet_dialog_title);
    }

    /** Load data from model to curr.fields view. */
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

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_npcs_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Npcs) intent.getParcelableExtra(
                NpcsContract.PARCEL);

        this.initializeComponent(view);
        this.loadData();

        return view;
    }

    /**
     * This class will update the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class EditTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final Npcs entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final NpcsEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new NpcsProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("NpcsEditFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result > 0) {
                final HarmonyFragmentActivity activity =
                        (HarmonyFragmentActivity) this.ctx;
                activity.setResult(HarmonyFragmentActivity.RESULT_OK);
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(this.ctx.getString(
                        R.string.npcs_error_edit));
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
        private NpcsEditFragment fragment;
        /** pokemon list. */
        private ArrayList<Pokemons> pokemonList;
    /** pokemon list. */
        private ArrayList<Pokemons> associatedPokemonList;
        /** badge list. */
        private ArrayList<Badges> badgeList;
    /** badge list. */
        private ArrayList<Badges> associatedBadgeList;
        /** objet list. */
        private ArrayList<Objets> objetList;
    /** objet list. */
        private ArrayList<Objets> associatedObjetList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final NpcsEditFragment fragment) {
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
            Uri pokemonUri = NpcsProviderAdapter.NPCS_URI;
            pokemonUri = Uri.withAppendedPath(pokemonUri, 
                                    String.valueOf(this.fragment.model.getId()));
            pokemonUri = Uri.withAppendedPath(pokemonUri, "pokemon");
            android.database.Cursor pokemonCursor = 
                    this.ctx.getContentResolver().query(
                            pokemonUri,
                            new String[]{PokemonsContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedPokemonList = new ArrayList<Pokemons>();
            if (pokemonCursor != null && pokemonCursor.getCount() > 0) {
                while (pokemonCursor.moveToNext()) {
                    int pokemonId = pokemonCursor.getInt(
                            pokemonCursor.getColumnIndex(PokemonsContract.COL_ID));
                    for (Pokemons pokemon : this.pokemonList) {
                        if (pokemon.getId() ==  pokemonId) {
                            this.associatedPokemonList.add(pokemon);
                        }
                    }
                }
                pokemonCursor.close();
            }
            this.badgeList = 
                new BadgesProviderUtils(this.ctx).queryAll();
            Uri badgeUri = NpcsProviderAdapter.NPCS_URI;
            badgeUri = Uri.withAppendedPath(badgeUri, 
                                    String.valueOf(this.fragment.model.getId()));
            badgeUri = Uri.withAppendedPath(badgeUri, "badge");
            android.database.Cursor badgeCursor = 
                    this.ctx.getContentResolver().query(
                            badgeUri,
                            new String[]{BadgesContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedBadgeList = new ArrayList<Badges>();
            if (badgeCursor != null && badgeCursor.getCount() > 0) {
                while (badgeCursor.moveToNext()) {
                    int badgeId = badgeCursor.getInt(
                            badgeCursor.getColumnIndex(BadgesContract.COL_ID));
                    for (Badges badge : this.badgeList) {
                        if (badge.getId() ==  badgeId) {
                            this.associatedBadgeList.add(badge);
                        }
                    }
                }
                badgeCursor.close();
            }
            this.objetList = 
                new ObjetsProviderUtils(this.ctx).queryAll();
            Uri objetUri = NpcsProviderAdapter.NPCS_URI;
            objetUri = Uri.withAppendedPath(objetUri, 
                                    String.valueOf(this.fragment.model.getId()));
            objetUri = Uri.withAppendedPath(objetUri, "objet");
            android.database.Cursor objetCursor = 
                    this.ctx.getContentResolver().query(
                            objetUri,
                            new String[]{ObjetsContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedObjetList = new ArrayList<Objets>();
            if (objetCursor != null && objetCursor.getCount() > 0) {
                while (objetCursor.moveToNext()) {
                    int objetId = objetCursor.getInt(
                            objetCursor.getColumnIndex(ObjetsContract.COL_ID));
                    for (Objets objet : this.objetList) {
                        if (objet.getId() ==  objetId) {
                            this.associatedObjetList.add(objet);
                        }
                    }
                }
                objetCursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setPokemon(this.associatedPokemonList);
            this.fragment.onPokemonLoaded(this.pokemonList);
            this.fragment.model.setBadge(this.associatedBadgeList);
            this.fragment.onBadgeLoaded(this.badgeList);
            this.fragment.model.setObjet(this.associatedObjetList);
            this.fragment.onObjetLoaded(this.objetList);

            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new EditTask(this, this.model).execute();
        }
    }

    /**
     * Called when pokemon have been loaded.
     * @param items The loaded items
     */
    protected void onPokemonLoaded(ArrayList<Pokemons> items) {
        this.pokemonAdapter.loadData(items);
        this.pokemonAdapter.setCheckedItems(this.model.getPokemon());
    }
    /**
     * Called when badge have been loaded.
     * @param items The loaded items
     */
    protected void onBadgeLoaded(ArrayList<Badges> items) {
        this.badgeAdapter.loadData(items);
        this.badgeAdapter.setCheckedItems(this.model.getBadge());
    }
    /**
     * Called when objet have been loaded.
     * @param items The loaded items
     */
    protected void onObjetLoaded(ArrayList<Objets> items) {
        this.objetAdapter.loadData(items);
        this.objetAdapter.setCheckedItems(this.model.getObjet());
    }
}
