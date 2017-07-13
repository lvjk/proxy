@echo off
@echo httpProxy 
set httpProxy_home=%~f0
set httpProxy_home=%httpProxy_home:\bin\run.bat=%

@echo %httpProxy_home%
:::::::::: set conf
set CLASSPATH=%CLASSPATH%;%httpProxy_home%\conf
set CLASSPATH=%CLASSPATH%;%httpProxy_home%\bin\http_proxy.jar

FOR %%F IN (%httpProxy_home%\lib\*.jar) DO call :addcp %%F
goto extlibe
:addcp
SET classPath=%CLASSPATH%;%1
goto :eof
:extlibe
@echo starting httpProxy 
java six.com.proxy.ProxyServer
pause