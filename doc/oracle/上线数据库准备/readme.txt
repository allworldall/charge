因线上数据库的安全原因，
所以，
1.需要将新生成的表的相应权限（insert、select delete、update），以及新生成的包的执行权限，附给对应的app用户。

2.需要将新生成的表和包，生成对应‘正式用户’，下的同义词
例：在ECHARGING_APP用户下
	create or replace synonym SYS_GAME
  	for ECHARGING.SYS_GAME;