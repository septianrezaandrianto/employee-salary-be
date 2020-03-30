package com.employee.Employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.dto.UserDTO;
import com.employee.Employee.model.User;
import com.employee.Employee.model.UserId;
import com.employee.Employee.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	 
	 ModelMapper modelMapper = new ModelMapper();
	
	 
	 @Autowired
	 UserRepository userRepository;
	 
	 public UserDTO convertToDTO(User user) {
		 UserDTO userDto = modelMapper.map(user, UserDTO.class);
         return userDto;
	 }
	    
    private User convertToEntity(UserDTO userDto) {
    	User user = modelMapper.map(userDto, User.class);
        return user;
    }
		 
	 //Get All User
	 @GetMapping("/user/all")
	 public HashMap<String, Object> getAllUser() {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<UserDTO> listUsers = new ArrayList<UserDTO>();
		for(User u : userRepository.findAll()) {
			UserDTO userDto = convertToDTO(u);
			listUsers.add(userDto);
		}
		
		String message;
        if(listUsers.isEmpty()) {
    		message = "Read All Failed!";
    	} else {
    		message = "Read All Success!";
    	}
    	showHashMap.put("Message", message);
    	showHashMap.put("Total", listUsers.size());
    	showHashMap.put("Data", listUsers);
		
		return showHashMap;
	 }
	 
	 // Read User By ID
	 @GetMapping("/user/{id}")
	 public HashMap<String, Object> getById(@PathVariable(value = "id") UserId id){
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		User user = userRepository.findById(id)
				.orElse(null);
		UserDTO userDto = convertToDTO(user);
		showHashMap.put("Messages", "Read Data Success");
		showHashMap.put("Data", userDto);
		return showHashMap;
	}
	 
	// Create a new User
	@PostMapping("/user/add")
	public HashMap<String, Object> createUser(@Valid @RequestBody ArrayList<UserDTO> userDTO) {
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	@Valid ArrayList<UserDTO> listUsers = userDTO;
    	String message;
    	
    	for(UserDTO d : listUsers) {
    		User user = convertToEntity(d);
    		userRepository.save(user);
    	}
    
    	if(listUsers == null) {
    		message = "Create Failed!";
    	} else {
    		message = "Create Success!";
    	}
    	
    	showHashMap.put("Message", message);
    	showHashMap.put("Total Insert", listUsers.size());
    	showHashMap.put("Data", listUsers);
    	
    	return showHashMap;
    }
	
	// Update a User
    @PutMapping("/user/update/{id}")
    public HashMap<String, Object> updateUser(@PathVariable(value = "id") UserId id,
            @Valid @RequestBody UserDTO userDetails) {
    	
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	String message;
    	
    	List<User> listUsers = userRepository.findAll();
    	
//    	for(User u : listUsers) {
//    		if(u.getId() == id) {
//    			if(UserDetails.getIdDto() == null) {
//    				UserDetails.setIdDto(listUsers.get(id.getIdUser()).getId());
//    	    	}
//    	    	if(UserDetails.getPassword() == null) {
//    	    		UserDetails.setPassword(listUsers.get(id.getIdUser()).getPassword());
//    	    	}
//    		}
//    	}	
    	
    	User user = userRepository.findById(id)
    			 .orElse(null);

//    	user.setId(UserDetails.getIdDto());
    	user.setPassword(userDetails.getPassword());
    	user.setStatus(userDetails.getStatus());
    	
    	User updateUser = userRepository.save(user);
    	
    	List<User> resultList = new ArrayList<User>();
    	resultList.add(updateUser);
    	
    	if(resultList.isEmpty()) {
    		message = "Update Failed!";
    	} else {
    		message = "Update Success!";
    	}
    	
    	showHashMap.put("Message", message);
    	showHashMap.put("Total Update", resultList.size());
    	showHashMap.put("Data", resultList);
    	
    	return showHashMap;
    }
    
    // Delete a User
    @DeleteMapping("/user/delete/{id}")
    public HashMap<String, Object> delete(@PathVariable(value = "id") UserId id) {
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	User user = userRepository.findById(id)
    			.orElse(null);

    	userRepository.delete(user);

        showHashMap.put("Messages", "Delete Data Success!");
        showHashMap.put("Delete data :", user);
    	return showHashMap;
    }
}
	 
