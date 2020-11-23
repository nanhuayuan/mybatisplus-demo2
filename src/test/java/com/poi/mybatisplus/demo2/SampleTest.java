package com.poi.mybatisplus.demo2;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.poi.mybatisplus.demo2.entity.Tenant;
import com.poi.mybatisplus.demo2.entity.User;
import com.poi.mybatisplus.demo2.mapper.TenantMapper;
import com.poi.mybatisplus.demo2.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private TenantMapper tenantMapper;
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = mapper.selectList(null);
        //Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }
    @Test
    public void testTenantFilter() {
        System.out.println("getAddrAndUser 结束 分割线");
        mapper.getUserAndAddr(null).forEach(System.out::println);
    }

 /*   @Test
    public void testInsert() {
        User user = new User();
        user.setId(4L);
        user.setName("张三");
        user.setAge(20);
        user.setEmail("999@qq.com");
        user.setManagerId(1087982257332887553L);
        user.setTenantId("zuhuA");
        user.setTenantId(null);
        int insert = mapper.insert(user);
        System.out.println("insert:" + insert);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(3L);
        user.setName("张三-修改3");

        int insert = mapper.updateById(user);
        System.out.println("insert:" + insert);
    }

    @Test
    public void testQuery() {

        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        //QueryWrapper<User>query =Wrappers.<User>query)
        //queryWrapper.in("tenant_id", "zuhuA");

        *//*QueryWrapper<Tenant> tWrapper = new QueryWrapper<Tenant>();
        tWrapper.eq("tenant_id", "zuhuA");
        Tenant tenant = tenantMapper.selectOne(tWrapper);*//*

        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    public void findAllChild( Tenant resource) {
        QueryWrapper<Tenant> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", resource.getPid());
        // 首次进入这个方法时，查出的是二级节点列表
        // 递归调用时，就能依次查出三、四、五等等级别的节点列表，
        // 递归能实现不同节点级别的无限调用,这个层次不易太深，否则有性能问题
        List<Tenant> resources = tenantMapper.selectList(wrapper);
        resource.setChildren(resources);
        if (resources != null && resources.size() > 0) {
            resources.forEach(this::findAllChild);
        }
    }


    @Test
    public void testTenantFilter2() {
//        mapper.getUserAndAddr().forEach(System.out::println);
        System.out.println("getAddrAndUser 结束 分割线");
        mapper.getUserAndAddr(null).forEach(System.out::println);

    }*/
}
