package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountService.getAccountByUsername(userName);
        UserDetails userDetails = User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles("USER").build();
        return userDetails;
    }
}
