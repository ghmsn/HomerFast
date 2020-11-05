package cn.homeron.homerfast.common.controller;

import cn.homeron.homerfast.common.bean.BaseBean;
import cn.homeron.homerfast.common.service.BaseService;
import cn.homeron.homerfast.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller基类
 *
 * @author Homer_Gu
 * @version 1.0.0
 * @date 2018-09-01
 */
@Api
public class BaseController<S extends BaseService, T extends BaseBean> extends BaseViewController<S, T> {

    @ApiOperation(value = "根据id删除数据", notes = "根据id删除数据")
    @ApiImplicitParam(name = "id", value = "业务表编码", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    public R delete(@PathVariable(value = "id") Object id) {

        service.delete(id);

        return R.ok();
    }

    @ApiOperation(value = "保存Bean对象数据", notes = "保存Bean对象数据")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public R save(@ApiParam(value = "Bean对象", required = true) @RequestBody T bean) {

        return R.ok(201, "Created").put("data", service.save(bean));
    }

    @ApiIgnore
    @Override
    @RequestMapping(value = "/viewBean", method = {RequestMethod.POST})
    public R viewBean(@RequestBody T bean) {
        return R.ok().put("data", bean);
    }
}
