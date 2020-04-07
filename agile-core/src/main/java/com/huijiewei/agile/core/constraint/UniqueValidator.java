package com.huijiewei.agile.core.constraint;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private String primaryKey;
    private String[] fields;
    private String message;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(Unique annotation) {
        this.primaryKey = annotation.primaryKey();
        this.fields = annotation.fields();
        this.message = annotation.message();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Class<?> clazz = value.getClass();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();

        Root<?> root = criteriaQuery.from(clazz);

        List<Predicate> predicates = new ArrayList<>(fields.length + 1);

        for (String field : fields) {
            Field fieldField = ReflectionUtils.findField(clazz, field);

            assert fieldField != null;
            fieldField.setAccessible(true);

            String fieldValue = (String) fieldField.get(value);

            predicates.add(criteriaBuilder.equal(root.get(field), fieldValue));
        }

        Field idField = ReflectionUtils.findField(clazz, primaryKey);

        assert idField != null;
        idField.setAccessible(true);

        Integer idValue = (Integer) idField.get(value);

        if (idValue != null && idValue > 0) {
            predicates.add(criteriaBuilder.notEqual(root.get(primaryKey), idValue));
        }

        criteriaQuery
                .select(criteriaBuilder.count(root.get(primaryKey)))
                .where(predicates.toArray(new Predicate[0]));

        TypedQuery<Object> typedQuery = entityManager.createQuery(criteriaQuery);

        Long resultCount = (Long) typedQuery.getSingleResult();

        boolean isValid = resultCount == 0;

        if (!isValid) {
            context.disableDefaultConstraintViolation();

            for (String field : fields) {
                context
                        .buildConstraintViolationWithTemplate(String.format(message, String.join(",", fields)))
                        .addPropertyNode(field)
                        .addConstraintViolation();
            }
        }

        return isValid;
    }
}
