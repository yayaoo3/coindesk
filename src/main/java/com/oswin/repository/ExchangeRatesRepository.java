package com.oswin.repository;

import com.oswin.model.ForeignCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExchangeRatesRepository extends JpaRepository<ForeignCurrency, Integer> {

    ForeignCurrency findById(String id);

    @Transactional
    @Modifying
    @Query(value = "insert into Exchange_Rates (id, code, symbol, description, rate_float) " +
            " values(:id,:code,:symbol, :description, :rate_float)", nativeQuery = true)
    Integer saveForeignCurrency(@Param("id") String id, @Param("code") String code, @Param("symbol") String symbol,
                                @Param("description") String description, @Param("rate_float") double rate_float);

    @Transactional
    @Modifying
    @Query(value = "update Exchange_Rates fc set fc.symbol =?1 , fc.description = ?2 , fc.rate_float = ?3 " +
            " where fc.id = ?4 and fc.code = ?5" , nativeQuery = true)
    Integer update(String symbol, String description, double rate_float, String id, String code);

    List<ForeignCurrency> findAll();

    @Transactional
    @Modifying
    @Query(value = "delete from ForeignCurrency e where e.id = ?1")
    Integer deleteById(String id);
}
