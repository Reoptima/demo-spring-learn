package com.example.demo.repo;

import com.example.demo.models.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkRepository extends CrudRepository<Work, Long> {
    List<Work> findByNameContains(String name);
}
