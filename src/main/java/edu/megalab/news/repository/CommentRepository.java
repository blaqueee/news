package edu.megalab.news.repository;

import edu.megalab.news.entity.Comment;
import edu.megalab.news.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostAndParentIsNull(Post post, Pageable pageable);
    Page<Comment> findByParent(Comment parent, Pageable pageable);
}
