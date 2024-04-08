package com.example.oldpeoplehome.anno;

import com.example.oldpeoplehome.validation.GenderValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented//元注解
@Target({ElementType.FIELD})//元注解，表示这个注解用在什么东西上，是属性上，还是方法上，还是什么别的东西上
@Retention(RetentionPolicy.RUNTIME)//元注解，标识注解被保留在哪个阶段
@Constraint(
        validatedBy = {GenderValidation.class}//指定提供校验规则的类
)//指定谁给注解提供校验的规则
public @interface Gender {
    //提供校验失败后的提示信息
    String message() default "gender参数的值只能是”Male“或”Female“";

    //指定分组
    Class<?>[] groups() default {};

    //负载，可以获取到State注解的附加信息
    //这个一般用不到
    Class<? extends Payload>[] payload() default {};
}
