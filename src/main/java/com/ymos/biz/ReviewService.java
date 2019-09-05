package com.ymos.biz;

import com.ymos.entity.Review;
import com.ymos.entity.ReviewReport;

import java.util.List;

public interface ReviewService extends BaseService<Review> {

    List<ReviewReport> queryExcelData(ReviewReport reviewReport);

}
