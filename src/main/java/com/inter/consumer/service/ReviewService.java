package com.inter.consumer.service;

import java.util.Map;

public interface ReviewService {

	String registerReview(Map<String, String> param);

	String getReviewList(Map<String, String> param);

}
