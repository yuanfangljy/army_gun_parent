package com.ybkj.gun.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WebUserExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andWebUserNameIsNull() {
            addCriterion("web_user_name is null");
            return (Criteria) this;
        }

        public Criteria andWebUserNameIsNotNull() {
            addCriterion("web_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andWebUserNameEqualTo(String value) {
            addCriterion("web_user_name =", value, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameNotEqualTo(String value) {
            addCriterion("web_user_name <>", value, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameGreaterThan(String value) {
            addCriterion("web_user_name >", value, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("web_user_name >=", value, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameLessThan(String value) {
            addCriterion("web_user_name <", value, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameLessThanOrEqualTo(String value) {
            addCriterion("web_user_name <=", value, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameLike(String value) {
            addCriterion("web_user_name like", value, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameNotLike(String value) {
            addCriterion("web_user_name not like", value, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameIn(List<String> values) {
            addCriterion("web_user_name in", values, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameNotIn(List<String> values) {
            addCriterion("web_user_name not in", values, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameBetween(String value1, String value2) {
            addCriterion("web_user_name between", value1, value2, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserNameNotBetween(String value1, String value2) {
            addCriterion("web_user_name not between", value1, value2, "webUserName");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordIsNull() {
            addCriterion("web_user_password is null");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordIsNotNull() {
            addCriterion("web_user_password is not null");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordEqualTo(String value) {
            addCriterion("web_user_password =", value, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordNotEqualTo(String value) {
            addCriterion("web_user_password <>", value, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordGreaterThan(String value) {
            addCriterion("web_user_password >", value, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("web_user_password >=", value, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordLessThan(String value) {
            addCriterion("web_user_password <", value, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordLessThanOrEqualTo(String value) {
            addCriterion("web_user_password <=", value, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordLike(String value) {
            addCriterion("web_user_password like", value, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordNotLike(String value) {
            addCriterion("web_user_password not like", value, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordIn(List<String> values) {
            addCriterion("web_user_password in", values, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordNotIn(List<String> values) {
            addCriterion("web_user_password not in", values, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordBetween(String value1, String value2) {
            addCriterion("web_user_password between", value1, value2, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPasswordNotBetween(String value1, String value2) {
            addCriterion("web_user_password not between", value1, value2, "webUserPassword");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneIsNull() {
            addCriterion("web_user_phone is null");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneIsNotNull() {
            addCriterion("web_user_phone is not null");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneEqualTo(String value) {
            addCriterion("web_user_phone =", value, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneNotEqualTo(String value) {
            addCriterion("web_user_phone <>", value, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneGreaterThan(String value) {
            addCriterion("web_user_phone >", value, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("web_user_phone >=", value, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneLessThan(String value) {
            addCriterion("web_user_phone <", value, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneLessThanOrEqualTo(String value) {
            addCriterion("web_user_phone <=", value, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneLike(String value) {
            addCriterion("web_user_phone like", value, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneNotLike(String value) {
            addCriterion("web_user_phone not like", value, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneIn(List<String> values) {
            addCriterion("web_user_phone in", values, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneNotIn(List<String> values) {
            addCriterion("web_user_phone not in", values, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneBetween(String value1, String value2) {
            addCriterion("web_user_phone between", value1, value2, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andWebUserPhoneNotBetween(String value1, String value2) {
            addCriterion("web_user_phone not between", value1, value2, "webUserPhone");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNull() {
            addCriterion("department_id is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNotNull() {
            addCriterion("department_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdEqualTo(Integer value) {
            addCriterion("department_id =", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotEqualTo(Integer value) {
            addCriterion("department_id <>", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThan(Integer value) {
            addCriterion("department_id >", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("department_id >=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThan(Integer value) {
            addCriterion("department_id <", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("department_id <=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIn(List<Integer> values) {
            addCriterion("department_id in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotIn(List<Integer> values) {
            addCriterion("department_id not in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("department_id between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("department_id not between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNull() {
            addCriterion("is_del is null");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNotNull() {
            addCriterion("is_del is not null");
            return (Criteria) this;
        }

        public Criteria andIsDelEqualTo(Boolean value) {
            addCriterion("is_del =", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotEqualTo(Boolean value) {
            addCriterion("is_del <>", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThan(Boolean value) {
            addCriterion("is_del >", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_del >=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThan(Boolean value) {
            addCriterion("is_del <", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThanOrEqualTo(Boolean value) {
            addCriterion("is_del <=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelIn(List<Boolean> values) {
            addCriterion("is_del in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotIn(List<Boolean> values) {
            addCriterion("is_del not in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelBetween(Boolean value1, Boolean value2) {
            addCriterion("is_del between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_del not between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsJobIsNull() {
            addCriterion("is_job is null");
            return (Criteria) this;
        }

        public Criteria andIsJobIsNotNull() {
            addCriterion("is_job is not null");
            return (Criteria) this;
        }

        public Criteria andIsJobEqualTo(Boolean value) {
            addCriterion("is_job =", value, "isJob");
            return (Criteria) this;
        }

        public Criteria andIsJobNotEqualTo(Boolean value) {
            addCriterion("is_job <>", value, "isJob");
            return (Criteria) this;
        }

        public Criteria andIsJobGreaterThan(Boolean value) {
            addCriterion("is_job >", value, "isJob");
            return (Criteria) this;
        }

        public Criteria andIsJobGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_job >=", value, "isJob");
            return (Criteria) this;
        }

        public Criteria andIsJobLessThan(Boolean value) {
            addCriterion("is_job <", value, "isJob");
            return (Criteria) this;
        }

        public Criteria andIsJobLessThanOrEqualTo(Boolean value) {
            addCriterion("is_job <=", value, "isJob");
            return (Criteria) this;
        }

        public Criteria andIsJobIn(List<Boolean> values) {
            addCriterion("is_job in", values, "isJob");
            return (Criteria) this;
        }

        public Criteria andIsJobNotIn(List<Boolean> values) {
            addCriterion("is_job not in", values, "isJob");
            return (Criteria) this;
        }

        public Criteria andIsJobBetween(Boolean value1, Boolean value2) {
            addCriterion("is_job between", value1, value2, "isJob");
            return (Criteria) this;
        }

        public Criteria andIsJobNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_job not between", value1, value2, "isJob");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Integer value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Integer value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Integer value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Integer value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Integer value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Integer> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Integer> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Integer value1, Integer value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Integer value1, Integer value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}