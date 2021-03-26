package com.RestApi.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RestApi.Entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	   Movie findById(int movieId);
}
