package com.poi.mybatisplus.demo2.comfig;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: mp配置文件
 * @Author: songkai
 * @Date: 2020/11/18
 * @Version: 1.0
 */
@Configuration
public class MybatisPlusConfiguration {
    @Bean
    public OptimisticLockerInterceptor optimisticLockerlnterceptor(){
        return new OptimisticLockerInterceptor();
    }

    /**
     * SQL 执行性能分析插件
     * 开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长
     * dev：开发环境
     * test：测试环境
     * prod：生产环境
     */
    /*@Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(100);//ms，超过此处设置的ms则sql不执行
        performanceInterceptor.setFormat(true);//格式化sql
        return performanceInterceptor;
    }*/

    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();


        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                /*return new LongValue(1);*/
                final boolean multipleTenantIds = true;

                if (multipleTenantIds) {

                    return multipleTenantIdCondition();

                } else {

                    return singleTenantIdCondition();

                }
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                return !"user".equalsIgnoreCase(tableName);
            }

            private Expression singleTenantIdCondition() {

                return new LongValue(1);//ID自己想办法获取到

            }

            private Expression multipleTenantIdCondition() {

                final InExpression inExpression = new InExpression();

                inExpression.setLeftExpression(new Column(getTenantIdColumn()));

                final ExpressionList itemsList = new ExpressionList();

                final List<Expression> inValues = new ArrayList<>(2);

                inValues.add(new LongValue(1));//ID自己想办法获取到

                inValues.add(new LongValue(2));

                itemsList.setExpressions(inValues);

                inExpression.setRightItemsList(itemsList);

                return inExpression;

            }
        }));
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
