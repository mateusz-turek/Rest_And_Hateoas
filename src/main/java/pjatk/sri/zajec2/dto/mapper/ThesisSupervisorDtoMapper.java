package pjatk.sri.zajec2.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pjatk.sri.zajec2.dto.ThesisSupervisorDetailsDto;
import pjatk.sri.zajec2.dto.ThesisSupervisorDto;
import pjatk.sri.zajec2.model.ThesisSupervisor;

@Component
@RequiredArgsConstructor
public class ThesisSupervisorDtoMapper {

    private final ModelMapper modelMapper;

    public ThesisSupervisor convertToEntity(ThesisSupervisorDto thesisSupervisorDto){
        return modelMapper.map(thesisSupervisorDto,ThesisSupervisor.class);
    }

    public ThesisSupervisorDto convertToDto(ThesisSupervisor thesisSupervisor){
        return modelMapper.map(thesisSupervisor, ThesisSupervisorDto.class);
    }

    public ThesisSupervisorDetailsDto convertToDetailsDto(ThesisSupervisor thesisSupervisor){
        return modelMapper.map(thesisSupervisor, ThesisSupervisorDetailsDto.class);
    }
}
