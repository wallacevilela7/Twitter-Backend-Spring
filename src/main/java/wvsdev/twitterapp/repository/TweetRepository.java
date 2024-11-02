package wvsdev.twitterapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import wvsdev.twitterapp.entities.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
