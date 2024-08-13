package com.TraperRoku.Polling.App.poll;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PollController {
    private final Map<VoteChoice, Integer> voteCounts = new HashMap<>();

    public PollController() {
        for (VoteChoice choice : VoteChoice.values()) {
            voteCounts.put(choice, 0);
        }
    }

    @MessageMapping("/poll.vote")
    @SendTo("/poll/public")
    public Map<VoteChoice, Integer> vote(@Payload PollVoter pollVoter) {
        VoteChoice choice = pollVoter.getChoice();
        voteCounts.put(choice, voteCounts.getOrDefault(choice, 0) + 1);

        return voteCounts;
    }
}
