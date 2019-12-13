package com.huijiewei.agile.core.constraint;

import com.huijiewei.agile.core.entity.BaseEntity;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Arrays;

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

    @SneakyThrows
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Field targetPropertyField = ReflectionUtils.findField(this.targetEntity, this.targetProperty);

        assert targetPropertyField != null;
        targetPropertyField.setAccessible(true);

        String value;

        if (StringUtils.isEmpty(this.sourceProperty)) {
            value = object.toString();
        } else {
            Class<?> objectClass = object.getClass();

            Field sourcePropertyField = ReflectionUtils.findField(objectClass, this.sourceProperty);

            assert sourcePropertyField != null;
            sourcePropertyField.setAccessible(true);

            value = sourcePropertyField.get(object).toString();
        }

        if (this.allowValues.length > 0 && Arrays.asList(this.allowValues).contains(value)) {
            return true;
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();

        Root<?> root = criteriaQuery.from(this.targetEntity);

        criteriaQuery
                .select(criteriaBuilder.count(root.get(this.targetProperty)))
                .where(criteriaBuilder.equal(root.get(this.targetProperty), value));

        TypedQuery<Object> typedQuery = entityManager.createQuery(criteriaQuery);

        Long resultCount = (Long) typedQuery.getSingleResult();

        return resultCount > 0;
    }
}
