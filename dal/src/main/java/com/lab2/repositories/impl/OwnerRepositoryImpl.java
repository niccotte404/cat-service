package com.lab2.repositories.impl;

import com.lab2.models.Cat;
import com.lab2.models.Owner;
import com.lab2.repositories.HibernateUtils;
import com.lab2.repositories.interfaces.OwnerRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OwnerRepositoryImpl implements OwnerRepository {

    private final SessionFactory sessionFactory;

    public OwnerRepositoryImpl() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public Optional<Owner> getById(UUID id) {
        Session session = sessionFactory.openSession();
        Optional<Owner> result = Optional.ofNullable(session.get(Owner.class, id));
        session.close();

        return result;
    }

    @Override
    public Collection<Owner> getAll() {
        Session session = sessionFactory.openSession();
        Collection<Owner> result = session.createQuery("from Owner", Owner.class).list();
        session.close();

        return result;
    }

    @Override
    public Owner update(Owner owner) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(owner);

        Owner result = session.get(Owner.class, owner.getId());
        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public void delete(Owner owner) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(owner);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void insert(Owner owner) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(owner);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Cat> getAllCatsByOwner(Owner owner) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Cat> catCriteriaQuery = criteriaBuilder.createQuery(Cat.class);
        Root<Owner> rootOwner = catCriteriaQuery.from(Owner.class);
        catCriteriaQuery.select(rootOwner.get("orders")).where(criteriaBuilder.equal(rootOwner, owner));

        List<Cat> orders = session.createQuery(catCriteriaQuery).getResultList();
        session.close();

        return orders;
    }

    @Override
    public void addCatToOwner(Owner owner, Cat cat) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        owner.getCats().add(cat);
        session.merge(owner);

        session.getTransaction().commit();
        session.close();
    }
}
