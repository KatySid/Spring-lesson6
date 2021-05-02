package ru.geekbrains.spring.one.repositories;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.one.model.Category;
import ru.geekbrains.spring.one.model.Product;
import ru.geekbrains.spring.one.utils.HibernateUtils;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryRepository {
    private HibernateUtils hibernateUtils;

    @Autowired
    public CategoryRepository(HibernateUtils hibernateUtils) {
        this.hibernateUtils = hibernateUtils;
    }

    public List<Category> findAll() {
        try (Session session = hibernateUtils.getCurrentSession()) {
            session.beginTransaction();
            List<Category> categories = session.createQuery("from Category").getResultList();
            session.getTransaction().commit();
            return categories;
        }
    }

    public void save(Category category) {
        try (Session session = hibernateUtils.getCurrentSession()) {
            session.beginTransaction();
            session.saveOrUpdate(category);
            session.getTransaction().commit();
        }
    }

    public Optional<Category> findOneById(Long id) {
        try (Session session = hibernateUtils.getCurrentSession()) {
            session.beginTransaction();
            Optional<Category> category = Optional.ofNullable(session.get(Category.class, id));
            session.getTransaction().commit();
            return category;
        }
    }

    }
