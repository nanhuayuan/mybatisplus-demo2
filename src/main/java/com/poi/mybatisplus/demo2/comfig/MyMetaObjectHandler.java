package com.poi.mybatisplus.demo2.comfig;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDateTime;

/**
 * @Description: 自动充填配置
 * @Author: songkai
 * @Date: 2020/11/18
 * @Version: 1.0
 */
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
    }
}
