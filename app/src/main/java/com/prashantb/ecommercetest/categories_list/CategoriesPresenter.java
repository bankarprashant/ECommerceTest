package com.prashantb.ecommercetest.categories_list;

import com.prashant.apilib.models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class CategoriesPresenter implements CategoriesListContract.Presenter {
    private static final String TAG = "CategoriesPresenter";
    private CategoriesListContract.View view;

    CategoriesPresenter(CategoriesListContract.View view) {
        this.view = view;
    }

    @Override
    public ArrayList<Category> getSubCategories(Map<Integer, Category> categoryMap, List<Integer> childCategories) {
        ArrayList<Category> categoryList = new ArrayList<>();

        if (categoryMap != null) {

            for (int i = 0; i < childCategories.size(); i++) {
                int id = childCategories.get(i);

                if (categoryMap.containsKey(id)) {
                    categoryList.add(categoryMap.get(id));
                }
            }
        }
        return categoryList;
    }
}
