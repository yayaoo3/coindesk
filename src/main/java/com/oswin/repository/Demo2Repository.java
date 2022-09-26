package com.oswin.repository;

import com.oswin.model.Demo2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Demo2Repository extends JpaRepository<Demo2,Integer> {
    Demo2 findById(String id);

    @Modifying
    @Query("update Demo2 d set d.name =?1 , type = ?2 where id = ?3")
    Integer update(String name,String type,String id);

    List<Demo2> findAll();
    Integer deleteById(String id);
}
