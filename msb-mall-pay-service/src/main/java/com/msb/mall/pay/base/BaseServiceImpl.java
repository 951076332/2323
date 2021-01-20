package com.msb.mall.pay.base;

import com.msb.cube.common.base.annotation.EntityLogicId;
import com.msb.cube.common.base.annotation.EntityLogicKey;
import com.msb.cube.common.util.ReflectionsUtil;

import com.msb.mall.pay.dto.KsUserDTO;
import com.msb.mall.pay.entity.BaseEntity;
import com.msb.mall.pay.idgenerator.IdGeneratorService;
import com.msb.uc.auth.UserContextHolder;
import com.msb.uc.model.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.data.util.Streamable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@SuppressWarnings("unchecked")
public class BaseServiceImpl<M extends MongoRepository<T, ID>, T extends BaseEntity, ID> implements IBaseService<T, ID> {

    @Autowired
    protected M baseMapper;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected IdGeneratorService idGeneratorService;

    protected final Class<T> entityClass = getEntityClass();


    @Override
    public M getRepository() {
        return baseMapper;
    }

    @Override
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }


    @Override
    public Page<T> findAllByEntityCondition(T tObject, Pageable pageable) {
        if (tObject == null) {
            return null;
        }
        Example<T> example = getLogicKeyExample(tObject);
        return findAll(example, pageable);
    }


    @Override
    public Page<T> findPageBySort(Criteria criteria, Sort sort, Pageable pageable) {
        Criteria baseCriteria = commonBaseCriteria();
        criteria = baseCriteria.andOperator(criteria);
        Query query = new Query(criteria);
        long count = getMongoTemplate().count(query, getEntityClass());

        query.with(pageable);
        query.with(sort);
        List<T> list = getMongoTemplate().find(query, getEntityClass());
        return PageableExecutionUtils.getPage(list, pageable, () -> count);
    }

    @Override
    public List<T> findAllByEntityCondition(T tObject) {
        if (tObject == null) {
            return null;
        }
        Example<T> example = getLogicKeyExample(tObject);
        return findAll(example);
    }


    @Override
    public List<T> findAllByEntityCondition(String fieldKey, String fieldValue) throws Exception {
        Example<T> example = getLogicKeyExample(fieldKey, fieldValue);
        return findAll(example);
    }

    @Override
    public Optional<T> findOneByEntityCondition(T tObject) {
        return this.findOne(getLogicKeyExample(tObject));
    }

    @Override
    public T findByEntityLogicId(String index) {
        Class<T> entityClass = getEntityClass();
        Query query = getQueryByEntityLogicId(index, entityClass);
        return getMongoTemplate().findOne(query, entityClass);
    }

    @Override
    public T updateWithoutNoneByEntityLogicAnnotation(T tObject) {
        setBaseFieldValue(tObject);
        Update update = new Update();
        Criteria criteria = commonBaseCriteria();
        AtomicBoolean setQueryField = new AtomicBoolean(false);
        ReflectionUtils.doWithFields(tObject.getClass(), field -> {
            // 设置字段可反射
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            if (field.get(tObject) != null && isEntityLogicIdOrKeyAnnotation(field)) {
                criteria.and(field.getName()).is(getIndexFieldValue(tObject, field));
                setQueryField.set(true);
            }

            // 排除指定条件
            if ("id".equals(field.getName()) // 排除 id 字段，因为作为查询主键
                    || field.getAnnotation(Transient.class) != null // 排除 @Transient 注解的字段，因为非存储字段
                    || field.getAnnotation(EntityLogicKey.class) != null // 排除 索引 字段
                    || field.getAnnotation(EntityLogicId.class) != null // 排除 逻辑主键 字段
                    || Modifier.isStatic(field.getModifiers())) { // 排除静态字段
                return;
            }
            // 排除字段为空的情况
            if (field.get(tObject) == null) {
                return;
            }
            // 设置更新条件
            update.set(field.getName(), field.get(tObject));

        });

        // 防御，避免有业务传递空的 Update 对象
        if (update.getUpdateObject().isEmpty()) {
            return tObject;
        }
        if (!setQueryField.get()) {
            return tObject;
        }
        Query query = new Query(criteria);
        getMongoTemplate().updateMulti(query, update, tObject.getClass());
        return tObject;
    }

    @Override
    public void updateBatch(T tObject, String fieldName, List<String> idList) {
        setBaseFieldValue(tObject);

        Update update = new Update();
        ReflectionUtils.doWithFields(tObject.getClass(), field -> {
            // 排除指定条件
            if ("id".equals(field.getName()) // 排除 id 字段，因为作为查询主键
                    || field.getAnnotation(Transient.class) != null // 排除 @Transient 注解的字段，因为非存储字段
                    || field.getAnnotation(EntityLogicKey.class) != null // 排除 索引 字段
                    || field.getAnnotation(EntityLogicId.class) != null // 排除 逻辑主键 字段
                    || Modifier.isStatic(field.getModifiers())) { // 排除静态字段
                return;
            }
            // 排除字段为空的情况
            if (field.get(tObject) == null) {
                return;
            }
            // 设置字段可反射
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            // 设置更新条件
            update.set(field.getName(), field.get(tObject));
        });

        // 防御，避免有业务传递空的 Update 对象
        if (update.getUpdateObject().isEmpty()) {
            return;
        }

        Criteria criteria = commonBaseCriteria();
        criteria.and(fieldName).in(idList);
        Query query = new Query(criteria);
        getMongoTemplate().updateMulti(query, update, tObject.getClass());
    }

    @Override
    public void deleteBatch(String fieldName, List<String> idList) {
        Query query = new Query(Criteria.where(fieldName).in(idList));
        Update update = Update.update(getDeleteFieldName(), true);
        Class<T> entityClass = getEntityClass();
        getMongoTemplate().updateMulti(query, update, entityClass);
    }

    @Override
    public void saveAllWithoutNone(List<T> entities) {
        Streamable<T> source = Streamable.of(entities);
        source.stream().map(this::updateWithoutNoneByEntityLogicAnnotation);
    }

    @Override
    public void deleteByIndexNo(String fieldName, String fieldValue) {
        Query query = new Query(Criteria.where(fieldName).is(fieldValue));
        Update update = Update.update(getDeleteFieldName(), true);
        Class<T> entityClass = getEntityClass();
        getMongoTemplate().updateMulti(query, update, entityClass);
    }

    @Override
    public void deleteByEntityLogicKeyAnnotation(T tObject) {
        Query query = getLogicKeyQuery(tObject);
        Update update = Update.update(getDeleteFieldName(), true);
        getMongoTemplate().updateMulti(query, update, tObject.getClass());
    }

    @Override
    public void deleteByEntityLogicKeyAnnotation(String fieldName, String indexNoValue) {
        Query query = new Query();
        Class<T> entityClass = getEntityClass();
        ReflectionUtils.doWithFields(entityClass, field -> {
            if (field.isAnnotationPresent(EntityLogicKey.class) && field.getName().equalsIgnoreCase(fieldName)) {
                query.addCriteria(Criteria.where(fieldName).is(indexNoValue));
            }
        });
        Update update = Update.update(getDeleteFieldName(), true);
        getMongoTemplate().updateMulti(query, update, entityClass);
    }

    @Override
    public void deleteByEntityLogicId(String index) {
        Query query = new Query();
        Class<T> entityClass = getEntityClass();
        ReflectionUtils.doWithFields(entityClass, field -> {
            if (field.isAnnotationPresent(EntityLogicId.class)) {
                query.addCriteria(Criteria.where(field.getName()).is(index));
            }
        });
        Update update = Update.update(getDeleteFieldName(), true);
        getMongoTemplate().updateFirst(query, update, entityClass);
    }


    @Override
    public <S extends T> List<S> insert(Iterable<S> entities) {
        entities.forEach(e -> setBaseFieldValue(e, true));
        return getRepository().insert(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <S extends T> S insert(S entity) {
        setBaseFieldValue(entity, true);
        return getRepository().insert(entity);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::setBaseFieldValue);
        return getRepository().saveAll(entities);
    }

    @Override
    public <S extends T> S save(S entity) {
        setBaseFieldValue(entity);
        return getRepository().save(entity);
    }

    @Override
    public List<T> findAll(Example<T> example) {
        return getRepository().findAll(example);
    }

    @Override
    public List<T> findAll(Example<T> example, Sort sort) {
        return getRepository().findAll(example, sort);
    }

    @Override
    public List<T> findAll() throws Exception {
        ExampleMatcher baseMatcher = getBaseMatcher();
        T entity = getBaseEntityWithInitFieldValue();
        Example<T> example = Example.of(entity, baseMatcher);
        return getRepository().findAll(example);
    }

    protected List<T> findAll(List<String> ids) {
        Criteria criteria = commonBaseCriteria();
        criteria.and(ReflectionsUtil.convert(T::getBzId)).in(ids);
        Query query = new Query(criteria);
        return getMongoTemplate().find(query, getEntityClass());
    }

    protected Query getQueryByEntityLogicId(String index, Class<T> tClass) {
        Criteria criteria = commonBaseCriteria();
        ReflectionUtils.doWithFields(tClass, field -> {
            if (field.isAnnotationPresent(EntityLogicId.class)) {
                criteria.and(field.getName()).is(index);
            }
        });
        return new Query(criteria);
    }


    private Class<T> getEntityClass() {
        int entityIndex = 0;
        Type[] genericInterfaces = getClass().getGenericInterfaces();
        Type genericInterface = genericInterfaces[genericInterfaces.length - 1];
        Type[] actualTypeArguments = ((ParameterizedTypeImpl) ((Class) genericInterface).getGenericInterfaces()[entityIndex]).getActualTypeArguments();
        return (Class<T>) actualTypeArguments[0];
    }

    private boolean isEntityLogicIdOrKeyAnnotation(Field field) {
        return field.isAnnotationPresent(EntityLogicKey.class) || field.isAnnotationPresent(EntityLogicId.class);
    }


    protected Query getLogicKeyQuery(T tObject) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        ReflectionUtils.doWithFields(tObject.getClass(), field -> {
            if (isEntityLogicIdOrKeyAnnotation(field)) {
                if (field.get(tObject) != null) {
                    criteria.and(field.getName()).is(field.get(tObject));
                }
            }
        });
        query.addCriteria(criteria);
        return query;
    }

    /**
     * isDelete 和 enabled字段条件添加
     *
     * @return
     */
    private ExampleMatcher getBaseMatcher() {
        return ExampleMatcher.matching();
    }

    private T getBaseEntityWithInitFieldValue() throws Exception {
        Class<T> entityClass = getEntityClass();
        T entity = entityClass.newInstance();
        return setInitFieldValue(entity);
    }

    private T getBaseEntityWithInitFieldValue(Class<T> entityClass) throws Exception {
        T entity = entityClass.newInstance();
        return setInitFieldValue(entity);
    }

    private T setInitFieldValue(T entity) {
        entity.setEnabled(true);
        entity.setIsDelete(false);
        return entity;
    }

    protected Example<T> getLogicKeyExample(String fieldKey, String logicKeyValue) throws Exception {
        ExampleMatcher matcher = getBaseMatcher();
        Class<T> entityClass = getEntityClass();
        T t = getBaseEntityWithInitFieldValue(entityClass);
        ReflectionUtils.doWithFields(entityClass, field -> {
            if (isEntityLogicIdOrKeyAnnotation(field) && field.getName().equalsIgnoreCase(fieldKey)) {
                matcher.withMatcher(field.getName(), ExampleMatcher.GenericPropertyMatchers.exact());
                field.set(t, logicKeyValue);
            }
        });
        return Example.of(t, matcher);
    }

    protected Example<T> getLogicKeyExample(T tObject) {
        ExampleMatcher matcher = getBaseMatcher();
        setInitFieldValue(tObject);
        ReflectionUtils.doWithFields(tObject.getClass(), field -> {
            if (isEntityLogicIdOrKeyAnnotation(field)) {
                matcher.withMatcher(field.getName(), ExampleMatcher.GenericPropertyMatchers.exact());
            }
        });
        return Example.of(tObject, matcher);
    }


    private Object getIndexFieldValue(T tObject, Field field) {
        try {
            return field.get(tObject);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private String getDeleteFieldName() {
        return "isDelete";
    }


    protected Criteria commonBaseCriteria() {
        return Criteria.where("enabled").is(true).and("isDelete").is(false);
    }


    protected LoginUserInfo getCurrentUser() {
        return UserContextHolder.getCurrentUser().orElse(null);
    }

    protected String generateUid() {
        return idGeneratorService.generateIdForSnowFlake();
    }

    protected void setBaseFieldValue(T tObject) {
        setBaseFieldValue(tObject, false);
    }

    /**
     * 更新共有的基础字段
     *
     * @param tObject
     * @param isInsert
     */
    protected void setBaseFieldValue(T tObject, Boolean isInsert) {
        LoginUserInfo currentUser = getCurrentUser();
        ReflectionUtils.doWithFields(tObject.getClass(), field -> {
            field.setAccessible(true);
            if (isInsert) {
                EntityLogicId entityLogicId = field.getAnnotation(EntityLogicId.class);
                if (entityLogicId != null && StringUtils.isEmpty(field.get(tObject))) {
                    field.set(tObject, generateUid());
                }
            }
        });

        long nowTime = System.currentTimeMillis();
        tObject.setGmtModified(nowTime);
        if (currentUser != null) {
            tObject.setModifiedUid(currentUser.getUserNo());
            tObject.setModifiedUname(currentUser.getNickname());
        }
        if (isInsert) {
            tObject.setGmtCreate(nowTime);
            if (currentUser != null) {
                tObject.setCreateUid(currentUser.getUserNo());
                tObject.setCreateUname(currentUser.getNickname());
            }
            tObject.setIsDelete(false);
            tObject.setEnabled(true);
        }
    }

}