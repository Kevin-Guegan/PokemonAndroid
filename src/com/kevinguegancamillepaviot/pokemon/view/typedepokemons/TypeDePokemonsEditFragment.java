/**************************************************************************
 * TypeDePokemonsEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.typedepokemons;

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
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Types;
import com.kevinguegancamillepaviot.pokemon.entity.Pokedexs;

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragment;
import com.kevinguegancamillepaviot.pokemon.harmony.widget.MultiEntityWidget;
import com.kevinguegancamillepaviot.pokemon.harmony.widget.SingleEntityWidget;
import com.kevinguegancamillepaviot.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.kevinguegancamillepaviot.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.utils.TypeDePokemonsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.TypesProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.PokedexsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.PokedexsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokedexsContract;

/** TypeDePokemons create fragment.
 *
 * This fragment gives you an interface to edit a TypeDePokemons.
 *
 * @see android.app.Fragment
 */
public class TypeDePokemonsEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected TypeDePokemons model = new TypeDePokemons();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** attaque View. */
    protected EditText attaqueView;
    /** attaque_spe View. */
    protected EditText attaque_speView;
    /** defence View. */
    protected EditText defenceView;
    /** defence_spe View. */
    protected EditText defence_speView;
    /** vitesse View. */
    protected EditText vitesseView;
    /** pv View. */
    protected EditText pvView;
    /** The type chooser component. */
    protected SingleEntityWidget typeWidget;
    /** The type Adapter. */
    protected SingleEntityWidget.EntityAdapter<Types>
            typeAdapter;
    /** The typeDePokemon chooser component. */
    protected MultiEntityWidget typeDePokemonWidget;
    /** The typeDePokemon Adapter. */
    protected MultiEntityWidget.EntityAdapter<TypeDePokemons>
            typeDePokemonAdapter;
    /** The pokedex chooser component. */
    protected MultiEntityWidget pokedexWidget;
    /** The pokedex Adapter. */
    protected MultiEntityWidget.EntityAdapter<Pokedexs>
            pokedexAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.typedepokemons_nom);
        this.attaqueView = (EditText) view.findViewById(
                R.id.typedepokemons_attaque);
        this.attaque_speView = (EditText) view.findViewById(
                R.id.typedepokemons_attaque_spe);
        this.defenceView = (EditText) view.findViewById(
                R.id.typedepokemons_defence);
        this.defence_speView = (EditText) view.findViewById(
                R.id.typedepokemons_defence_spe);
        this.vitesseView = (EditText) view.findViewById(
                R.id.typedepokemons_vitesse);
        this.pvView = (EditText) view.findViewById(
                R.id.typedepokemons_pv);
        this.typeAdapter =
                new SingleEntityWidget.EntityAdapter<Types>() {
            @Override
            public String entityToString(Types item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeWidget =
            (SingleEntityWidget) view.findViewById(R.id.typedepokemons_type_button);
        this.typeWidget.setAdapter(this.typeAdapter);
        this.typeWidget.setTitle(R.string.typedepokemons_type_dialog_title);
        this.typeDePokemonAdapter =
                new MultiEntityWidget.EntityAdapter<TypeDePokemons>() {
            @Override
            public String entityToString(TypeDePokemons item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeDePokemonWidget = (MultiEntityWidget) view.findViewById(
                        R.id.typedepokemons_typedepokemon_button);
        this.typeDePokemonWidget.setAdapter(this.typeDePokemonAdapter);
        this.typeDePokemonWidget.setTitle(R.string.typedepokemons_typedepokemon_dialog_title);
        this.pokedexAdapter =
                new MultiEntityWidget.EntityAdapter<Pokedexs>() {
            @Override
            public String entityToString(Pokedexs item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokedexWidget = (MultiEntityWidget) view.findViewById(
                        R.id.typedepokemons_pokedex_button);
        this.pokedexWidget.setAdapter(this.pokedexAdapter);
        this.pokedexWidget.setTitle(R.string.typedepokemons_pokedex_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.attaqueView.setText(String.valueOf(this.model.getAttaque()));
        this.attaque_speView.setText(String.valueOf(this.model.getAttaque_spe()));
        this.defenceView.setText(String.valueOf(this.model.getDefence()));
        this.defence_speView.setText(String.valueOf(this.model.getDefence_spe()));
        this.vitesseView.setText(String.valueOf(this.model.getVitesse()));
        this.pvView.setText(String.valueOf(this.model.getPv()));

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setAttaque(Integer.parseInt(
                    this.attaqueView.getEditableText().toString()));

        this.model.setAttaque_spe(Integer.parseInt(
                    this.attaque_speView.getEditableText().toString()));

        this.model.setDefence(Integer.parseInt(
                    this.defenceView.getEditableText().toString()));

        this.model.setDefence_spe(Integer.parseInt(
                    this.defence_speView.getEditableText().toString()));

        this.model.setVitesse(Integer.parseInt(
                    this.vitesseView.getEditableText().toString()));

        this.model.setPv(Integer.parseInt(
                    this.pvView.getEditableText().toString()));

        this.model.setType(this.typeAdapter.getSelectedItem());

        this.model.setTypeDePokemon(this.typeDePokemonAdapter.getCheckedItems());

        this.model.setPokedex(this.pokedexAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.typedepokemons_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.attaqueView.getText().toString().trim())) {
            error = R.string.typedepokemons_attaque_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.attaque_speView.getText().toString().trim())) {
            error = R.string.typedepokemons_attaque_spe_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.defenceView.getText().toString().trim())) {
            error = R.string.typedepokemons_defence_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.defence_speView.getText().toString().trim())) {
            error = R.string.typedepokemons_defence_spe_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.vitesseView.getText().toString().trim())) {
            error = R.string.typedepokemons_vitesse_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.pvView.getText().toString().trim())) {
            error = R.string.typedepokemons_pv_invalid_field_error;
        }
        if (this.typeAdapter.getSelectedItem() == null) {
            error = R.string.typedepokemons_type_invalid_field_error;
        }
        if (this.pokedexAdapter.getCheckedItems().isEmpty()) {
            error = R.string.typedepokemons_pokedex_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_typedepokemons_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (TypeDePokemons) intent.getParcelableExtra(
                TypeDePokemonsContract.PARCEL);

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
        private final TypeDePokemons entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final TypeDePokemonsEditFragment fragment,
                    final TypeDePokemons entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.typedepokemons_progress_save_title),
                    this.ctx.getString(
                            R.string.typedepokemons_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new TypeDePokemonsProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("TypeDePokemonsEditFragment", e.getMessage());
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
                        R.string.typedepokemons_error_edit));
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
        private TypeDePokemonsEditFragment fragment;
        /** type list. */
        private ArrayList<Types> typeList;
        /** typeDePokemon list. */
        private ArrayList<TypeDePokemons> typeDePokemonList;
    /** typeDePokemon list. */
        private ArrayList<TypeDePokemons> associatedTypeDePokemonList;
        /** pokedex list. */
        private ArrayList<Pokedexs> pokedexList;
    /** pokedex list. */
        private ArrayList<Pokedexs> associatedPokedexList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final TypeDePokemonsEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.typedepokemons_progress_load_relations_title),
                this.ctx.getString(
                    R.string.typedepokemons_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.typeList = 
                new TypesProviderUtils(this.ctx).queryAll();
            this.typeDePokemonList = 
                new TypeDePokemonsProviderUtils(this.ctx).queryAll();
            Uri typeDePokemonUri = TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI;
            typeDePokemonUri = Uri.withAppendedPath(typeDePokemonUri, 
                                    String.valueOf(this.fragment.model.getId()));
            typeDePokemonUri = Uri.withAppendedPath(typeDePokemonUri, "typeDePokemon");
            android.database.Cursor typeDePokemonCursor = 
                    this.ctx.getContentResolver().query(
                            typeDePokemonUri,
                            new String[]{TypeDePokemonsContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedTypeDePokemonList = new ArrayList<TypeDePokemons>();
            if (typeDePokemonCursor != null && typeDePokemonCursor.getCount() > 0) {
                while (typeDePokemonCursor.moveToNext()) {
                    int typeDePokemonId = typeDePokemonCursor.getInt(
                            typeDePokemonCursor.getColumnIndex(TypeDePokemonsContract.COL_ID));
                    for (TypeDePokemons typeDePokemon : this.typeDePokemonList) {
                        if (typeDePokemon.getId() ==  typeDePokemonId) {
                            this.associatedTypeDePokemonList.add(typeDePokemon);
                        }
                    }
                }
                typeDePokemonCursor.close();
            }
            this.pokedexList = 
                new PokedexsProviderUtils(this.ctx).queryAll();
            Uri pokedexUri = TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI;
            pokedexUri = Uri.withAppendedPath(pokedexUri, 
                                    String.valueOf(this.fragment.model.getId()));
            pokedexUri = Uri.withAppendedPath(pokedexUri, "pokedex");
            android.database.Cursor pokedexCursor = 
                    this.ctx.getContentResolver().query(
                            pokedexUri,
                            new String[]{PokedexsContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedPokedexList = new ArrayList<Pokedexs>();
            if (pokedexCursor != null && pokedexCursor.getCount() > 0) {
                while (pokedexCursor.moveToNext()) {
                    int pokedexId = pokedexCursor.getInt(
                            pokedexCursor.getColumnIndex(PokedexsContract.COL_ID));
                    for (Pokedexs pokedex : this.pokedexList) {
                        if (pokedex.getId() ==  pokedexId) {
                            this.associatedPokedexList.add(pokedex);
                        }
                    }
                }
                pokedexCursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onTypeLoaded(this.typeList);
            this.fragment.model.setTypeDePokemon(this.associatedTypeDePokemonList);
            this.fragment.onTypeDePokemonLoaded(this.typeDePokemonList);
            this.fragment.model.setPokedex(this.associatedPokedexList);
            this.fragment.onPokedexLoaded(this.pokedexList);

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
     * Called when type have been loaded.
     * @param items The loaded items
     */
    protected void onTypeLoaded(ArrayList<Types> items) {
        this.typeAdapter.loadData(items);
        
        if (this.model.getType() != null) {
            for (Types item : items) {
                if (item.getId() == this.model.getType().getId()) {
                    this.typeAdapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when typeDePokemon have been loaded.
     * @param items The loaded items
     */
    protected void onTypeDePokemonLoaded(ArrayList<TypeDePokemons> items) {
        this.typeDePokemonAdapter.loadData(items);
        this.typeDePokemonAdapter.setCheckedItems(this.model.getTypeDePokemon());
    }
    /**
     * Called when pokedex have been loaded.
     * @param items The loaded items
     */
    protected void onPokedexLoaded(ArrayList<Pokedexs> items) {
        this.pokedexAdapter.loadData(items);
        this.pokedexAdapter.setCheckedItems(this.model.getPokedex());
    }
}
