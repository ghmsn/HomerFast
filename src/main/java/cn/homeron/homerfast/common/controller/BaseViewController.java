package cn.homeron.homerfast.common.controller;

import cn.homeron.homerfast.common.bean.BaseBean;
import cn.homeron.homerfast.common.bean.SpecCountParameter;
import cn.homeron.homerfast.common.bean.SpecListParameter;
import cn.homeron.homerfast.common.bean.SpecPageParameter;
import cn.homeron.homerfast.common.service.BaseService;
import cn.homeron.homerfast.common.utils.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/getAll")
    public R getAll() {
        return R.ok().put("data", service.getAll());
    }

    @ApiOperation(value = "查询全部Count", notes = "查询全部Count")
    @GetMapping(value = "/getAllCount")
    public String getAllCount() {
        return String.valueOf(service.getAllCount());
    }

    @ApiOperation(value = "根据主键查询数据", notes = "根据主键查询数据")
    @ApiImplicitParam(name = "id", value = "业务表编码", required = true, dataType = "Long")
    @GetMapping(value = "/{id}")
    public R getOne(@PathVariable(value = "id") Long id) {
        return R.ok().put("data", service.getById(id));
    }

    @ApiOperation(value = "根据jsonParam查询Page数据", notes = "根据jsonParam查询Page数据")
    @PostMapping(value = "/getAllBySpec")
    public R getAllBySpec(@ApiParam(value = "BaseParameter对象", required = true) @RequestBody SpecPageParameter jsonParam) {
        return R.ok().put("data", service.getAllBySpec((JSONObject) JSON.toJSON(jsonParam)));
    }

    @ApiOperation(value = "根据jsonParam查询List数据", notes = "根据jsonParam查询List数据")
    @PostMapping(value = "/getAllListBySpec")
    public R getAllListBySpec(@ApiParam(value = "BaseParameter对象", required = true) @RequestBody SpecListParameter jsonParam) {
        return R.ok().put("data", service.getAllListBySpec((JSONObject) JSON.toJSON(jsonParam)));
    }

    @ApiOperation(value = "根据jsonParam查询Count", notes = "根据jsonParam查询Count")
    @PostMapping(value = "/getAllCountBySpec")
    public R getAllCountBySpec(@ApiParam(value = "BaseParameter对象", required = true) @RequestBody SpecCountParameter jsonParam) {
        return R.ok().put("data", service.getAllCountBySpec((JSONObject) JSON.toJSON(jsonParam)));
    }
}
