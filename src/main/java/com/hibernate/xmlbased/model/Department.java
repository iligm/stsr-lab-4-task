package com.hibernate.xmlbased.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "department_id", length = 3)
    private String departmentId;

    @Column(name = "department_name", unique = true, nullable = false, length = 100)
    private String departmentName;

    @Column(name = "location", nullable = false)
    private String location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Developer> developers;

    public Department(String departmentId, String departmentName, String location) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.location = location;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public Department() {
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Department [departmentId=" + departmentId +
               ", departmentName=" + departmentName +
               ", location=" + location + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, departmentName, location);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;
        return Objects.equals(departmentId, other.departmentId)
            && Objects.equals(departmentName, other.departmentName)
            && Objects.equals(location, other.location);
    }
}
