package com.walter;

import com.walter.domain.Employee;
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
	public void testGetEmployeeById() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
		    // statement = [<mapperXmlNamespace>.]<statementId>
			Employee employee = session.selectOne("com.walter.EmployeeMapper.selectEmployee", 1);
			log.info(employee.toString());
		}
	}

}
