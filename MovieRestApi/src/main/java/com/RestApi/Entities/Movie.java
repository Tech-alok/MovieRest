package com.RestApi.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Movie {
	@Id
	@GeneratedValue
	private int id;
	@Column(unique = true, nullable = false)
	private String title;
	@Column(nullable = false)
	private Date release_date;

	@Column(nullable = false)
	private String plot;

	@JsonIgnoreProperties("movies")
	@ManyToMany(mappedBy = "movies", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	Set<Actor> actors = new HashSet<Actor>();

	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Movie(int id, String title, Date release_date, String plot, Set<Actor> actors) {
		super();
		this.id = id;
		this.title = title;
		this.release_date = release_date;
		this.plot = plot;
		this.actors = actors;
	}

	public String getTitle() {
		return title;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getRelease_date() {
		return release_date;
	}

	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}

	public void addActors(Actor actor) {
		this.getActors().add(actor);
		actor.getMovies().add(this);
	}

	public void deleteActors(Actor actor) {
		this.getActors().remove(actor);
		actor.getMovies().remove(this);
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", release_date=" + release_date + "]";
	}
}