package com.hibernate.xmlbased.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.hibernate.xmlbased.config.SessionConfig;
import com.hibernate.xmlbased.model.Department;
import com.hibernate.xmlbased.model.Developer;

public class DeveloperDAO {

    private SessionConfig sc;

    public DeveloperDAO() {
        sc = SessionConfig.getInstanceOfSeccionFactory();
    }

    public void addDeveloper(Developer developer) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(developer);
        transaction.commit();
        session.close();
    }

    public Developer getDeveloperById(Integer id) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Developer developer = session.get(Developer.class, id);
        transaction.commit();
        session.close();
        return developer;
    }

    public List<Developer> getDevelopers() {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Developer> developers =
                session.createQuery("FROM Developer", Developer.class)
                       .list();
        transaction.commit();
        session.close();
        return developers;
    }

    public Developer updateDevelopersDepartment(Integer devId, Department department) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Developer> cq = cb.createQuery(Developer.class);
        Root<Developer> root = cq.from(Developer.class);
        cq.select(root).where(cb.equal(root.get("id"), devId));
        Developer developer = session.createQuery(cq).uniqueResult();

        if (developer != null) {
            developer.setDepartment(department);
            session.merge(developer);
        }

        transaction.commit();
        session.close();

        return developer;
    }

    public void removeDeveloper(Integer id) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Developer developer = session.get(Developer.class, id);
        session.remove(developer);
        transaction.commit();
        session.close();
    }

    public List<Developer> findByExperienceEqual(Integer experience) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Developer> cq = cb.createQuery(Developer.class);
        Root<Developer> root = cq.from(Developer.class);
        cq.select(root).where(cb.equal(root.get("experience"), experience));
        List<Developer> developers = session.createQuery(cq).getResultList();
        transaction.commit();
        session.close();
        return developers;
    }

    public List<Developer> findByExperienceEqualCriteria(Integer experience) {
        // Реализация почти идентична, оставим для примера
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Developer> cq = cb.createQuery(Developer.class);
        Root<Developer> root = cq.from(Developer.class);
        cq.select(root).where(cb.equal(root.get("experience"), experience));
        List<Developer> developers = session.createQuery(cq).getResultList();
        transaction.commit();
        session.close();
        return developers;
    }
}
