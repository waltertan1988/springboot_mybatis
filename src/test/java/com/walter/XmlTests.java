package com.walter;

import com.walter.domain.Employee;
import com.walter.mapper.EmployeeMapperByXml;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class XmlTests {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Test
	public void testWithoutMapperGetEmployeeById() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
		    // statement = [<mapperXmlNamespace>.]<statementId>
			Employee employee = session.selectOne("com.walter.mapper.EmployeeMapperByXml.getEmployeeById", 1);
			log.info(employee.toString());
		}
	}

	@Test
	public void testWithMapperGetEmployeeById() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			EmployeeMapperByXml mapper = session.getMapper(EmployeeMapperByXml.class);
			Employee employee = mapper.getEmployeeById(1L);
			log.info(employee.toString());
		}
	}

    @Test
    public void testAlias() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            EmployeeMapperByXml mapper = session.getMapper(EmployeeMapperByXml.class);
            Employee employee = mapper.getEmployeeByUsername("0009785");
            log.info(employee.toString());
        }
    }
}
