package cn.xianbin.analytics.idmappig.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TrackEvent implements Serializable {

    private String distinctId;

    private String originalId;

    private Long time;

    private String type;

    private String event;

    private Map<String, String> properties;


}
