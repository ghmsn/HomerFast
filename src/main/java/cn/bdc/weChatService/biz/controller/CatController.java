package cn.bdc.weChatService.biz.controller;


import cn.bdc.weChatService.biz.bean.CatBean;
import cn.bdc.weChatService.biz.service.CatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/cat")
public class CatController {

	@Resource
	private CatService catService;

	@PostMapping("/save")
	public String save() {
		CatBean cat = new CatBean();
		cat.setCatName("Tom");
		cat.setCatAge(3);
		catService.save(cat);
		
		return "save ok.";
	}

	@PostMapping("/delete")
	public String delete(int id) {
		catService.delete(id);
		return "delete ok.";
	}

	@PostMapping("/getAll")
	public Iterable<CatBean> getAll(){
		return catService.getAll();
	}
	
	

}
