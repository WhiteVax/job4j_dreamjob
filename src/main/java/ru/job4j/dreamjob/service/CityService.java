package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ThreadSafe
public class CityService {
    private final Map<Integer, City> cities = new ConcurrentHashMap<>();

    public CityService() {
        cities.put(1, new City(1, "Москва"));
        cities.put(2, new City(2, "Тула"));
        cities.put(3, new City(3, "Самара"));
        cities.put(4, new City(4, "Новомосковск"));
        cities.put(5, new City(5, "Ставрополь"));
        cities.put(6, new City(6, "Екб"));
        cities.put(7, new City(7, "Пятигорск"));
        cities.put(8, new City(8, "Омск"));
    }

    public List<City> getAllCities() {
        return new ArrayList<>(cities.values());
    }

    public City findById(int id) {
        return cities.get(id);
    }
}
