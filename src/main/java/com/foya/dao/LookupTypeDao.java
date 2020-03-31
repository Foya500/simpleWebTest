package com.foya.dao;
/*
*

* */

import com.foya.model.LookupType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import java.util.Collection;

public interface LookupTypeDao extends CrudRepository<LookupType, String> {


    Collection<LookupType> findByDscrLike(String dscr);

    Page<LookupType> findAll(Pageable pageable);

}
