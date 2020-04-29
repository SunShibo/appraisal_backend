package com.wisewin.api.system.idempotency;//package com.wisewin.api.system.idempotency;
//
///**
// * 处理中策略
// * cache + status + client control
// *
// * @author sunshibo
// */
//public class InProcessStrategy extends AbstractCacheStrategy {
//
//	public static final String CONTEXT_ATTR_INPROCESSFLAGKEY = "inProcessFlagKey";
//
//	private String inProcessFlagPrefix = "idem:ip:"; // Idempotence:inProcess
//
//	private long processTimeout = 1000 * 10; // 10 second
//
//	@Override
//	public String getName() {
//		return "InProcess";
//	}
//
//	@Override
//	public Object before(InvokeContext invokeContext) throws Throwable {
//		Object returnValue = super.before(invokeContext);
//		if (returnValue != null) {
//			return returnValue;
//		}
//
//		String xid = invokeContext.getXid();
//		String inProcessFlagKey = getInProcessFlagKey(xid);
//
//		Idempotent idempotence = invokeContext.getInvokeMeta().getIdempotenceAnnotation();
//		long _processTimeout = (idempotence != null ? idempotence.timeout() : processTimeout);
//		if (cache.setnx(inProcessFlagKey, "1", _processTimeout) == 0) {
//			throw new InProcessException();
//		}
//		invokeContext.setAttr(CONTEXT_ATTR_INPROCESSFLAGKEY, inProcessFlagKey); // lock acquired
//		logger.debug("lock acquired {}", inProcessFlagKey);
//
//		returnValue = super.before(invokeContext);
//
//		return returnValue;
//	}
//
//	@Override
//	public void after(InvokeContext invokeContext) {
//		String inProcessFlagKey = (String) invokeContext.getAttr(CONTEXT_ATTR_INPROCESSFLAGKEY);
//		if (inProcessFlagKey != null) {
//			cache.del(inProcessFlagKey);
//			logger.debug("release lock {}", inProcessFlagKey);
//		}
//	}
//
//	protected String getInProcessFlagKey(String xid) {
//		return inProcessFlagPrefix + xid;
//	}
//
//	public String getInProcessFlagPrefix() {
//		return inProcessFlagPrefix;
//	}
//
//	public void setInProcessFlagPrefix(String inProcessFlagPrefix) {
//		this.inProcessFlagPrefix = inProcessFlagPrefix;
//	}
//
//	public long getProcessTimeout() {
//		return processTimeout;
//	}
//
//	public void setProcessTimeout(long processTimeout) {
//		this.processTimeout = processTimeout;
//	}
//
//	public static class InProcessException extends RuntimeException {
//		public InProcessException() {
//			super("request in process");
//		}
//	}
//}