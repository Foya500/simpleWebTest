package com.foya.service;


import com.foya.dao.LookupTypeDao;
import com.foya.dao.LookupTypeDao;
import com.foya.model.FyTbLookupType;
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


    public List<FyTbLookupType> findAll(){

        List<FyTbLookupType> returnList =new ArrayList<>();
        lookupTypeDao.findAll().forEach(returnList::add);
        return returnList;
    }

    public FyTbLookupType findById(String FyTbLookupTypeId){
        Optional<FyTbLookupType> u= lookupTypeDao.findById(FyTbLookupTypeId);
        return u.get();
    }

    public Collection<FyTbLookupType>  findByDscr(String dscr){
        Collection<FyTbLookupType> u= lookupTypeDao.findByDscrLike(dscr);
        return u;
    }

    public Page<FyTbLookupType> findAll(Pageable pageable){
;
        return lookupTypeDao.findAll(pageable);

    }

}
