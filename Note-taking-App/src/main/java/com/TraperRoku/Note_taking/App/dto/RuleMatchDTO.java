package com.TraperRoku.Note_taking.App.dto;

import lombok.*;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleMatchDTO {
    private String message;
    private int offset;
    private int length;
}
