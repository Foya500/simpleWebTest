package com.foya.controller;

import com.foya.model.LookupType;
import com.foya.service.LookupTypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
	    public List<LookupType> findAll() {
	       return lookupTypeService.findAll();
	    }

		@GetMapping("/findById")
		public LookupType findById(@RequestParam(value="lookupType", defaultValue="") String lookupType) {
			return lookupTypeService.findById(lookupType);
		}

		@GetMapping("/dscr")
		public Collection<LookupType> findByDscr(@RequestParam(value="dscr", defaultValue="") String dscr) {
			return lookupTypeService.findByDscr(dscr);
		}
}
