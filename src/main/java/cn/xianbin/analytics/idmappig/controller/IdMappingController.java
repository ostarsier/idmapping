package cn.xianbin.analytics.idmappig.controller;

import cn.xianbin.analytics.idmappig.service.TrackService;
import cn.xianbin.analytics.idmappig.vo.TrackEvent;
import cn.xianbin.analytics.idmappig.job.DataCleanJob;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class IdMappingController {

    @Resource
    private TrackService trackService;

    @Resource
    private DataCleanJob dataCleanJob;

    @PostMapping("/event/track")
    public void track(@RequestBody TrackEvent trackEvent) {
        trackService.track(trackEvent);
    }

    @GetMapping("/clean")
    public void clean() {
        dataCleanJob.clean();
    }


}
