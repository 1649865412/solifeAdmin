package com.cartmatic.estore.core.view;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.core.model.JFieldError;

/**
 * 继承ModelAndView 是为了处理方便
 * 利用wasCleared方法做实际输出处理
 * @author kedou
 *
 */
public class AjaxView extends ModelAndView{
	private ServletResponse response;
	private AjaxView(){};
	
	public AjaxView(ServletResponse response){
		this.response=response;
	};
	
	/**
	 * 返回结果
	 * 默认情况下
	 * 1表示成功
	 * 0表示失败
	 */
	private short status=1;
	/**
	 * 提示信息
	 */
	private String msg="";
	
	/**
	 * 错误字段提示
	 */
	private List<JFieldError> filedErrors;
	
	/**
	 * 返回数据
	 */
	private Object data;

	public short getStatus() {
		return status;
	}

	public AjaxView setStatus(short status) {
		this.status = status;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public AjaxView setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getData() {
		return data;
	}

	public List<JFieldError> getFiledErrors() {
		return filedErrors;
	}

	public void setFiledErrors(List<JFieldError> filedErrors) {
		this.filedErrors = filedErrors;
	}

	public AjaxView setData(Object data) {
		this.data = data;
		return this;
	}
	
	public String toJSONString(){
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"model", "modelMap","reference","view","viewName","empty"});
		JSON jsonObject = JSONSerializer.toJSON(this,jsonConfig);
		return jsonObject.toString();
	}
	
	@Override
	public boolean hasView() {
		return true;
	}

	private boolean wasWritten=false;

	@Override
	public boolean wasCleared() {
		if(!wasWritten){
			try {
				response.setContentType("application/json;charset=UTF-8");   
		        response.setCharacterEncoding("utf-8");
				response.getWriter().println(toJSONString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			wasWritten=true;
		}
		return true;
	}
	
}