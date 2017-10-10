package com.icloud.model.demand;

public enum DemandStatus {
	newPublish {public String getName(){return "待响应";}},
    /** 待审核 */
    response {public String getName(){return "需求确认";}},
    
    confirm {public String getName(){return "订单确认";}},
    
    ongoing {public String getName(){return "订单进行中";}},
   
    /** 正在配货 */
    ADMEASUREPRODUCT {public String getName(){return "正在配货";}},
    
    /** 等待付款 */
    PAYMENT {public String getName(){return "等待付款";}},
    
    /** 完结 */
    end {public String getName(){return "已完成";}},
    /** 终止中 */
    terminationing {public String getName(){return "终止申请中";}},
    /** 已终止 */
    terminationed {public String getName(){return "已终止";}};
    
    public abstract String getName();
    
    
}
