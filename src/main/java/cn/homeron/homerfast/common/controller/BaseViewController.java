package cn.homeron.homerfast.common.controller;

import cn.homeron.homerfast.common.bean.BaseBean;
import cn.homeron.homerfast.common.bean.SepcCountParameter;
import cn.homeron.homerfast.common.bean.SepcListParameter;
import cn.homeron.homerfast.common.bean.SepcPageParameter;
import cn.homeron.homerfast.common.service.BaseService;
import cn.homeron.homerfast.common.utils.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ViewController基类
 *
 * @author Homer_Gu
 * @version 1.0.0
 * @date 2020-09-12
 */
@Api
public class BaseViewController<S extends BaseService, T extends BaseBean> extends SuperController {

    @Autowired
    protected S service;

    @ApiOperation(value = "查询全部数据", notes = "查询全部数据")
    @RequestMapping(value = "/getAll", method = {RequestMethod.GET})
    public R getAll() {

        return R.ok().put("data", service.getAll());
    }

    @ApiOperation(value = "查询全部Count", notes = "查询全部Count")
    @RequestMapping(value = "/getAllCount", method = {RequestMethod.GET})
    public String getAllCount() {
        return String.valueOf(service.getAllCount());
    }

    @ApiOperation(value = "根据主键查询数据", notes = "根据主键查询数据")
    @ApiImplicitParam(name = "id", value = "业务表编码", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public R getOne(@PathVariable(value = "id") Object id) {
        return R.ok().put("data", service.getById(id));
    }

    @ApiOperation(value = "根据jsonParam查询Page数据", notes = "根据jsonParam查询Page数据")
    @RequestMapping(value = "/getAllBySepc", method = {RequestMethod.POST})
    public R getAllBySepc(@ApiParam(value = "BaseParameter对象", required = true) @RequestBody SepcPageParameter jsonParam) {
        return R.ok().put("data", service.getAllBySepc((JSONObject) JSON.toJSON(jsonParam)));
    }

    @ApiOperation(value = "根据jsonParam查询List数据", notes = "根据jsonParam查询List数据")
    @RequestMapping(value = "/getAllListBySepc", method = {RequestMethod.POST})
    public R getAllListBySepc(@ApiParam(value = "BaseParameter对象", required = true) @RequestBody SepcListParameter jsonParam) {
        return R.ok().put("data", service.getAllListBySepc((JSONObject) JSON.toJSON(jsonParam)));
    }

    @ApiOperation(value = "根据jsonParam查询Count", notes = "根据jsonParam查询Count")
    @RequestMapping(value = "/getAllCountBySepc", method = {RequestMethod.POST})
    public R getAllCountBySepc(@ApiParam(value = "BaseParameter对象", required = true) @RequestBody SepcCountParameter jsonParam) {
        return R.ok().put("data", service.getAllCountBySepc((JSONObject) JSON.toJSON(jsonParam)));
    }

    @ApiOperation(value = "查看Bean对象", notes = "查看Bean对象")
    @RequestMapping(value = "/viewBean", method = {RequestMethod.POST})
    public R viewBean(@ApiParam(value = "Bean对象") @RequestBody T bean) {
        return R.ok().put("data", bean);
    }
}
