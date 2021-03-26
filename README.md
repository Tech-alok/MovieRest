 # Actor

GET /actors

POST /actors

GET /actors/{actorId}

PUT /actors/{actorId}

DELETE /actors/{actorId}


# Movie

GET /movies

POST /movies

GET /movies/{movieId}

PUT /movies/{movieId}

DELETE /movies/{movieId}

# Association between Actors and Movies

PUT /actors/{actorId}/movies

DELETE /actors/{actorId}/movies

PUT /movies/{movieId}/actors

DELETE /movies/{movieId}/actors


# Example of Request body for association, adding movies to a actor

PUT /actors/{actorId}/movies

{
"movies" : [] //list of movieIds
}
