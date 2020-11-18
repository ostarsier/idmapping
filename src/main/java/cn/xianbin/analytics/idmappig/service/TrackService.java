package cn.xianbin.analytics.idmappig.service;

import cn.xianbin.analytics.idmappig.mapper.EventsMapper;
import cn.xianbin.analytics.idmappig.po.Events;
import cn.xianbin.analytics.idmappig.po.Users;
import cn.xianbin.analytics.idmappig.vo.TrackEvent;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import javax.annotation.Resource;

@Service
public class TrackService extends ServiceImpl<EventsMapper, Events> {

    @Resource
    private UsersService usersService;

    @Resource
    private EventsService eventsService;

    private boolean one2many = true;

    @Transactional
    public void track(TrackEvent trackEvent) {
        String userId = userId(trackEvent);

        Events events = Events.builder()
                .distinctId(trackEvent.getDistinctId())
                .userId(userId)
                .event(trackEvent.getEvent())
                .build();
        eventsService.save(events);
    }

    public String userId(TrackEvent trackEvent) {

        String type = trackEvent.getType();
        //事件记录
        if ("track".equals(type)) {
            String distinctId = trackEvent.getDistinctId();
            Map<String, String> properties = trackEvent.getProperties();
            boolean isLoginId = MapUtils.getBoolean(properties, "$is_login_id", false);

            if (isLoginId) {// 登录后
                return usersService.getBySecondId(distinctId).getId();
            } else { // 未登录
                Users user = usersService.getByFirstId(distinctId);
                if (user == null) {// 第一次访问
                    Users newUser = Users.builder()
                            .firstId(distinctId)
                            .build();
                    usersService.save(newUser);
                    return newUser.getId();
                } else {// 非第一次访问
                    return user.getId();
                }

            }
            // 用户关联
        } else if ("track_signup".equals(type)) {
            String distinctId = trackEvent.getDistinctId();
            String originalId = trackEvent.getOriginalId();

            Users firstIdUser = usersService.getByFirstId(originalId);
            String secondId = firstIdUser.getSecondId();
            if (StringUtils.isBlank(secondId)) { // 设备第一次关联
                Users secondIdUser = usersService.getBySecondId(distinctId);
                if (secondIdUser == null) { // 用户第一次关联
                    Users updateUser = Users.builder()
                            .id(firstIdUser.getId())
                            .secondId(distinctId)
                            .build();
                    usersService.updateById(updateUser);
                    return firstIdUser.getId();
                } else {
                    if (one2many) { //用户已经关联
                        String deviceIdList = secondIdUser.getDeviceIdList();
                        if (deviceIdList == null) {
                            deviceIdList = originalId;
                        } else {
                            deviceIdList = deviceIdList + "," + originalId;
                        }
                        Users updateUser = Users.builder()
                                .id(secondIdUser.getId())
                                .deviceIdList(deviceIdList)
                                .build();
                        usersService.updateById(updateUser);
                        return secondIdUser.getId();
                    } else {
                        //已经关联了的用户，不再处理
                    }
                }

            } else {// 设备已经关联
                if (one2many) {//关联的设备是自己,添加进用户的关联设备集合里
                    if (firstIdUser.getSecondId().equals(distinctId)) {
                        String deviceIdList = firstIdUser.getDeviceIdList();
                        if (deviceIdList == null) {
                            deviceIdList = originalId;
                        } else {
                            deviceIdList = deviceIdList + "," + originalId;
                        }
                        Users updateUser = Users.builder()
                                .id(firstIdUser.getId())
                                .deviceIdList(deviceIdList)
                                .build();
                        usersService.updateById(updateUser);
                        return firstIdUser.getId();
                    } else {//关联的设备不是自己,创建新用户
                        Users newUser = Users.builder()
                                .firstId(distinctId)
                                .secondId(distinctId)
                                .build();
                        usersService.save(newUser);
                        return newUser.getId();
                    }

                } else { //一个设备只能关联一个用户
                    Users newUser = Users.builder()
                            .firstId(distinctId)
                            .secondId(distinctId)
                            .build();
                    usersService.save(newUser);
                    return newUser.getId();
                }

            }
        }

        return null;
    }


}
