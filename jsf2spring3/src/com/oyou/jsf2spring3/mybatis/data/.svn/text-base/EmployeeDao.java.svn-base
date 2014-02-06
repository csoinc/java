package com.oyou.jsf2spring3.mybatis.data;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.oyou.jsf2spring3.entity.Employee;

public interface EmployeeDao {
    
    @Select("SELECT * FROM employee WHERE id = #{id}")
    Employee selectEmployee(int id);
    //Employee selectPerson(int id);
    @Insert("INSERT INTO Employee (id,firstname,lastname,gender,age) VALUES (#{id},#{firstname},#{lastname},#{gender},#{age})")
    void insertEmployee(Employee employee);
    //void updateEmployee(Employee employee);
    //void deleteEmployee(int id);

}