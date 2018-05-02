package cn.bdc.weChatService.repository;


import org.springframework.data.repository.CrudRepository;

import cn.bdc.weChatService.bean.CatBean;


public interface CatRepository extends CrudRepository<CatBean, Integer> {

}
