package com.afkl.travel.exercise.repository;

import com.afkl.travel.exercise.data.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to interact with Location table
 */
@Repository
public interface TranslationRepository extends CrudRepository<Location, Long> {

    /**
     * Use native query
     * to filter out required translation and parent child relation in the database, not in the application level
     *
     * @param language the language
     * @return
     */
    @Query(value =
            " SELECT l.* " +
                    " FROM LOCATION l LEFT JOIN LOCATION pl on l.parent_id = pl.id " +
                    " LEFT JOIN LOCATION ppl on pl.parent_id = ppl.id " +
                    " LEFT JOIN TRANSLATION t on l.id = t.location " +
                    " WHERE t.language = :language AND l.type = :type AND (ppl.code = :code OR pl.code = :code OR l.code = :code) ",
            nativeQuery = true)
    List<Location> findAllByLanguageAndTypeAndCode(@Param("language") String language);

    @Query(value =
            " SELECT DISTINCT l.* " +
                    " FROM LOCATION) ",
            nativeQuery = true)
    List<String> fetchAllDistinctLanguages();
}