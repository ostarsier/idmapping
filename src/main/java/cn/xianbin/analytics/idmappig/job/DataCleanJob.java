package cn.xianbin.analytics.idmappig.job;

import cn.xianbin.analytics.idmappig.service.EventsService;
import cn.xianbin.analytics.idmappig.service.UsersService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cn.xianbin.analytics.idmappig.po.Users;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import javax.annotation.Resource;

/**
 * 一对多, 脏数据清洗
 * 1.对已经关联了设备的用户，该用户换了设备再次访问且未登录前的访问记录，找到对应的user_id并覆盖
 * 2.删除该原来的user_id
 */
@Service
@Slf4j
public class DataCleanJob {

    @Resource
    private UsersService usersService;

    @Resource
    private EventsService eventsService;

    public void clean() {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNotNull(Users::getDeviceIdList);
        usersService.list(wrapper).stream().flatMap(user -> {
            return Arrays.stream(user.getDeviceIdList().split(",")).map(deviceId -> {
                return Pair.of(user.getId(), deviceId);
            });
        }).forEach(pair -> {
            // 非第一次关联的设备id
            String userId = pair.getLeft();
            String deviceId = pair.getRight();
            log.info("userId={}, deviceId={}", userId, deviceId);

            eventsService.updateUserIdByDistinctId(deviceId, userId);
            usersService.removeByFirstId(deviceId);
        });
    }


}
