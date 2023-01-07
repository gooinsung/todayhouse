package com.example.demo.src.contents;

import com.example.demo.src.contents.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class ContentDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 전체 게시글 조회
    public List<GetContentResponse> getContents(){
        String query="select c.contentNum,c.contentImg,c.contentTitle,u.userNickName from content c inner join user u on c.userNum=u.userNum where c.status='active'";
        return this.jdbcTemplate.query(query, new RowMapper<GetContentResponse>() {
            @Override
            public GetContentResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetContentResponse res=new GetContentResponse();
                res.setContentNum(rs.getInt("contentNum"));
                res.setContentImg(rs.getString("contentImg"));
                res.setContentTitle(rs.getString("contentTitle"));
                res.setUserNickName(rs.getString("userNickName"));
                return res;
            }
        });
    }

    // 특정 게시글 조회
    public GetContentDetailsResponse getContent(int contentNum){
        String query="select c.contentNum, c.contentCate, c.size, c.form, c.style, c.contentImg, c.contentTitle, c.contents, c.createdAt, u.userNum, u.userNickName, u.userImg from content c inner join user u on c.userNum=u.userNum where c.status='active' and c.contentNum=?";
        return this.jdbcTemplate.queryForObject(query, new RowMapper<GetContentDetailsResponse>() {
            @Override
            public GetContentDetailsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetContentDetailsResponse res= new GetContentDetailsResponse();
                res.setContentNum(rs.getInt("contentNum"));
                res.setContentCate(rs.getInt("contentCate"));
                res.setSize(rs.getInt("size"));
                res.setForm(rs.getInt("form"));
                res.setStyle(rs.getInt("style"));
                res.setContentImg(rs.getString("contentImg"));
                res.setContentTitle(rs.getString("contentTitle"));
                res.setContents(rs.getString("contents"));
                res.setCreatedAt(rs.getTimestamp("createdAt"));
                res.setUserNum(rs.getInt("userNum"));
                res.setUserNickName(rs.getString("userNickName"));
                res.setUserImg(rs.getString("userImg"));
                return res;
            }
        },contentNum);
    }

    // 게시글 작성
    public int postContent(PostContentRequest req){
        int result=0;
        String query1="insert into content (contentCate,size,form,style,userNum,contentImg,contentTitle,contents) values(?,?,?,?,?,?,?,?)";
        Object[] insertContentParam= new Object[]{req.getContentCate(),req.getSize(),req.getForm(),req.getStyle(),req.getUserNum(),req.getContentImg(),req.getContentTitle(),req.getContents()};
        if(this.jdbcTemplate.update(query1,insertContentParam)==1){
            String query2="select last_insert_id() as contentNum";
            int contentNum=this.jdbcTemplate.queryForObject(query2,int.class);
            if(this.postKeyword(contentNum,req.getKeywords())==req.getKeywords().size()){
                result=1;
            }
        }

        return result;
    }

    @Transactional
    // 게시글 수정
    public int updateContent(int contentNum ,PatchContentRequest req){
        int result=1;
        String query="update content set contentCate=?,size=?,form=?,style=?,contentTitle=?,contentImg=?,contents=? where contentNum=?";
        Object[] updateContentParam=new Object[]{req.getContentCate(),req.getSize(),req.getForm(),req.getStyle(),req.getContentTitle(),req.getContentImg(),req.getContents(),contentNum};
        if(this.jdbcTemplate.update(query,updateContentParam)==1){
            this.updateKeywords(contentNum,req.getKeywords());
        }
        return result;
    }

    @Transactional
    //게시글 키워드 수정
    public void updateKeywords(int contentNum,List<String> keywords){
        String query="update keywords set keyword=? where contentNum=?";
        for(String keyword:keywords){
            Object[] updateKeywordParam=new Object[]{keyword,contentNum};
            this.jdbcTemplate.update(query,updateKeywordParam);
        }
    }

    // 게시글 키워드 입력
    public int postKeyword(int contentNum, List<String> keywords){
        int count=0;
        String query="insert into keywords (contentNum,keyword) values(?,?)";
        for(String keyword:keywords){
            Object[] insertParam=new Object[]{contentNum,keyword};
            if(this.jdbcTemplate.update(query,insertParam)==1){
                count++;
            }
        }
        return count;
    }

    // 게시글 이미지 파일 가져오기
    public String getContentImg(int contentNum){
        String query="select contentImg from content where contentNum=?";
        return this.jdbcTemplate.queryForObject(query, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("contentImg");
            }
        },contentNum);
    }

    // 게시글 상태 변경 메서드
    public int changeContentStatus(int contentNum) {
        String query="update content set status='inactive' where contentNum=?";
        return this.jdbcTemplate.update(query,contentNum);
    }

    // 게시글 검색 메서드
    public List<GetContentResponse> getContentBySearch(String search){
        String query="select c.contentNum,c.contentImg,c.contentTitle,u.userNickName from content c inner join user u on c.userNum=u.userNum where c.status='active' and c.contentTitle like ?";
        return this.jdbcTemplate.query(query, new RowMapper<GetContentResponse>() {
            @Override
            public GetContentResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetContentResponse res=new GetContentResponse();
                res.setContentNum(rs.getInt("contentNum"));
                res.setContentImg(rs.getString("contentImg"));
                res.setContentTitle(rs.getString("contentTitle"));
                res.setUserNickName(rs.getString("userNickName"));
                return res;
            }
        },search);

    }

    // 게시글 스크랩 메서드
    public int contentScrap(int userNum,int contentNum){
        String query="insert into scrap (userNum,contentNum) values(?,?)";
        Object[] insertParam=new Object[]{userNum,contentNum};
        return this.jdbcTemplate.update(query,insertParam);
    }

    // 홈 화면 게시글 리스트
    public List<GetHomeContent> getHomeContents(){
        String query="select contentNum,contentTitle,contentImg from content order by contentNum desc limit 4";
        return this.jdbcTemplate.query(query, new RowMapper<GetHomeContent>() {
            @Override
            public GetHomeContent mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetHomeContent res= new GetHomeContent();
                res.setContentNum(rs.getInt("contentNum"));
                res.setContentTitle(rs.getString("contentTitle"));
                res.setContentImg(rs.getString("contentImg"));
                return res;
            }
        });
    }
    // 홈 화면 인기 게시글 리스트
    public List<GetHomeFamousContent> getHomeFamousContents(){
        String query="select c.contentNum,c.contentImg, (select u.userNickName from user u where u.userNum=c.userNum) as userNickName from content c order by contentNum desc limit 4 ";
        return this.jdbcTemplate.query(query, new RowMapper<GetHomeFamousContent>() {
            @Override
            public GetHomeFamousContent mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetHomeFamousContent res= new GetHomeFamousContent();
                res.setContentNum(rs.getInt("contentNum"));
                res.setContentImg(rs.getString("contentImg"));
                res.setUserNickName(rs.getString("userNickName"));
                return res;
            }
        });
    }
}
