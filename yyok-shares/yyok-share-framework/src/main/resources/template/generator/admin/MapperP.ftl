package ${package}.service.mapper;

import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import ${package}.domain.${className};
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author ${author}
* @date ${date}
*/
@Repository
@Mapper
public interface I${className}Mapper extends ICoreMapper<${className}> {

}
