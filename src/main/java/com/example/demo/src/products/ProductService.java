package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.src.products.dto.*;
import com.example.demo.src.products.dto.object.SaveReviewDTO;
import com.example.demo.src.products.dto.object.UpdateReviewDTO;
import com.example.demo.src.users.UserDao;
import com.example.demo.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.NONE_ORDERED_PRODUCT;

@Service
public class ProductService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ProductDao productDao;
    private S3Uploader s3Uploader;
    private UserDao userDao;

    @Autowired
    public ProductService(ProductDao productDao,S3Uploader s3Uploader,UserDao userDao) {
        this.productDao = productDao;
        this.s3Uploader=s3Uploader;
        this.userDao=userDao;
    }

    // 리뷰 작성 메서드
    @Transactional
    public boolean postReview(PostReviewRequest req,MultipartFile file, int productNum)throws BaseException, IOException {
        try{
            String savedUrl="";
            if(!file.isEmpty()){
                savedUrl=s3Uploader.uploadFiles(file,"todayhouse");
            }
            boolean result= false;
            if(productDao.checkOrdered(req.getUserNum(),productNum)!=0){
                SaveReviewDTO dto= new SaveReviewDTO(req, savedUrl,productNum);
                if(productDao.insertReview(dto)==1){
                    result=true;
                }
            }
            return result;
        }catch(Exception exception){
            logger.error("App - postReview ProductService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }



    // 리뷰 수정 메서드
    @Transactional
    public boolean patchReview(PatchReviewRequest req,MultipartFile file, int reviewNum) throws BaseException,IOException{
        try{
            boolean result=false;
            String updateImg="storedFilename";
            if(!file.isEmpty()){
                String originalSavedFile= productDao.getReviewImg(reviewNum);
                if(originalSavedFile!=null){
                    s3Uploader.delete(originalSavedFile);
                    updateImg= s3Uploader.uploadFiles(file,"todayhouse");
                }
            }

            UpdateReviewDTO dto= new UpdateReviewDTO(req,updateImg,reviewNum);
            if(productDao.updateReview(dto)==1){
                result=true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - patchReview ProductService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상품 삭제 메서드
    @Transactional
    public boolean deleteReview(int reviewNum) throws BaseException{
        try{
            boolean result=false;
            if(productDao.deleteReview(reviewNum)==1){
                result=true;
            }
            return result;
        }catch (Exception exception){
            logger.error("App - deleteReview ProductService Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

















    @Transactional
    // 테스트 데이터 입력용 메서드
    public boolean postProduct(ExamRequest request,MultipartFile[] thumbnails,MultipartFile[] files)throws BaseException,IOException{
        boolean result= false;
        List<String> savedThumbnails= new ArrayList<>();
        for(MultipartFile thumbnail:thumbnails){
            savedThumbnails.add(s3Uploader.uploadFiles(thumbnail,"todayhouse"));
        }

        ExamSaveDTO dto= new ExamSaveDTO(request);
        List<String> filenames= new ArrayList<>();
        for(MultipartFile file:files){
            filenames.add(s3Uploader.uploadFiles(file,"todayhouse"));
        }

        if(productDao.postProduct(dto,filenames,savedThumbnails)==1){
            result=true;
        }
        return result;
    }

    public void deleteAllProductPics(){

    }
}
