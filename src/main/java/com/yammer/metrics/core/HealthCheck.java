// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

public abstract class HealthCheck
{
    private final String name;
    
    protected HealthCheck(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    protected abstract Result check() throws Exception;
    
    public Result execute() {
        try {
            return this.check();
        }
        catch (Error e) {
            throw e;
        }
        catch (Throwable e2) {
            return Result.unhealthy(e2);
        }
    }
    
    public static class Result
    {
        private static final Result HEALTHY;
        private static final int PRIME = 31;
        private final boolean healthy;
        private final String message;
        private final Throwable error;
        
        public static Result healthy() {
            return Result.HEALTHY;
        }
        
        public static Result healthy(final String message) {
            return new Result(true, message, null);
        }
        
        public static Result healthy(final String message, final Object... args) {
            return healthy(String.format(message, args));
        }
        
        public static Result unhealthy(final String message) {
            return new Result(false, message, null);
        }
        
        public static Result unhealthy(final String message, final Object... args) {
            return unhealthy(String.format(message, args));
        }
        
        public static Result unhealthy(final Throwable error) {
            return new Result(false, error.getMessage(), error);
        }
        
        private Result(final boolean isHealthy, final String message, final Throwable error) {
            this.healthy = isHealthy;
            this.message = message;
            this.error = error;
        }
        
        public boolean isHealthy() {
            return this.healthy;
        }
        
        public String getMessage() {
            return this.message;
        }
        
        public Throwable getError() {
            return this.error;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final Result result = (Result)o;
            if (this.healthy == result.healthy) {
                if (this.error != null) {
                    if (!this.error.equals(result.error)) {
                        return false;
                    }
                }
                else if (result.error != null) {
                    return false;
                }
                if (this.message != null) {
                    if (!this.message.equals(result.message)) {
                        return false;
                    }
                }
                else if (result.message != null) {
                    return false;
                }
                return true;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            int result = this.healthy ? 1 : 0;
            result = 31 * result + ((this.message != null) ? this.message.hashCode() : 0);
            result = 31 * result + ((this.error != null) ? this.error.hashCode() : 0);
            return result;
        }
        
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder("Result{isHealthy=");
            builder.append(this.healthy);
            if (this.message != null) {
                builder.append(", message=").append(this.message);
            }
            if (this.error != null) {
                builder.append(", error=").append(this.error);
            }
            builder.append('}');
            return builder.toString();
        }
        
        static {
            HEALTHY = new Result(true, null, null);
        }
    }
}
