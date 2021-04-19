create table person (
    id serial not null primary key,
    birth_year varchar(255),
    eye_color varchar(255),
    gender integer,
    hair_color varchar(255),
    height integer,
    mass integer,
    name varchar(255) not null,
    skin_color varchar(255),
    planet_id bigint,
    specie_id bigint
);

alter table person
   add constraint FKq6v0yf9hmtw159tw3nj9ndx2o
   foreign key (planet_id)
   references planet;

alter table person
   add constraint FKt6y193esdnylhqg4p1jclq4th
   foreign key (specie_id)
   references specie;