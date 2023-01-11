package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.src.contents.ContentDao;
import com.example.demo.src.contents.dto.GetContentComment;
import com.example.demo.src.products.ProductDao;
import com.example.demo.src.products.dto.GetReviewResponse;
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
import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

@Service
public class UserProvider {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private UserDao userDao;
    private ProductDao productDao;
    private ContentDao contentDao;
    private S3Uploader s3Uploader;

    @Autowired
    public UserProvider(UserDao userDao, S3Uploader s3Uploader, ProductDao productDao,ContentDao contentDao) {
        this.userDao = userDao;
        this.s3Uploader = s3Uploader;
        this.productDao = productDao;
        this.contentDao = contentDao;
    }

    // 스크랩 조회
    @Transactional
    public List<GetScrapResponse> getScraps(int userNum) throws BaseException{
        try{
            if(userDao.checkUserNum(userNum)!=1){
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
            List<GetScrapResponse> response= new ArrayList<>();
            List<ScrapTypeNumber> tns=userDao.getTypeAndNumber(userNum);
            for(ScrapTypeNumber tn:tns){
                System.out.println("type: "+tn.getType());
                System.out.println("number :"+tn.getNumber());
                GetScrapResponse res=new GetScrapResponse();
                res.setType(tn.getType());
                res.setNumber(tn.getNumber());
                if(tn.getType().equals("c")){
                    res.setThumbnail(userDao.getContentThumbnail(tn.getNumber()));
                }else{
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

    // MyPage 조회 메서드
    @Transactional
    public MyPageResponse getMyPage(int userNum) throws BaseException{
        try{
            if(userDao.checkUserNum(userNum)!=1){
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
            MyPageResponse response= userDao.getMyPage(userNum);
            response.setScraps(this.getScraps(userNum));
            response.setContents(userDao.getMyContents(userNum));
            return response;
        }catch (Exception exception){
            logger.error("App - getMyPage UserProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 내가 쓴 리뷰 조회 메서드
    @Transactional
    public List<GetReviewResponse> getMyReviews(int userNum) throws BaseException{
        try{
            return productDao.getMyReviews(userNum);
        }catch (Exception exception){
            logger.error("App - getMyReviews UserProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 내가 쓴 댓글 조회 메서드
    @Transactional
    public List<GetContentComment> getMyComments(int userNum) throws BaseException{
        try{
            if(userDao.checkUserNum(userNum)!=1){
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
            return contentDao.getMyComments(userNum);
        }catch (Exception exception){
            logger.error("App - getMyComments UserProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
