package com.tieucake.dao.impl;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class BasicManagerDAO {
	
	protected DatastoreService datastore;
	
	public BasicManagerDAO() {
		super();
		this.datastore = DatastoreServiceFactory.getDatastoreService();;
	}

}
