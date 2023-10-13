package com.turbochat.chat.server.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.turbochat.chat.server.modules.user.domain.AppUser;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 业务用户Mapper
 * @Date 2023/7/27 15:45
 * @Created by Alickx
 */
public interface AppUserMapper extends BaseMapper<AppUser> {

	AppUser queryUserByUserName(@Param("userName") String userName);

	AppUser queryUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
