package com.poi.mybatisplus.demo2;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.poi.mybatisplus.demo2.entity.User;
import com.poi.mybatisplus.demo2.mapper.TenantMapper;
import com.poi.mybatisplus.demo2.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptimisticLockTest {


/*    乐观锁实现
@Configuration
public class MybatisPlusConfiguration {
    @Bean
    public OptimisticLockerInterceptor optimisticLockerlnterceptor(){
        return new OptimisticLockerInterceptor();
    }
}
    2.bean中加上版本注解
    @Version
    private Integer version;

    */
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TenantMapper tenantMapper;

    /*
        更新
    */
    @Test
    public void testUpdate() {
        int version = 1;
        User user=new User();
        user.setAge(26);
        user.setId(1328999345945337858L);
        user.setVersion(version);
        int rows=userMapper.updateById(user);
        System.out.println("影响行数："+ rows );
    }
/*
//加上版本
==>  Preparing: UPDATE user SET age=?, update_time=?, version=? WHERE id=? AND version=? AND deleted=0
==> Parameters: 26(Integer), 2020-11-18T19:02:51.084(LocalDateTime), 2(Integer), 1328999345945337858(Long), 1(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7f829c76]
影响行数：1
    */

    // TODO wrapper不能复用
    /*
特别说明：
·支持的数据类型只有：int，Integer，long，Long，Date，Timestamp，LocalDateTime
·整数类型下neVersion=oldVersion+1
·neVersion 会回写到entity中
·仅支持updateByrd（id）与update（entity，wrapper）方法·在update（entity，wrapper）方法下，wrapper 不能复用！！！
*/
     /*
        更新
    */
    @Test
    public void testUpdate2() {
        int version = 2;
        User user=new User();
        user.setAge(27);
        user.setId(1328999345945337858L);
        user.setVersion(version);

        QueryWrapper<User> query= Wrappers.<User>query();
        query.eq("name","刘明超");
        int rows=userMapper.update(user,query);
        System.out.println("影响行数："+rows);


        int version2 = 3;
        User user2=new User();
        user2.setAge(28);
        user2.setVersion(version2);
        //这里复用了wp
        query.eq("name","刘明超");
        int rows2=userMapper.update(user2,query);
        System.out.println("影响行数："+ rows2 );
    }
/*
//第一次执行成功
==>  Preparing: UPDATE user SET age=?, update_time=?, version=? WHERE id=? AND version=? AND deleted=0
        ==> Parameters: 26(Integer), 2020-11-18T19:02:51.084(LocalDateTime), 2(Integer), 1328999345945337858(Long), 1(Integer)
        <==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7f829c76]
影响行数：1
//第二次执行  第一个更新成功 versin =2 更新为3了
/第二个更新失败 拼接了两个version  version=?AND age=? AND version=?
==>  Preparing: UPDATE user SET age=?, update_time=?, version=? WHERE deleted=0 AND (name = ? AND version = ?)
==> Parameters: 27(Integer), 2020-11-18T19:12:46.188(LocalDateTime), 3(Integer), 刘明超(String), 2(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5c748168]
影响行数：1
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@8ee0c23] was not registered for synchronization because synchronization is not active
updateFil~~
JDBC Connection [HikariProxyConnection@716550087 wrapping com.mysql.cj.jdbc.ConnectionImpl@4d27d9d] will not be managed by Spring
==>  Preparing: UPDATE user SET age=?, update_time=?, version=? WHERE deleted=0 AND (name = ? AND version = ? AND name = ? AND version = ?)
==> Parameters: 28(Integer), 2020-11-18T19:12:46.422(LocalDateTime), 4(Integer), 刘明超(String), 2(Integer), 刘明超(String), 3(Integer)
<==    Updates: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@8ee0c23]
影响行数：0
*/
}
