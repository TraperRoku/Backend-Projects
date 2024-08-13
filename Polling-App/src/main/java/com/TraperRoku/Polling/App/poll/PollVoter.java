package com.TraperRoku.Polling.App.poll;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PollVoter {
   /* private String sender;*/
    private VoteChoice choice;
}
