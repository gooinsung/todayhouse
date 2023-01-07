package com.example.demo.src.contents;

import com.amazonaws.util.EncodingSchemeEnum;
import com.example.demo.config.BaseException;
import com.example.demo.src.IndexResponse;
import com.example.demo.src.contents.dto.GetContentDetailsResponse;
import com.example.demo.src.contents.dto.GetContentResponse;
import com.example.demo.src.contents.dto.GetHomeContent;
import com.example.demo.src.contents.dto.GetHomeFamousContent;
import com.example.demo.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ContentProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ContentDao contentDao;
    private S3Uploader s3Uploader;

    @Autowired
    public void ContentProvider(ContentDao contentDao,S3Uploader s3Uploader){
        this.contentDao=contentDao;
        this.s3Uploader=s3Uploader;
    }
    @Transactional
    // 전체 게시글 조회 메서드
    public List<GetContentResponse> getContents() throws BaseException{
        try{
            return contentDao.getContents();
        }catch (Exception exception){
            logger.error("App - getContents ContentProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    // 게시글 검색 조회 메서드
    public List<GetContentResponse> getContentsBySearch(String search) throws BaseException{
        try{
            String searchKeyword="%"+search+"%";
            return contentDao.getContentBySearch(searchKeyword);
        }catch (Exception exception){
            logger.error("App - getContentsBySearch ContentProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
    @Transactional
    // 특정 게시글 조회 메서드
    public GetContentDetailsResponse getContent(int contentNum) throws BaseException{
        try{
            return contentDao.getContent(contentNum);
        }catch (Exception exception){
            logger.error("App - getContent ContentProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    // 홈 화면 구성 메서드
    public List<GetHomeContent> getHomeContents() throws BaseException{
        try{
            return contentDao.getHomeContents();
        }catch (Exception exception){
            logger.error("App - getHomeContents ContentProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    // 홈 화면 인기 게시글
    public List<GetHomeFamousContent> getHomeFamousContents() throws BaseException{
        try{
            return contentDao.getHomeFamousContents();
        }catch (Exception exception){
            logger.error("App - getHomeFamousContents ContentProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
