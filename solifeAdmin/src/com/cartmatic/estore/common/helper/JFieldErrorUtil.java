package com.cartmatic.estore.common.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.util.I18nUtil;

/**
 * 用于读取validation验证信息，将错误信息封装为JFieldError，转换为JSON输出
 * @author kedou
 *
 *Controller指定错误信息
 *1.
 *errors.rejectValue("productSkuCode", msgKey);
 *errors.rejectValue("错误属性", 错误信息Key);
 *
 *2.以DefaultMessage指定错误属性
 *errors.reject(msgKey,"mainCategoryId");
 *errors.reject(错误信息Key,"错误属性");
 *
 */
public class JFieldErrorUtil {
	/**
	 * @param errors
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<JFieldError> getFiledErrors(BindException errors){
		List<JFieldError>jFiledErrors=new ArrayList<JFieldError>();
		if(errors.hasErrors()){
			List<ObjectError> ObjectErrors=errors.getAllErrors();
			for (ObjectError objectError : ObjectErrors) {
				JFieldError jFieldError=new JFieldError(); 
				String message="";
				if(objectError instanceof FieldError){
					FieldError fieldError=(FieldError)objectError;
					jFieldError.setObjectName(fieldError.getObjectName());
					jFieldError.setField(fieldError.getField());
					String key=fieldError.getCodes()[3];
					jFieldError.setKey(key);
					if(StringUtils.isNotBlank(key)){
						String fieldName=I18nUtil.getInstance().getMessage(fieldError.getObjectName()+"."+fieldError.getField());
						message=I18nUtil.getInstance().getMessage(key,new Object[]{fieldName});
					}
					if(StringUtils.isBlank(message)&&StringUtils.isNotBlank(fieldError.getDefaultMessage())){
						message=fieldError.getDefaultMessage();
					}
				}else{
					jFieldError.setField(objectError.getDefaultMessage());
					message=I18nUtil.getInstance().getMessage(objectError.getCodes()[1]);
				}
				jFieldError.setMessage(message);
				jFiledErrors.add(jFieldError);
			}
		}
		return jFiledErrors;
	}
}
