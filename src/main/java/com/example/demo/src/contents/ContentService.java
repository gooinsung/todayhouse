package com.example.demo.src.contents;

import com.example.demo.config.BaseException;
import com.example.demo.src.contents.dto.PatchContentRequest;
import com.example.demo.src.contents.dto.PostContentRequest;
import com.example.demo.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    // 게시글 수정 메서드
    public boolean modifyContent(int contentNum, PatchContentRequest request,MultipartFile file) throws BaseException,IOException{
        try{
            String savedImg= contentDao.getContentImg(contentNum);
            s3Uploader.delete(savedImg);
            String reSavedImg= s3Uploader.uploadFiles(file,"todayhouse");
            request.setContentImg(reSavedImg);

        }catch (Exception exception){
            logger.error("App - modifyContent ContentService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
        return false;
    }
}
