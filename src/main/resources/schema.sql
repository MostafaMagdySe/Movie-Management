CREATE TABLE public.roles (
                              id SERIAL PRIMARY KEY,
                              role VARCHAR(20) NOT NULL UNIQUE
);


INSERT INTO public.roles (role) VALUES ('Admin'), ('User');



CREATE TABLE public.users (
                              id SERIAL PRIMARY KEY,
                              username VARCHAR(20) NOT NULL UNIQUE,
                              email VARCHAR(40) NOT NULL DEFAULT '0',
                              password VARCHAR(200) NOT NULL,
                              phone VARCHAR(11) NOT NULL DEFAULT '0',
                              role_id INTEGER NOT NULL DEFAULT 2,
                              CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES public.roles(id)
);

CREATE TABLE public.movies (
                               id SERIAL PRIMARY KEY,
                               title VARCHAR(500),
                               year VARCHAR(10),
                               rated VARCHAR(50),
                               released VARCHAR(50),
                               runtime VARCHAR(50),
                               genre VARCHAR(255),
                               director VARCHAR(255),
                               writer VARCHAR(255),
                               actors VARCHAR(255),
                               plot VARCHAR(255),
                               language VARCHAR(255),
                               country VARCHAR(255),
                               awards VARCHAR(255),
                               poster VARCHAR(350),
                               metascore VARCHAR(30),
                               imdb_votes VARCHAR(30),
                               imdbid VARCHAR(50),
                               type VARCHAR(50),
                               box_office VARCHAR(50),
                               imdb_rating VARCHAR(10),
                               rotten_tomatoes VARCHAR(30),
                               rotten_tomatoes_rating VARCHAR(30),
                               metacritic VARCHAR(30),
                               metacritic_rating VARCHAR(30),
                               response BOOLEAN
);

CREATE TABLE public.rating (
                               id SERIAL PRIMARY KEY,
                               rating INTEGER,
                               user_id INTEGER,
                               movie_id INTEGER,
                               comment VARCHAR(355),
                               CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES public.movies(id) ON DELETE CASCADE,
                               CONSTRAINT rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

CREATE TABLE public.resetpassword (
                                      id SERIAL PRIMARY KEY,
                                      email VARCHAR(50),
                                      code VARCHAR(50),
                                      createdat TIMESTAMP
);

