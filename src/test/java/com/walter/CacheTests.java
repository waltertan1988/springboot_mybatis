package com.walter;

import com.walter.domain.Department;
import com.walter.domain.Employee;
import com.walter.mapper.DepartmentMapperByXml;
import com.walter.mapper.EmployeeMapperByXml;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Consumer;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTests {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private void handleDepartment(Consumer<DepartmentMapperByXml> consumer){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DepartmentMapperByXml mapper = session.getMapper(DepartmentMapperByXml.class);
            consumer.accept(mapper);
            session.commit();
        }
    }

    /**
     * 一级缓存（Session级别），默认一直开启
     * （模拟时，请先关闭二级缓存）
     */
    @Test
    public void testFirstCache(){
        final String DEPT_CODE = "D0001";
        final Long ID = 1L;

        this.handleDepartment(mapper -> {
            Department department1 = mapper.getDepartmentByCode(DEPT_CODE); //发SQL

            Department department2 = mapper.getDepartmentByCode(DEPT_CODE); //同一SqlSession的同一条件的查询，不发SQL
            Assert.assertTrue(department2 == department1 && department2.equals(department1));

            Department department3 = mapper.getDepartmentById(ID); //同一SqlSession的不同条件的查询，发SQL
            Assert.assertTrue(department3 != department1 && department3.equals(department1));

            mapper.deleteByCode("XXXXX");
            Department department4 = mapper.getDepartmentByCode(DEPT_CODE); //同一SqlSession的同一条件的查询，但前面执行了增删改操作，发SQL
            Assert.assertTrue(department4 != department1 && department4.equals(department1));

            this.handleDepartment(mapper2 -> {
                Department department5 = mapper2.getDepartmentByCode(DEPT_CODE); //不同SqlSession的同一条件的查询，发SQL
                Assert.assertTrue(department5 != department1 && department5.equals(department1));
            });
        });
    }

    @Test
    public void testClearFirstCache(){
        final String DEPT_CODE = "D0001";

        SqlSession sqlSession = sqlSessionFactory.openSession();

        Department department1 = sqlSession.getMapper(DepartmentMapperByXml.class).getDepartmentByCode(DEPT_CODE);

        sqlSession.clearCache();

        // 同一SqlSession的同一条件的查询，但前面执行了clearCache，发SQL
        Department department2 = sqlSession.getMapper(DepartmentMapperByXml.class).getDepartmentByCode(DEPT_CODE);
        Assert.assertTrue(department2 != department1 && department2.equals(department1));

        sqlSession.close();
    }

    @Test
    public void testSecondCache(){
        final String USER_NAME = "0009785";

        SqlSession sqlSession = null;
        sqlSession = sqlSessionFactory.openSession();
        Employee employee1 = sqlSession.getMapper(EmployeeMapperByXml.class).getEmployeeByUsername(USER_NAME); // 发SQL
        sqlSession.close(); //sqlSession关闭时写入二级缓存

        sqlSession = sqlSessionFactory.openSession();
        Employee employee2 = sqlSession.getMapper(EmployeeMapperByXml.class).getEmployeeByUsername(USER_NAME); // 不发SQL
        sqlSession.close();

        Assert.assertTrue(employee2 != employee1 && employee2.equals(employee1));
    }
}
