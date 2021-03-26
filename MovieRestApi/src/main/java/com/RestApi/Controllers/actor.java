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
import com.RestApi.Repo.ActorRepository;
import com.RestApi.Repo.MovieRepository;

@RestController
public class actor {

	@Autowired
	ActorRepository actorRepository;

	@Autowired
	MovieRepository movieRepository;

	public void actorValidation(int actorId) {
		if (!actorRepository.existsById(actorId)) {
			throw new RuntimeException("actor " + actorId + " Not found");
		}
	}

	@PostMapping("/actors")
	public Actor createActor(@Valid @RequestBody Actor actor) {
		return actorRepository.save(actor);
	}

	@GetMapping("/actors")
	List<Actor> getActors() {
		return actorRepository.findAll();
	}

	@PutMapping("/actors/{id}")
	public Actor updateActor(@PathVariable(value = "id") int actorId, @Valid @RequestBody Actor actorRequest) {
		this.actorValidation(actorId);
		Actor actor = actorRepository.findById(actorId);
		actor.setBio(actorRequest.getBio());
		actor.setName(actorRequest.getName());
		actor.setSex(actor.getSex());
		return actorRepository.save(actor);

	}

	@GetMapping("/actors/{id}")
	public Actor getActor(@PathVariable("id") int actorId) {
		actorValidation(actorId);
		return actorRepository.findById(actorId);
	}

	@DeleteMapping("/actors/{id}")
	public ResponseEntity<?> deleteActor(@PathVariable("id") int actorId) {
		this.actorValidation(actorId);

		actorRepository.deleteById(actorId);
		return ResponseEntity.ok().build();
	}

	@PutMapping("actors/{id}/movies")
	public Actor putActorMovies(@Valid @PathVariable(value="id") int actorId, @RequestBody HashMap<String, ArrayList<Integer>> movies) {
		this.actorValidation(actorId);
		 ArrayList<Integer> movieIds = movies.get(movies);
		
		Actor actor = actorRepository.findById(actorId);
		for (int movieId : movieIds) {
			if (!movieRepository.existsById(movieId))
				throw new RuntimeException("Movie " + movieId + " Not found");

			actor.addMovies(movieRepository.findById(movieId));
		}
		return actorRepository.save(actor);
	}

	@DeleteMapping("actors/{id}/movies")
	public Actor deleteActorMovies(@Valid @PathVariable(value = "id") int actorId,
			@RequestBody HashMap<String, ArrayList<Integer>> movies) {
		ArrayList<Integer> movieIds = movies.get("movies");
		this.actorValidation(actorId);

		Actor actor = actorRepository.findById(actorId);
		for (int movieId : movieIds) {
			if (!movieRepository.existsById(movieId))
				throw new RuntimeException("Movie " + movieId + " Not found");
			actor.deleteMovies(movieRepository.findById(movieId));
		}
		return actorRepository.save(actor);
	}

}
