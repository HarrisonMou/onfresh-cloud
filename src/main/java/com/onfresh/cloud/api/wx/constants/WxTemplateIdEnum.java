package com.onfresh.cloud.api.wx.constants;

public enum WxTemplateIdEnum {
    ORDER_COST_MONEY_SPLIT_ADVICE("订单金额拆分完成", "qcO70b4X_hVr4UHsP29NQFKHUoJp1ZKmsKjfihB49FA");
    private String name;
    private String templateId;

    WxTemplateIdEnum(String name, String templateId) {
        this.name = name;
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public String getTemplateId() {
        return templateId;
    }
}
