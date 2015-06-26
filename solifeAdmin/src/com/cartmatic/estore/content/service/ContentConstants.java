package com.cartmatic.estore.content.service;

/**
 * User: fire
 * Date: 2007-7-12
 * Time: 15:56:16
 * @deprecated noted by pengzhirong
 */
public class ContentConstants {
    /*栏目类型 1:普通栏目  包括产品菜单栏目,自定义菜单栏目
     *        2:html文件栏目  此栏目下面的所有内容都只能是html格式的文件
     *        3:超连接栏目  此栏目只作为超链接之用,例如首页栏目.
     */
    public static final Short COLUMN_TYPE_NORMAL =new Short("1");
    public static final Short COLUMN_TYPE_FILE =new Short("2");
    public static final Short COLUMN_TYPE_URL =new Short("3");
    /*内容类型 1:普通内容  手工发布的内容
     *        2:html文件内容 此内容为一个html文件的路径
     *        3:超连接内容  此内容只作为超链接之用.
     */
    public static final Short CONTENT_TYPE_NORMAL =new Short("1");
    public static final Short CONTENT_TYPE_FILE =new Short("2");
    public static final Short CONTENT_TYPE_URL =new Short("3");

    public static final String CONTENT_CODE_MAINPAGE ="mainPage";
    public static final String CONTENT_CODE_PRODUCT ="product";
    public static final String CONTENT_CODE_NEWS ="news";
    public static final String CONTENT_CODE_HELPS ="helps";
}
