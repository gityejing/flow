package com.maven.flow.util;


public class Sequence{
        
    private String sequenceName = null;
    private long sequenceValue = -1;//���ݿ��ж�Ӧ��ֵ
    private int counter = 0;
    private int step = 0;
    
    public void setSequenceValue(long sequenceValue){
        this.sequenceValue = sequenceValue;
    }
    
    public long getSequenceValue(){
        return this.sequenceValue;
    }
    
    public void setSequenceName(String sequenceName){
        this.sequenceName = sequenceName;
    }
    
    public String getSequenceName(){
        return this.sequenceName;
    }
    
    public void setStep(int step){
        this.step = step;
    }
    
    //��ȡ��һ��ֵ 
    public long getNextValue(){
        this.counter++;
        return this.sequenceValue+this.counter;
    }
    
    protected boolean shouldStep(){
        return this.counter >= this.step;
    }
    
    protected void step(){
        this.sequenceValue += step;
        this.counter = 0;
    }
}