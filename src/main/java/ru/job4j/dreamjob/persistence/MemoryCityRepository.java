package ru.job4j.dreamjob.persistence;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryCityRepository implements CityRepository {
    private final Map<Integer, City> cities = new HashMap<>() {
        {
            cities.put(0, new City(0, "Не указан"));
            cities.put(1, new City(1, "Москва"));
            cities.put(2, new City(2, "Тула"));
            cities.put(3, new City(3, "Самара"));
            cities.put(4, new City(4, "Новомосковск"));
            cities.put(5, new City(5, "Ставрополь"));
            cities.put(6, new City(6, "Екб"));
            cities.put(7, new City(7, "Пятигорск"));
            cities.put(8, new City(8, "Омск"));
        }
    };

    @Override
    public Collection<City> findAll() {
        return cities.values();
    }

    @Override
    public City findById(int id) {
        return cities.get(id);
    }
}
