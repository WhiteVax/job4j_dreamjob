package ru.job4j.dreamjob.persistence;

import ru.job4j.dreamjob.model.City;

import java.util.Collection;

public interface CityRepository {
    Collection<City> findAll();
    public City findById(int id);

}
