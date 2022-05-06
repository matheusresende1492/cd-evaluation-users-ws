package com.cd.evaluation.users.commons.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for conversion of searchDTOs to models
 * @param <S> SearchDTO
 * @param <M> Model
 */
public class SearchDTOConverter<S, M> {
    protected final Class<S> searchClass;
    protected final Class<M> modelClass;
    protected final ModelMapper modelMapper;

    public SearchDTOConverter(@Autowired ModelMapper modelMapper, Class<S> searchClass, Class<M> modelClass) {
        this.searchClass = searchClass;
        this.modelClass = modelClass;
        this.modelMapper = modelMapper;
    }

    public M convertToModelFromSearch(S search) { return modelMapper.map(search, modelClass); }
}
