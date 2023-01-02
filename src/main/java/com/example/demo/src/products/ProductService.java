package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.src.products.dto.ExamRequest;
import com.example.demo.src.products.dto.ExamSaveDTO;
import com.example.demo.src.products.dto.PostReviewRequest;
import com.example.demo.src.products.dto.SaveReviewDTO;
import com.example.demo.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ProductDao productDao;
    private S3Uploader s3Uploader;

    @Autowired
    public ProductService(ProductDao productDao,S3Uploader s3Uploader) {
        this.productDao = productDao;
        this.s3Uploader=s3Uploader;
    }

    public boolean postReview(PostReviewRequest req,MultipartFile file, int productNum)throws BaseException, IOException {
        boolean result= false;
        String savedUrl=s3Uploader.uploadFiles(file,"todayhouse");
        System.out.println(savedUrl);
        SaveReviewDTO dto= new SaveReviewDTO(req, savedUrl,productNum);
        if(productDao.insertReview(dto)==1){
            result=true;
        }
        return result;
    }

















    @Transactional
    // 테스트 데이터 입력용 메서드
    public boolean postProduct(ExamRequest request,MultipartFile thumbnail,MultipartFile[] files)throws BaseException,IOException{
        boolean result= false;
        String savedThumbnail=s3Uploader.uploadFiles(thumbnail,"todayhouse");
        ExamSaveDTO dto= new ExamSaveDTO(request,savedThumbnail);
        List<String> filenames= new ArrayList<>();
        for(MultipartFile file:files){
            filenames.add(s3Uploader.uploadFiles(file,"todayhouse"));
        }
        if(productDao.postProduct(dto,filenames)==1){
            result=true;
        }
        return result;
    }
}
