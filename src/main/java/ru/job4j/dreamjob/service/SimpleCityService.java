package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.persistence.CityRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
@ThreadSafe
public class SimpleCityService implements CityService {
    private final CityRepository cityRepository;

    public SimpleCityService(CityRepository sql2oCityRepository) {
        this.cityRepository = sql2oCityRepository;
    }

    @Override
    public City findById(int id) {
        return cityRepository.findById(id);
    }

    @Override
    public Collection<City> findAll() {
        return new ArrayList<>(cityRepository.findAll());
    }
}
