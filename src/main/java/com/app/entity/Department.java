package com.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "department", schema = "public")
public class Department {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, unique = true)
    protected long id;

    @Basic
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "departmentByDepartmentId", targetEntity = Employee.class)
    private Collection<Employee> employeesById;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Department{").append("\n\t");
        sb.append("id=").append(id).append(",\n\t");
        sb.append("name='").append(name).append("',\n\t");
        sb.append('}');
        return sb.toString();
    }

    @JsonIgnore
    public Collection<Employee> getEmployeesById() {
        return employeesById;
    }

    public void setEmployeesById(Collection<Employee> employeesById) {
        this.employeesById = employeesById;
    }
}
