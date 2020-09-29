package cn.bdc.weChatService.biz.repository;


import org.springframework.data.repository.CrudRepository;

import cn.bdc.weChatService.biz.bean.CatBean;


public interface CatRepository extends CrudRepository<CatBean, Integer> {

}
