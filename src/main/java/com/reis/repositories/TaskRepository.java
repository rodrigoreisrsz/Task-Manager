package com.reis.repositories;


import com.reis.entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface TaskRepository extends JpaRepository<Tasks, Integer>{

    Map<Object, Object> findById(Long id);
}



