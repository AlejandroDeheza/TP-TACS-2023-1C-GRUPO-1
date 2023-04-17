package com.tacs.backend.mapper;

import com.tacs.backend.dto.EventOptionDto;
import com.tacs.backend.model.EventOption;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface EventOptionMapper {

    EventOptionMapper INSTANCE = Mappers.getMapper(EventOptionMapper.class);

    EventOptionDto entityToDto(EventOption EventOption);

    EventOption dtoToEntity(EventOptionDto EventOptionDto);

    Set<EventOptionDto> entitySetToDtoSet(Set<EventOption> EventOptions);

    Set<EventOption> dtoSetToEntitySet(Set<EventOptionDto> EventOptionsDto);

    List<EventOptionDto> entityListToDtoList(List<EventOption> EventOptions);

}
