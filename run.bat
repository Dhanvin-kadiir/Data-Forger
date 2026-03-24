@echo off
title DATA-FORGER // GHOST SYSTEM v1.0

echo.
echo  ____   __  ____   __       ____  __  ____   __  ____  ____ 
echo (    \ (  )(  __) /  \     (  __)(  )(  _ \ /  \(  _ \(  _ \
echo  ) D (  )(  ) _) (  O )___  ) _)  )(  )   /(  O ))   / )   /
echo (____/ (__)(____) \__/(___)(__)  (__)(__\_) \__/(__\_)(__\_)
echo.
echo  [BOOT] DATA-FORGER Ghost System Launcher
echo  [BOOT] Building project with Maven...
echo.

:: Check Maven
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERR] Maven not found! Please install Apache Maven and add it to PATH.
    pause
    exit /b 1
)

:: Package with Maven (skip tests)
mvn package -q -DskipTests
if %errorlevel% neq 0 (
    echo.
    echo [ERR] Build failed. Check the output above.
    pause
    exit /b 1
)

echo.
echo  [OK] Build successful.
echo  [BOOT] Starting Ghost System server on http://localhost:7070
echo  [BOOT] Open your browser to: http://localhost:7070
echo  [BOOT] Press Ctrl+C to terminate.
echo.

:: Launch the fat JAR
java -jar target\dataforger-1.0.0.jar

pause
