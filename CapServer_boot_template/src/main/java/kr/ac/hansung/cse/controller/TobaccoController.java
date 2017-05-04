package kr.ac.hansung.cse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.cse.model.Tobacco;
import kr.ac.hansung.cse.service.TobaccoService;

@Controller
public class TobaccoController {
	@Autowired
	private TobaccoService tobaccoService;
	
	@RequestMapping("/tobaccos")
	public String getTobaccos(Model model){
		List<Tobacco> tobaccos = tobaccoService.getTobaccos();
		model.addAttribute("tobaccos",tobaccos);
		return "tobaccos";
	}

	@RequestMapping("/tobaccos/{tobaccoId}")
	public String getTobaccoDetail(@PathVariable int tobaccoId, Model model){
		Tobacco tobacco = tobaccoService.getTobaccoById(tobaccoId);
		model.addAttribute("tobacco",tobacco);
		return "tobaccodetail";
	}

}
