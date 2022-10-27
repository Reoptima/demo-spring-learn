package com.example.demo.repo;

import com.example.demo.models.Cities;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CitiesRepository extends CrudRepository<Cities, Long> {
    List<Cities> findBynameContains(String title);
    List<Cities> findByname(String title);
}
