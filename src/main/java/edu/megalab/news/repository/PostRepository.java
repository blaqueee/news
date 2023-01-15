package edu.megalab.news.repository;

import edu.megalab.news.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByOrderByCreatedTimeDesc(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.category.id IN :list")
    Page<Post> filterByCategories(@Param("list") List<Long> list, Pageable pageable);
}
