package wvsdev.twitterapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wvsdev.twitterapp.controller.dto.CreateTweetDto;
import wvsdev.twitterapp.entities.Tweet;
import wvsdev.twitterapp.entities.User;
import wvsdev.twitterapp.repository.TweetRepository;
import wvsdev.twitterapp.repository.UserRepository;

import java.util.UUID;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;

    private final UserRepository userRepository;




    public TweetController(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto,
                                            JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());

        tweetRepository.save(tweet);

        return ResponseEntity.ok().build();
    }
}
