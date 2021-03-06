package com.gk.erp012;

/**
 * Created by pc_home on 2016/11/30.
 */

public class Constants {

//    public static final String WEB_SITE ="http://192.168.1.110:8080/ERP_011/";
    public static final String WEB_SITE ="http://47.94.246.193:8080/ERP_011/";
//    public static final String WEB_SITE ="http://47.94.246.193:8080/ERP_012/";
//    47.94.246.193
//    public static final String WEB_SITE ="http://10.64.33.43:8080/ERP_011/";
//    public static final String WEB_SITE ="http://172.17.12.1:8080/ERP_001/";
    public static final String API_DOMAIN = WEB_SITE+"api/";
    public static final String IMG_DOMAIN = WEB_SITE+"img/";
    //验证
    public static final String METHOD_ACCOUNT_AUTHORIZE = API_DOMAIN  + "LoginServlet";

    public static final String METHOD_GETALL_TASK = API_DOMAIN+"getAllTask";
    public static final String METHOD_DELETE_TASK = API_DOMAIN+"deleteTask";
    public static final String METHOD_GET_REPORT = API_DOMAIN+"getReport";
    public static final String METHOD_GETALL_USER = API_DOMAIN+"getAllUser";
    //验证1
    public static final String METHOD_GETALL_DEPART = API_DOMAIN+"getAllDepart";
    public static final String METHOD_GETALL_DEPART2 = API_DOMAIN+"getAllDepart2";
    public static final String METHOD_ADD_DEPARTCLASS = API_DOMAIN+"createDepartClass";
    public static final String METHOD_ADD_DEPART = API_DOMAIN+"createDepart";
    public static final String METHOD_DELETE = API_DOMAIN+"delete";
    public static final String METHOD_DELETE_USER = API_DOMAIN+"deleteUser";



    public static final String METHOD_TASK_INSERT = API_DOMAIN + "creatTask";
    public static final String METHOD_DEPARTMENT_INSERT = API_DOMAIN + "CreateDepart";

    public static final String GET_PROJECT_Depart_ALL = API_DOMAIN + "GetAll";

    public static final String GET_TASK = API_DOMAIN +"getTask";
    public static final String GET_DEPART =API_DOMAIN +"getDepart";

    public static final String GET_REPORT_ALL = API_DOMAIN +"GetAllReport";

    public static final String CREATE_SUPER = API_DOMAIN +"createSuper";
    public static final String CREATE_LEADER = API_DOMAIN +"createLeader";
    public static final String CREATE_REPORT = API_DOMAIN +"createReport";
    public static final String CREATE_TASK = API_DOMAIN +"createtask";
    public static final String CREATE_SUPERVISE = API_DOMAIN +"createSuper";

    public static final String UPDATE_TASK = API_DOMAIN+"updateTask";


    public static final String UPDATE_USER = API_DOMAIN +"updateUser";
    public static final String UPDATE_USER2 = API_DOMAIN +"updateUser2";

    public static final String DELETE_DEPART = API_DOMAIN + "deleteDepart";

    public final static String FLAG_LOGIN="FLAG_LOGIN";
}
