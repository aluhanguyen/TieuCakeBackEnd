package com.tieucake.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tieucake.dao.IUserManagerDAO;
import com.tieucake.dto.ResultDTO;
import com.tieucake.dto.UserDTO;
import com.tieucake.entity.UserEntity;

@RestController
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class.getName());

	@Autowired
	private IUserManagerDAO userManagerDAO;

	@PostMapping(value = "/registerUser")
	public ResponseEntity registerUser(@RequestBody UserDTO userDto) {
		try {
			Long userId = userManagerDAO.save(userDto);
			return ResponseEntity.status(HttpStatus.OK).body(userId);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
	
	@GetMapping(value = "/getUserById/{id}")
	public ResponseEntity getUserById(@PathVariable("id") Long userId) {
		try {
			logger.log(Level.INFO, "User id : {}", userId);
			UserDTO userDto = userManagerDAO.findById(userId);
			return ResponseEntity.status(HttpStatus.OK).body(userDto);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}

	@GetMapping(value = "/getUsers")
	public ResponseEntity getUsers(@RequestParam("startCursor") String startCursor) {
		try {
			logger.log(Level.INFO, "StartCursor : {}", startCursor);
			ResultDTO<UserEntity> resultDTO = userManagerDAO.findAllUser(startCursor);
			return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
	
	@GetMapping(value = "/deleteUser/{id}")
	public ResponseEntity deleteUserById(@PathVariable("id") Long userId) {
		try {
			logger.log(Level.INFO, "User id : {}", userId);
			userManagerDAO.delete(userId);
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
}
