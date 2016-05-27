package com.kevinguegancamillepaviot.pokemon.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;

@Entity
@Rest
public class Npcs  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.STRING)
	private String profession;
	
	@Column(type = Type.STRING)
	private String texte;
	
	@OneToMany()
	@Column(nullable = true)
	private ArrayList<Pokemons> pokemon;
	
	@ManyToMany()
	@Column(nullable = true)
	private ArrayList<Badges> badge;
	
	@ManyToMany()
	@Column(nullable = true)
	private ArrayList<Objets> objet;

    /**
     * Default constructor.
     */
    public Npcs() {

    }

     /**
     * Get the Id.
     * @return the id
     */
    public int getId() {
         return this.id;
    }
     /**
     * Set the Id.
     * @param value the id to set
     */
    public void setId(final int value) {
         this.id = value;
    }
     /**
     * Get the Nom.
     * @return the nom
     */
    public String getNom() {
         return this.nom;
    }
     /**
     * Set the Nom.
     * @param value the nom to set
     */
    public void setNom(final String value) {
         this.nom = value;
    }
     /**
     * Get the Profession.
     * @return the profession
     */
    public String getProfession() {
         return this.profession;
    }
     /**
     * Set the Profession.
     * @param value the profession to set
     */
    public void setProfession(final String value) {
         this.profession = value;
    }
     /**
     * Get the Texte.
     * @return the texte
     */
    public String getTexte() {
         return this.texte;
    }
     /**
     * Set the Texte.
     * @param value the texte to set
     */
    public void setTexte(final String value) {
         this.texte = value;
    }
     /**
     * Get the Pokemon.
     * @return the pokemon
     */
    public ArrayList<Pokemons> getPokemon() {
         return this.pokemon;
    }
     /**
     * Set the Pokemon.
     * @param value the pokemon to set
     */
    public void setPokemon(final ArrayList<Pokemons> value) {
         this.pokemon = value;
    }
     /**
     * Get the Badge.
     * @return the badge
     */
    public ArrayList<Badges> getBadge() {
         return this.badge;
    }
     /**
     * Set the Badge.
     * @param value the badge to set
     */
    public void setBadge(final ArrayList<Badges> value) {
         this.badge = value;
    }
     /**
     * Get the Objet.
     * @return the objet
     */
    public ArrayList<Objets> getObjet() {
         return this.objet;
    }
     /**
     * Set the Objet.
     * @param value the objet to set
     */
    public void setObjet(final ArrayList<Objets> value) {
         this.objet = value;
    }
    /**
     * This stub of code is regenerated. DO NOT MODIFY.
     * 
     * @param dest Destination parcel
     * @param flags flags
     */
    public void writeToParcelRegen(Parcel dest, int flags) {
        if (this.parcelableParents == null) {
            this.parcelableParents = new ArrayList<Parcelable>();
        }
        if (!this.parcelableParents.contains(this)) {
            this.parcelableParents.add(this);
        }
        dest.writeInt(this.getId());
        if (this.getNom() != null) {
            dest.writeInt(1);
            dest.writeString(this.getNom());
        } else {
            dest.writeInt(0);
        }
        if (this.getProfession() != null) {
            dest.writeInt(1);
            dest.writeString(this.getProfession());
        } else {
            dest.writeInt(0);
        }
        if (this.getTexte() != null) {
            dest.writeInt(1);
            dest.writeString(this.getTexte());
        } else {
            dest.writeInt(0);
        }

        if (this.getPokemon() != null) {
            dest.writeInt(this.getPokemon().size());
            for (Pokemons item : this.getPokemon()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getBadge() != null) {
            dest.writeInt(this.getBadge().size());
            for (Badges item : this.getBadge()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getObjet() != null) {
            dest.writeInt(this.getObjet().size());
            for (Objets item : this.getObjet()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }
    }

    /**
     * Regenerated Parcel Constructor. 
     *
     * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
     *
     * @param parc The parcel to read from
     */
    public void readFromParcel(Parcel parc) {
        this.setId(parc.readInt());
        int nomBool = parc.readInt();
        if (nomBool == 1) {
            this.setNom(parc.readString());
        }
        int professionBool = parc.readInt();
        if (professionBool == 1) {
            this.setProfession(parc.readString());
        }
        int texteBool = parc.readInt();
        if (texteBool == 1) {
            this.setTexte(parc.readString());
        }

        int nbPokemon = parc.readInt();
        if (nbPokemon > -1) {
            ArrayList<Pokemons> items =
                new ArrayList<Pokemons>();
            for (int i = 0; i < nbPokemon; i++) {
                items.add((Pokemons) parc.readParcelable(
                        Pokemons.class.getClassLoader()));
            }
            this.setPokemon(items);
        }

        int nbBadge = parc.readInt();
        if (nbBadge > -1) {
            ArrayList<Badges> items =
                new ArrayList<Badges>();
            for (int i = 0; i < nbBadge; i++) {
                items.add((Badges) parc.readParcelable(
                        Badges.class.getClassLoader()));
            }
            this.setBadge(items);
        }

        int nbObjet = parc.readInt();
        if (nbObjet > -1) {
            ArrayList<Objets> items =
                new ArrayList<Objets>();
            for (int i = 0; i < nbObjet; i++) {
                items.add((Objets) parc.readParcelable(
                        Objets.class.getClassLoader()));
            }
            this.setObjet(items);
        }
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Npcs(Parcel parc) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.readFromParcel(parc);

        // You can  implement your own parcel mechanics here.

    }

    /* This method is not regenerated. You can implement your own parcel mechanics here. */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.writeToParcelRegen(dest, flags);
        // You can  implement your own parcel mechanics here.
    }

    /**
     * Use this method to write this entity to a parcel from another entity.
     * (Useful for relations)
     *
     * @param parent The entity being parcelled that need to parcel this one
     * @param dest The destination parcel
     * @param flags The flags
     */
    public synchronized void writeToParcel(List<Parcelable> parents, Parcel dest, int flags) {
        this.parcelableParents = new ArrayList<Parcelable>(parents);
        dest.writeParcelable(this, flags);
        this.parcelableParents = null;
    }

    @Override
    public int describeContents() {
        // This should return 0 
        // or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
        return 0;
    }

    /**
     * Parcelable creator.
     */
    public static final Parcelable.Creator<Npcs> CREATOR
        = new Parcelable.Creator<Npcs>() {
        public Npcs createFromParcel(Parcel in) {
            return new Npcs(in);
        }
        
        public Npcs[] newArray(int size) {
            return new Npcs[size];
        }
    };

}
