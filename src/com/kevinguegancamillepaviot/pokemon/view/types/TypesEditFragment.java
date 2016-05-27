/**************************************************************************
 * TypesEditFragment.java, pokemon Android
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
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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
import com.kevinguegancamillepaviot.pokemon.provider.TypesProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.utils.TypesProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.TypeDePokemonsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.AttaquesProviderUtils;
import com.kevinguegancamillepaviot.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.AttaquesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.AttaquesContract;

/** Types create fragment.
 *
 * This fragment gives you an interface to edit a Types.
 *
 * @see android.app.Fragment
 */
public class TypesEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Types model = new Types();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.typeDePokemonAdapter =
                new MultiEntityWidget.EntityAdapter<TypeDePokemons>() {
            @Override
            public String entityToString(TypeDePokemons item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeDePokemonWidget = (MultiEntityWidget) view.findViewById(
                        R.id.types_typedepokemon_button);
        this.typeDePokemonWidget.setAdapter(this.typeDePokemonAdapter);
        this.typeDePokemonWidget.setTitle(R.string.types_typedepokemon_dialog_title);
        this.attaqueAdapter =
                new MultiEntityWidget.EntityAdapter<Attaques>() {
            @Override
            public String entityToString(Attaques item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaqueWidget = (MultiEntityWidget) view.findViewById(
                        R.id.types_attaque_button);
        this.attaqueWidget.setAdapter(this.attaqueAdapter);
        this.attaqueWidget.setTitle(R.string.types_attaque_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {


        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_types_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Types) intent.getParcelableExtra(
                TypesContract.PARCEL);

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
        private final Types entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final TypesEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new TypesProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("TypesEditFragment", e.getMessage());
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
                        R.string.types_error_edit));
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
        private TypesEditFragment fragment;
        /** typeDePokemon list. */
        private ArrayList<TypeDePokemons> typeDePokemonList;
    /** typeDePokemon list. */
        private ArrayList<TypeDePokemons> associatedTypeDePokemonList;
        /** attaque list. */
        private ArrayList<Attaques> attaqueList;
    /** attaque list. */
        private ArrayList<Attaques> associatedAttaqueList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final TypesEditFragment fragment) {
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
            Uri typeDePokemonUri = TypesProviderAdapter.TYPES_URI;
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
            this.attaqueList = 
                new AttaquesProviderUtils(this.ctx).queryAll();
            Uri attaqueUri = TypesProviderAdapter.TYPES_URI;
            attaqueUri = Uri.withAppendedPath(attaqueUri, 
                                    String.valueOf(this.fragment.model.getId()));
            attaqueUri = Uri.withAppendedPath(attaqueUri, "attaque");
            android.database.Cursor attaqueCursor = 
                    this.ctx.getContentResolver().query(
                            attaqueUri,
                            new String[]{AttaquesContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedAttaqueList = new ArrayList<Attaques>();
            if (attaqueCursor != null && attaqueCursor.getCount() > 0) {
                while (attaqueCursor.moveToNext()) {
                    int attaqueId = attaqueCursor.getInt(
                            attaqueCursor.getColumnIndex(AttaquesContract.COL_ID));
                    for (Attaques attaque : this.attaqueList) {
                        if (attaque.getId() ==  attaqueId) {
                            this.associatedAttaqueList.add(attaque);
                        }
                    }
                }
                attaqueCursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setTypeDePokemon(this.associatedTypeDePokemonList);
            this.fragment.onTypeDePokemonLoaded(this.typeDePokemonList);
            this.fragment.model.setAttaque(this.associatedAttaqueList);
            this.fragment.onAttaqueLoaded(this.attaqueList);

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
     * Called when typeDePokemon have been loaded.
     * @param items The loaded items
     */
    protected void onTypeDePokemonLoaded(ArrayList<TypeDePokemons> items) {
        this.typeDePokemonAdapter.loadData(items);
        this.typeDePokemonAdapter.setCheckedItems(this.model.getTypeDePokemon());
    }
    /**
     * Called when attaque have been loaded.
     * @param items The loaded items
     */
    protected void onAttaqueLoaded(ArrayList<Attaques> items) {
        this.attaqueAdapter.loadData(items);
        this.attaqueAdapter.setCheckedItems(this.model.getAttaque());
    }
}
