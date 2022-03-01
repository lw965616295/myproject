package com.weil.convert;

import com.weil.bean.User;
import com.weil.bean.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @InterfaceName MapStructConvert
 * @Author weil
 * @Description //转换工具
 * @Date 2022/2/28 10:34
 * @Version 1.0.0
 **/
@Mapper
public interface MapStructConvert {
    MapStructConvert INSTANCE = Mappers.getMapper(MapStructConvert.class);
    @Mappings({
            @Mapping(source = "id", target = "UID")
    })
    UserDto toUserDto(User user);
}
