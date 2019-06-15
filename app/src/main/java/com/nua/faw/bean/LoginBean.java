package com.nua.faw.bean;

/**
 * 作者：yuhb on 2018/2/28 11:07.
 * 描述：
 */

public class LoginBean {

    private String token;
    private String userId;
    private String mobile;
    private String nickName;
    private String realName;
    private String sex;
    private String headImage;
    private String signature;
    private String passwordFlag;

    //第三登录多的字段
    private int loginStatus;
    private String headUrl;
    private String internalTest;


    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getInternalTest() {
        return internalTest;
    }

    public void setInternalTest(String internalTest) {
        this.internalTest = internalTest;
    }

    public String getPasswordFlag() {
        return passwordFlag;
    }

    public void setPasswordFlag(String passwordFlag) {
        this.passwordFlag = passwordFlag;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    //    private LoginDetail detail;
//
//    public LoginDetail getDetail() {
//        return detail;
//    }
//
//    public void setDetail(LoginDetail detail) {
//        this.detail = detail;
//    }
//
//    public static class LoginDetail {
//        private String token;
//        private String userId;
//        private String mobile;
//        private String nickName;
//        private String realName;
//        private String sex;
//        private String headImage;
//
//        public String getToken() {
//            return token;
//        }
//
//        public void setToken(String token) {
//            this.token = token;
//        }
//
//        public String getUserId() {
//            return userId;
//        }
//
//        public void setUserId(String userId) {
//            this.userId = userId;
//        }
//
//        public String getMobile() {
//            return mobile;
//        }
//
//        public void setMobile(String mobile) {
//            this.mobile = mobile;
//        }
//
//        public String getNickName() {
//            return nickName;
//        }
//
//        public void setNickName(String nickName) {
//            this.nickName = nickName;
//        }
//
//        public String getRealName() {
//            return realName;
//        }
//
//        public void setRealName(String realName) {
//            this.realName = realName;
//        }
//
//        public String getSex() {
//            return sex;
//        }
//
//        public void setSex(String sex) {
//            this.sex = sex;
//        }
//
//        public String getHeadImage() {
//            return headImage;
//        }
//
//        public void setHeadImage(String headImage) {
//            this.headImage = headImage;
//        }
//    }
}
