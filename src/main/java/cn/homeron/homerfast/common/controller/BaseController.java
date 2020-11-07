package cn.homeron.homerfast.common.controller;

import cn.homeron.homerfast.common.bean.BaseBean;
import cn.homeron.homerfast.common.service.BaseService;
import cn.homeron.homerfast.common.utils.R;
import cn.homeron.homerfast.common.valid.AddGroup;
import cn.homeron.homerfast.common.valid.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller基类
 *
 * @author Homer_Gu
 * @version 1.0.0
 * @date 2018-09-01
 */
@Api
@EnableTransactionManagement
public class BaseController<S extends BaseService, T extends BaseBean> extends BaseViewController<S, T> {

    @ApiOperation(value = "根据id删除数据", notes = "根据id删除数据")
    @ApiImplicitParam(name = "id", value = "业务表编码", required = true, dataType = "Long")
    @DeleteMapping(value = "/{id}")
    @Transactional
    public R delete(@PathVariable(value = "id") Long id) {

        service.delete(id);

        return R.ok();
    }

    @ApiOperation(value = "新增Bean对象数据", notes = "新增Bean对象数据")
    @PostMapping(value = "/save")
    @Transactional
    public R add(@Validated({AddGroup.class}) @ApiParam(value = "Bean对象", required = true) @RequestBody T bean) {
        return R.ok(201, "Created").put("data", service.save(bean));
    }

    @ApiOperation(value = "更新Bean对象数据", notes = "更新Bean对象数据")
    @PutMapping(value = "/save")
    @Transactional
    public R update(@Validated({UpdateGroup.class}) @ApiParam(value = "Bean对象", required = true) @RequestBody T bean) {
        return R.ok(201, "Updated").put("data", service.save(bean));
    }

    @ApiOperation(value = "新增全部Bean对象数据", notes = "新增全部Bean对象数据")
    @PostMapping(value = "/saveAll")
    @Transactional
    public R addAll(@Validated({AddGroup.class}) @ApiParam(value = "Bean对象集合", required = true) @RequestBody Iterable<T> beans) {
        return R.ok(201, "Created").put("data", service.saveAll(beans));
    }

    @ApiOperation(value = "更新全部Bean对象数据", notes = "更新全部Bean对象数据")
    @PutMapping(value = "/saveAll")
    @Transactional
    public R updateAll(@Validated({UpdateGroup.class}) @ApiParam(value = "Bean对象集合", required = true) @RequestBody Iterable<T> beans) {
        return R.ok(201, "Updated").put("data", service.saveAll(beans));
    }
}
