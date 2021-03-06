package com.walter;

import com.walter.domain.Department;
import com.walter.domain.Employee;
import com.walter.mapper.DepartmentMapperByXml;
import com.walter.mapper.EmployeeMapperByXml;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class XmlTests {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	private void handleEmployee(Consumer<EmployeeMapperByXml> consumer){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			EmployeeMapperByXml mapper = session.getMapper(EmployeeMapperByXml.class);
			consumer.accept(mapper);
			session.commit();
		}
	}

    private void handleDepartment(Consumer<DepartmentMapperByXml> consumer){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DepartmentMapperByXml mapper = session.getMapper(DepartmentMapperByXml.class);
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
		this.handleEmployee(mapper -> {
			Employee employee = mapper.getEmployeeById(1L);
			log.info(employee.toString());
		});
	}

    @Test
    public void testAlias() {
        this.handleEmployee(mapper -> {
			Employee employee = mapper.getEmployeeByUsername("0009785");
			log.info(employee.toString());
		});
    }

    @Test
    public void testAddOne(){
		Employee employee = new Employee("0008792", "CathyChen",'M',"Cathy.Chen@xxx.com", null);
		this.handleEmployee(mapper -> {
			Long num = mapper.addOne(employee);
			log.info(num.toString());
			log.info(employee.toString());
		});
	}

	@Test
	public void testUpdateOneByUsername(){
		Employee employee = new Employee("0008792", "Cathy Chen",'F',"Cathy.Chen@infinitus.com.cn", null);
		this.handleEmployee(mapper -> {
			Boolean success = mapper.updateOneByUsername(employee);
			log.info(success.toString());
		});
	}

	@Test
	public void testDeleteOneByUsername(){
		this.handleEmployee(mapper -> {
			Integer num = mapper.deleteOneByUsername("0008792");
			log.info(num.toString());
		});
	}

	@Test
	public void testIsIdMatchUsername(){
		this.handleEmployee(mapper -> {
			Boolean isIdMatchUsername = mapper.isIdMatchUsername(1L, "0009785");
			log.info(isIdMatchUsername.toString());
		});
	}

	@Test
	public void testFindByUserRealName(){
		this.handleEmployee(mapper -> {
			mapper.findByUserRealName("%a%").forEach(employee -> log.info(employee.toString()));
		});
	}

	@Test
	public void testGetEmployeeMapById(){
		this.handleEmployee(mapper -> {
			Map<String, Object> employeeMap = mapper.getEmployeeMapByUsername("0009785");
			log.info(employeeMap.toString());
		});
	}

	@Test
	public void testMapUsernameToEmployeeByUserRealName(){
		this.handleEmployee(mapper -> {
			Map<String, Employee> employeeMap = mapper.mapUsernameToEmployeeByUserRealName("%a%");
			employeeMap.entrySet().forEach(entry -> log.info(entry.toString()));
		});
	}

	@Test
	public void testGetEmployeeByUsernameUsingResultMap(){
		this.handleEmployee(mapper -> {
			Employee employee = mapper.getEmployeeByUsernameUsingResultMap("0009785");
			log.info(employee.toString());
		});
	}

	@Test
	public void testFindWithDepartmentByUserRealNameUsingResultMap(){
		this.handleEmployee(mapper -> mapper.findWithDepartmentByUserRealNameUsingResultMap("%a%")
				.forEach(employee -> log.info(employee.toString())));
	}

	@Test
	public void testFindWithDepartmentByUserRealNameUsingResultMapBy2Steps(){
		this.handleEmployee(mapper -> mapper.findWithDepartmentByUserRealNameUsingResultMapBy2Steps("%a%")
				.forEach(employee -> log.info(employee.toString())));
	}

    @Test
    public void testFindWithDepartmentByUserRealNameUsingResultMapBy2StepsWithLazyLoad(){
        this.handleEmployee(mapper -> mapper.findWithDepartmentByUserRealNameUsingResultMapBy2Steps("%a%")
                .forEach(employee -> log.info(employee.getUsername())));
    }

    @Test
    public void testGetDepartmentWithEmployeesByCode(){
        this.handleDepartment(mapper -> {
            Department department = mapper.getDepartmentWithEmployeesByCode("D0001");
            log.info(department.toString());
        });
    }

    @Test
    public void testGetDepartmentWithEmployeesByCodeUsingResultMapBy2Steps(){
        this.handleDepartment(mapper -> {
            Department department = mapper.getDepartmentWithEmployeesByCodeUsingResultMapBy2Steps("D0001");
            log.info(department.toString());
        });
    }

    @Test
    public void testFindByConditionIf(){
		this.handleEmployee(mapper -> {
			Employee condition = new Employee("0009785","Walter",'M',null, null);
			List<Employee> employees = mapper.findByConditionIf(condition);
			employees.forEach(employee -> log.info(employee.toString()));
		});
	}

	@Test
	public void testFindByConditionChoose(){
        this.handleEmployee(mapper -> {
            Employee condition = new Employee();
//            condition.setId(1L);
//            condition.setUsername("0008792");
            List<Employee> employees = mapper.findByConditionChoose(condition);
            employees.forEach(employee -> log.info(employee.toString()));
        });
    }

	@Test
	public void testFindByConditionChooseWithInnerParameters(){
		this.handleEmployee(mapper -> {
			List<Employee> employees = mapper.findByConditionChoose(null);
			employees.forEach(employee -> log.info(employee.toString()));
		});
	}

    @Test
    public void testUpdateEmployeeByCondition(){
		this.handleEmployee(mapper -> {
			Employee condition = new Employee("0008792", "Cathy Chen", 'F', "Cathy.Chen@infinitus.com.cn", null);
			Long count = mapper.updateEmployeeByCondition(condition);
			log.info(count.toString());
		});
	}

	@Test
	public void testFindByUsernamesWithConditionForeach(){
		this.handleEmployee(mapper -> mapper.findByUsernamesWithConditionForeach(Arrays.asList("0009785","0008792"))
				.forEach(employee -> log.info(employee.toString()))
		);
	}
}
