package com.tieucake.dao;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.tieucake.dto.ResultDTO;
import com.tieucake.dto.UserDTO;
import com.tieucake.entity.UserEntity;

public interface IUserManagerDAO {
	
	Long save(UserDTO user);
	
	Long update(UserDTO user);
	
	void delete(Long userId);
	
	ResultDTO<UserEntity> findAllUser(String startCursorString);
	
	UserDTO findById(Long userId) throws EntityNotFoundException;
}
