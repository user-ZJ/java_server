package com.demo;

import javax.sql.DataSource;
import java.util.List;

public interface StudentDAO {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);

    /**
     * This is the method to be used to create
     * a record in the Student table.
     */
    public void create(String name, Integer age);
    //public Student getStudent(Integer id);

    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */
    public List<Student> listStudents();
}
