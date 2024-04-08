package com.example.oldpeoplehome.validation;

import com.example.oldpeoplehome.anno.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidation implements ConstraintValidator<Gender,String> {//这里填写的两个泛型，1：给哪个注解提供校验规则 2：校验数据的类型

    /**
     *
     * @param s 将来要校验的数据
     * @param constraintValidatorContext
     * @return 如果返回false则校验不通过，返回true则校验通过
     */

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //提供校验规则
        if(s == null){
            return false;
        }
        if(s.equals("Male") || s.equals("Female")){
            return true;
        }
        return false;
    }
}