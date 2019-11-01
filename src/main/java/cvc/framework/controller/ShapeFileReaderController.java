package cvc.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cvc.framework.result.RestMessage;
import cvc.framework.result.RestResult;
import cvc.framework.service.ShapeFileReaderService;
@Controller
public class ShapeFileReaderController {
	@Autowired
	private ShapeFileReaderService service;
	//跳转controller
	@RequestMapping("/drawmap")
	public String drawmap() {
		return "map";
	}
	//服务controller
	@RequestMapping(value="/getPoints",method=RequestMethod.GET)
	@ResponseBody
	public RestResult getPoints()
	{
		RestResult result=new RestResult();
		result.state=0;
		result.error="";
		result.message=new RestMessage();
		result.message.data=service.getPoints();
		return result;
	}
}
