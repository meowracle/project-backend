package com.horizonevent.project.repository.post;

import com.horizonevent.project.models.Post;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class PostRepositoryImpl implements PostRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Post> findAll() {
        TypedQuery<Post> query = em.createQuery("select p from Post p", Post.class);
        return query.getResultList();
    }

    @Override
    public Post findById(Long id) {
        TypedQuery<Post> query = em.createQuery("select p from Post p where p.id = :id", Post.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Post model) {
        if (model.getId() != null) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public void remove(Long id) {
        Post post = findById(id);
        if (post != null) {
            em.remove(post);
        }
    }
}
