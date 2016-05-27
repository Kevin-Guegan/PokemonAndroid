/**************************************************************************
 * NpcsProviderUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kevinguegancamillepaviot.pokemon.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.kevinguegancamillepaviot.pokemon.provider.utils.ProviderUtils;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion;
import com.kevinguegancamillepaviot.pokemon.criterias.base.Criterion.Type;
import com.kevinguegancamillepaviot.pokemon.criterias.base.value.ArrayValue;
import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression;
import com.kevinguegancamillepaviot.pokemon.criterias.base.CriteriaExpression.GroupType;
import com.kevinguegancamillepaviot.pokemon.data.BadgesSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.data.ObjetsSQLiteAdapter;
import com.kevinguegancamillepaviot.pokemon.entity.Npcs;
import com.kevinguegancamillepaviot.pokemon.entity.Pokemons;
import com.kevinguegancamillepaviot.pokemon.entity.Badges;
import com.kevinguegancamillepaviot.pokemon.entity.Objets;

import com.kevinguegancamillepaviot.pokemon.provider.NpcsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.NpcstoBadgesProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.BadgesProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.ObjetstoNpcsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.ObjetsProviderAdapter;
import com.kevinguegancamillepaviot.pokemon.provider.PokemonProvider;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.DresseursContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.PokemonsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.NpcstoBadgesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.BadgesContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetstoNpcsContract;
import com.kevinguegancamillepaviot.pokemon.provider.contract.ObjetsContract;

/**
 * Npcs Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class NpcsProviderUtilsBase
            extends ProviderUtils<Npcs> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "NpcsProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public NpcsProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Npcs item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = NpcsContract.itemToContentValues(item);
        itemValues.remove(NpcsContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                NpcsProviderAdapter.NPCS_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getPokemon() != null && item.getPokemon().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokemonsContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPokemon().size(); i++) {
                inValue.addValue(String.valueOf(item.getPokemon().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokemonsProviderAdapter.POKEMONS_URI)
                    .withValueBackReference(
                            PokemonsContract
                                    .COL_NPCSPOKEMONINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getBadge() != null && item.getBadge().size() > 0) {
            for (Badges badges : item.getBadge()) {
                ContentValues badgesValues = new ContentValues();
                badgesValues.put(
                        NpcstoBadgesContract.COL_BADGE_ID,
                        badges.getId());

                operations.add(ContentProviderOperation.newInsert(
                    NpcstoBadgesProviderAdapter.NPCSTOBADGES_URI)
                        .withValues(badgesValues)
                        .withValueBackReference(
                                NpcstoBadgesContract.COL_NPCSINTERNALID_ID,
                                0)
                        .build());

            }
        }
        if (item.getObjet() != null && item.getObjet().size() > 0) {
            for (Objets objets : item.getObjet()) {
                ContentValues objetsValues = new ContentValues();
                objetsValues.put(
                        ObjetstoNpcsContract.COL_OBJET_ID,
                        objets.getId());

                operations.add(ContentProviderOperation.newInsert(
                    ObjetstoNpcsProviderAdapter.OBJETSTONPCS_URI)
                        .withValues(objetsValues)
                        .withValueBackReference(
                                ObjetstoNpcsContract.COL_NPCSINTERNALID_ID,
                                0)
                        .build());

            }
        }

        try {
            ContentProviderResult[] results =
                    prov.applyBatch(PokemonProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * Insert into DB.
     * @param item Npcs to insert
     * @param dresseursnpcInternalId dresseursnpcInternal Id
     * @return number of rows affected
     */
    public Uri insert(final Npcs item,
                             final int dresseursnpcInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = NpcsContract.itemToContentValues(item,
                    dresseursnpcInternalId);
        itemValues.remove(NpcsContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                NpcsProviderAdapter.NPCS_URI)
                    .withValues(itemValues)
                    .build());


        if (item.getPokemon() != null && item.getPokemon().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokemonsContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPokemon().size(); i++) {
                inValue.addValue(String.valueOf(item.getPokemon().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokemonsProviderAdapter.POKEMONS_URI)
                    .withValueBackReference(
                            PokemonsContract
                                    .COL_NPCSPOKEMONINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getBadge() != null && item.getBadge().size() > 0) {
            for (Badges badges : item.getBadge()) {
                ContentValues badgesValues = new ContentValues();
                badgesValues.put(
                        NpcstoBadgesContract.COL_BADGE_ID,
                        badges.getId());

                operations.add(ContentProviderOperation.newInsert(
                    NpcstoBadgesProviderAdapter.NPCSTOBADGES_URI)
                        .withValues(badgesValues)
                        .withValueBackReference(
                                NpcstoBadgesContract.COL_NPCSINTERNALID_ID,
                                0)
                        .build());

            }
        }
        if (item.getObjet() != null && item.getObjet().size() > 0) {
            for (Objets objets : item.getObjet()) {
                ContentValues objetsValues = new ContentValues();
                objetsValues.put(
                        ObjetstoNpcsContract.COL_OBJET_ID,
                        objets.getId());

                operations.add(ContentProviderOperation.newInsert(
                    ObjetstoNpcsProviderAdapter.OBJETSTONPCS_URI)
                        .withValues(objetsValues)
                        .withValueBackReference(
                                ObjetstoNpcsContract.COL_NPCSINTERNALID_ID,
                                0)
                        .build());

            }
        }

        try {
            ContentProviderResult[] results =
                prov.applyBatch(PokemonProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getLastPathSegment()));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * Delete from DB.
     * @param item Npcs
     * @return number of row affected
     */
    public int delete(final Npcs item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = NpcsProviderAdapter.NPCS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Npcs
     */
    public Npcs query(final Npcs item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Npcs
     */
    public Npcs query(final int id) {
        Npcs result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(NpcsContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            NpcsProviderAdapter.NPCS_URI,
            NpcsContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = NpcsContract.cursorToItem(cursor);

            result.setPokemon(
                this.getAssociatePokemon(result));
            result.setBadge(
                this.getAssociateBadge(result));
            result.setObjet(
                this.getAssociateObjet(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Npcs>
     */
    public ArrayList<Npcs> queryAll() {
        ArrayList<Npcs> result =
                    new ArrayList<Npcs>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                NpcsProviderAdapter.NPCS_URI,
                NpcsContract.ALIASED_COLS,
                null,
                null,
                null);

        result = NpcsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Npcs>
     */
    public ArrayList<Npcs> query(CriteriaExpression expression) {
        ArrayList<Npcs> result =
                    new ArrayList<Npcs>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                NpcsProviderAdapter.NPCS_URI,
                NpcsContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = NpcsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Npcs
     * @return number of rows updated
     */
    public int update(final Npcs item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = NpcsContract.itemToContentValues(item);

        Uri uri = NpcsProviderAdapter.NPCS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getPokemon() != null && item.getPokemon().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new pokemon for Npcs
            CriteriaExpression pokemonCrit = 
                    new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokemonsContract.COL_ID);
            crit.addValue(values);
            pokemonCrit.add(crit);


            for (Pokemons pokemon : item.getPokemon()) {
                values.addValue(
                    String.valueOf(pokemon.getId()));
            }
            selection = pokemonCrit.toSQLiteSelection();
            selectionArgs = pokemonCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonsProviderAdapter.POKEMONS_URI)
                    .withValue(
                            PokemonsContract.COL_NPCSPOKEMONINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated pokemon
            crit.setType(Type.NOT_IN);
            pokemonCrit.add(PokemonsContract.COL_NPCSPOKEMONINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonsProviderAdapter.POKEMONS_URI)
                    .withValue(
                            PokemonsContract.COL_NPCSPOKEMONINTERNAL_ID,
                            null)
                    .withSelection(
                            pokemonCrit.toSQLiteSelection(),
                            pokemonCrit.toSQLiteSelectionArgs())
                    .build());
        }

        operations.add(ContentProviderOperation.newDelete(NpcstoBadgesProviderAdapter.NPCSTOBADGES_URI)
                .withSelection(NpcstoBadgesContract.COL_NPCSINTERNALID_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (Badges badges : item.getBadge()) {
            ContentValues badgesValues = new ContentValues();
            badgesValues.put(NpcstoBadgesContract.COL_BADGE_ID,
                    badges.getId());
            badgesValues.put(NpcstoBadgesContract.COL_NPCSINTERNALID_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(NpcstoBadgesProviderAdapter.NPCSTOBADGES_URI)
                    .withValues(badgesValues)
                    .build());
        }
        operations.add(ContentProviderOperation.newDelete(ObjetstoNpcsProviderAdapter.OBJETSTONPCS_URI)
                .withSelection(ObjetstoNpcsContract.COL_NPCSINTERNALID_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (Objets objets : item.getObjet()) {
            ContentValues objetsValues = new ContentValues();
            objetsValues.put(ObjetstoNpcsContract.COL_OBJET_ID,
                    objets.getId());
            objetsValues.put(ObjetstoNpcsContract.COL_NPCSINTERNALID_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(ObjetstoNpcsProviderAdapter.OBJETSTONPCS_URI)
                    .withValues(objetsValues)
                    .build());
        }

        try {
            ContentProviderResult[] results = prov.applyBatch(PokemonProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * Updates the DB.
     * @param item Npcs
     * @param dresseursnpcInternalId dresseursnpcInternal Id
     * @return number of rows updated
     */
    public int update(final Npcs item,
                             final int dresseursnpcInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = NpcsContract.itemToContentValues(
                item,
                dresseursnpcInternalId);

        Uri uri = NpcsProviderAdapter.NPCS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getPokemon() != null && item.getPokemon().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new pokemon for Npcs
            CriteriaExpression pokemonCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokemonsContract.COL_ID);
            crit.addValue(values);
            pokemonCrit.add(crit);


            for (Pokemons pokemon : item.getPokemon()) {
                values.addValue(
                    String.valueOf(pokemon.getId()));
            }
            selection = pokemonCrit.toSQLiteSelection();
            selectionArgs = pokemonCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonsProviderAdapter.POKEMONS_URI)
                    .withValue(
                            PokemonsContract.COL_NPCSPOKEMONINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated pokemon
            crit.setType(Type.NOT_IN);
            pokemonCrit.add(PokemonsContract.COL_NPCSPOKEMONINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonsProviderAdapter.POKEMONS_URI)
                    .withValue(
                            PokemonsContract.COL_NPCSPOKEMONINTERNAL_ID,
                            null)
                    .withSelection(
                            pokemonCrit.toSQLiteSelection(),
                            pokemonCrit.toSQLiteSelectionArgs())
                    .build());
        }

        operations.add(ContentProviderOperation.newDelete(NpcstoBadgesProviderAdapter.NPCSTOBADGES_URI)
                .withSelection(NpcstoBadgesContract.COL_NPCSINTERNALID_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (Badges badges : item.getBadge()) {
            ContentValues badgesValues = new ContentValues();
            badgesValues.put(NpcstoBadgesContract.COL_BADGE_ID,
                    badges.getId());
            badgesValues.put(NpcstoBadgesContract.COL_NPCSINTERNALID_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(NpcstoBadgesProviderAdapter.NPCSTOBADGES_URI)
                    .withValues(badgesValues)
                    .build());
        }
        operations.add(ContentProviderOperation.newDelete(ObjetstoNpcsProviderAdapter.OBJETSTONPCS_URI)
                .withSelection(ObjetstoNpcsContract.COL_NPCSINTERNALID_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (Objets objets : item.getObjet()) {
            ContentValues objetsValues = new ContentValues();
            objetsValues.put(ObjetstoNpcsContract.COL_OBJET_ID,
                    objets.getId());
            objetsValues.put(ObjetstoNpcsContract.COL_NPCSINTERNALID_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(ObjetstoNpcsProviderAdapter.OBJETSTONPCS_URI)
                    .withValues(objetsValues)
                    .build());
        }

        try {
            ContentProviderResult[] results = prov.applyBatch(PokemonProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /** Relations operations. */
    /**
     * Get associate Pokemon.
     * @param item Npcs
     * @return Pokemons
     */
    public ArrayList<Pokemons> getAssociatePokemon(
            final Npcs item) {
        ArrayList<Pokemons> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokemonsCursor = prov.query(
                PokemonsProviderAdapter.POKEMONS_URI,
                PokemonsContract.ALIASED_COLS,
                PokemonsContract.ALIASED_COL_NPCSPOKEMONINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokemonsContract.cursorToItems(
                        pokemonsCursor);
        pokemonsCursor.close();

        return result;
    }

    /**
     * Get associate Badge.
     * @param item Npcs
     * @return Badges
     */
    public ArrayList<Badges> getAssociateBadge(
            final Npcs item) {
        ArrayList<Badges> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor npcstoBadgesCursor = prov.query(
                NpcstoBadgesProviderAdapter.NPCSTOBADGES_URI,
                NpcstoBadgesContract.ALIASED_COLS,
                NpcstoBadgesContract.ALIASED_COL_NPCSINTERNALID_ID 
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        if (npcstoBadgesCursor.getCount() > 0) {
            CriteriaExpression badgesCrits =
                    new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            ArrayValue arrayValue = new ArrayValue();
            inCrit.setKey(BadgesContract.ALIASED_COL_ID);
            inCrit.setType(Type.IN);
            inCrit.addValue(arrayValue);
            badgesCrits.add(inCrit);

            while (npcstoBadgesCursor.moveToNext()) {
                int index = npcstoBadgesCursor.getColumnIndex(
                        NpcstoBadgesContract.COL_BADGE_ID);
                String badgesId = npcstoBadgesCursor.getString(index);

                arrayValue.addValue(badgesId);
            }
            npcstoBadgesCursor.close();
            android.database.Cursor badgesCursor = prov.query(
                    BadgesProviderAdapter.BADGES_URI,
                    BadgesContract.ALIASED_COLS,
                    badgesCrits.toSQLiteSelection(),
                    badgesCrits.toSQLiteSelectionArgs(),
                    null);

            result = BadgesContract.cursorToItems(badgesCursor);
            badgesCursor.close();
        } else {
            result = new ArrayList<Badges>();
        }

        return result;
    }

    /**
     * Get associate Objet.
     * @param item Npcs
     * @return Objets
     */
    public ArrayList<Objets> getAssociateObjet(
            final Npcs item) {
        ArrayList<Objets> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor objetstoNpcsCursor = prov.query(
                ObjetstoNpcsProviderAdapter.OBJETSTONPCS_URI,
                ObjetstoNpcsContract.ALIASED_COLS,
                ObjetstoNpcsContract.ALIASED_COL_NPCSINTERNALID_ID 
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        if (objetstoNpcsCursor.getCount() > 0) {
            CriteriaExpression objetsCrits =
                    new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            ArrayValue arrayValue = new ArrayValue();
            inCrit.setKey(ObjetsContract.ALIASED_COL_ID);
            inCrit.setType(Type.IN);
            inCrit.addValue(arrayValue);
            objetsCrits.add(inCrit);

            while (objetstoNpcsCursor.moveToNext()) {
                int index = objetstoNpcsCursor.getColumnIndex(
                        ObjetstoNpcsContract.COL_OBJET_ID);
                String objetsId = objetstoNpcsCursor.getString(index);

                arrayValue.addValue(objetsId);
            }
            objetstoNpcsCursor.close();
            android.database.Cursor objetsCursor = prov.query(
                    ObjetsProviderAdapter.OBJETS_URI,
                    ObjetsContract.ALIASED_COLS,
                    objetsCrits.toSQLiteSelection(),
                    objetsCrits.toSQLiteSelectionArgs(),
                    null);

            result = ObjetsContract.cursorToItems(objetsCursor);
            objetsCursor.close();
        } else {
            result = new ArrayList<Objets>();
        }

        return result;
    }

}
