package cn.homeron.homerfast.biz.controller;


import cn.homeron.homerfast.biz.bean.CatBean;
import cn.homeron.homerfast.biz.service.CatService;
import cn.homeron.homerfast.common.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cat")
public class CatController extends BaseController<CatService, CatBean> {
}
