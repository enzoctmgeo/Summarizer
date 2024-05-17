package com.example.summarize.repository;


import com.example.summarize.dto.GenderByStateSortDto;
import com.example.summarize.dto.StateGenderCountDto;
import com.example.summarize.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import jakarta.persistence.Tuple;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    // Query para >= 50 anos
    @Query("""
            select p.state, count(p) as cnt
            from Person p
            where EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM p.birthday) >= 50
            group by p.state
            order by cnt desc
            """)
    List<Object[]> findStateWithPeopleAgeGreaterEquals50();

    // Query para <= 20 anos
    @Query("""
            select p.state, count(p) as cnt
            from Person p
            where EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM p.birthday) <= 20
            group by p.state
            order by cnt desc
            """)
    List<Object[]> findStateWithPeopleAgeLowerEquals20();

    // Query para contar gêneros de todos os estados (1)
    @Query("""
             select p.state,
            sum(case when p.gender = 'male' then 1 else 0 end) as male,
            sum(case when p.gender = 'female' then 1 else 0 end) as female
            from Person p
            group by p.state
            """)
    List<Object[]> findGenderCountForAllStates();

    // Query para contar gêneros de todos os estados (2)
    @Query("""
           select p.state as state,
        sum(case when p.gender = 'male' then 1 else 0 end) as male,
        sum(case when p.gender = 'female' then 1 else 0 end) as female
        from Person p
        group by p.state
        """)
    List<Tuple> findGenderCountByStates();
}

