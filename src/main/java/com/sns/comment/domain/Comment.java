package com.sns.comment.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comment {
	private int id;
	private int userId;
	private int postId;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
