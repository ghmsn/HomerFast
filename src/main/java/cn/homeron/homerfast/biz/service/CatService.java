package cn.homeron.homerfast.biz.service;


import cn.homeron.homerfast.biz.bean.CatBean;
import cn.homeron.homerfast.biz.repository.CatRepository;
import cn.homeron.homerfast.common.service.BaseService;
import org.springframework.stereotype.Service;


@Service
public class CatService extends BaseService<CatRepository, CatBean, Integer> {
}
