package com.prashantb.ecommercetest.categories_list;

import com.prashant.apilib.models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CategoriesListContract {
    interface View {
    }

    interface Presenter {
        ArrayList<Category> getSubCategories(Map<Integer, Category> categoryMap, List<Integer> childCategories);
    }
}
