package cn.homeron.homerfast.common.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Repository基类
 * @author Homer_Gu
 * @version 1.0.0
 * @date 2018-09-01
 */
@NoRepositoryBean
@EnableTransactionManagement
public interface BaseRepository<T, ID> extends CrudRepository<T, ID>, JpaSpecificationExecutor<T>{
}
