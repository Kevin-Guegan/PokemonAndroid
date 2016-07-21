/**************************************************************************
 * TypeDePokemonsListFragment.java, pokemon Android
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
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression;
import com.kevinguegancamillepaviot.pokemon.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.kevinguegancamillepaviot.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.contract.TypeDePokemonsContract;
import com.kevinguegancamillepaviot.pokemon.harmony.view.HarmonyListFragment;
import com.kevinguegancamillepaviot.pokemon.R;
import com.kevinguegancamillepaviot.pokemon.entity.TypeDePokemons;


/** TypeDePokemons list fragment.
 *
 * This fragment gives you an interface to list all your TypeDePokemonss.
 *
 * @see android.app.Fragment
 */
public class TypeDePokemonsListFragment
        extends HarmonyListFragment<TypeDePokemons>
        implements CrudCreateMenuInterface {

    /** The adapter which handles list population. */
    protected TypeDePokemonsListAdapter mAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        final View view =
                inflater.inflate(R.layout.fragment_typedepokemons_list,
                        null);

        this.initializeHackCustomList(view,
                R.id.typedepokemonsProgressLayout,
                R.id.typedepokemonsListContainer);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        this.setEmptyText(this.getString(
                R.string.typedepokemons_empty_list));

        // Create an empty adapter we will use to display the loaded data.
        this.mAdapter = new TypeDePokemonsListAdapter(this.getActivity(), null);

        // Start out with a progress indicator.
        this.setListShown(false);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        /* Do click action inside your fragment here. */
    }

    @Override
    public Loader<android.database.Cursor> onCreateLoader(int id, Bundle bundle) {
        Loader<android.database.Cursor> result = null;
        CriteriaExpression crit = null;
        if (bundle != null) {
            crit = (CriteriaExpression) bundle.get(
                        CriteriaExpression.PARCELABLE);
        }

        if (crit != null) {
            result = new TypeDePokemonsListLoader(this.getActivity(),
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
                TypeDePokemonsContract.ALIASED_COLS,
                crit,
                null);
        } else {
            result = new TypeDePokemonsListLoader(this.getActivity(),
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
                TypeDePokemonsContract.ALIASED_COLS,
                null,
                null,
                null);
        }
        return result;
    }

    @Override
    public void onLoadFinished(
            Loader<android.database.Cursor> loader,
            android.database.Cursor data) {

        // Set the new data in the adapter.
        data.setNotificationUri(this.getActivity().getContentResolver(),
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI);

        this.mAdapter.swapCursor(data);

        if (this.getListAdapter() == null) {
            this.setListAdapter(this.mAdapter);
        }

        // The list should now be shown.
        if (this.isResumed()) {
            this.setListShown(true);
        } else {
            this.setListShownNoAnimation(true);
        }

        super.onLoadFinished(loader, data);
    }

    @Override
    public void onLoaderReset(Loader<android.database.Cursor> loader) {
        // Clear the data in the adapter.
        this.mAdapter.swapCursor(null);
    }
    
    @Override
    public void onClickAdd() {
        Intent intent = new Intent(this.getActivity(),
                    TypeDePokemonsCreateActivity.class);
        this.startActivity(intent);
    }

}
