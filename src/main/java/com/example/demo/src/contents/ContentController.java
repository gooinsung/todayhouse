package com.example.demo.src.contents;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.contents.dto.*;
import com.example.demo.src.users.UserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
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
    private UserProvider userProvider;
    @Autowired
    public ContentController(ContentProvider contentProvider, ContentService contentService,UserProvider userProvider) {
        this.contentProvider = contentProvider;
        this.contentService = contentService;
        this.userProvider = userProvider;
    }

    // 전체 게시글 조회 API(11), 게시글 검색 조회 API(16)
    @GetMapping("")
    public BaseResponse<List<GetContentResponse>> getContents(@RequestParam(required = false) String search) throws BaseException{
        List<GetContentResponse> responses=new ArrayList<>();
        if(search==null){
            logger.info("ContentController 내 11번 API 실행");
           responses= contentProvider.getContents();
        }
        if(search!=null){
            logger.info("ContentController 내 16번 API 실행");
            responses= contentProvider.getContentsBySearch(search);
        }

        return new BaseResponse<>(responses);
    }

    // 특정 게시글 조회 API(12)
    @GetMapping("/{contentNum}")
    public BaseResponse<GetContentDetailsResponse> getContent(@PathVariable int contentNum) throws BaseException{
        logger.info("ContentController 내 12번 API 실행");
        contentProvider.checkContent(contentNum);
        GetContentDetailsResponse response= contentProvider.getContent(contentNum);
        return new BaseResponse<>(response);
    }

    // 게시글 작성 API(13)
    @PostMapping("/write")
    public BaseResponse<String> writeContent(@RequestPart @Validated PostContentRequest postContentRequest, @RequestPart(value = "contentImg") MultipartFile contentImg)throws BaseException, IOException{
        logger.info("ContentController 내 13번 API 실행");
        userProvider.checkUserNum(postContentRequest.getUserNum());
        String result="게시글 작성 실패";
        if(contentService.postContent(postContentRequest,contentImg)){
            result="게시글 작성 성공";
        }
        return new BaseResponse<>(result);
    }

    // 게시글 수정 API(14)
    @PatchMapping("/{contentNum}")
    public BaseResponse<String> modifyContent(@PathVariable int contentNum, @RequestPart @Validated PatchContentRequest patchContentRequest, @RequestPart MultipartFile contentImg) throws BaseException,IOException{
        logger.info("ContentController 내 14번 API 실행");
        contentProvider.checkContent(contentNum);
        String result=" 게시글 수정 실패";
        if(contentService.modifyContent(contentNum,patchContentRequest, contentImg)){
            result="게시글 수정 성공";
        }
        return new BaseResponse<>(result);
    }

    // 게시글 삭제 API(15)
    @DeleteMapping("/{contentNum}")
    public BaseResponse<String> deleteContent(@PathVariable int contentNum) throws BaseException{
        logger.info("ContentController 내 15번 API 실행");
        contentProvider.checkContent(contentNum);
        String result="게시글 삭제 실패";
        if(contentService.deleteContent(contentNum)){
            result="게시글 삭제 성공";
        }
        return new BaseResponse<>(result);
    }

    // 게시글 댓글 조회 API(25)
    @GetMapping("/{contentNum}/comments")
    public BaseResponse<List<GetContentComment>> getComments(@PathVariable int contentNum) throws BaseException{
        logger.info("ContentController 내 25번 API 실행");
        contentProvider.checkContent(contentNum);
        List<GetContentComment> comments=contentProvider.getComments(contentNum);
        return new BaseResponse<>(comments);
    }

    // 게시글 댓글 작성 API(26)
    @PostMapping("/{contentNum}/comments")
    public BaseResponse<String> postComment(@PathVariable int contentNum,@RequestBody @Validated PostCommentRequest postCommentRequest) throws BaseException{
        logger.info("ContentController 내 26번 API 실행");
        contentProvider.checkContent(contentNum);
        userProvider.checkUserNum(postCommentRequest.getUserNum());
        String result="댓글 작성 실패";
        if(contentService.postComment(postCommentRequest)){
            result="댓글 작성 성공";
        }
        return new BaseResponse<>(result);
    }

    // 게시글 댓글 수정 API(27)
    @PatchMapping("/{contentNum}/comments")
    public BaseResponse<String> patchComment(@PathVariable int contentNum, @RequestParam int commentNum, @RequestBody PatchCommentRequest request) throws BaseException{
        logger.info("ContentController 내 27번 API 실행");
        contentProvider.checkContent(contentNum);
        contentProvider.checkComment(commentNum);
        String result="댓글 수정 실패";
        if(contentService.patchComment(commentNum,request.getComment())){
            result="댓글 수정 성공";
        };
        return new BaseResponse<>(result);
    }

    // 게시글 댓글 삭제 API(28)
    @DeleteMapping("/{contentNum}/comments")
    public BaseResponse<String> deleteComment(@PathVariable int contentNum, @RequestParam int commentNum) throws BaseException{
        logger.info("ContentController 내 28번 API 실행");
        contentProvider.checkContent(contentNum);
        contentProvider.checkComment(commentNum);
        String result="댓글 삭제 실패";
        if(contentService.deleteComment(commentNum)){
            result="댓글 삭제 성공";
        }
        return new BaseResponse<>(result);
    }


    // 게시글 좋아요 API(29)
    @PostMapping("/{contentNum}/likes")
    public BaseResponse<String> contentLike(@PathVariable int contentNum,@RequestParam int userNum) throws BaseException{
        logger.info("ContentController 내 29번 API 실행");
        contentProvider.checkContent(contentNum);
        String result="게시글 좋아요 실패";
        if(contentService.contentLike(contentNum,userNum)){
            result="게시글 좋아요 성공";
        }
        return new BaseResponse<>(result);
    }

    // 게시글 좋아요 조회 API(30)
    @GetMapping("/{contentNum}/likes")
    public BaseResponse<List<GetLikeUserResponse>> getLikeUser(@PathVariable int contentNum) throws BaseException{
        logger.info("ContentController 내 30번 API 실행");
        contentProvider.checkContent(contentNum);
        List<GetLikeUserResponse> response= contentProvider.getLikeUserResponses(contentNum);
        return new BaseResponse<>(response);
    }


}
