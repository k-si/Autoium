package run.autoium.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 数据库表中的字段注入
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 在insert数据的时候自动填充gmtCreate、gmtModified字段
     *
     * @param metaObject 数据库表中一条记录的抽象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

    /**
     * 在update数据的时候自动填充gmtCreate、gmtModified字段
     *
     * @param metaObject 数据库表中一条记录的抽象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
