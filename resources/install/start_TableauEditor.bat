SET POS_HOME=%~dp0
SET POS_HOME=%POS_HOME:\=/%

SET POS_UI_PROPERTIES=/config/standard/parameter/client/ui.properties
SET POS_CLIENTSYSTEM_PROPERTIES=/config/standard/parameter/client/clientSystem.properties
SET POS_PRELOADED_FISCALCLIENTSYSTEM_PROPERTIES=preloaded/config/static/parameter/client/clientSystem.properties
SET POS_PRELOADED_FISCALUI_PROPERTIES=preloaded/config/static/parameter/client/ui.properties
SET POS_CONFIG_DIRS=preloaded/config/static/parameter,config/custom/parameter,preloaded/config/default/parameter,config/standard/parameter



SET classpath=%~dp0/*;config

SET classpath=%classpath%;lib/bouncycastle/*

if %PROCESSOR_ARCHITECTURE% == 32 (
    SET classpath=%classpath%;lib/arch/win32/*
) else (
  SET classpath=%classpath%;lib/arch/win64/*
)

SET classpath=%classpath%;config/custom/ui;config/standard/ui
SET classpath=%classpath%;config/custom/processes;config/standard/processes
SET classpath=%classpath%;config/custom/parameter/client;config/standard/parameter/client

SET classpath=%classpath%;lib_tableau_editor/*
SET classpath=%classpath%;lib_cst/*
SET classpath=%classpath%;lib_ext/*
SET classpath=%classpath%;lib_pvh/*
SET classpath=%classpath%;lib/*



java -cp "%classpath%"  -Djava.library.path="%librarypath%" -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8001,suspend=n -Dpos.root.dir="%POS_HOME%" -Dpos.ui.properties.filename="%POS_UI_PROPERTIES%" -Dpos.client.system.properties.filename="%POS_CLIENTSYSTEM_PROPERTIES%" -Dpos.preloaded.fiscal.client.system.properties.filename="%POS_PRELOADED_FISCALCLIENTSYSTEM_PROPERTIES%" -Dpos.preloaded.fiscal.ui.properties.filename="%POS_PRELOADED_FISCALUI_PROPERTIES%" -Dpos.config.dirs="%POS_CONFIG_DIRS%" com.gk_software.cst.development.tableau_editor.sample.TableauEditor
pause