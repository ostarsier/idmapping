package cn.xianbin.analytics.idmappig.service;

import cn.xianbin.analytics.idmappig.mapper.EventsMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xianbin.analytics.idmappig.po.Events;
import org.springframework.stereotype.Service;

@Service
public class EventsService extends ServiceImpl<EventsMapper, Events> {

    public void updateUserIdByDistinctId(String distinctId, String userId) {
        LambdaQueryWrapper<Events> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Events::getDistinctId, distinctId);

        Events event = Events.builder()
                .userId(userId)
                .build();

        baseMapper.update(event, wrapper);
    }
}
