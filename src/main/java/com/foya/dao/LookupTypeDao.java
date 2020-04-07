package com.foya.dao;
/*
*

* */

import com.foya.model.FyTbLookupType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import java.util.Collection;

public interface LookupTypeDao extends CrudRepository<FyTbLookupType, String> {


    Collection<FyTbLookupType> findByDscrLike(String dscr);

    Page<FyTbLookupType> findAll(Pageable pageable);

}
