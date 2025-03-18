package com.cherry.erp.common.repository.impl;

import com.cherry.erp.common.model.SearchParam;
import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.common.repository.GenericSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GenericSearchRepositoryImpl2<T extends GenericEntity, U, V> implements GenericSearchRepository<T> {

    private Class<T> clazz;

    private Class<U> parentClazz;

    private Class<V> parentClazz2;

    @PersistenceContext
    private EntityManager em;

    public GenericSearchRepositoryImpl2(Class<T> clazz, Class<U> parentClazz, Class<V> parentClazz2) {
        this.clazz = clazz;
        this.parentClazz = parentClazz;
        this.parentClazz2 = parentClazz2;
    }

    // (!) do not delete this constructor
    public GenericSearchRepositoryImpl2() {
    }

    @Override
    public List<T> findByParams(List<SearchParam> searchParams) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<T> criteriaQuery = cb.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();

        searchParams.stream().filter(searchParam -> Objects.nonNull(searchParam.getValue())).forEach(searchParam -> {
            log.info(">>>> Search Param: {}", searchParam.toString());
            try {
                Field field = clazz.getDeclaredField(searchParam.getField());
                addPredicateBySearchParam(cb, root, predicates, searchParam, field);
            } catch (NoSuchFieldException | ParseException e) {
                Field field;
                try {
                    field = parentClazz.getDeclaredField(searchParam.getField());
                    addPredicateBySearchParam(cb, root, predicates, searchParam, field);
                } catch (NoSuchFieldException | ParseException ex) {
                    try {
                        field = parentClazz2.getDeclaredField(searchParam.getField());
                        addPredicateBySearchParam(cb, root, predicates, searchParam, field);
                    } catch (NoSuchFieldException | ParseException ex2) {
                        log.error("Search could not be accomplished with given parameters: ", e);
                    }
                }
            }
        });

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

        org.hibernate.query.Query query = em.createQuery(criteriaQuery).unwrap(org.hibernate.query.Query.class);

        log.info(">>>>> Query: {}", query.getQueryString());

        return query.getResultList();
    }

    @Override
    @Transactional
    public Page<T> findByParams(List<SearchParam> searchParams, Pageable pageable) {
        List<T> result = findByParams(searchParams);

        long total = result.size();
        int start = Math.min(pageable.getPageNumber() * pageable.getPageSize(), result.size());
        int end = Math.min(start + pageable.getPageSize(), result.size());

        return new PageImpl<>(result.subList(start, end), pageable, total);
    }

    @Override
    public List<T> findByParam(String param) {
        List<T> result = new ArrayList<>();
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        fields.addAll(Arrays.asList(parentClazz.getDeclaredFields()));
        fields.addAll(Arrays.asList(parentClazz2.getDeclaredFields()));
        for (Field field : fields) {
            if (field.getType().getCanonicalName().equals("java.lang.String")) {
                log.info("Search by {}", field.getName());
                SearchParam searchParam = SearchParam.builder()
                        .name(field.getName())
                        .field(field.getName())
                        .type(SearchParam.SearchParamTypeEnum.STRING.getType())
                        .operator(SearchParam.ConditionOperator.LIKE)
                        .value(param).build();
                List<T> resultForField = findByParams(Collections.singletonList(searchParam));
                result.addAll(resultForField);
            }
        }
        result = result.stream().distinct().collect(Collectors.toList());
        return result;
    }

    @Override
    public Page<T> findByParam(String param, Pageable pageable) {
        List<T> result = findByParam(param);

        long total = result.size();
        int start = Math.min(pageable.getPageNumber() * pageable.getPageSize(), result.size());
        int end = Math.min(start + pageable.getPageSize(), result.size());

        return new PageImpl<>(result.subList(start, end), pageable, total);
    }

    private void addPredicateBySearchParam(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates, SearchParam searchParam, Field field) throws ParseException {
        log.info("field type : {} ", field.getGenericType());
        Class fieldClass = field.getType();
        if (fieldClass.isEnum()) {
            if (EnumUtils.isValidEnum(fieldClass, searchParam.getValue().toString())) {
                searchParam.setValue(Enum.valueOf(fieldClass, searchParam.getValue().toString()));
                addPredicateByField(cb, root, predicates, searchParam);
            }
        } else {
            addPredicateByField(cb, root, predicates, searchParam);
        }
    }

    private void addPredicateByField(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates, SearchParam searchParam) throws ParseException {
        Predicate predicate;
        String unexpectedValue = "Unexpected value: ";
        if (SearchParam.SearchParamTypeEnum.STRING.getType().equals(searchParam.getType())) {
            switch (searchParam.getOperator()) {
                case E:
                    predicate = cb.equal(root.get(searchParam.getField()), searchParam.getValue());
                    break;
                case LIKE:
                    predicate = cb.like(root.get(searchParam.getField()), "%" + searchParam.getValue().toString() + "%");
                    break;
                case NE:
                    predicate = cb.notEqual(root.get(searchParam.getField()), searchParam.getValue());
                    break;
                case NOTLIKE:
                    predicate = cb.notLike(root.get(searchParam.getField()), "%" + searchParam.getValue().toString() + "%");
                    break;
                default:
                    throw new IllegalStateException(unexpectedValue + searchParam.getOperator());
            }
            predicates.add(predicate);
        } else if (SearchParam.SearchParamTypeEnum.NUMBER.getType().equals(searchParam.getType())) {
            switch (searchParam.getOperator()) {
                case E:
                    predicate = cb.equal(root.get(searchParam.getField()), searchParam.getValue());
                    break;
                case LT:
                    predicate = cb.lessThan(root.get(searchParam.getField()), searchParam.getValue().toString());
                    break;
                case LTE:
                    predicate = cb.lessThanOrEqualTo(root.get(searchParam.getField()), searchParam.getValue().toString());
                    break;
                case GT:
                    predicate = cb.greaterThan(root.get(searchParam.getField()), searchParam.getValue().toString());
                    break;
                case GTE:
                    predicate = cb.greaterThanOrEqualTo(root.get(searchParam.getField()), searchParam.getValue().toString());
                    break;
                case NE:
                    predicate = cb.notEqual(root.get(searchParam.getField()), searchParam.getValue());
                    break;
                default:
                    throw new IllegalStateException(unexpectedValue + searchParam.getOperator());
            }
            predicates.add(predicate);
        } else if (SearchParam.SearchParamTypeEnum.BOOLEAN.getType().equals(searchParam.getType())) {
            switch (searchParam.getOperator()) {
                case TRUE:
                    predicate = cb.equal(root.get(searchParam.getField()), searchParam.getValue());
                    break;
                case FALSE:
                    predicate = cb.notEqual(root.get(searchParam.getField()), searchParam.getValue());
                    break;
                default:
                    throw new IllegalStateException(unexpectedValue + searchParam.getOperator());
            }
            predicates.add(predicate);
        } else if (SearchParam.SearchParamTypeEnum.DATE.getType().equals(searchParam.getType())) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date value = formatter.parse(searchParam.getValue().toString());
            switch (searchParam.getOperator()) {
                case EQUAL:
                    predicate = cb.equal(root.get(searchParam.getField()), value);
                    break;
                case BEFORE:
                    predicate = cb.lessThanOrEqualTo(root.get(searchParam.getField()), value);
                    break;
                case AFTER:
                    predicate = cb.greaterThanOrEqualTo(root.get(searchParam.getField()), value);
                    break;
                default:
                    throw new IllegalStateException(unexpectedValue + searchParam.getOperator());
            }
            predicates.add(predicate);
        }
    }
}
