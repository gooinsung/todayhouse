package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.users.dto.PostJoinRequest;
import com.example.demo.src.users.dto.PostLoginRequest;
import com.example.demo.utils.S3Uploader;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserService {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private UserDao userDao;

    private S3Uploader s3Uploader;

    @Autowired
    public UserService(UserDao userDao, S3Uploader s3Uploader) {
        this.userDao = userDao;
        this.s3Uploader = s3Uploader;
    }


    // 회원 가입
    @Transactional
    public boolean createUser(PostJoinRequest request) throws BaseException {
        try{
            boolean result= false;
            if(userDao.checkEmail(request.getUserEmail())==1){
                throw new BaseException(POST_USERS_EXISTS_EMAIL);
            }
            String encryptedPw= SHA256.encrypt(request.getUserPw());
            request.setUserPw(encryptedPw);
            if(userDao.createUser(request)==1){
                result=true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 로그인
    @Transactional
    public boolean loginUser(PostLoginRequest postLoginRequest) throws BaseException{
        try{
            boolean result= false;
            if(userDao.checkEmail(postLoginRequest.getUserEmail())!=1){
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
            String savedPw= userDao.getUserPw(postLoginRequest.getUserEmail());
            String encryptedPw=SHA256.encrypt(postLoginRequest.getUserPw());
            if(savedPw.equals(encryptedPw)){
                result=true;
            }

            return result;
        }catch(Exception exception){
            logger.error("App - loginUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
