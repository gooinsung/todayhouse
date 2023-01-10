package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.users.dto.GetScrapResponse;
import com.example.demo.src.users.dto.PostJoinRequest;
import com.example.demo.src.users.dto.PostLoginRequest;
import com.example.demo.src.users.dto.object.ScrapTypeNumber;
import com.example.demo.utils.S3Uploader;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            logger.error("App - createUser UserService Error", exception);
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
            logger.error("App - loginUser UserService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 스크랩 추가
    @Transactional
    public boolean addScrap(int userNum, String type, int number) throws BaseException{
        try{
            boolean result= false;
            if(userDao.insertScrap(userNum,type,number)==1){
                result=true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - addScrap UserService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 유저 정보 수정 메서드
    public boolean updateUser(int userNum, String userNickName, MultipartFile file) throws BaseException, IOException {
        try{
            boolean result=false;
            String savedUserImg=null;
            String saveUserImg=null;
            if(userNickName!=null && file!=null){
                logger.info("닉네임,사진 변경");
                savedUserImg= userDao.getUserSavedImg(userNum);
                s3Uploader.delete(savedUserImg);
                saveUserImg= s3Uploader.uploadFiles(file,"todayhouse");
                if(userDao.updateUser(userNum,userNickName,saveUserImg)==1) result=true;
            }else if(userNickName!=null && file==null){
                logger.info("닉네임만 변경");
                if(userDao.updateUserNickName(userNum, userNickName)==1) result= true;
            }else if(userNickName==null && file!=null){
                logger.info("사진만 변경");
                savedUserImg=userDao.getUserSavedImg(userNum);
                s3Uploader.delete(savedUserImg);
                saveUserImg= s3Uploader.uploadFiles(file,"todayhouse");
                if(userDao.updateUserImg(userNum,saveUserImg)==1) result=true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - updateUser UserService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }



}
