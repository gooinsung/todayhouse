package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.src.users.dto.GetScrapResponse;
import com.example.demo.src.users.dto.MyPageResponse;
import com.example.demo.src.users.dto.object.ScrapTypeNumber;
import com.example.demo.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

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

    // 스크랩 조회
    @Transactional
    public List<GetScrapResponse> getScraps(int userNum) throws BaseException{
        try{
            List<GetScrapResponse> response= new ArrayList<>();
            List<ScrapTypeNumber> tns=userDao.getTypeAndNumber(userNum);
            for(ScrapTypeNumber tn:tns){
                GetScrapResponse res=new GetScrapResponse();
                if(tn.getType().equals("c")){
                    res.setType(tn.getType());
                    res.setNumber(tn.getNumber());
                    res.setThumbnail(userDao.getContentThumbnail(tn.getNumber()));
                }else{
                    res.setType(tn.getType());
                    res.setNumber(tn.getNumber());
                    res.setThumbnail(userDao.getProductThumbnail(tn.getNumber()));
                }
                response.add(res);
            }

            return response;
        }catch (Exception exception){
            logger.error("App - getScraps UserProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // MyPage조회 메서드
    @Transactional
    public MyPageResponse getMyPage(int userNum) throws BaseException{
        try{
            MyPageResponse response= userDao.getMyPage(userNum);
            response.setScraps(this.getScraps(userNum));
            response.setContents(userDao.getMyContents(userNum));
            return response;
        }catch (Exception exception){
            logger.error("App - getMyPage UserProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
