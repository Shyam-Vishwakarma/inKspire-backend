package com.inKspire.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookmarkRequest {
    private Long userId;
    private Long blogId;
}
