package com.hibernate.xmlbased.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.hibernate.xmlbased.config.SessionConfig;
import com.hibernate.xmlbased.model.Department;
import com.hibernate.xmlbased.model.Developer;

public class DepartmentDAO {

    private SessionConfig sc;

    public DepartmentDAO() {
        sc = SessionConfig.getInstanceOfSeccionFactory();
    }

    public List<Department> getDepartments() {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Department> deps =
                session.createQuery("FROM Department", Department.class)
                       .getResultList();
        transaction.commit();
        session.close();
        return deps;
    }

    // этот кусок кода решает проблему n+1
    public Set<Department> getDepartmentWithWorkers() {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Set<Department> departments = new HashSet<>(
                session.createQuery(
                        "FROM Department d LEFT JOIN FETCH d.developers",
                        Department.class
                ).getResultList()
        );
        transaction.commit();
        session.close();
        return departments;
    }

    public List<Developer> getDevelopersByDepartment(Department department) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department dep = session.get(Department.class, department.getDepartmentId());
        Hibernate.initialize(dep.getDevelopers());
        List<Developer> devs = dep.getDevelopers();
        transaction.commit();
        session.close();
        return devs;
    }

    public Department findDepartmentByID(String departmentID) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department department = null;
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Department> cq = cb.createQuery(Department.class);
            Root<Department> root = cq.from(Department.class);
            cq.select(root).where(cb.equal(root.get("departmentId"), departmentID));
            department = session.createQuery(cq).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return department;
    }

    public Department addDepartament(Department dep) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Department mergedDep = null;
        try {
            mergedDep = session.merge(dep);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return mergedDep;
    }

    public void deleteDepartment(String ID) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Department> cq = cb.createQuery(Department.class);
            Root<Department> root = cq.from(Department.class);
            cq.select(root).where(cb.equal(root.get("departmentId"), ID));
            Department department = session.createQuery(cq).uniqueResult();
            if (department != null) {
                session.remove(department);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public boolean findDepartmentByLetterInID(char c) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        boolean found = false;
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Department> root = cq.from(Department.class);
            cq.select(cb.count(root)).where(cb.like(root.get("departmentId"), c + "%"));
            Long count = session.createQuery(cq).getSingleResult();
            found = count != null && count > 0;
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return found;
    }
}
