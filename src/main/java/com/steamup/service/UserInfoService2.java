package com.steamup.service;

import com.steamup.entities.UserInfo;
import com.steamup.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserInfoService2 implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByName(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserInfo userInfo)  {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        if(repository.existsByEmail(userInfo.getEmail())){
            log.warn("Email exists in db "+ userInfo.getEmail());
        }else if(repository.existsByName(userInfo.getName())){
            log.warn("Email exists in db "+userInfo.getName());
        } else {
            try {
                repository.save(userInfo);
            } catch (Exception sqle) {
                log.warn("",sqle);
            }
        }
        return "Success";
    }


}
