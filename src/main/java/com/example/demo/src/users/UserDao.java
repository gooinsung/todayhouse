package com.example.demo.src.users;

import com.amazonaws.services.ec2.model.CreateSpotDatafeedSubscriptionRequest;
import com.example.demo.config.security.User;
import com.example.demo.src.users.dto.GetScrapResponse;
import com.example.demo.src.users.dto.MyPageResponse;
import com.example.demo.src.users.dto.PostJoinRequest;
import com.example.demo.src.users.dto.object.MyPageContent;
import com.example.demo.src.users.dto.object.MyPageScrap;
import com.example.demo.src.users.dto.object.ScrapTypeNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.sql.RowSetWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 이메일 중복 체크
    public int checkEmail(String email){
        String query="select exists(select * from user where status='active' and userEmail=?)";
        return  this.jdbcTemplate.queryForObject(query,int.class,email);
    }

    // 비밀번호 가져오기
    public String getUserPw(String email){
        String query="select userPw from user where status='active' and userEmail=?";
        return this.jdbcTemplate.queryForObject(query,String.class,email);
    }

    // 회원 가입
    public int createUser(PostJoinRequest req){
        String query="insert into user (userEmail,userPw,userNickName,agree1,agree2) values(?,?,?,?,?)";
        Object[] insertParam=new Object[]{req.getUserEmail(),req.getUserPw(),req.getUserNickName(),req.getAgree1(),req.getAgree2()};
        return this.jdbcTemplate.update(query,insertParam);
    }

    // 게시글 스크랩 추가
    public int insertContentScrap(int userNum, int contentNum){
        String query="insert into scrap (userNum,contentNum) values(?,?)";
        Object[] insertParam=new Object[]{userNum,contentNum};
        return this.jdbcTemplate.update(query,insertParam);
    }

    // 상품 스크랩 추가
    public int insertProductScrap(int userNum,int productNum){
        String query="insert into scrap (userNum,number,type) values(?,?,?)";
        Object[] insertParam=new Object[]{userNum,productNum};
        return this.jdbcTemplate.update(query,insertParam);
    }

    public int insertScrap(int userNum, String type, int number){
        String query="insert into scrap(userNum,type,number) values(?,?,?)";
        Object[] insertParam=new Object[]{userNum,type,number};
        return this.jdbcTemplate.update(query,insertParam);
    }

    // 스크랩 상품 조회
    public List<ScrapTypeNumber> getTypeAndNumber(int userNum){
        String query="select type,number from scrap where userNum=?";
        return this.jdbcTemplate.query(query, new RowMapper<ScrapTypeNumber>() {
            @Override
            public ScrapTypeNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
                ScrapTypeNumber tn= new ScrapTypeNumber();
                tn.setNumber(rs.getInt("number"));
                tn.setType(rs.getString("type"));
                return tn;
            }
        },userNum);
    }

    // 게시글 썸네일 조회
    public String getContentThumbnail(int number){
        String query="select contentImg from content where contentNum=?";
        return this.jdbcTemplate.queryForObject(query, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("contentImg");
            }
        },number);
    }

    // 상품 썸네일 조회
    public String getProductThumbnail(int number){
        String query="select thumbnail from productThumbnails where productNum=? limit 1";
        return this.jdbcTemplate.queryForObject(query, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("thumbnail");
            }
        },number);
    }


    // 마이페이지 조회를 위한 MyPageResponse 메서드
    public MyPageResponse getMyPage(int userNum){
        String query="select u.userNum,u.userNickName,u.userImg, count(s.scrapNum) as scrapCnt from user u inner join scrap s on u.userNum=s.userNum where u.status='active' and u.userNum=?";
        return this.jdbcTemplate.queryForObject(query, new RowMapper<MyPageResponse>() {
            @Override
            public MyPageResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                MyPageResponse res= new MyPageResponse();
                res.setUserNum(rs.getInt("userNum"));
                res.setUserNickName(rs.getString("userNickName"));
                res.setUserImg(rs.getString("userImg"));
                res.setScrapCnt(rs.getInt("scrapCnt"));
                return res;
            }
        },userNum);
    }

    // 마이페이지 게시글 조회를 위한 메서드
    public List<MyPageContent> getMyContents(int userNum){
        List<MyPageContent> response=new ArrayList<>();
        for(int i=1; i<=9; i++){
            String query="select contentNum,contentImg from content where userNum=? and contentCate="+i+" limit 1";
            MyPageContent content= this.jdbcTemplate.queryForObject(query, new RowMapper<MyPageContent>() {
                @Override
                public MyPageContent mapRow(ResultSet rs, int rowNum) throws SQLException {
                    MyPageContent res=new MyPageContent();
                    res.setContentNum(rs.getInt("contentNum"));
                    res.setThumbnail(rs.getString("contentImg"));
                    return res;
                }
            },userNum);
            response.add(content);
        }
        return response;
    }
    private String type;
    private int number;
    private String thumbnail;






    // PrincipalDetailsService 를 위한 loadByUsername
    public User loadByUsername(String userNickName){
        String query="select userNum,userEmail,userPw,userNickName,userImg from user where userEmail=?";
        return this.jdbcTemplate.queryForObject(query, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user= new User();
                user.setUserNum(rs.getInt("userNum"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setUserNickName(rs.getString("userNickName"));
                user.setUserPw(rs.getString("userPw"));
                user.setUserImg(rs.getString("userImg"));
                return user;
            }
        },userNickName);
    }
}
