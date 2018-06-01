/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv243.service;

import cz.fi.muni.pv243.entity.Parser;
import cz.fi.muni.pv243.entity.Restaurant;
import cz.fi.muni.pv243.infinispan.annotation.CachedStore;
import cz.fi.muni.pv243.infinispan.store.CachedParserStore;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

/**
 *
 * @author Michaela Bocanova
 */
//@Stateless
public class ParserConfigurationServiceImpl implements ParserConfigurationService {

    @Inject
    @CachedStore
    private CachedParserStore parserStore;
    
    @Override
    public void confirm(long parserId) {
    	Parser parser = parserStore.findParser(parserId);
    	
    	Parser old = getConfirmedParser(parser.getRestaurant());
    	if (old != null) {
    		old.setConfirmed(false);
    		parserStore.updateParser(old);
    	}
    	parser.setConfirmed(true);
		parserStore.updateParser(parser);
    }
    
    @Override
    public List<Parser> getAll() {
        return parserStore.getAllParsers();
    }
    
    @Override
    public List<Parser> getAll(boolean confirmed) {
        return parserStore.getAllParsers(confirmed);
    }
    
    @Override
    public Parser getConfirmedParser(Restaurant restaurant) {
    	return  parserStore.getConfirmedParser(restaurant.getGooglePlaceID());
    }
    
    
    
}
