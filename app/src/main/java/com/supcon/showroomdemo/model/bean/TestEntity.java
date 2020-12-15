package com.supcon.showroomdemo.model.bean;

import java.util.List;

/**
 * @author : yaobing
 * @date : 2020/12/15 11:15
 * @desc :
 */
public class TestEntity {

    /**
     * companies : [{"code":"default_org_company","id":1000,"name":"默认公司"}]
     * currentCompany : {"code":"default_org_company","id":1000,"name":"默认公司"}
     * ticket : 3fc2d17ca71143b29971d8f36db6f2d9
     * user : {"id":877126527181584,"userName":"dyh","userType":0}
     */

    private CurrentCompanyBean currentCompany;
    private String ticket;
    private UserBean user;
    private List<CompaniesBean> companies;

    public CurrentCompanyBean getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(CurrentCompanyBean currentCompany) {
        this.currentCompany = currentCompany;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<CompaniesBean> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompaniesBean> companies) {
        this.companies = companies;
    }

    public static class CurrentCompanyBean {
        /**
         * code : default_org_company
         * id : 1000
         * name : 默认公司
         */

        private String code;
        private int id;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class UserBean {
        /**
         * id : 877126527181584
         * userName : dyh
         * userType : 0
         */

        private long id;
        private String userName;
        private int userType;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }

    public static class CompaniesBean {
        /**
         * code : default_org_company
         * id : 1000
         * name : 默认公司
         */

        private String code;
        private int id;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
