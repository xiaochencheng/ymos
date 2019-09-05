package com.ymos.mapper;

import com.ymos.entity.Product;
import com.ymos.entity.Review;
import com.ymos.entity.ReviewReport;

import java.util.List;

public interface ReviewMapper extends BaseMapper<Review>{

    List<ReviewReport> queryExcelData(ReviewReport reviewReport);

}
