package com.oswin.repository;

import com.oswin.model.Coin;
import com.oswin.model.Demo2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Integer> {

    Coin findById(String id);

    @Transactional
    @Modifying
    @Query(value = "insert into Cryptocurrency (id, CHART_NAME, CHINESE_NAME) values(:id,:chartName, :chineseName)", nativeQuery = true)
    Integer saveCoin(@Param("id") String id, @Param("chartName") String chartName,  @Param("chineseName") String chineseName);

    @Transactional
    @Modifying
    @Query("update Coin c set c.chartName =?1 , chineseName = ?2 , updatedTime = now() where id = ?3")
    Integer update(String chartName, String chineseName, String id);

    List<Coin> findAll();

    @Transactional
    @Modifying
    Integer deleteById(String id);
}
