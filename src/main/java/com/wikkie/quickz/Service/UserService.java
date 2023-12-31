package com.wikkie.quickz.Service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.wikkie.quickz.Dao.UserDao;
import com.wikkie.quickz.Model.Users;

@Service
public class UserService {

    UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public ResponseEntity<String> getUser(Users users) {
        Users result = userDao.findByEmail(users.getEmail());

        System.out.println(result);
        HttpHeaders httpHeaders = new HttpHeaders();

        if (userDao.findByEmail(users.getEmail()) != null) {
            if (new BCryptPasswordEncoder().matches(users.getPassword(), result.getPassword())) {

                httpHeaders.add("LOGIN", "SUCCESS");
                return new ResponseEntity<String>("Logged In Successfully", httpHeaders, HttpStatus.OK);
            } else {
                httpHeaders.add("LOGIN", "FAILED");
                return new ResponseEntity<String>("Invalid credentials", httpHeaders, HttpStatus.BAD_GATEWAY);
            }
        } else {
            httpHeaders.add("LOGIN", "FAILED");
            return new ResponseEntity<String>("Internal Server Error", httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
