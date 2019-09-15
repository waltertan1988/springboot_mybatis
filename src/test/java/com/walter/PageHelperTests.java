package com.walter;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.walter.domain.mbg.MbgEmployee;
import com.walter.domain.mbg.MbgEmployeeExample;
import com.walter.mapper.mbg.MbgEmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PageHelperTests {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private void handleMbgEmployee(Consumer<MbgEmployeeMapper> consumer){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MbgEmployeeMapper mapper = session.getMapper(MbgEmployeeMapper.class);
            consumer.accept(mapper);
            session.commit();
        }
    }

    @Before
    public void before(){
        this.handleMbgEmployee(mapper -> {
            for (int i = 0; i < 51; i++) {
                MbgEmployee mbgEmployee = new MbgEmployee(null, UUID.randomUUID().toString(),"YYY","Z","AAA","D0001");
                mapper.insertSelective(mbgEmployee);
            }
        });
    }

    @After
    public void after(){
        this.handleMbgEmployee(mapper -> {
            MbgEmployeeExample example = new MbgEmployeeExample();
            example.createCriteria().andUserRealNameLike("%YYY%");
            mapper.deleteByExample(example);
        });
    }

    @Test
    public void testSelect(){
        this.handleMbgEmployee(mapper -> {
            MbgEmployeeExample example = new MbgEmployeeExample();
            example.createCriteria().andUserRealNameLike("%YYY%");
            Page<MbgEmployee> page = PageHelper.startPage(1, 5);
            mapper.selectByExample(example).forEach(mbgEmployee -> log.info("Case1: {}", mbgEmployee.toString()));

            log.info("当前页：{}", page.getPageNum());
            log.info("总记录数：{}", page.getTotal());
            log.info("每页记录数：{}", page.getPageSize());
            log.info("总页数：{}", page.getPages());
        });
    }

    @Test
    public void testPageInfo(){
        this.handleMbgEmployee(mapper -> {
            MbgEmployeeExample example = new MbgEmployeeExample();
            example.createCriteria().andUserRealNameLike("%YYY%");
            PageHelper.startPage(6, 5);
            List<MbgEmployee> result = mapper.selectByExample(example);
            PageInfo<MbgEmployee> info = new PageInfo<>(result, 5);

            log.info("当前页：{}", info.getPageNum());
            log.info("总记录数：{}", info.getTotal());
            log.info("每页记录数：{}", info.getPageSize());
            log.info("总页数：{}", info.getPages());
            log.info("下一页：{}",info.getNextPage());
            log.info("上一页：{}",info.getPrePage());
            log.info("是否第一页：{}", info.isIsFirstPage());
            log.info("是否有下一页：{}", info.isHasNextPage());
            log.info("是否最末页：{}", info.isIsLastPage());
            log.info("是否有上一页：{}", info.isHasPreviousPage());
            log.info("连续显示的导航页：{}", info.getNavigatepageNums());
        });
    }
}
