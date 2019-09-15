package com.walter;

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
    public void testMbg(){
        this.handleMbgEmployee(mapper -> {
            // 查询所有
            mapper.selectByExample(null).forEach(mbgEmployee -> log.info(mbgEmployee.toString()));

            // 条件查询
            MbgEmployeeExample mbgEmployeeExample = new MbgEmployeeExample();
            mbgEmployeeExample.createCriteria().andUserRealNameLike("%Walter%").andGenderEqualTo("M"); // AND条件
            mbgEmployeeExample.or(mbgEmployeeExample.createCriteria().andUserRealNameLike("%Cathy%")); // OR条件
            mapper.selectByExample(mbgEmployeeExample).forEach(mbgEmployee -> log.info(mbgEmployee.toString()));;
        });
    }
}
