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

import java.util.Map;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class XmlTests {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	private void handle(Consumer<EmployeeMapperByXml> consumer){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			EmployeeMapperByXml mapper = session.getMapper(EmployeeMapperByXml.class);
			consumer.accept(mapper);
			session.commit();
		}
	}

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
		this.handle(mapper -> {
			Employee employee = mapper.getEmployeeById(1L);
			log.info(employee.toString());
		});
	}

    @Test
    public void testAlias() {
        this.handle(mapper -> {
			Employee employee = mapper.getEmployeeByUsername("0009785");
			log.info(employee.toString());
		});
    }

    @Test
    public void testAddOne(){
		Employee employee = new Employee("0008792", "CathyChen",'M',"Cathy.Chen@xxx.com");
		this.handle(mapper -> {
			Long num = mapper.addOne(employee);
			log.info(num.toString());
			log.info(employee.toString());
		});
	}

	@Test
	public void testUpdateOneByUsername(){
		Employee employee = new Employee("0008792", "Cathy Chen",'F',"Cathy.Chen@infinitus.com.cn");
		this.handle(mapper -> {
			Boolean success = mapper.updateOneByUsername(employee);
			log.info(success.toString());
		});
	}

	@Test
	public void testDeleteOneByUsername(){
		this.handle(mapper -> {
			Integer num = mapper.deleteOneByUsername("0008792");
			log.info(num.toString());
		});
	}

	@Test
	public void testIsIdMatchUsername(){
		this.handle(mapper -> {
			Boolean isIdMatchUsername = mapper.isIdMatchUsername(1L, "0009785");
			log.info(isIdMatchUsername.toString());
		});
	}

	@Test
	public void testFindByUserRealName(){
		this.handle(mapper -> {
			mapper.findByUserRealName("%a%").forEach(employee -> log.info(employee.toString()));
		});
	}

	@Test
	public void testGetEmployeeMapById(){
		this.handle(mapper -> {
			Map<String, Object> employeeMap = mapper.getEmployeeMapByUsername("0009785");
			log.info(employeeMap.toString());
		});
	}

	@Test
	public void testMapUsernameToEmployeeByUserRealName(){
		this.handle(mapper -> {
			Map<String, Employee> employeeMap = mapper.mapUsernameToEmployeeByUserRealName("%a%");
			employeeMap.entrySet().forEach(entry -> log.info(entry.toString()));
		});
	}
}
