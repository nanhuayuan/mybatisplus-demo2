package com.poi.mybatisplus.demo2;


import com.poi.mybatisplus.demo2.entity.User;
import com.poi.mybatisplus.demo2.mapper.TenantMapper;
import com.poi.mybatisplus.demo2.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceTest {


/*    性能分析插件
    用于输出sql语句以及时间
1.开启
    1.bean注解
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //更新时间
    @TableField(fill = FieldFill.UPDATE)//也可以插入，更新时填充
    private LocalDateTime updateTime;
    2.处理器接口
    @Component
public class MyMetaObjectHandler  implements MetaObjectHandler {
    @Override
    //新增时
    public void insertFill(MetaObject metaObject) {
        //第一个字段是bean的属性，不是数据库字段 第二是值
        setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject);
        //既写用于新增也可以写在更新
        setFieldValByName("createTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新
        setUpdateFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}


    */
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TenantMapper tenantMapper;
    @Test
    public void testInsert() {
        User user = new User();
        user.setName("刘明超");
        user.setAge(31);
        user.setEmail("Imc@baomidou.com");
        user.setManagerId(1088248166370832385L);
        int rows=userMapper.insert(user);
        System.out.println("影响行数："+rows);
    }
/*
//create_time也冲充填了
==>  Preparing: INSERT INTO user ( id, name, age, email, manager_id, create_time ) VALUES ( ?, ?, ?, ?, ?, ? )
==> Parameters: 1328999345945337858(Long), 刘明超(String), 31(Integer), Imc@baomidou.com(String), 1088248166370832385(Long), 2020-11-18T17:51:50.349(LocalDateTime)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5a67e962]
影响行数：1
    */

    /*
        更新
    */
    @Test
    public void testUpdate() {
        User user=new User();
        user.setAge(26);
        user.setId(1088248166370832385L);
        int rows=userMapper.updateById(user);
        System.out.println("影响行数："+ rows );
    }
/*
==>  Preparing: UPDATE user SET age=?, update_time=? WHERE id=? AND deleted=0
==> Parameters: 26(Integer), 2020-11-18T17:53:17.982(LocalDateTime), 1088248166370832385(Long)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@545e57d7]
影响行数：1
    */

    // TODO 自动填充优化
    /*
    自动填充优化
每个类都会调用，有性能开销。希望有这个属性的调用
当设置了更新时间，就不自动设置，没设置就自动设置
*/
 /*   拦截器
 @Component
    public class MyMetaObjectHandler  implements MetaObjectHandler {
        @Override
        //新增时
        public void insertFill(MetaObject metaObject) {
            //bean中是否有这个属性
            boolean hasSetter = metaObject. hasSetter("createTime");
            if(hasSetter){
                //第一个字段是bean的属性，不是数据库字段 第二是值
                setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject);
            }
            //既写用于新增也可以写在更新
            //setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            //获得这个属性的值
            Object val=getFieldValByName("updateTime", metaObject);
            if(val==null){
                System.out.println("updateFil~~");
                //更新
                setUpdateFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            }
        }*/
}
