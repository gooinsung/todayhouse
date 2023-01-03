package com.example.demo.src.users;

import com.example.demo.config.security.User;
import com.example.demo.src.users.dto.PostJoinRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String query="insert into user (userEmail,userPw,userNickName) values(?,?,?)";
        Object[] insertParam=new Object[]{req.getUserEmail(),req.getUserPw(),req.getUserNickName()};
        return this.jdbcTemplate.update(query,insertParam);
    }







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
