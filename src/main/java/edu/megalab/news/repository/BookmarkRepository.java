package edu.megalab.news.repository;

import edu.megalab.news.entity.Bookmark;
import edu.megalab.news.entity.Post;
import edu.megalab.news.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Page<Bookmark> findByUser(User user, Pageable pageable);
    void deleteByPostAndUser(Post post, User user);
    boolean existsByPostAndUser(Post post, User user);

    Optional<Bookmark> findByPostAndUser(Post post, User user);
}
