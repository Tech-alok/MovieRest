package com.RestApi.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RestApi.Entities.*;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
	Actor findById(int actorId);
}
