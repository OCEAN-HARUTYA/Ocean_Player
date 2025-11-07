package com.cy.kcat.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.kcat.content.domain.DramaCategories;
import com.cy.kcat.content.service.DramaCategoriesService;
import com.cy.kcat.content.mapper.DramaCategoriesMapper;
import org.springframework.stereotype.Service;

/**
* @author 19788
* @description 针对表【drama_categories(短剧分类关联表)】的数据库操作Service实现
* @createDate 2025-11-06 13:29:10
*/
@Service
public class DramaCategoriesServiceImpl extends ServiceImpl<DramaCategoriesMapper, DramaCategories>
    implements DramaCategoriesService{

}




