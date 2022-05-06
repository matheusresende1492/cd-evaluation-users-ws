package com.cd.evaluation.users.commons.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for conversion of models classes to DTO classes, and the other way around
 * @param <D> DTO
 * @param <M> Model
 */
public class DTOConverter<D, M> {
    protected final Class<D> dtoClass;
    protected final Class<M> modelClass;
    protected final ModelMapper modelMapper;

    public DTOConverter(@Autowired ModelMapper modelMapper, Class<D> dtoClass, Class<M> modelClass) {
        this.dtoClass = dtoClass;
        this.modelClass = modelClass;
        this.modelMapper = modelMapper;
    }

    public List<D> convertToDTOList(List<M> models){
        return models.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public D convertToDTO(M model) {return modelMapper.map(model, dtoClass); }

    public M convertToModel(D dto) { return modelMapper.map(dto, modelClass); }
}
