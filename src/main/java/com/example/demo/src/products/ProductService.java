package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.src.products.dto.PostReviewRequest;
import com.example.demo.src.products.dto.SaveReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ProductService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public boolean postReview(PostReviewRequest req,MultipartFile file)throws BaseException, IOException {
        boolean result= false;
        // 파일 물리적 저장
        MultipartFile boardFile=file;
        String savePath=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
        File saveFile=new File(savePath,boardFile.getOriginalFilename());
        boardFile.transferTo(saveFile);
        
        // 데이터베이스에 리뷰 저장
        SaveReviewDTO dto= new SaveReviewDTO(req, boardFile.getOriginalFilename());
        if(productDao.insertReview(dto)==1){
            result=true;
        }
        return result;
    }
}
