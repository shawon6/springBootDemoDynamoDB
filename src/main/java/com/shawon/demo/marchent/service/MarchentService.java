package com.shawon.demo.marchent.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shawon.demo.marchent.dto.MarchentDTO;
import com.shawon.demo.marchent.entity.Marchent;
//import com.shawon.demo.marchent.repository.MarchentReporsitory;
import com.shawon.demo.marchent.repository.MarchentRepositoryDynamoDB;

@Service
public class MarchentService {
	@Autowired
	private MarchentRepositoryDynamoDB marchentReporsitory;

	@Autowired
	private ModelMapper modelMapper;
	
	public void add(MarchentDTO model) throws Exception {
		if (model == null ) {
			throw new Exception("Model can't be Null");
		}
		
		MarchentDTO checkDTO = getMarchentById(model.getId());
		if(checkDTO.getId() != "") {
			throw new Exception("Already Exists");
		}
		
		marchentReporsitory.save(convertToEntity(model));
	}

	
	
	public MarchentDTO modifyMarchent(MarchentDTO model) throws Exception {
		
		if (model == null ) {
			throw new Exception("Model can't be Null");
		}
		
		MarchentDTO checkDTO = getMarchentById(model.getId());
		
		if(checkDTO.getId() == "") {
			throw new Exception("No data found to update");
		}
		
		marchentReporsitory.save(convertToEntity(model));
		
		return model;
	}


	public MarchentDTO deleteMarchentById(MarchentDTO model) throws Exception {
		if (model == null ) {
			throw new Exception("Model can't be Null");
		}
		
		MarchentDTO checkDTO = getMarchentById(model.getId());
		
		if(checkDTO.getId() == "") {
			throw new Exception("No data found to delete");
		}
		
		marchentReporsitory.delete(convertToEntity(model));
		return model;
	}

	public MarchentDTO getMarchentById(String id) {
		MarchentDTO marchentDTO = convertToDTO(marchentReporsitory.findById(id));
		return marchentDTO;
	}

	public List<MarchentDTO> getAllMarchent() {
		List<MarchentDTO> marchentDTOlist = ConvertToDtoList(marchentReporsitory.getAllMarchent());
		return marchentDTOlist;
	}
	
	private Marchent convertToEntity(MarchentDTO model) {
		Marchent marchent = modelMapper.map(model, Marchent.class);
		return marchent;
	}

	private MarchentDTO convertToDTO(Marchent marchent) {
		MarchentDTO dto = modelMapper.map(marchent, MarchentDTO.class);
		return dto;
	}


	private List<MarchentDTO> ConvertToDtoList(List<Marchent> mrchentList) {
		List<MarchentDTO> MarchentDTOList = mrchentList.stream().map(entity -> convertToDTO(entity)).collect(Collectors.toList());
		return MarchentDTOList;
	}
	
}
