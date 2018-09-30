package com.tieucake.dao.impl.datastore;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.tieucake.dao.IUserManagerDAO;
import com.tieucake.dao.impl.BasicManagerDAO;
import com.tieucake.dto.ResultDTO;
import com.tieucake.dto.UserDTO;
import com.tieucake.entity.UserEntity;
import com.tieucake.util.EntityTypeUtil;

@Repository("iUserManagerDAO")
public class UserManagerDAOImpl extends BasicManagerDAO implements IUserManagerDAO {

	public UserManagerDAOImpl() {
		super();
	}

	@Override
	public Long save(UserDTO user) {
		Entity userEntity = new Entity(EntityTypeUtil.USER);
		userEntity.setProperty("userName", user.getUserName());
		userEntity.setProperty("password", user.getPassword());
		Key userKey = datastore.put(userEntity);
		return userKey.getId();
	}

	@Override
	public Long update(UserDTO user) {
		Key userKey = KeyFactory.createKey(EntityTypeUtil.USER, user.getUserId()); // From a book, create a Key
		Entity userEntity = new Entity(userKey); // Convert Book to an Entity
		userEntity.setProperty("userName", user.getUserName());
		userEntity.setProperty("password", user.getPassword());
		datastore.put(userEntity);
		return userKey.getId();
	}

	@Override
	public void delete(Long userId) {
		Key userKey = KeyFactory.createKey(EntityTypeUtil.USER, userId);
		datastore.delete(userKey);

	}

	@Override
	public ResultDTO<UserEntity> findAllUser(String startCursorString) {
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10); // Only show 10 at a time
		if (startCursorString != null && !startCursorString.equals("")) {
		    fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
		  }
		Query query = new Query(EntityTypeUtil.USER);
		PreparedQuery preparedQuery = datastore.prepare(query);
		QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);
		List<UserEntity> resultBooks = entitiesToUserEntities(results); // Retrieve and convert Entities
		Cursor cursor = results.getCursor(); // Where to start next time
		if (cursor != null && resultBooks.size() == 10) { // Are we paging? Save Cursor
			String cursorString = cursor.toWebSafeString(); // Cursors are WebSafe
			return new ResultDTO<UserEntity>(resultBooks, cursorString);
		} else {
			return new ResultDTO<>(resultBooks);
		}

	}

	private List<UserEntity> entitiesToUserEntities(QueryResultIterator<Entity> results) {
		List<UserEntity> resultBooks = new ArrayList<>();
		while (results.hasNext()) { // We still have data
			resultBooks.add(entityToUserEntity(results.next())); // Add the Book to the List
		}
		return resultBooks;
	}

	@Override
	public UserDTO findById(Long userId) throws EntityNotFoundException {
		Key userKey = KeyFactory.createKey(EntityTypeUtil.USER, userId);
		Entity entity = datastore.get(userKey);
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(entityToUserEntity(entity),UserDTO.class);
	}

	public UserEntity entityToUserEntity(Entity userEntity) {
		return new UserEntity.Builder() // Convert to Book form
				.userId(userEntity.getKey().getId()).userName((String) userEntity.getProperty(UserEntity.USERNAME))
				.password((String) userEntity.getProperty(UserEntity.PASSWORD)).build();
	}

}
