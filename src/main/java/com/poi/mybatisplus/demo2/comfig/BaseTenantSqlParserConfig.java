package com.poi.mybatisplus.demo2.comfig;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.ValueListExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;


/**
 * 向MP注入多租户解析器，其中自定义MyTenantSqlParser和MyTenantHandler对MP的租户处理逻辑进行继承拓展
 * 处理器将会判断当前用户是否为租户用户再决定是否过滤不做租户解析
 * 过滤处理的表格将通过配置注入进来
 *
 * @author wangqichang
 * @since 2019/6/10
 */
//@Configuration
public class BaseTenantSqlParserConfig {

    /**
     * 多租户属于 SQL 解析部分，依赖 MP 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        /*
         * 【测试多租户】 SQL 解析处理拦截器<br>
         * 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
         */
        List<ISqlParser> sqlParserList = new ArrayList<>();
        MyTenantParser tenantSqlParser = new MyTenantParser();

        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId(boolean select) {
                /*return new StringValue("zuhuA");*/
                final boolean multipleTenantIds = true;

                if (select) {

                    return multipleTenantIdCondition();

                } else {

                    return singleTenantIdCondition();

                }
                /*return null;*/
            }

            private Expression singleTenantIdCondition() {

                return new StringValue("zuhuA");//ID自己想办法获取到

            }

            private Expression multipleTenantIdCondition() {

                /*final InExpression inExpression = new InExpression();

                inExpression.setLeftExpression(new Column(getTenantIdColumn()));

                final ExpressionList itemsList = new ExpressionList();

                final List<Expression> inValues = new ArrayList<>(2);

                *//*inValues.add(new LongValue(1));//ID自己想办法获取到

                inValues.add(new LongValue(2));*//*

                inValues.add(new StringValue("zuhuA"));
                inValues.add(new StringValue("zuhuB-1"));
                itemsList.setExpressions(inValues);

                inExpression.setRightItemsList(itemsList);
                return inExpression;
                */

                ValueListExpression expression = new ValueListExpression();
                ExpressionList list = new ExpressionList(new StringValue("zuhuA"), new StringValue("zuhuB-1"));
                expression.setExpressionList(list);
                return expression;



            }
        });
        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }
}
