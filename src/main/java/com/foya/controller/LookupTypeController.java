package com.foya.controller;

import com.foya.model.FyTbLookupType;
import com.foya.service.LookupTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("lookupType")
public class LookupTypeController {

		@Autowired
		LookupTypeService lookupTypeService;

	    @GetMapping("/findAll")
	    public List<FyTbLookupType> findAll() {
	       return lookupTypeService.findAll();
	    }

		@GetMapping("/findById")
		public FyTbLookupType findById(@RequestParam(value="lookupType", defaultValue="") String lookupType) {
			return lookupTypeService.findById(lookupType);
		}

		@GetMapping("/dscr")
		public Collection<FyTbLookupType> findByDscr(@RequestParam(value="dscr", defaultValue="") String dscr) {
			return lookupTypeService.findByDscr(dscr);
		}


		/*
		* {"content":[[{"lookupType":"aaa","dscr":"aaa"}],
		* "pageable":{"sort":{"sorted":true,"unsorted":false,"empty":false},
		*           "offset":10,"pageNumber":1,"pageSize":10,"paged":true,"unpaged":false},
		* "last":true,"totalPages":1,"totalElements":1,"number":1,"size":10,"
		* sort":{"sorted":true,"unsorted":false,"empty":false},"numberOfElements":0,"first":false,"empty":true}
		*
		* */
	@GetMapping("/findAllByPageable")
	public Page<FyTbLookupType> findAllByPageable(@PageableDefault(value = 15, sort = { "lookupType" }, direction = Sort.Direction.DESC)
													  Pageable pageable) {
		return lookupTypeService.findAll(pageable);
	}

}
