目前的静态化方案是Apache + Tomcat。

下载连接器jk1.2，把mod_jk.so复制到${Apache.root}/modules目录下。

把cartmatic.conf cartmaticmap.properties workers.properties这三个文件copy 到${Apache.root}/conf目录下。

修改${Apache.root}/conf/httpd.conf在最后加上这行：
Include conf/cartmatic.conf

配置cartmatic.conf，设置虚拟机和目录。这个目录也是静态化的发布目录。

如果不采用zip方式，修改cartmatic.conf的<Directory>段，注释掉设置zip的部分；
<Directory "D:/website/testApache">
    AllowOverride None
    Options None
    Order allow,deny
    Allow from all
#    SetEnvIf Request_URI "\.html$" value IS_GZIP
#    SetEnvIf Request_URI "\.js$" value IS_GZIP
#    SetEnvIf Request_URI "\.css$" value IS_GZIP
#    Header append Content-Encoding "gzip" env=IS_GZIP
#    Header append Cache-Control "max-age=3600, must-revalidate" env=IS_GZIP
</Directory>

重启Apache就可以。

参考资料：
Apache2.2的中文手册http://www.evance.name/static/manual/apache/index.html
The Apache Tomcat Connector http://tomcat.apache.org/connectors-doc/