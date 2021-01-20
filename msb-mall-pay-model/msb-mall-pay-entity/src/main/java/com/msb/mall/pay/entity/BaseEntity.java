package com.msb.mall.pay.entity;



import com.msb.cube.common.base.annotation.EntityLogicId;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 10:36
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@ToString
public class BaseEntity implements Serializable {

    @Id
    protected String id;

    @EntityLogicId
    @Indexed(unique = true)
    protected String bzId;

    protected Long gmtCreate ;

    protected Long gmtModified ;

    protected String createUid ;

    protected String createUname ;

    protected String modifiedUid ;

    protected String modifiedUname ;

    protected Boolean isDelete;

    protected Boolean enabled;

}
