package com.huijiewei.agile.core.constraint;

import com.huijiewei.agile.core.entity.BaseEntity;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ExistValidator implements ConstraintValidator<Exist, Object> {
    @Autowired
    private EntityManager entityManager;

    private Class<? extends BaseEntity> targetEntity;
    private String targetProperty;
    private String sourceProperty;
    private String[] allowValues;

    @Override
    public void initialize(Exist annotation) {
        this.targetEntity = annotation.targetEntity();
        this.targetProperty = annotation.targetProperty();
        this.sourceProperty = annotation.sourceProperty();
        this.allowValues = annotation.allowValues();
    }

    private CriteriaBuilder getCriteriaBuilder() {
        return this.entityManager.getCriteriaBuilder();
    }

    private CriteriaQuery<Object> getCriteriaQuery() {
        return this.getCriteriaBuilder().createQuery();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Field targetPropertyField = ReflectionUtils.findField(this.targetEntity, this.targetProperty);

        assert targetPropertyField != null;
        targetPropertyField.setAccessible(true);

        Object value;

        if (StringUtils.isEmpty(this.sourceProperty)) {
            value = object;
        } else {
            Class<?> objectClass = object.getClass();

            Field sourcePropertyField = ReflectionUtils.findField(objectClass, this.sourceProperty);

            assert sourcePropertyField != null;
            sourcePropertyField.setAccessible(true);

            value = sourcePropertyField.get(object);
        }

        if (value == null) {
            return true;
        }

        CriteriaQuery<Object> criteriaQuery;

        if (value instanceof Collection) {
            List<?> list = new ArrayList<>((Collection<?>) value);

            List<String> search = new ArrayList<>();

            for (Object item : list) {
                String str = item.toString();

                if (this.allowValues.length == 0 || !Arrays.asList(this.allowValues).contains(str)) {
                    search.add(str);
                }
            }

            CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
            criteriaQuery = this.getCriteriaQuery();

            Root<?> root = criteriaQuery.from(this.targetEntity);
            Expression<String> expression = root.get(this.targetProperty);

            criteriaQuery
                    .select(criteriaBuilder.count(root.get(this.targetProperty)))
                    .where(expression.in(search));

        } else {
            if (this.allowValues.length > 0 && Arrays.asList(this.allowValues).contains(value.toString())) {
                return true;
            }

            CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
            criteriaQuery = this.getCriteriaQuery();

            Root<?> root = criteriaQuery.from(this.targetEntity);
            Path<?> path = root.get(this.targetProperty);

            criteriaQuery
                    .select(criteriaBuilder.count(path))
                    .where(criteriaBuilder.equal(path, value));
        }

        TypedQuery<Object> typedQuery = entityManager.createQuery(criteriaQuery);

        Long resultCount = (Long) typedQuery.getSingleResult();

        return resultCount > 0;
    }
}
