package com.shawon.demo.marchent.controller;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shawon.demo.common.ResponseDTO;
import com.shawon.demo.marchent.dto.MarchentDTO;
import com.shawon.demo.marchent.entity.Marchent;
import com.shawon.demo.marchent.service.MarchentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/marchent")
public class MarchentController {
//	@Autowired
//	private MarchentService marchentService;
	
	private final MarchentService marchentService;

    public MarchentController(MarchentService marchentService) {
        this.marchentService = marchentService;
    }
	
	@PostMapping("/addMarchent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addMarchent(@RequestBody MarchentDTO model) {
		if(model == null) {
			return null;
		}
		try {
			marchentService.add(model);
			return "Save Successfully";
		}catch (Exception e) {
			e.printStackTrace();
			return "Save Failed. "+e.getMessage();
		}
		
	}
	
	@PostMapping("/getMarchentById")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getMarchent(@RequestBody MarchentDTO model) {
		
		if(model == null) {
			return null;
		}
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		try {
			MarchentDTO marchentDTO = marchentService.getMarchentByKey(marchentService.convertToEntity(model));
			responseDTO.setSuccess(true);
			responseDTO.setData(marchentDTO);
			return responseDTO;
		}catch (Exception e) {
			e.printStackTrace();
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
			return responseDTO;
		}
		
	}
	
	@PostMapping("/modifyMarchent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO modifyMarchent(@RequestBody MarchentDTO model) {
		
		if(model == null) {
			return null;
		}
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		try {
			MarchentDTO marchentDTO = marchentService.modifyMarchent(model);
			responseDTO.setSuccess(true);
			responseDTO.setData(marchentDTO);
			return responseDTO;
		}catch (Exception e) {
			e.printStackTrace();
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
			return responseDTO;
		}
		
	}
	
	@PostMapping("/deleteMarchentById")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO deleteMarchentById(@RequestBody MarchentDTO model) {
		
		if(model == null) {
			return null;
		}
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		try {
			MarchentDTO marchentDTO = marchentService.deleteMarchentById(model);
			responseDTO.setSuccess(true);
			responseDTO.setData(marchentDTO);
			return responseDTO;
		}catch (Exception e) {
			e.printStackTrace();
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
			return responseDTO;
		}
	}
	
	@PostMapping("/getAllMarchent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDTO getAllMarchent() {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		try {
			List<MarchentDTO> marchentDTOlist = marchentService.getAllMarchent();
			responseDTO.setSuccess(true);
			responseDTO.setData(marchentDTOlist);
			return responseDTO;
		}catch (Exception e) {
			e.printStackTrace();
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
			return responseDTO;
		}
		
	}
	
	@PostMapping("/addMarchentUsingWebFlux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Mono<Boolean> addMarchentUsingWebFlux(@RequestBody Marchent model) {
		
		Mono<Boolean> marchentDTOlist = marchentService.addMarchentUsingWebFlux(model);
		return marchentDTOlist;

		
	}
	
	@PostMapping("/deleteMarchentUsingWebFlux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Mono<Boolean> deleteMarchentUsingWebFlux(@RequestBody Marchent model) {
		
		Mono<Boolean> marchentDTOlist = marchentService.deleteMarchentUsingWebFlux(model);
		return marchentDTOlist;

		
	}
	
	@PostMapping("/getMarchentByIdUsingWebFlux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Mono<Marchent> getMarchentByIdUsingWebFlux(@Valid @RequestBody Marchent model){
		
		Mono<Marchent> marchentDTOlist = marchentService.getMarchentByIdUsingWebFlux(model);
		return marchentDTOlist;
		
	}
	
	@PostMapping("/getAllMarchentByIdUsingWebFlux")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Flux<Marchent> getAllMarchentByIdUsingWebFlux(){
		
		Flux<Marchent> marchentDTOlist = marchentService.getAllMarchentByIdUsingWebFlux();
		return marchentDTOlist;
		
	}
}
