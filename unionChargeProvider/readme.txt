#由于此系统是基于jdk7NIO来实现的所以要求线上的jdk版本必须是jdk7及以上版本
#配置文件中主要需要包含以下两个配置文件
# 1、spring 配置文件路径
# 2、log4j 配置文件路径
# for example:
#begin==========================================
#spring 配置文件路径
spring_path=D:\\config\\application.xml
#log4j 配置文件路径
log4j_path=D:\\config\\log4j.properties
#end============================================

#启动============================================
java -jar unionChargeProvider.jar D:\\config\charge.conf