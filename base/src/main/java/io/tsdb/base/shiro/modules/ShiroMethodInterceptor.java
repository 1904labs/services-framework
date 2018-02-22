package io.tsdb.base.shiro.modules;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author pablo.biagioli
 */
public class ShiroMethodInterceptor implements MethodInterceptor {
    static final org.slf4j.Logger logger = LoggerFactory.getLogger(ShiroMethodInterceptor.class.getName());

    private org.apache.shiro.aop.MethodInterceptor methodInterceptor;

    public ShiroMethodInterceptor(org.apache.shiro.aop.MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return methodInterceptor.invoke(new ShiroMethodInvocation(methodInvocation));
    }

    private static class ShiroMethodInvocation implements org.apache.shiro.aop.MethodInvocation {

        private final MethodInvocation methodInvocation;

        public ShiroMethodInvocation(MethodInvocation methodInvocation) {
            this.methodInvocation = methodInvocation;
        }

        @Override
        public Object proceed() throws Throwable {
            return methodInvocation.proceed();
        }

        @Override
        public Method getMethod() {
            return methodInvocation.getMethod();
        }

        @Override
        public Object[] getArguments() {
            return methodInvocation.getArguments();
        }

        @Override
        public Object getThis() {
            return methodInvocation.getThis();
        }
    }

}
