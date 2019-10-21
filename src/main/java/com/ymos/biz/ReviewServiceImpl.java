package com.ymos.biz;

import com.ymos.entity.Product;
import com.ymos.entity.Review;
import com.ymos.entity.ReviewReport;
import com.ymos.mapper.ProductMapper;
import com.ymos.mapper.ReviewMapper;
import com.ymos.biz.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("reviewService")
public class ReviewServiceImpl extends BaseServiceImpl<Review> implements ReviewService {


   ReviewMapper reviewMapper;

   @Resource
    public void setReviewMapper(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
        this.mapper=reviewMapper;
    }

    @Override
    public List<ReviewReport> queryExcelData(ReviewReport reviewReport) {
        return reviewMapper.queryExcelData(reviewReport);

    }

    @Override
    public int create(Product product) {
        return reviewMapper.create(product);
    }

    @Override
    public int queryMaxId() {
        return reviewMapper.queryMaxId();
    }

    @Override
    public int update(Product product) {
        return reviewMapper.update(product);
    }

    @Override
    public String getFindProListName(int id) {
        return reviewMapper.getFindProListName(id);
    }
}
