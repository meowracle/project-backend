package com.horizonevent.project.repository.post;

import com.horizonevent.project.models.Post;
import org.springframework.data.domain.Page;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long > {
    List<Post> findAllByTitleContaining(String tittle);
    List<Post> findAllByShareStatusIsTrue();
    List<Post> findAllByUser_Id(Long user_id);
}
