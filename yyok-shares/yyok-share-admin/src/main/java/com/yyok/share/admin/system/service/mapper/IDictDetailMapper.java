package com.yyok.share.admin.system.service.mapper;

import com.yyok.share.admin.system.domain.DictDetail;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Repository
@Mapper
public interface IDictDetailMapper extends ICoreMapper<DictDetail> {

    @Select("<script>SELECT d.* from dict_detail d LEFT JOIN dict t on d.dict_code = t.coder where 1=1 <if test = \"label !=null\" > and d.label LIKE concat('%', #{label}, '%') </if> <if test = \"dictName != ''||dictName !=null\" > AND t.name = #{dictName} order by t.sorter asc</if></script>")
    List<DictDetail> selectDictDetailList(@Param("label") String label, @Param("dictName") String dictName);
}
