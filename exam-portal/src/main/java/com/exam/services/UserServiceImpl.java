package com.exam.services;

import com.exam.exception.UserFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repositories.RoleRepository;
import com.exam.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    //creating user
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User local= userRepository.findByUsername(user.getUsername());
        if(local!=null){
            System.out.println("User is already there !!");
            throw new UserFoundException("User already present!!");
        }else {
            //User crete
            for(UserRole ur:userRoles){
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            local=this.userRepository.save(user);
        }
        return local;
    }

    //getting user by username
    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    //delete by userid
    @Override
    public void deleteUser(Long userId) {
      this.userRepository.deleteById(userId);
    }
}
