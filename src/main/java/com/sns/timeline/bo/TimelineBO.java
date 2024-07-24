package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.CommentView;
import com.sns.like.bo.LikeBO;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.domain.CardView;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class TimelineBO {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;

	// input:userId(로그인 된 사람 번호)    output: List<CardView>
	public List<CardView> generateCardViewList(Integer userId) { // 비로그인도 타임라인은 보여지므로 null 가능
		List<CardView> cardViewList = new ArrayList<>();
		
		// 글목록을 가져온다. List<PostEntity>
		List<PostEntity> postList = postBO.getPostEntityList();
		
		// 글목록 반복문 순회
		// PostEntity => CardView     -> cardViewList에 넣기
		for (PostEntity post : postList) {
			CardView card = new CardView();
			
			// 글
			card.setPost(post);
			
			// 글쓴이
			UserEntity user = userBO.getUserEntityById(post.getUserId());
			card.setUser(user);
			
			// 댓글 N개
			List<CommentView> commentViewList = commentBO.generateCommentViewListByPostId(post.getId());
			// 댓글을 카드에 넣는다.
			card.setCommentList(commentViewList);
			
			// 좋아요 개수
			int likeCount = likeBO.getLikeCountByPostId(post.getId());
			card.setLikeCount(likeCount);
			
			// 좋아요 여부 채우기
			card.setFilledLike(likeBO.filledLikeByPostIdUserId(post.getId(), userId));
			
			//!!!!!!!!! 반드시 리스트에 넣는다.
			cardViewList.add(card);
		}
		
		return cardViewList;
	}
}