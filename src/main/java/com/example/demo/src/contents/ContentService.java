package com.example.demo.src.contents;

import com.example.demo.config.BaseException;
import com.example.demo.src.contents.dto.PatchContentRequest;
import com.example.demo.src.contents.dto.PostContentRequest;
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
    @Autowired
    public ContentService(ContentDao contentDao, S3Uploader s3Uploader) {
        this.contentDao = contentDao;
        this.s3Uploader = s3Uploader;
    }

    @Transactional
    // 게시글 작성 메서드
    public boolean postContent(PostContentRequest request, MultipartFile file)throws BaseException, IOException{
        try{
            boolean result= false;
            String savedFile=s3Uploader.uploadFiles(file,"todayhouse");
            request.setContentImg(savedFile);
            if(contentDao.postContent(request)==1){
                result=true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - postContent ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    // 게시글 수정 메서드
    public boolean modifyContent(int contentNum, PatchContentRequest request,MultipartFile file) throws BaseException,IOException{
        try{
            boolean result=false;
            String savedImg= contentDao.getContentImg(contentNum);
            System.out.println("원래 이미지: "+savedImg);

            s3Uploader.delete(savedImg);
            String updatedImg= s3Uploader.uploadFiles(file,"todayhouse");
            System.out.println("업데이트된 이미지: "+updatedImg);
            request.setContentImg(updatedImg);
            if (contentDao.updateContent(contentNum, request)==1) result=true;
            return result;
        }catch (Exception exception){
            logger.error("App - modifyContent ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    // 게시글 삭제 메서드
    public boolean deleteContent(int contentNum) throws BaseException{
        try{
            boolean result=false;
            if(contentDao.changeContentStatus(contentNum)==1){
                result=true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - deleteContent ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
