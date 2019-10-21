package com.ymos.biz;

import com.ymos.entity.Product;
import com.ymos.entity.Review;
import com.ymos.entity.ReviewReport;

import java.util.List;

public interface ReviewService extends BaseService<Review> {

    List<ReviewReport> queryExcelData(ReviewReport reviewReport);

    int create(Product product);

    int queryMaxId();

    int update(Product product);

    String getFindProListName(int id);

}
