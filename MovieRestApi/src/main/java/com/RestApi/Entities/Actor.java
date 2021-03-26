package com.RestApi.Entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Actor {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String sex;

	@Column(nullable = false)
	private String bio;

	@JsonIgnoreProperties("actors")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "actor_movie", joinColumns = { @JoinColumn(name = "actor_id") }, inverseJoinColumns = {
			@JoinColumn(name = "movie_id") })
	Set<Movie> movies = new HashSet<Movie>();
	public Actor() {
	}

	

	public Actor(int id, String name, String sex, String bio, Set<Movie> movies) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.bio = bio;
		this.movies = movies;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}



	public String getBio() {
		return bio;
	}



	public void setBio(String bio) {
		this.bio = bio;
	}



	public Set<Movie> getMovies() {
		return movies;
	}



	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}



	public void addMovies(Movie movie) {
		this.getMovies().add(movie);
		movie.getActors().add(this);
	}

	public void deleteMovies(Movie movie) {
		this.getMovies().remove(movie);
		movie.getActors().remove(this);
	}



	@Override
	public String toString() {
		return "Actor [id=" + id + ", name=" + name + ", sex=" + sex + ", bio=" + bio + ", movies=" + movies + "]";
	}

	
}