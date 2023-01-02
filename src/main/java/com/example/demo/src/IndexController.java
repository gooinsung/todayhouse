package com.example.demo.src;

import com.example.demo.config.BaseResponse;
import com.example.demo.utils.S3Uploader;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class IndexController {

    private S3Uploader s3Uploader;
    @Autowired
    public IndexController(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    @GetMapping("")
    public BaseResponse<String> home(){
        return new BaseResponse<>("홈 페이지");
    }


    @PostMapping("/S3")
    public ResponseEntity<String> S3upload(@RequestParam MultipartFile file) throws IOException {
        String result=s3Uploader.uploadFiles(file,"todayhouse");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
