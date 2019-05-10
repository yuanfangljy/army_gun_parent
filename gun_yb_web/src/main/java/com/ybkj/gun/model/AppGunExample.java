package com.ybkj.gun.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppGunExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppGunExample() {
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

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(Integer value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(Integer value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(Integer value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(Integer value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(Integer value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<Integer> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<Integer> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(Integer value1, Integer value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(Integer value1, Integer value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andGunIdIsNull() {
            addCriterion("gun_id is null");
            return (Criteria) this;
        }

        public Criteria andGunIdIsNotNull() {
            addCriterion("gun_id is not null");
            return (Criteria) this;
        }

        public Criteria andGunIdEqualTo(String value) {
            addCriterion("gun_id =", value, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdNotEqualTo(String value) {
            addCriterion("gun_id <>", value, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdGreaterThan(String value) {
            addCriterion("gun_id >", value, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdGreaterThanOrEqualTo(String value) {
            addCriterion("gun_id >=", value, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdLessThan(String value) {
            addCriterion("gun_id <", value, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdLessThanOrEqualTo(String value) {
            addCriterion("gun_id <=", value, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdLike(String value) {
            addCriterion("gun_id like", value, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdNotLike(String value) {
            addCriterion("gun_id not like", value, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdIn(List<String> values) {
            addCriterion("gun_id in", values, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdNotIn(List<String> values) {
            addCriterion("gun_id not in", values, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdBetween(String value1, String value2) {
            addCriterion("gun_id between", value1, value2, "gunId");
            return (Criteria) this;
        }

        public Criteria andGunIdNotBetween(String value1, String value2) {
            addCriterion("gun_id not between", value1, value2, "gunId");
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

        public Criteria andAllotStateIsNull() {
            addCriterion("allot_state is null");
            return (Criteria) this;
        }

        public Criteria andAllotStateIsNotNull() {
            addCriterion("allot_state is not null");
            return (Criteria) this;
        }

        public Criteria andAllotStateEqualTo(Integer value) {
            addCriterion("allot_state =", value, "allotState");
            return (Criteria) this;
        }

        public Criteria andAllotStateNotEqualTo(Integer value) {
            addCriterion("allot_state <>", value, "allotState");
            return (Criteria) this;
        }

        public Criteria andAllotStateGreaterThan(Integer value) {
            addCriterion("allot_state >", value, "allotState");
            return (Criteria) this;
        }

        public Criteria andAllotStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("allot_state >=", value, "allotState");
            return (Criteria) this;
        }

        public Criteria andAllotStateLessThan(Integer value) {
            addCriterion("allot_state <", value, "allotState");
            return (Criteria) this;
        }

        public Criteria andAllotStateLessThanOrEqualTo(Integer value) {
            addCriterion("allot_state <=", value, "allotState");
            return (Criteria) this;
        }

        public Criteria andAllotStateIn(List<Integer> values) {
            addCriterion("allot_state in", values, "allotState");
            return (Criteria) this;
        }

        public Criteria andAllotStateNotIn(List<Integer> values) {
            addCriterion("allot_state not in", values, "allotState");
            return (Criteria) this;
        }

        public Criteria andAllotStateBetween(Integer value1, Integer value2) {
            addCriterion("allot_state between", value1, value2, "allotState");
            return (Criteria) this;
        }

        public Criteria andAllotStateNotBetween(Integer value1, Integer value2) {
            addCriterion("allot_state not between", value1, value2, "allotState");
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