package com.sns.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.mapper.LikeMapper;

@Service
public class LikeBO {

	@Autowired
	private LikeMapper likeMapper;
	
	// input: postId, userId
	// output:
	public void likeToggle(int postId, int userId) {
		// 조회
		int count = likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
		
		// 여부 => 삭제 or 추가
		if (count > 0) {
			// 삭제
			likeMapper.deleteLikeByPostIdUserId(postId, userId);
		} else {
			// 추가
			likeMapper.insertLike(postId, userId);
		}
	}
	
	// input: postId , userId
	// output: int
	public int getLikeCountByPostIdUserId(int postId, int userId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
	}
	// input: postId
	// output: int(좋아요 개수)
	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, null);
	}
	
	// 좋아요 채울지 여부
	// input: postId(필수), userId(로그인/ 비로그인) 
	// output: boolean (채울지 여부)
	public boolean filledLikeByPostIdUserId(int postId, Integer userId) {
		// 비로그인 이면 DB 조회 없이 하트 채우지 않음
		if (userId == null) {
			return false;
		}
		
		// 로그인이면 1. 행이 있으면(1) true 	2. 없으면(0) false
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) == 1 ? true : false;
	}
	
	public void deleteLikeByPostId(int postId) {
		likeMapper.deleteLikeByPostId(postId);
	}
}
