package cn.xianbin.analytics.idmappig.po;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
public class Events implements Serializable {

    private static final long serialVersionUID = 1L;

    private String distinctId;

    private String userId;

    private String event;


}
