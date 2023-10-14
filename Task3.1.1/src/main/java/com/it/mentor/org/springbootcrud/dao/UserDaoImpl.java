package com.it.mentor.org.springbootcrud.dao;

import com.it.mentor.org.springbootcrud.model.*;
import jakarta.persistence.*;
import org.springframework.stereotype.*;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getOne(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void deleteUser(Long id) {
        User user = getOne(id);
        entityManager.remove(user);
    }

    @Override
    public List<User> search(String keyword) {
        String hqlSearch = """
                SELECT u FROM User u WHERE
                u.name LIKE '%' || :keyword || '%'
                OR u.email LIKE '%' || :keyword || '%'
                OR u.address LIKE '%' || :keyword || '%'
                """;
        return entityManager.createQuery(hqlSearch, User.class).setParameter("keyword", keyword).getResultList();
    }
}