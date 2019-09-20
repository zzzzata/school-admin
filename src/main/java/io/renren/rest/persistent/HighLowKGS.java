package io.renren.rest.persistent;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * KGS server
 * date: 12-8-14 上午11:46
 *
 * @author: wubinjie@ak.cc
 */
public class HighLowKGS extends SimpleKGS {

    public void setStep(int step) {
        if(step >  1)
            this.step = step;
    }
    private int step =  10;

    volatile  int setpMaxValue;

    final AtomicInteger idGenerator = new AtomicInteger();

    public void setPrettys(BitSet prettys) {
        this.prettys = prettys;
    }

    private BitSet prettys ;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        resetGenerator();
    }

    protected void resetGenerator(){
        int current = idGenerator.get();
        int high = 0;
        while (high <= current){
            high = nextId.getAndAdd(step);
        }
        if(idGenerator.compareAndSet(current,high)){
            setpMaxValue = high + step;
        }
    }



    public int nextId(){
        int i = next();
        if(prettys  == null ){
            return i;
        }

        if(! prettys.get(i) ){
            return i;
        }

        int delta = prettys.nextClearBit(i) - i;

        if(delta>1){
           idGenerator.compareAndSet(i,i + delta -1);
        }

        return nextId();
    }


    private int next(){
        int next  = idGenerator.incrementAndGet();
        if(next >= setpMaxValue){
            synchronized (idGenerator){
                if(idGenerator.get()>= setpMaxValue){
                    resetGenerator();
                }
                return next();
            }
        }
        //java long 操作非原子性,修正为使用 AtomicLong
        return next;
    }

}
