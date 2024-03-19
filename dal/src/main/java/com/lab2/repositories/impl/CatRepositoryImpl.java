package com.lab2.repositories.impl;

import com.lab2.models.Cat;
import com.lab2.models.Color;
import com.lab2.models.Owner;
import com.lab2.repositories.HibernateUtils;
import com.lab2.repositories.interfaces.CatRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CatRepositoryImpl implements CatRepository {

    private final SessionFactory sessionFactory;

    public CatRepositoryImpl() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public Optional<Cat> getById(UUID id) {
        Session session = sessionFactory.openSession();
        Optional<Cat> result = Optional.ofNullable(session.get(Cat.class, id));
        session.close();

        return result;
    }

    @Override
    public Collection<Cat> getAll() {
        Session session = sessionFactory.openSession();
        Collection<Cat> result = session.createQuery("from Cat", Cat.class).list();
        session.close();

        return result;
    }

    @Override
    public Cat update(Cat cat) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(cat);

        Cat result = session.get(Cat.class, cat.getId());
        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public void delete(Cat cat) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(cat);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void insert(Cat cat) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(cat);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Owner getOwnerByCat(Cat cat) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);
        Root<Cat> rootCat = criteriaQuery.from(Cat.class);
        criteriaQuery.select(rootCat.get("owner")).where(criteriaBuilder.equal(rootCat, cat));

        Owner owner = session.createQuery(criteriaQuery).getSingleResult();
        session.close();

        return owner;
    }

    @Override
    public List<Cat> getCatFriends(Cat cat) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Cat> criteriaQuery = criteriaBuilder.createQuery(Cat.class);
        Root<Cat> rootCat = criteriaQuery.from(Cat.class);
        Join<Cat, Cat> friends = rootCat.join("friends");

        criteriaQuery.select(friends).where(criteriaBuilder.equal(rootCat, cat));
        List<Cat> friendsList = session.createQuery(criteriaQuery).getResultList();
        session.close();

        return friendsList;
    }

    @Override
    public void addCatFriend(Cat cat, Cat friendCat) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        cat.getFriends().add(friendCat);
        friendCat.getFriends().add(cat);
        session.merge(cat);
        session.merge(friendCat);

        session.getTransaction().commit();
        session.close();
    }
}
