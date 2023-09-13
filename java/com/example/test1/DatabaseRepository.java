package com.example.test1;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.dialect.Database;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import java.util.Optional;

@Repository
public class DatabaseRepository {
    private final EntityManager entityManager;
    public DatabaseRepository (EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Transactional
    public void save (Object object) {
        entityManager.persist(object);
    }

    public Optional<Ingredient> findById(String name) {
        return Optional.ofNullable(entityManager.find(Ingredient.class, name));
    }
    @Transactional
    public void deleteById(String name) {
        findById(name).ifPresent(entityManager::remove);
    }


}