package com.oswin.repository;

import com.oswin.model.Coin;
import com.oswin.model.Demo2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CoinRepository extends JpaRepository<Coin,Integer> {
    Coin findById(String id);

    @Transactional
    @Modifying
    @Query(value = "insert into Cryptocurrency (id, CHART_NAME, DISCLAIMER) values(:id,:chartName,:disclaimer)", nativeQuery = true)
    Integer saveCoin(@Param("id") String id,@Param("chartName") String chartName,@Param("disclaimer") String disclaimer);

    @Modifying
    @Query("update Coin c set c.chartName =?1 , disclaimer = ?2 where id = ?3")
    Integer update(String chartName,String disclaimer,String id);

    List<Coin> findAll();
    Integer deleteById(String id);
}
