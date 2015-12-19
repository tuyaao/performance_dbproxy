package com.free4lab.monitorproxy.daomysql;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


public abstract class AbstractDAO<T>{
	
	//classname
	@SuppressWarnings("rawtypes")
	public abstract Class getEntityClass();
	
	public String getClassName() {
        return getEntityClass().getName();
    }
	
	//logger
	private static Logger logger = LoggerFactory.getLogger(AbstractDAO.class);
    
    //EntityManager
    @PersistenceContext  
    private EntityManager entityManager; 
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected EntityManager getEntityManager(){
		return entityManager;
	}
	
	@Transactional
	public void save(Collection<T> el) {

        EntityManager em = getEntityManager();

        logger.info("saving " + getClassName() + " instance");
        try {
        	Session session = (Session) em.getDelegate();
        	session.setFlushMode(FlushMode.MANUAL);
        	
            for (T entity : el) {
            	session.save(entity);
            }
            session.flush();
            session.clear();
            
            logger.info("save successful");
        } catch (RuntimeException re) {
        	logger.error("save failed");
            throw re;
        }

    }

    /**
     * 保存一个数据库实例
     */
	@Transactional
    public void save(T entity) {
        EntityManager em = getEntityManager();

        logger.info("saving " + getClassName() + " instance");
        try {
            em.persist(entity);
            logger.info("save successful!");
        } catch (RuntimeException re) {
            logger.error("save failed");
            throw re;
        }

    }

    /**
     * 通过主键删除一个数据库实例
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public void deleteByPrimaryKey(Object primaryKey) {
        EntityManager em = getEntityManager();
        logger.info("deleting " + getClassName() + " instance");
        try {
            Object entity = em.getReference(getEntityClass(),
                    primaryKey);
            em.remove(entity);
            logger.info("delete successful");
        } catch (RuntimeException re) {
            logger.error("delete failed");
            throw re;
        }
    }

    /**
     * 更新一个数据库实例
     */
    @Transactional
    public void update(Collection<T> el) {
        EntityManager em = getEntityManager();
        logger.info("updating " + getClassName() + " instance");
        try {
            for (T entity : el) {
                em.merge(entity);
            }
            logger.info("update successful");
        } catch (RuntimeException re) {
            logger.error("update failed");
            throw re;
        }
    }

    /**
     * 更新一个数据库实例
     */
    @Transactional
    public T update(T entity) {
        EntityManager em = getEntityManager();

        logger.info("updating " + getClassName() + " instance");
        try {
            T result = em.merge(entity);
            logger.info("update successful");
            return result;
        } catch (RuntimeException re) {
            logger.error("update failed");
            em.getTransaction().rollback();
            throw re;
        }
    }

    /**
     * 通过主键寻找数据库实例
     * @param pKey 主键
     * @return
     */
    @SuppressWarnings("unchecked")
    public T findByPrimaryKey(Object pKey) {
        logger.info("finding " + getClassName() + " instance with primary key: " + pKey);
        try {
            Object instance = getEntityManager().find(getEntityClass(), pKey);
            return (T) instance;
        } catch (RuntimeException re) {
            logger.error("find failed");
            throw re;
        }
    }

    /**
     * 通过id寻找实体
     * @param id
     * @return
     */
    public T findById(Integer id) {
        return findByPrimaryKey(id);
    }

    /**
     * 通过属性查找
     */
    @SuppressWarnings("unchecked")
    public List<T> findByProperty(String propertyName,
            final Object value) {
        logger.info("finding " + getClassName() + " instance with property: "
                + propertyName + ", value: " + value);
        try {
            final String queryString = "select model from " + getClassName() + " model where model."
                    + propertyName + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            logger.error("find by property name failed");
            throw re;
        }
    }
    
    /**
     * 通过属性查找
     */
    @SuppressWarnings("unchecked")
    public List<T> findByProperty2(String name1,
            final Object value1,String name2,final Object value2) {
        logger.info("finding " + getClassName() + " instance with property1: "
                + name1 + ", value1: " + value1 
                + "; propety2: "+ name2 + ", value2: " + value2);
        try {
            final String queryString = "select model from " + getClassName() + " model where model."
                    + name1 + "= :value1 and model." + name2 + "= :value2";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("value1", value1);
            query.setParameter("value2", value2);
            return query.getResultList();
        } catch (RuntimeException re) {
            logger.error("find by property name2 failed");
            throw re;
        }
    }
    

    /**
     * 通过属性查找
     * @param page 从0开始，page0代表最靠前的数据
     * @param size
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findByProperty(String propertyName,
            final Object value, int page, int size) {
        logger.info("finding " + getClassName() + " instance with property: "
                + propertyName + ", value: " + value);
        try {
            final String queryString = "select model from " + getClassName() + " model where model."
                    + propertyName + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            query.setMaxResults(size).setFirstResult(page * size);
            return query.getResultList();
        } catch (RuntimeException re) {
            logger.error("find by property name failed");
            throw re;
        }
    }

    /**
     * 查找所有
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        logger.info("finding all " + getClassName() + " instances");
        try {
            final String queryString = "select model from " + getClassName() + " model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            logger.error("find all failed");
            throw re;
        }
    }

    /**
     * 查找所有, 包含分页
     * @param page 从0开始，page0代表最靠前的数据
     * @param size
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll(int page, int size) {
        logger.info("finding all " + getClassName() + " instances");
        try {
            final String queryString = "select model from " + getClassName() + " model";
            Query query = getEntityManager().createQuery(queryString);
            query.setMaxResults(size).setFirstResult(page * size);
            return query.getResultList();
        } catch (RuntimeException re) {
            logger.error("find all failed");
            throw re;
        }
    }
    
    /**
     * 计数
     * @author wenlele
     */
    public long countAll(){
    	logger.info("count all " + getClassName() + "instances");
    	try {
			final String qlString = "select count(model)" +
					" from " + getClassName() + " model";
			Query query = getEntityManager().createQuery(qlString);
			Long count = (Long)query.getSingleResult();
			return count.longValue();
		} catch (RuntimeException re) {
			logger.error("count all failed");
			throw re;
		}
    }
    
    /**
     * 
     * @param property
     * @param value
     * @return
     */
    public long countByProperty(String property, Object value){
    	logger.info("finding " + getClassName() + " instance with property: "
                + property + ", value: " + value);
        try {
            final String queryString = "select count(model)" +
            		" from " + getClassName() + " model" +
            		" where model." + property + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            Long count = (Long)query.getSingleResult();
            return count.longValue();
        } catch (RuntimeException re) {
            logger.error("count by property name failed");
            throw re;
        }
    }
    /**
     * 查找匹配两个字段值的计数
     * @param firstName
     * @param firstValue
     * @param secondName
     * @param secondValue
     * @return
     */
    public long countByProperty(String firstName, Object firstValue,
    		String secondName, Object secondValue){
    	logger.info("finding " + getClassName() + " instance with property1: "
                + firstName + ", value1: " + firstValue 
                + "; propety2: "+ secondName + ", value2: " + secondValue);
        try {
            final String queryString = "select count(model)" +
            		" from " + getClassName() + " model" +
            		" where model." + firstName + "= :value1" +
            		" and model." + secondName + "=:value2";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("value1", firstValue);
            query.setParameter("value2", secondValue);
            Long count = (Long)query.getSingleResult();
            return count.longValue();
        } catch (RuntimeException re) {
            logger.error("count by property name2 failed");
            throw re;
        }
    }
}