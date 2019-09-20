package io.renren.rest.persistent;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;


/**
 * KGS server
 * date: 12-8-14 上午11:46
 *
 * @author: wubinjie@ak.cc
 */
public class SimpleKGS implements InitializingBean, KGS {

    protected String nameSpace;
    protected StringRedisTemplate kgsRedis;
    protected int offset = 0;

    public void setNameSpace(String nameSpace) {
        this.nameSpace = "kgs:"+nameSpace;
    }

    public void setKgsRedis(StringRedisTemplate kgsRedis) {
        this.kgsRedis = kgsRedis;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    protected RedisAtomicInteger nextId;
    @Override
    public void afterPropertiesSet() throws Exception {
        nextId = new RedisAtomicInteger(nameSpace + ":nextId",kgsRedis.getConnectionFactory());
        if(nextId.get() < offset){
            nextId.set(offset);
        }
    }

    public int nextId(){
        return nextId.incrementAndGet();
    }

    @Override
    public Integer nextId(String nameSpace) {
        throw new UnsupportedOperationException("nameSpace is not support");
    }
}
