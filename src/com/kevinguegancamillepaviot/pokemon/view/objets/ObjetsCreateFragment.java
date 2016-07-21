/**************************************************************************
 * ObjetsCreateFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 19, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.view.objets;

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
import com.kevinguegancamillepaviot.pokemon.entity.Objets;
import com.kevinguegancamillepaviot.pokemon.entity.TypeObjets;

import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyFragment;

import com.kevinguegancamillepaviot.pokemon.harmony.widget.SingleEntityWidget;
import com.kevinguegancamillepaviot.pokemon.menu.SaveMenuWrapper.SaveMenuInterface;
import com.kevinguegancamillepaviot.pokemon.provider.utils.ObjetsProviderUtils;
import com.kevinguegancamillepaviot.pokemon.provider.utils.TypeObjetsProviderUtils;

/**
 * Objets create fragment.
 *
 * This fragment gives you an interface to create a Objets.
 */
public class ObjetsCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Objets model = new Objets();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** quantite View. */
    protected EditText quantiteView;
    /** The objet chooser component. */
    protected SingleEntityWidget objetWidget;
    /** The objet Adapter. */
    protected SingleEntityWidget.EntityAdapter<TypeObjets> 
                objetAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.objets_nom);
        this.quantiteView =
            (EditText) view.findViewById(R.id.objets_quantite);
        this.objetAdapter = 
                new SingleEntityWidget.EntityAdapter<TypeObjets>() {
            @Override
            public String entityToString(TypeObjets item) {
                return String.valueOf(item.getId());
            }
        };
        this.objetWidget =
            (SingleEntityWidget) view.findViewById(R.id.objets_objet_button);
        this.objetWidget.setAdapter(this.objetAdapter);
        this.objetWidget.setTitle(R.string.objets_objet_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.quantiteView.setText(String.valueOf(this.model.getQuantite()));

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setQuantite(Integer.parseInt(
                    this.quantiteView.getEditableText().toString()));

        this.model.setObjet(this.objetAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.objets_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.quantiteView.getText().toString().trim())) {
            error = R.string.objets_quantite_invalid_field_error;
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
                R.layout.fragment_objets_create,
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
        private final Objets entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final ObjetsCreateFragment fragment,
                final Objets entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.objets_progress_save_title),
                    this.ctx.getString(
                            R.string.objets_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new ObjetsProviderUtils(this.ctx).insert(
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
                                R.string.objets_error_create));
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
        private ObjetsCreateFragment fragment;
        /** objet list. */
        private ArrayList<TypeObjets> objetList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final ObjetsCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.objets_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.objets_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.objetList = 
                new TypeObjetsProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
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
