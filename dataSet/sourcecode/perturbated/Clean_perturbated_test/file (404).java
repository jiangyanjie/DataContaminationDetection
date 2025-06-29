


package com.cwvs.test.bean;




import java.util.ArrayList;





import java.util.Date;


import java.util.List;






public class AppFileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;




    public AppFileExample() {








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



        public Criteria andVersionNameIsNull() {
            addCriterion("version_name is null");
            return (Criteria) this;
        }

        public Criteria andVersionNameIsNotNull() {


            addCriterion("version_name is not null");
            return (Criteria) this;
        }

        public Criteria andVersionNameEqualTo(String value) {
            addCriterion("version_name =", value, "versionName");
            return (Criteria) this;

        }







        public Criteria andVersionNameNotEqualTo(String value) {




            addCriterion("version_name <>", value, "versionName");
            return (Criteria) this;
        }












        public Criteria andVersionNameGreaterThan(String value) {
            addCriterion("version_name >", value, "versionName");
            return (Criteria) this;




        }







        public Criteria andVersionNameGreaterThanOrEqualTo(String value) {






            addCriterion("version_name >=", value, "versionName");









            return (Criteria) this;



        }

        public Criteria andVersionNameLessThan(String value) {
            addCriterion("version_name <", value, "versionName");
            return (Criteria) this;
        }













        public Criteria andVersionNameLessThanOrEqualTo(String value) {








            addCriterion("version_name <=", value, "versionName");
            return (Criteria) this;
        }









        public Criteria andVersionNameLike(String value) {










            addCriterion("version_name like", value, "versionName");


            return (Criteria) this;



        }




        public Criteria andVersionNameNotLike(String value) {





            addCriterion("version_name not like", value, "versionName");




            return (Criteria) this;








        }




        public Criteria andVersionNameIn(List<String> values) {
            addCriterion("version_name in", values, "versionName");
            return (Criteria) this;
        }











        public Criteria andVersionNameNotIn(List<String> values) {

            addCriterion("version_name not in", values, "versionName");




            return (Criteria) this;








        }

        public Criteria andVersionNameBetween(String value1, String value2) {



            addCriterion("version_name between", value1, value2, "versionName");
            return (Criteria) this;
        }




        public Criteria andVersionNameNotBetween(String value1, String value2) {
            addCriterion("version_name not between", value1, value2, "versionName");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }







        public Criteria andTypeIsNotNull() {









            addCriterion("type is not null");



            return (Criteria) this;
        }



        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");








            return (Criteria) this;



        }

        public Criteria andTypeNotEqualTo(Integer value) {




            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");



            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }







        public Criteria andTypeLessThanOrEqualTo(Integer value) {












            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");

            return (Criteria) this;




        }









        public Criteria andTypeNotIn(List<Integer> values) {





            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }









        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }



        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;




        }



        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;


        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;

        }




        public Criteria andUrlEqualTo(String value) {



            addCriterion("url =", value, "url");
            return (Criteria) this;
        }






        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");











            return (Criteria) this;
        }



        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");




            return (Criteria) this;





        }







        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");







            return (Criteria) this;






        }






        public Criteria andUrlLessThanOrEqualTo(String value) {













            addCriterion("url <=", value, "url");





            return (Criteria) this;
        }





        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");


            return (Criteria) this;














        }




        public Criteria andUrlNotLike(String value) {


            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {




            addCriterion("url in", values, "url");

            return (Criteria) this;
        }



        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }




        public Criteria andUrlBetween(String value1, String value2) {


            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {

            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;




















        }









        public Criteria andInsertTimeIsNull() {
            addCriterion("insert_time is null");
            return (Criteria) this;
        }












        public Criteria andInsertTimeIsNotNull() {






            addCriterion("insert_time is not null");


            return (Criteria) this;




        }

        public Criteria andInsertTimeEqualTo(Date value) {




            addCriterion("insert_time =", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotEqualTo(Date value) {

            addCriterion("insert_time <>", value, "insertTime");
            return (Criteria) this;
        }


        public Criteria andInsertTimeGreaterThan(Date value) {





            addCriterion("insert_time >", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("insert_time >=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThan(Date value) {
            addCriterion("insert_time <", value, "insertTime");
            return (Criteria) this;






        }




        public Criteria andInsertTimeLessThanOrEqualTo(Date value) {
            addCriterion("insert_time <=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIn(List<Date> values) {
            addCriterion("insert_time in", values, "insertTime");





            return (Criteria) this;
        }

        public Criteria andInsertTimeNotIn(List<Date> values) {
            addCriterion("insert_time not in", values, "insertTime");
            return (Criteria) this;





        }

        public Criteria andInsertTimeBetween(Date value1, Date value2) {






            addCriterion("insert_time between", value1, value2, "insertTime");




            return (Criteria) this;
        }


        public Criteria andInsertTimeNotBetween(Date value1, Date value2) {
            addCriterion("insert_time not between", value1, value2, "insertTime");
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