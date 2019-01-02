package com.sykj.shenfu.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sykj.shenfu.po.Cell;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/map")
@Transactional
public class MapController extends BaseController {

	/**
	 * 进入地图界面
	 */
	@RequestMapping(value = "/mapBaseChoice")
	public String mapBaseChoice(Cell form) {
		return "map/map";
	}
	
	
	
}
