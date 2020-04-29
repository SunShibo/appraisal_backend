package com.wisewin.api.system.idempotency.invoke;

import java.util.HashMap;
import java.util.Map;

/**
 * 方法执行上下文
 * 
 * @author sunshibo
 */
public class InvokeContext {
	
	private InvokeMeta invokeMeta;
	
	private Object target;
	
	private Object[] args;
	
	private String xid;
	
	private Map<String, Object> attrs = new HashMap<String, Object>();
	
	public void setAttr(String name, Object value) {
		attrs.put(name, value);
	}
	
	public Object getAttr(String name) {
		return attrs.get(name);
	}

	public InvokeMeta getInvokeMeta() {
		return invokeMeta;
	}

	public void setInvokeMeta(InvokeMeta invokeMeta) {
		this.invokeMeta = invokeMeta;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getXid() {
		return xid;
	}

	public void setXid(String xid) {
		this.xid = xid;
	}
}