package io.tsdb.services.framework.base.shiro.modules;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author pablo.biagioli
 */
public final class ShiroMethodInterceptor implements MethodInterceptor {
    private org.apache.shiro.aop.MethodInterceptor methodInterceptor;

    /**
     * A method Interceptor to process the Shiro annotations.
     *
     * @param providedMethodInterceptor the methodinterceptor
     */
    ShiroMethodInterceptor(final org.apache.shiro.aop.MethodInterceptor providedMethodInterceptor) {
        this.methodInterceptor = providedMethodInterceptor;
    }

    @Override
    public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
        return methodInterceptor.invoke(new ShiroMethodInvocation(methodInvocation));
    }

    /**
     * ShiroMethodInvocation.
     */
    private static class ShiroMethodInvocation implements org.apache.shiro.aop.MethodInvocation {

        private final MethodInvocation methodInvocation;

        /**
         * A method invocation.
         *
         * @param providedMethodInvocation the provided one
         */
        ShiroMethodInvocation(final MethodInvocation providedMethodInvocation) {
            this.methodInvocation = providedMethodInvocation;
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
