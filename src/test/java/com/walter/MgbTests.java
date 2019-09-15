package com.walter;

import com.walter.domain.mbg.MbgEmployee;
import com.walter.domain.mbg.MbgEmployeeExample;
import com.walter.mapper.mbg.MbgEmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MgbTests {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private void handleMbgEmployee(Consumer<MbgEmployeeMapper> consumer){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MbgEmployeeMapper mapper = session.getMapper(MbgEmployeeMapper.class);
            consumer.accept(mapper);
            session.commit();
        }
    }

    @Test
    public void testMbgSelect(){
        this.handleMbgEmployee(mapper -> {
            // 查询所有
            mapper.selectByExample(null).forEach(mbgEmployee -> log.info("Case1: {}", mbgEmployee.toString()));

            // 条件查询
            MbgEmployeeExample mbgEmployeeExample = new MbgEmployeeExample();
            mbgEmployeeExample.createCriteria().andUserRealNameLike("%Walter%").andGenderEqualTo("M"); // AND条件
            mbgEmployeeExample.or(mbgEmployeeExample.createCriteria().andUserRealNameLike("%Cathy%")); // OR条件
            mapper.selectByExample(mbgEmployeeExample).forEach(mbgEmployee -> log.info("Case2: {}", mbgEmployee.toString()));

            log.info("Case3: {}", mapper.countByExample(mbgEmployeeExample));
        });
    }

    @Test
    public void testMbgInsert(){
        this.handleMbgEmployee(mapper -> {
            MbgEmployee mbgEmployee = new MbgEmployee(null, UUID.randomUUID().toString(),"YYY","Z","AAA","D0001");
            int count = mapper.insertSelective(mbgEmployee);
            log.info("id/count: {}/{}", mbgEmployee.getId(), count);
        });
    }

    @Test
    public void testMbgUpdateSelective(){
        this.handleMbgEmployee(mapper -> {
            MbgEmployee mbgEmployee = new MbgEmployee();
            mbgEmployee.setUserRealName("new user real name");

            MbgEmployeeExample example = new MbgEmployeeExample();
            example.createCriteria().andEmailEqualTo("AAA");
            int count = mapper.updateByExampleSelective(mbgEmployee, example);
            log.info("count: {}", count);
        });
    }

    @Test
    public void testMbgDelete(){
        this.handleMbgEmployee(mapper -> {
            MbgEmployeeExample example = new MbgEmployeeExample();
            example.createCriteria().andEmailEqualTo("AAA");
            int count = mapper.deleteByExample(example);
            log.info("count: {}", count);
        });
    }
}
