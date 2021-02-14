package com.app.entity;


import javax.persistence.*;

@Entity
@Table(name = "employee", schema = "public")
public class Employee {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "salary")
    private long salary;

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id")
    private Department departmentByDepartmentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee that = (Employee) o;

        if (salary != that.salary) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (salary ^ (salary >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{").append("\n\t");
        sb.append("id=").append(id).append(",\n\t");
        sb.append("name='").append(name).append('\'').append(",\n\t");
        sb.append("salary='").append(salary).append('\'').append(",\n\t");
        sb.append("department='").append(departmentByDepartmentId.toString()).append('\'').append("\n");
        sb.append('}');
        return sb.toString();
    }

    public Department getDepartmentByDepartmentId() {
        return departmentByDepartmentId;
    }

    public void setDepartmentByDepartmentId(Department departmentByDepartmentId) {
        this.departmentByDepartmentId = departmentByDepartmentId;
    }
}
