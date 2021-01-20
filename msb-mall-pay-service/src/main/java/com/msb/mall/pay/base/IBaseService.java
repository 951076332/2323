package com.msb.mall.pay.base;

import com.msb.mall.pay.entity.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T extends BaseEntity, ID> {

    /**
     * 获取对应 entity 的 BaseMapper
     * @return BaseMapper
     */
    MongoRepository<T, ID> getRepository();

    /**
     * 获取对应 MongoTemplate
     * @return MongoTemplate
     */
    MongoTemplate getMongoTemplate();

    /**
     * 根据逻辑主键查找
     * @param tObject
     * @return
     */
    Optional<T> findOneByEntityCondition(T tObject);

    /**
     * 根据单一逻辑主键查找
     * @param indexNoValue
     * @return
     * @throws Exception
     */
    List<T> findAllByEntityCondition(String fieldKey, String indexNoValue) throws Exception;

    /**
     * 根据逻辑主键查找集合
     * @param tObject
     * @param pageable
     * @return
     */
    Page<T> findAllByEntityCondition(T tObject, Pageable pageable);


    Page<T> findPageBySort(Criteria criteria, Sort sort, Pageable pageable);

    /**
     * 根据逻辑主键查找集合
     * @param tObject
     * @return
     */
    List<T> findAllByEntityCondition(T tObject);

    /**
     * 更新对象不为null的字段
     * @param t
     * @return
     */
    T updateWithoutNoneByEntityLogicAnnotation(T t);

    /**
     * @param t         要更新的对象
     * @param fieldName 更新条件-字段名
     * @param idList    更新条件-字段值
     */
    void updateBatch(T t, String fieldName, List<String> idList);


    void saveAllWithoutNone(List<T> entities);

    /**根据逻辑主键，逻辑删除
     * @param tObject
     */
    void deleteByEntityLogicKeyAnnotation(T tObject);

    /**
     * 根据逻辑主键，逻辑删除
     * @param keyNoFieldName
     * @param indexNoValue
     */
    void deleteByIndexNo(String keyNoFieldName, String indexNoValue);

    void deleteByEntityLogicKeyAnnotation(String keyNoFieldName, String indexNoValue);

    /**
     * 根据逻辑ID，逻辑删除
     * @param index
     */
    void deleteByEntityLogicId(String index);

    T findByEntityLogicId(String index);


    default Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    default Page<T> findAll(Pageable pageable){
        return getRepository().findAll(pageable);
    }

    default <S extends T> Page<S> findAll(Example<S> example, Pageable pageable){
        return getRepository().findAll(example, pageable);
    }

    default <S extends T> Optional<S> findOne(Example<S> example){
        return getRepository().findOne(example);
    }

    List<T> findAll(Example<T> example);

    List<T> findAll(Example<T> example, Sort sort);

    /**
     * 排除逻辑删除
     * @return
     * @throws Exception
     */
    List<T> findAll() throws Exception;

    default Iterable<T> findAll(Sort sort){
        return getRepository().findAll(sort);
    }

    default Iterable<T> findAllById(Iterable<ID> ids){
        return getRepository().findAllById(ids);
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    default void deleteById(ID id){
        getRepository().deleteById(id);
    }

    /**
     * Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    default void delete(T entity){
        getRepository().delete(entity);
    }

    /**
     * 批量删除(逻辑删除)
     *
     * @param fieldName 根据该字段删除
     * @param idList    根据该字段删除
     */
    void deleteBatch(String fieldName, List<String> idList);

    /**
     * Deletes the given entities.
     *
     * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal entities} or one of its entities is {@literal null}.
     */
    default void deleteAll(Iterable<? extends T> entities){
        getRepository().deleteAll(entities);
    }

    <S extends T> List<S> insert(Iterable<S> entities);

    <S extends T> S insert(S entity);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    <S extends T> S save(S entity);


}
