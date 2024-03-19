package com.lab2.repositories.impl;

import com.lab2.repositories.HibernateUtils;
import com.lab2.models.Color;
import com.lab2.repositories.interfaces.ColorRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.Optional;

public class ColorRepositoryImpl implements ColorRepository {

    private final SessionFactory sessionFactory;

    public ColorRepositoryImpl() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public Optional<Color> getById(Long id) {
        Session session = sessionFactory.openSession();
        Optional<Color> result = Optional.ofNullable(session.get(Color.class, id));
        session.close();

        return result;
    }

    @Override
    public Collection<Color> getAll() {
        Session session = sessionFactory.openSession();
        Collection<Color> result = session.createQuery("from Color", Color.class).list();
        session.close();

        return result;
    }

    @Override
    public Color update(Color color) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(color);

        Color result = session.get(Color.class, color.getId());
        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public void delete(Color color) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(color);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void insert(Color color) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(color);
        session.getTransaction().commit();
        session.close();
    }
}
