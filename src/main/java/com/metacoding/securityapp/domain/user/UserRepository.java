package com.metacoding.securityapp.domain.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public void save(String roles, String username, String password, String email) {
        em.createNativeQuery("insert into user_tb (roles, username, password, email) values (?, ?, ?, ?)")
                .setParameter(1, roles)
                .setParameter(2, username)
                .setParameter(3, password)
                .setParameter(4, email)
                .executeUpdate();
    }

    public User findByUsername(String username) {
        try {
            Query query = em.createNativeQuery("select * from user_tb where username = ?", User.class);
            query.setParameter(1, username);
            return (User) query.getSingleResult();
        } catch (Exception e) { // 못찾으면 예외가 발생
            return null;
        }
    }
}
