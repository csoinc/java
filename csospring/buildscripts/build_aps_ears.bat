echo set ANT_HOME=C:\UserApps\apache-ant-1.8.0
echo %ANT_HOME%\bin\ant

set WAS_HOME=C:\UserApps\IBM\SDP
set WORKSPACE=C:\buildNight\aps
%WAS_HOME%\bin\runAnt.bat -buildfile %WORKSPACE%\APSEar\buildscripts\build.xml
