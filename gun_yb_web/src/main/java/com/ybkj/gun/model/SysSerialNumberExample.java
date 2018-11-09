package com.ybkj.gun.model;

import java.util.ArrayList;
import java.util.List;

public class SysSerialNumberExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysSerialNumberExample() {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andModuleNameIsNull() {
            addCriterion("module_name is null");
            return (Criteria) this;
        }

        public Criteria andModuleNameIsNotNull() {
            addCriterion("module_name is not null");
            return (Criteria) this;
        }

        public Criteria andModuleNameEqualTo(String value) {
            addCriterion("module_name =", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameNotEqualTo(String value) {
            addCriterion("module_name <>", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameGreaterThan(String value) {
            addCriterion("module_name >", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameGreaterThanOrEqualTo(String value) {
            addCriterion("module_name >=", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameLessThan(String value) {
            addCriterion("module_name <", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameLessThanOrEqualTo(String value) {
            addCriterion("module_name <=", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameLike(String value) {
            addCriterion("module_name like", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameNotLike(String value) {
            addCriterion("module_name not like", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameIn(List<String> values) {
            addCriterion("module_name in", values, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameNotIn(List<String> values) {
            addCriterion("module_name not in", values, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameBetween(String value1, String value2) {
            addCriterion("module_name between", value1, value2, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameNotBetween(String value1, String value2) {
            addCriterion("module_name not between", value1, value2, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleCodeIsNull() {
            addCriterion("module_code is null");
            return (Criteria) this;
        }

        public Criteria andModuleCodeIsNotNull() {
            addCriterion("module_code is not null");
            return (Criteria) this;
        }

        public Criteria andModuleCodeEqualTo(String value) {
            addCriterion("module_code =", value, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeNotEqualTo(String value) {
            addCriterion("module_code <>", value, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeGreaterThan(String value) {
            addCriterion("module_code >", value, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeGreaterThanOrEqualTo(String value) {
            addCriterion("module_code >=", value, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeLessThan(String value) {
            addCriterion("module_code <", value, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeLessThanOrEqualTo(String value) {
            addCriterion("module_code <=", value, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeLike(String value) {
            addCriterion("module_code like", value, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeNotLike(String value) {
            addCriterion("module_code not like", value, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeIn(List<String> values) {
            addCriterion("module_code in", values, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeNotIn(List<String> values) {
            addCriterion("module_code not in", values, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeBetween(String value1, String value2) {
            addCriterion("module_code between", value1, value2, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andModuleCodeNotBetween(String value1, String value2) {
            addCriterion("module_code not between", value1, value2, "moduleCode");
            return (Criteria) this;
        }

        public Criteria andConfigTempletIsNull() {
            addCriterion("config_templet is null");
            return (Criteria) this;
        }

        public Criteria andConfigTempletIsNotNull() {
            addCriterion("config_templet is not null");
            return (Criteria) this;
        }

        public Criteria andConfigTempletEqualTo(String value) {
            addCriterion("config_templet =", value, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletNotEqualTo(String value) {
            addCriterion("config_templet <>", value, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletGreaterThan(String value) {
            addCriterion("config_templet >", value, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletGreaterThanOrEqualTo(String value) {
            addCriterion("config_templet >=", value, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletLessThan(String value) {
            addCriterion("config_templet <", value, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletLessThanOrEqualTo(String value) {
            addCriterion("config_templet <=", value, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletLike(String value) {
            addCriterion("config_templet like", value, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletNotLike(String value) {
            addCriterion("config_templet not like", value, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletIn(List<String> values) {
            addCriterion("config_templet in", values, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletNotIn(List<String> values) {
            addCriterion("config_templet not in", values, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletBetween(String value1, String value2) {
            addCriterion("config_templet between", value1, value2, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andConfigTempletNotBetween(String value1, String value2) {
            addCriterion("config_templet not between", value1, value2, "configTemplet");
            return (Criteria) this;
        }

        public Criteria andMaxSerialIsNull() {
            addCriterion("max_serial is null");
            return (Criteria) this;
        }

        public Criteria andMaxSerialIsNotNull() {
            addCriterion("max_serial is not null");
            return (Criteria) this;
        }

        public Criteria andMaxSerialEqualTo(String value) {
            addCriterion("max_serial =", value, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialNotEqualTo(String value) {
            addCriterion("max_serial <>", value, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialGreaterThan(String value) {
            addCriterion("max_serial >", value, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialGreaterThanOrEqualTo(String value) {
            addCriterion("max_serial >=", value, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialLessThan(String value) {
            addCriterion("max_serial <", value, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialLessThanOrEqualTo(String value) {
            addCriterion("max_serial <=", value, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialLike(String value) {
            addCriterion("max_serial like", value, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialNotLike(String value) {
            addCriterion("max_serial not like", value, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialIn(List<String> values) {
            addCriterion("max_serial in", values, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialNotIn(List<String> values) {
            addCriterion("max_serial not in", values, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialBetween(String value1, String value2) {
            addCriterion("max_serial between", value1, value2, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andMaxSerialNotBetween(String value1, String value2) {
            addCriterion("max_serial not between", value1, value2, "maxSerial");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumIsNull() {
            addCriterion("pre_max_num is null");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumIsNotNull() {
            addCriterion("pre_max_num is not null");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumEqualTo(String value) {
            addCriterion("pre_max_num =", value, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumNotEqualTo(String value) {
            addCriterion("pre_max_num <>", value, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumGreaterThan(String value) {
            addCriterion("pre_max_num >", value, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumGreaterThanOrEqualTo(String value) {
            addCriterion("pre_max_num >=", value, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumLessThan(String value) {
            addCriterion("pre_max_num <", value, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumLessThanOrEqualTo(String value) {
            addCriterion("pre_max_num <=", value, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumLike(String value) {
            addCriterion("pre_max_num like", value, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumNotLike(String value) {
            addCriterion("pre_max_num not like", value, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumIn(List<String> values) {
            addCriterion("pre_max_num in", values, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumNotIn(List<String> values) {
            addCriterion("pre_max_num not in", values, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumBetween(String value1, String value2) {
            addCriterion("pre_max_num between", value1, value2, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andPreMaxNumNotBetween(String value1, String value2) {
            addCriterion("pre_max_num not between", value1, value2, "preMaxNum");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementIsNull() {
            addCriterion("is_auto_increment is null");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementIsNotNull() {
            addCriterion("is_auto_increment is not null");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementEqualTo(String value) {
            addCriterion("is_auto_increment =", value, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementNotEqualTo(String value) {
            addCriterion("is_auto_increment <>", value, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementGreaterThan(String value) {
            addCriterion("is_auto_increment >", value, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementGreaterThanOrEqualTo(String value) {
            addCriterion("is_auto_increment >=", value, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementLessThan(String value) {
            addCriterion("is_auto_increment <", value, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementLessThanOrEqualTo(String value) {
            addCriterion("is_auto_increment <=", value, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementLike(String value) {
            addCriterion("is_auto_increment like", value, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementNotLike(String value) {
            addCriterion("is_auto_increment not like", value, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementIn(List<String> values) {
            addCriterion("is_auto_increment in", values, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementNotIn(List<String> values) {
            addCriterion("is_auto_increment not in", values, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementBetween(String value1, String value2) {
            addCriterion("is_auto_increment between", value1, value2, "isAutoIncrement");
            return (Criteria) this;
        }

        public Criteria andIsAutoIncrementNotBetween(String value1, String value2) {
            addCriterion("is_auto_increment not between", value1, value2, "isAutoIncrement");
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