package com.cartmatic.estore.textsearch;

public class SearchConstants
{
    public static String CORE_NAME_PRODUCT = "product";
    public static String CORE_NAME_CONTENT = "content";
    public static String CORE_NAME_SALESORDER = "salesorder";
    public static String CORE_NAME_ADMIN = "admin";
    /***
     * 重建索引的类型.
     */
    public static enum UPDATE_TYPE { REBUILD_ALL, DEL, UPDATE}
}
