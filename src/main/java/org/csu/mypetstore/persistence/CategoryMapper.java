package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Category;

public interface CategoryMapper {

    Category getCategory(String categoryId);
}
