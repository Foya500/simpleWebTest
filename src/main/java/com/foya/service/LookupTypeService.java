package com.foya.service;


import com.foya.dao.LookupTypeDao;
import com.foya.model.LookupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class LookupTypeService {

    @Autowired
    LookupTypeDao lookupTypeDao;


    public List<LookupType> findAll(){

        List<LookupType> returnList =new ArrayList<>();
        lookupTypeDao.findAll().forEach(returnList::add);
        return returnList;
    }

    public LookupType findById(String lookupTypeId){
        Optional<LookupType> u= lookupTypeDao.findById(lookupTypeId);
        return u.get();
    }

    public Collection<LookupType>  findByDscr(String dscr){
        Collection<LookupType> u= lookupTypeDao.findByDscrLike(dscr);
        return u;
    }

    public Page<LookupType> findAll(Pageable pageable){
;
        return lookupTypeDao.findAll(pageable);

    }

}
