package cn.xianbin.analytics.idmappig.service;

import cn.xianbin.analytics.idmappig.mapper.UsersMapper;
import cn.xianbin.analytics.idmappig.po.Users;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UsersService extends ServiceImpl<UsersMapper, Users> {

    public Users getByFirstId(String distinctId) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getFirstId, distinctId);
        return baseMapper.selectOne(wrapper);
    }

    public Users getBySecondId(String distinctId) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getSecondId, distinctId);
        return baseMapper.selectOne(wrapper);
    }

    public Users getFirstId(String deviceId) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getFirstId, deviceId);
        return baseMapper.selectOne(wrapper);
    }

    public void removeByFirstId(String deviceId) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getFirstId, deviceId);
        baseMapper.delete(wrapper);
    }

}
