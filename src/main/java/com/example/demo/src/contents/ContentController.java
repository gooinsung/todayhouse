package com.example.demo.src.contents;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.contents.dto.GetContentDetailsResponse;
import com.example.demo.src.contents.dto.GetContentResponse;
import com.example.demo.src.contents.dto.PatchContentRequest;
import com.example.demo.src.contents.dto.PostContentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.Patch;
import javax.swing.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// @@ content 는 자신의 집의 일부를 소개하는 게시글 이다. 사진을 한장씩 표현할 수 있고, 그에 설명과 키워드 등을 적을 수 있다.
// 생활꿀팁, 노하우 아님!
@RestController
@RequestMapping("/contents")
public class ContentController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ContentProvider contentProvider;
    private ContentService contentService;

    @Autowired
    public ContentController(ContentProvider contentProvider, ContentService contentService) {
        this.contentProvider = contentProvider;
        this.contentService = contentService;
    }

    // 전체 게시글 조회 API(11)
    @GetMapping("")
    public BaseResponse<List<GetContentResponse>> getContents() throws BaseException{
        List<GetContentResponse> responses= contentProvider.getContents();
        return new BaseResponse<>(responses);
    }

    // 특정 게시글 조회 API(12)
    @GetMapping("/{contentNum}")
    public BaseResponse<GetContentDetailsResponse> getContent(@PathVariable int contentNum) throws BaseException{
        GetContentDetailsResponse response= contentProvider.getContent(contentNum);
        return new BaseResponse<>(response);
    }

    // 게시글 작성 API(13)
    @PostMapping("/write")
    public BaseResponse<String> writeContent(@RequestPart @Validated PostContentRequest postContentRequest, @RequestPart MultipartFile contentImg)throws BaseException, IOException{
        String result="게시글 작성 실패";
        if(contentService.postContent(postContentRequest,contentImg)){
            result="게시글 작성 성공";
        }
        return new BaseResponse<>(result);
    }

    // 게시글 수정 API(14)
    @PatchMapping("/{contentNum}")
    public BaseResponse<String> modifyContent(@PathVariable int contentNum, @RequestPart @Validated PatchContentRequest patchContentRequest, @RequestPart MultipartFile contentImg) throws BaseException,IOException{
        String result=" 게시글 수정 실패";
        if(contentService.modifyContent(contentNum,patchContentRequest, contentImg)){
            result="게시글 수정 성공";
        }
        return new BaseResponse<>(result);
    }

    // 게시글 삭제 API(15)
    @DeleteMapping("/{contentNum}")
    public BaseResponse<String> deleteContent(@PathVariable int contentNum) throws BaseException{
        String result="게시글 삭제 실패";
        if(contentService.deleteContent(contentNum)){
            result="게시글 삭제 성공";
        }
        return new BaseResponse<>(result);
    }




}
