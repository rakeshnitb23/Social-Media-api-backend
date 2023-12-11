package com.socialmedia.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {

    private long userCount;
    private long postCount;
    private long likeCount;
    private long commentCount;
}
