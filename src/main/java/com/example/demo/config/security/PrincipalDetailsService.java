package com.example.demo.config.security;

import com.example.demo.src.users.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {
    private UserDao userDao;

    @Autowired
    public PrincipalDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PrincipalDetails principalDetails= new PrincipalDetails(userDao.loadByUsername(username));
        if(principalDetails==null){
            System.out.println("PrincipalDetails is NULL");
        }
        return principalDetails;
    }
}
