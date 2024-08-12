package gft.teste.contabilidade.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import gft.teste.contabilidade.dto.ContabilidadeDTO;
import gft.teste.contabilidade.entity.Contabilidade;

@Mapper
public interface ContabilidadeMapper {

    ContabilidadeMapper INSTANCE = Mappers.getMapper(ContabilidadeMapper.class);

    Contabilidade toModel(ContabilidadeDTO contabilidadeDTO);

    ContabilidadeDTO toDTO(Contabilidade contabilidade);
}
