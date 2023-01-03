package com.example.demo.src.users;

import com.example.demo.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProvider {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private UserDao userDao;

    private S3Uploader s3Uploader;

    @Autowired
    public UserProvider(UserDao userDao, S3Uploader s3Uploader) {
        this.userDao = userDao;
        this.s3Uploader = s3Uploader;
    }
}
