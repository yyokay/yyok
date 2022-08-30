package ${package}.service.mapper;

import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import ${package}.domain.${className};
import ${package}.service.dto.${className}Dto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author ${author}
* @date ${date}
*/
@Repository
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface I${className}Mapper extends ICoreMapper
<${className}Dto, ${className}> {

}
