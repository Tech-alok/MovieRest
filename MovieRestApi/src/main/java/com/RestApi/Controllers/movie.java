package com.RestApi.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestApi.Entities.Actor;
import com.RestApi.Entities.Movie;
import com.RestApi.Repo.ActorRepository;
import com.RestApi.Repo.MovieRepository;

@RestController
@RequestMapping("/movies")
public class movie {

	@Autowired
	ActorRepository actorRepository;

	@Autowired
	MovieRepository movieRepository;


	public void movieValidation(int movieId) {
		if (!movieRepository.existsById(movieId)) {
			throw new RuntimeException("Movie " + movieId + " Not found");
		}
	}

	@PostMapping("")
	public Movie createMovie(@Valid @RequestBody Movie movies) {
		return movieRepository.save(movies);
	}

	@GetMapping("")
	List<Movie> getMovies() {
		return movieRepository.findAll();
	}

	@PutMapping("/{id}")
	public Movie updateMovie(@PathVariable(value = "id") int movieId, @Valid @RequestBody Movie movieRequest) {
		this.movieValidation(movieId);
		Movie movie = movieRepository.findById(movieId);
		movie.setTitle(movieRequest.getTitle());
		movie.setRelease_date(movieRequest.getRelease_date());
		movie.setPlot(movieRequest.getPlot());
		return movieRepository.save(movie);

	}

	@GetMapping("/{id}")
	public Movie getMovie(@PathVariable(value = "id") int movieId) {
		this.movieValidation(movieId);
		return movieRepository.findById(movieId);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable("id") int movieId) {
		this.movieValidation(movieId);
		Movie movie = movieRepository.findById(movieId);
		for (Actor actor : movie.getActors())
			movie.deleteActors(actor);

		movieRepository.deleteById(movieId);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}/actors")
	public Movie putMoviesActors(
			@Valid @PathVariable(value = "id") int movieId,
			@RequestBody HashMap<String, ArrayList<Integer>> actors) {
		this.movieValidation(movieId);
		ArrayList<Integer> actorIds = actors.get("actors");

		Movie movie = movieRepository.findById(movieId);
		for (int actorId : actorIds) {
			if (!actorRepository.existsById(actorId))
				throw new RuntimeException("Actor " + actorId + " Not found");

			movie.addActors(actorRepository.findById(actorId));
		}
		return movieRepository.save(movie);
	}

	@DeleteMapping("/{id}/actors")
	public Movie deleteMoviesActors(@Valid @PathVariable(value = "id") int movieId,
			@RequestBody HashMap<String, ArrayList<Integer>> actors) {
		this.movieValidation(movieId);
		ArrayList<Integer> actorIds = actors.get("actors");

		Movie movie = movieRepository.findById(movieId);
		for (int actorId : actorIds) {
			if (!actorRepository.existsById(actorId))
				throw new RuntimeException("Actor " + actorId + " Not found");

			movie.deleteActors(actorRepository.findById(actorId));
		}
		return movieRepository.save(movie);
	}

}
