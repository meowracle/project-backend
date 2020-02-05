package com.horizonevent.project.repository.post;

import com.horizonevent.project.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long > {
    Page<Post> findAllByTitleContaining(String tittle, Pageable pageable);

}
