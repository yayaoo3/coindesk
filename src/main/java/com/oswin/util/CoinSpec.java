package com.oswin.util;

public enum CoinSpec {
    Bitcoin(
            "Bitcoin",
            "比特幣",
            "Bitcoin description"
    ),
    TestId(
            "TestId",
            "測試幣",
            "Test description"
    );


    private final String code;
    private final String chineseName;
    private final String description;

    CoinSpec(String code, String chineseName, String description) {
        this.description = description;
        this.chineseName = chineseName;
        this.code = code;
    }


    public String getCode() {
        return code;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getDescription() {
        return description;
    }
}
