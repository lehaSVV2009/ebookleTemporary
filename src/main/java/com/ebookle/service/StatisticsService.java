package com.ebookle.service;

import com.ebookle.entity.Category;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 30.08.13
 * Time: 7:05
 * To change this template use File | Settings | File Templates.
 */
public interface StatisticsService {

    Map<Category, Integer> getBookCategoryStatistics ();
}
