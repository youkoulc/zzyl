package com.zzyl.intercept;


import cn.hutool.core.util.ObjectUtil;
import com.zzyl.utils.EmptyUtil;
import com.zzyl.utils.UserThreadLocal;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @Intercepts: 配置mybatis拦截器注解
 *     type = Executor.class 拦截sql命令执行器，拦截要执行的sql语句
 *     method = "update"， 拦截增，删，改的sql命令,以insert / update /delete 等开头的命令
 *     args = {MappedStatement.class, Object.class} 固定mybatis映射参数数据
 * 目标：拦截insert / update /delete 等开头sql语句进行拦截处理（补全数据创建人，创建时间，修改人，修改时间）
 */

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class AutoFillInterceptor implements Interceptor {

    private static final String CREATE_BY = "createBy";
    private static final String UPDATE_BY = "updateBy";

    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_TIME = "updateTime";

    /**
     * intercept方法：拦截的方法
     * - 在拦截器中，如果是新增方法，则会给参数对象的CREATE_BY、CREATE_TIME、UPDATE_TIME三个字段赋值
     * - 如果是更新方法，则会给参数对象UPDATE_BY、UPDATE_TIME三个字段赋值
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();// invocation 可以获取mapper执行的方法对象，invocation.getArgs()获取mapper方法参数
        // 获取用于描述SQL语句的映射信息
        MappedStatement ms = (MappedStatement) args[0];// args[0] 是获取mapper方法绑定的映射sql命令对象信息
        SqlCommandType sqlCommandType = ms.getSqlCommandType();

        // 获取sql参数实体 ParamMap
        Object parameter = args[1];// args[1]是mapper方法第一个参数（需要补全的实体类对象）

        if (parameter != null && sqlCommandType != null) {
            // 获取用户ID
            Long userId = loadUserId();

            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                // 插入操作
                if (parameter instanceof MapperMethod.ParamMap) {
                    // 批量插入的情况
                    MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameter;
                    ArrayList list = (ArrayList) paramMap.get("list");
                    list.forEach(v -> {
                        // 设置创建人和创建时间字段值
                        setFieldValByName(CREATE_BY, userId, v);
                        setFieldValByName(CREATE_TIME, LocalDateTime.now(), v);
                        setFieldValByName(UPDATE_TIME, LocalDateTime.now(), v);
                    });
                    paramMap.put("list", list);
                } else {
                    // 单条插入的情况
                    // 设置创建人和创建时间字段值
                    setFieldValByName(CREATE_BY, userId, parameter);
                    setFieldValByName(CREATE_TIME, LocalDateTime.now(), parameter);
                    setFieldValByName(UPDATE_TIME, LocalDateTime.now(), parameter);
                }
            } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                // 更新操作
                // 设置更新人和更新时间字段值
                setFieldValByName(UPDATE_BY, userId, parameter);
                setFieldValByName(UPDATE_TIME, LocalDateTime.now(), parameter);
            }
        }

        // 继续执行原始方法
        return invocation.proceed();
    }

    /**
     * 通过反射设置实体的字段值
     * @param fieldName 字段名
     * @param fieldVal  字段值
     * @param parameter 实体对象
     */
    private void setFieldValByName(String fieldName, Object fieldVal, Object parameter) {
        MetaObject metaObject = SystemMetaObject.forObject(parameter);
        if (fieldName.equals(CREATE_BY)) {
            Object value = metaObject.getValue(fieldName);
            if (ObjectUtil.isNotEmpty(value)) {
                return;
            }
        }

        if (metaObject.hasSetter(fieldName)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            // 对目标对象进行包装，返回代理对象
            return Plugin.wrap(target, this);
        }
        // 非 Executor 类型的对象，直接返回原始对象
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        // 读取配置文件中的属性，此处没有使用
    }

    /**
     * 获取当前用户的ID，用于填充创建人和更新人字段的值
     *
     * @return 当前用户ID
     */
    public static Long loadUserId() {
        // 从 ThreadLocal 中获取用户ID
        Long userId = UserThreadLocal.getUserId();
        // 如果 ThreadLocal 中不存在用户ID，则从管理用户ID中获取
        if (ObjectUtil.isNotEmpty(userId)) {
            return userId;
        }
        userId = UserThreadLocal.getMgtUserId();
        // 如果管理用户ID也不存在，则默认返回ID为1的用户
        if (!EmptyUtil.isNullOrEmpty(userId)) {
            return userId;
        }
        return 1L;
    }
}

