package com.design.pattern.builder.demo04;

/**
 * 实体类 包含一个静态内部类 Builder
 */
public class CompanyClient {
    private final String companyName;
    private final String companyAddress;

    private final double companyRegfunds;
    private final String mPerson;
    private final String mType;

    //构造方法

    /**
     * 私有的构造方法，不能使用new创建对象
     */
    private CompanyClient() {
        this(new Builder());
    }

    //构造方法
    private CompanyClient(Builder builder) {
        this.companyName = builder.companyName;
        this.companyAddress = builder.companyAddress;
        this.companyRegfunds = builder.companyRegfunds;
        this.mPerson = builder.person;
        this.mType = builder.type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public double getCompanyRegfunds() {
        return companyRegfunds;
    }

    public String getmPerson() {
        return mPerson;
    }

    public String getmType() {
        return mType;
    }


    public Builder newBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        return "CompanyClient{" +
                "companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyRegfunds=" + companyRegfunds + "千万" +
                ", mPerson=" + mPerson +
                ", mType='" + mType + '\'' +
                '}';
    }

    /**
     * 静态内部类 Builder
     */

    public static class Builder {
        private String companyName;
        private String companyAddress;
        private double companyRegfunds;
        private String person;
        private String type;

        //构造方法
        public Builder() {
            companyName = companyName;
            companyAddress = companyAddress;
            companyRegfunds = companyRegfunds;
            person = person;
            type = type;

        }

        //构造方法
        Builder(CompanyClient companyClient) {
            this.companyName = companyClient.companyName;
            this.companyAddress = companyClient.companyAddress;
            this.companyRegfunds = companyClient.companyRegfunds;
            this.person = companyClient.mPerson;
            this.type = companyClient.mType;
        }

        public Builder setCompanyName(String name) {
            companyName = name;
            return this;
        }

        public Builder setCompanyAddress(String address) {
            companyAddress = address;
            return this;
        }

        public Builder setCompanyRegfunds(double regfunds) {
            companyRegfunds = regfunds;
            return this;
        }

        public Builder setmPerson(String per) {
            person = per;
            return this;
        }

        public Builder setmType(String typeStr) {
            type = typeStr;
            return this;
        }

        //构建一个实体
        public CompanyClient build() {
            return new CompanyClient(this);
        }
    }
}