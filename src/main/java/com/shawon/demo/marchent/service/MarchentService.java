package com.shawon.demo.marchent.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shawon.demo.marchent.dto.MarchentDTO;
import com.shawon.demo.marchent.entity.Merchent;
import com.shawon.demo.marchent.repository.MarchentRepositoryDynamoDB;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MarchentService {
//	@Autowired
//	private MarchentRepositoryDynamoDB marchentReporsitory;
	
	private final MarchentRepositoryDynamoDB marchentReporsitory;

    public MarchentService(MarchentRepositoryDynamoDB marchentReporsitory) {
        this.marchentReporsitory = marchentReporsitory;
    }

	@Autowired
	private ModelMapper modelMapper;
	
//	@Autowired
//	private MarchentRepoReactive marchentRepoReactive;
	
	public void add(MarchentDTO model) throws Exception {
		if (model == null ) {
			throw new Exception("Model can't be Null");
		}
		
		Merchent m = convertToEntity(model);
//		MarchentDTO checkDTO = getMarchentByKey(m);
//		if (checkDTO!= null)
//		if(checkDTO.getId()!=null) {
//			throw new Exception("Already Exists");
//		}
		
		marchentReporsitory.save(m);
	}

	
	
	public MarchentDTO modifyMarchent(MarchentDTO model) throws Exception {
		
		if (model == null ) {
			throw new Exception("Model can't be Null");
		}
		
		MarchentDTO checkDTO = getMarchentByKey(convertToEntity(model));
		
		if(checkDTO.getId() != null) {
			throw new Exception("No data found to update");
		}
		
		marchentReporsitory.save(convertToEntity(model));
		
		return model;
	}


	public MarchentDTO deleteMarchentById(MarchentDTO model) throws Exception {
		if (model == null ) {
			throw new Exception("Model can't be Null");
		}
		
		MarchentDTO checkDTO = getMarchentByKey(convertToEntity(model));
		
		if(checkDTO.getId() == "") {
			throw new Exception("No data found to delete");
		}
		
		marchentReporsitory.delete(convertToEntity(model));
		return model;
	}

	public MarchentDTO getMarchentByKey(Merchent merchent) {
		Merchent m = marchentReporsitory.findByKey(merchent);
		MarchentDTO marchentDTO = new MarchentDTO();
		if(m!= null)
			 marchentDTO = convertToDTO(m);
		return marchentDTO;
	}

	public List<MarchentDTO> getAllMarchent() {
		List<MarchentDTO> marchentDTOlist = ConvertToDtoList(marchentReporsitory.getUsingQuery());
		return marchentDTOlist;
	}
	
	public Merchent convertToEntity(MarchentDTO model) {
		Merchent merchent = modelMapper.map(model, Merchent.class);
		return merchent;
	}

	private MarchentDTO convertToDTO(Merchent merchent) {
		MarchentDTO dto = modelMapper.map(merchent, MarchentDTO.class);
		return dto;
	}


	private List<MarchentDTO> ConvertToDtoList(List<Merchent> mrchentList) {
		List<MarchentDTO> MarchentDTOList = mrchentList.stream().map(entity -> convertToDTO(entity)).collect(Collectors.toList());
		return MarchentDTOList;
	}
	
	public Mono<Boolean> addMarchentUsingWebFlux(Merchent model) {
        return marchentReporsitory.addMarchentUsingWebFlux(model);
	}

	
	public Mono<Boolean> deleteMarchentUsingWebFlux(Merchent model) {
        return marchentReporsitory.deleteMarchentUsingWebFlux(model);
	}



	public Mono<Merchent> getMarchentByIdUsingWebFlux(Merchent model) {
		
		return marchentReporsitory.getMarchentByIdUsingWebFlux(model);
	}



	public Flux<Merchent> getAllMarchentByIdUsingWebFlux() {
		return marchentReporsitory.getAllMarchentByIdUsingWebFlux();
	}



	public Mono<Boolean> modMarchentUsingWebFlux( Merchent model) {
		if(ObjectUtils.isEmpty(model.getNumber())){
			return Mono.just(false);
		}

		return marchentReporsitory.getMarchentByIdUsingWebFlux(model).flatMap(merchent -> {
			log.info(merchent.toString());
			merchent.setNumber(model.getNumber());
			return marchentReporsitory.addMarchentUsingWebFlux(merchent);
		});
	}
	
}
