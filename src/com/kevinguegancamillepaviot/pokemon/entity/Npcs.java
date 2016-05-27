package com.kevinguegancamillepaviot.pokemon.entity;

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
public class Npcs {

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
}
