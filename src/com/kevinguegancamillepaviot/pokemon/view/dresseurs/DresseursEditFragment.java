/**************************************************************************
 * DresseursEditFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.dresseurs;

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
import com.kevinguegancamillepaviot.pokemon.entity.Dresseurs;
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragment;
import com.kevinguegancamillepaviot.pokemon.harmony.widget.MultiEntityWidget;
import com.kevinguegancamillepaviot.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.kevinguegancamillepaviot.pokemon.provider.DresseursProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.utils.DresseursProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.NpcsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.data.NpcsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.DresseursContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;

/** Dresseurs create fragment.
 *
 * This fragment gives you an interface to edit a Dresseurs.
 *
 * @see android.app.Fragment
 */
public class DresseursEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Dresseurs model = new Dresseurs();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** login View. */
    protected EditText loginView;
    /** password View. */
    protected EditText passwordView;
    /** The npc chooser component. */
    protected MultiEntityWidget npcWidget;
    /** The npc Adapter. */
    protected MultiEntityWidget.EntityAdapter<Npcs>
            npcAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.dresseurs_nom);
        this.loginView = (EditText) view.findViewById(
                R.id.dresseurs_login);
        this.passwordView = (EditText) view.findViewById(
                R.id.dresseurs_password);
        this.npcAdapter =
                new MultiEntityWidget.EntityAdapter<Npcs>() {
            @Override
            public String entityToString(Npcs item) {
                return String.valueOf(item.getId());
            }
        };
        this.npcWidget = (MultiEntityWidget) view.findViewById(
                        R.id.dresseurs_npc_button);
        this.npcWidget.setAdapter(this.npcAdapter);
        this.npcWidget.setTitle(R.string.dresseurs_npc_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getLogin() != null) {
            this.loginView.setText(this.model.getLogin());
        }
        if (this.model.getPassword() != null) {
            this.passwordView.setText(this.model.getPassword());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setLogin(this.loginView.getEditableText().toString());

        this.model.setPassword(this.passwordView.getEditableText().toString());

        this.model.setNpc(this.npcAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.dresseurs_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.loginView.getText().toString().trim())) {
            error = R.string.dresseurs_login_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.passwordView.getText().toString().trim())) {
            error = R.string.dresseurs_password_invalid_field_error;
        }
        if (this.npcAdapter.getCheckedItems().isEmpty()) {
            error = R.string.dresseurs_npc_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_dresseurs_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Dresseurs) intent.getParcelableExtra(
                DresseursContract.PARCEL);

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
        private final Dresseurs entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final DresseursEditFragment fragment,
                    final Dresseurs entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.dresseurs_progress_save_title),
                    this.ctx.getString(
                            R.string.dresseurs_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new DresseursProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("DresseursEditFragment", e.getMessage());
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
                        R.string.dresseurs_error_edit));
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
        private DresseursEditFragment fragment;
        /** npc list. */
        private ArrayList<Npcs> npcList;
    /** npc list. */
        private ArrayList<Npcs> associatedNpcList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final DresseursEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.dresseurs_progress_load_relations_title),
                this.ctx.getString(
                    R.string.dresseurs_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.npcList = 
                new NpcsProviderUtils(this.ctx).queryAll();
            Uri npcUri = DresseursProviderAdapter.DRESSEURS_URI;
            npcUri = Uri.withAppendedPath(npcUri, 
                                    String.valueOf(this.fragment.model.getId()));
            npcUri = Uri.withAppendedPath(npcUri, "npc");
            android.database.Cursor npcCursor = 
                    this.ctx.getContentResolver().query(
                            npcUri,
                            new String[]{NpcsContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedNpcList = new ArrayList<Npcs>();
            if (npcCursor != null && npcCursor.getCount() > 0) {
                while (npcCursor.moveToNext()) {
                    int npcId = npcCursor.getInt(
                            npcCursor.getColumnIndex(NpcsContract.COL_ID));
                    for (Npcs npc : this.npcList) {
                        if (npc.getId() ==  npcId) {
                            this.associatedNpcList.add(npc);
                        }
                    }
                }
                npcCursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setNpc(this.associatedNpcList);
            this.fragment.onNpcLoaded(this.npcList);

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
     * Called when npc have been loaded.
     * @param items The loaded items
     */
    protected void onNpcLoaded(ArrayList<Npcs> items) {
        this.npcAdapter.loadData(items);
        this.npcAdapter.setCheckedItems(this.model.getNpc());
    }
}
