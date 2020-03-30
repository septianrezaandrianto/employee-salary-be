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
import com.employee.Employee.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	 
	 ModelMapper modelMapper = new ModelMapper();
	
	 
	 @Autowired
	 UserRepository UserRepository;
	 
	 public UserDTO convertToDTO(User user) {
		 UserDTO userDto = modelMapper.map(user, UserDTO.class);
         return userDto;
	 }
	    
    private User convertToEntity(UserDTO userDto) {
    	User user = modelMapper.map(userDto, User.class);
        return user;
    }
		 
	 //Get All User
	 @GetMapping("/User/readAll")
	 public HashMap<String, Object> getAllUser() {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<UserDTO> listUsers = new ArrayList<UserDTO>();
		for(User d : UserRepository.findAll()) {
			UserDTO UserDTO = convertToDTO(d);
			listUsers.add(UserDTO);
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
	 @GetMapping("/User/{id}")
	 public HashMap<String, Object> getById(@PathVariable(value = "id") Integer id){
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		User User = UserRepository.findById(id)
				.orElse(null);
		UserDTO UserDTO = convertToDTO(User);
		showHashMap.put("Messages", "Read Data Success");
		showHashMap.put("Data", UserDTO);
		return showHashMap;
	}
	 
	// Create a new User
	@PostMapping("/User/add")
	public HashMap<String, Object> createUser(@Valid @RequestBody ArrayList<UserDTO> UserDTO) {
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	@Valid ArrayList<UserDTO> listUsers = UserDTO;
    	String message;
    	
    	for(UserDTO d : listUsers) {
    		User User = convertToEntity(d);
    		UserRepository.save(User);
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
//    @PutMapping("/User/update/{id}")
//    public HashMap<String, Object> updateUser(@PathVariable(value = "id") Integer id,
//            @Valid @RequestBody UserDTO UserDetails) {
//    	
//    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
//    	String message;
//    	
//    	int UserId = id.intValue();
//    	List<User> listUsers = UserRepository.findAll();
//    	
//    	for(User d : listUsers) {
//    		if(d.getDirId() == UserId) {
//    			if(UserDetails.getDirFname() == null) {
//    				UserDetails.setDirFname(listUsers.get(UserId).getDirFname());
//    	    	}
//    	    	if(UserDetails.getDirLname() == null) {
//    	    		UserDetails.setDirLname(listUsers.get(UserId).getDirLname());
//    	    	}
//    		}
//    	}	
//    	
//    	User User = UserRepository.findById(id)
//    			 .orElse(null);
//
//    	User.setDirFname(UserDetails.getDirFname());
//    	User.setDirLname(UserDetails.getDirLname());
//    	
//    	User updateUser = UserRepository.save(User);
//    	
//    	List<User> resultList = new ArrayList<User>();
//    	resultList.add(updateUser);
//    	
//    	if(resultList.isEmpty()) {
//    		message = "Update Failed!";
//    	} else {
//    		message = "Update Success!";
//    	}
//    	
//    	showHashMap.put("Message", message);
//    	showHashMap.put("Total Update", resultList.size());
//    	showHashMap.put("Data", resultList);
//    	
//    	return showHashMap;
//    }
    
    // Delete a User
    @DeleteMapping("/User/delete/{id}")
    public HashMap<String, Object> delete(@PathVariable(value = "id") Integer id) {
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	User User = UserRepository.findById(id)
    			.orElse(null);

    	UserRepository.delete(User);

        showHashMap.put("Messages", "Delete Data Success!");
        showHashMap.put("Delete data :", User);
    	return showHashMap;
    }
}
	 
