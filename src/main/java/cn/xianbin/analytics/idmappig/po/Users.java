package cn.xianbin.analytics.idmappig.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String firstId;

    private String secondId;

    //除first_id,额外关联的设备id
    private String deviceIdList;


}
