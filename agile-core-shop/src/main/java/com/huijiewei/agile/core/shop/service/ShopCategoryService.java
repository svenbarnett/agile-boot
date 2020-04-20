package com.huijiewei.agile.core.shop.service;

import com.huijiewei.agile.core.exception.ConflictException;
import com.huijiewei.agile.core.exception.NotFoundException;
import com.huijiewei.agile.core.shop.entity.ShopCategory;
import com.huijiewei.agile.core.shop.manager.ShopCategoryManager;
import com.huijiewei.agile.core.shop.mapper.ShopCategoryMapper;
import com.huijiewei.agile.core.shop.repository.ShopCategoryRepository;
import com.huijiewei.agile.core.shop.repository.ShopProductRepository;
import com.huijiewei.agile.core.shop.request.ShopCategoryRequest;
import com.huijiewei.agile.core.shop.response.ShopCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ShopCategoryService {

    private final ShopCategoryRepository shopCategoryRepository;
    private final ShopCategoryManager shopCategoryManager;
    private final ShopProductRepository shopProductRepository;

    @Autowired
    public ShopCategoryService(ShopCategoryRepository shopCategoryRepository,
                               ShopCategoryManager shopCategoryManager,
                               ShopProductRepository shopProductRepository) {
        this.shopCategoryRepository = shopCategoryRepository;
        this.shopCategoryManager = shopCategoryManager;
        this.shopProductRepository = shopProductRepository;
    }

    private List<ShopCategoryResponse> getParentsById(Integer id) {
        return ShopCategoryMapper.INSTANCE.toShopCategoryResponses(this.shopCategoryManager.getParents(id));
    }

    public List<ShopCategoryResponse> getTree() {
        return ShopCategoryMapper.INSTANCE.toShopCategoryResponses(this.shopCategoryManager.getTree());
    }

    public List<ShopCategoryResponse> getPath(Integer id) {
        return this.getParentsById(id);
    }

    public ShopCategoryResponse view(Integer id, Boolean withParents) {
        ShopCategory shopCategory = this.getById(id);

        ShopCategoryResponse response = ShopCategoryMapper.INSTANCE.toShopCategoryResponse(shopCategory);

        if (withParents) {
            response.setParents(this.getParentsById(shopCategory.getParentId()));
        }

        return response;
    }

    private ShopCategory getById(Integer id) {
        Optional<ShopCategory> shopCategoryOptional = this.shopCategoryRepository.findById(id);

        if (shopCategoryOptional.isEmpty()) {
            throw new NotFoundException("分类不存在");
        }

        return shopCategoryOptional.get();
    }

    public ShopCategoryResponse create(@Valid ShopCategoryRequest request) {
        ShopCategory shopCategory = ShopCategoryMapper.INSTANCE.toShopCategory(request);

        this.shopCategoryManager.save(shopCategory);

        return ShopCategoryMapper.INSTANCE.toShopCategoryResponse(shopCategory);
    }

    public ShopCategoryResponse edit(Integer id, @Valid ShopCategoryRequest request) {
        ShopCategory current = this.getById(id);

        ShopCategory shopCategory = ShopCategoryMapper.INSTANCE.toShopCategory(request, current);

        this.shopCategoryManager.save(shopCategory);

        return ShopCategoryMapper.INSTANCE.toShopCategoryResponse(shopCategory);
    }

    public void delete(Integer id) {
        ShopCategory current = this.getById(id);

        List<Integer> shopCategoryIds = this.shopCategoryManager.getChildrenIds(current.getId(), true);

        if (shopProductRepository.existsByShopCategoryIdIn(shopCategoryIds)) {
            throw new ConflictException("商品分类内存在商品，无法删除");
        }

        this.shopCategoryManager.delete(shopCategoryIds);
    }
}
