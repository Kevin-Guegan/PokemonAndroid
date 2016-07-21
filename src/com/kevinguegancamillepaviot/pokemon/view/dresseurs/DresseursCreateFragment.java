/**************************************************************************
 * DresseursCreateFragment.java, pokemon Android
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
import com.kevinguegancamillepaviot.pokemon.provider.utils.DresseursProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.NpcsProviderUtils;

/**
 * Dresseurs create fragment.
 *
 * This fragment gives you an interface to create a Dresseurs.
 */
public class DresseursCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Dresseurs model = new Dresseurs();

    /** Fields View. */
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

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.dresseurs_nom);
        this.loginView =
            (EditText) view.findViewById(R.id.dresseurs_login);
        this.passwordView =
            (EditText) view.findViewById(R.id.dresseurs_password);
        this.npcAdapter = 
                new MultiEntityWidget.EntityAdapter<Npcs>() {
            @Override
            public String entityToString(Npcs item) {
                return String.valueOf(item.getId());
            }
        };
        this.npcWidget =
            (MultiEntityWidget) view.findViewById(R.id.dresseurs_npc_button);
        this.npcWidget.setAdapter(this.npcAdapter);
        this.npcWidget.setTitle(R.string.dresseurs_npc_dialog_title);
    }

    /** Load data from model to fields view. */
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

    /** Save data from fields view to model. */
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
        final View view = inflater.inflate(
                R.layout.fragment_dresseurs_create,
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
        private final Dresseurs entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final DresseursCreateFragment fragment,
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
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new DresseursProviderUtils(this.ctx).insert(
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
                                R.string.dresseurs_error_create));
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
        private DresseursCreateFragment fragment;
        /** npc list. */
        private ArrayList<Npcs> npcList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final DresseursCreateFragment fragment) {
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
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.npcAdapter.loadData(this.npcList);
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
