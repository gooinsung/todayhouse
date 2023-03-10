package com.example.demo.src.contents;

import com.example.demo.config.BaseException;
import com.example.demo.src.contents.dto.PatchContentRequest;
import com.example.demo.src.contents.dto.PostCommentRequest;
import com.example.demo.src.contents.dto.PostContentRequest;
import com.example.demo.src.users.UserDao;
import com.example.demo.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
import java.io.IOException;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ContentService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ContentDao contentDao;
    private S3Uploader s3Uploader;
    private UserDao userDao;

    @Autowired
    public ContentService(ContentDao contentDao, S3Uploader s3Uploader, UserDao userDao) {
        this.contentDao = contentDao;
        this.s3Uploader = s3Uploader;
        this.userDao=userDao;
    }

    // 게시글 작성 메서드
    @Transactional
    public boolean postContent(PostContentRequest request, MultipartFile file) throws BaseException, IOException {
        try {
            boolean result = false;
            String savedFile = s3Uploader.uploadFiles(file, "todayhouse");
            request.setContentImg(savedFile);
            if (contentDao.postContent(request) == 1) {
                result = true;
            }
            return result;
        } catch (Exception exception) {
            logger.error("App - postContent ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시글 수정 메서드
    @Transactional
    public boolean modifyContent(int contentNum, PatchContentRequest request, MultipartFile file) throws BaseException, IOException {
        try {
            String updatedImg= "contentImg";
            boolean result = false;
            if(!file.isEmpty()){
                String savedImg = contentDao.getContentImg(contentNum);
                s3Uploader.delete(savedImg);
                updatedImg = s3Uploader.uploadFiles(file, "todayhouse");
            }
            request.setContentImg(updatedImg);
            if (contentDao.updateContent(contentNum, request) == 1) result = true;
            return result;
        } catch (Exception exception) {
            logger.error("App - modifyContent ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시글 삭제 메서드
    @Transactional
    public boolean deleteContent(int contentNum) throws BaseException {
        try {
            boolean result = false;
            if (contentDao.changeContentStatus(contentNum) == 1) {
                result = true;
            }
            return result;
        } catch (Exception exception) {
            logger.error("App - deleteContent ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시글 댓글 작성 메서드
    @Transactional
    public boolean postComment(PostCommentRequest request) throws BaseException {
        try {
            boolean result = false;
            if (contentDao.insertComment(request) == 1) {
                result = true;
            }
            return result;
        } catch (Exception exception) {
            logger.error("App - postComment ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시글 댓글 수정 메서드
    @Transactional
    public boolean patchComment(int commentNum, String comment) throws BaseException {
        try {
            boolean result = false;
            if (contentDao.updateComment(commentNum, comment) == 1) {
                result = true;
            }
            return result;
        } catch (Exception exception) {
            logger.error("App - patchComment ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시글 댓글 삭제 메서드
    @Transactional
    public boolean deleteComment(int commentNum) throws BaseException {
        try {
            boolean result = false;
            if (contentDao.updateCommentStatus(commentNum) == 1) {
                result = true;
            }
            return result;
        } catch (Exception exception) {
            logger.error("App - deleteComment ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시글 좋아요 메서드
    @Transactional
    public boolean contentLike(int contentNum, int userNum) throws BaseException{
        try{
            boolean result= false;
            if(contentDao.like(contentNum,userNum)==1){
                result= true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - contentLike ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}