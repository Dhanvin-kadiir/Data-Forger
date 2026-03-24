@echo off
title DATA-FORGER // GHOST SYSTEM v1.0
color 0A

echo.
echo '########:::::'###::::'########::::'###:::::::'########::'#######::'########:::'######:::'########:
echo  ##.... ##:::'## ##:::... ##..::::'## ##:::::: ##.....::'##.... ##: ##.... ##:'##... ##:: ##.....::
echo  ##:::: ##::'##:. ##::::: ##:::::'##:. ##::::: ##::::::: ##:::: ##: ##:::: ##: ##:::..::: ##:::::::
echo  ##:::: ##:'##:::. ##:::: ##::::'##:::. ##:::: ######::: ##:::: ##: ########:: ##::'####: ######:::
echo  ##:::: ##: #########:::: ##:::: #########:::: ##...:::: ##:::: ##: ##.. ##::: ##::: ##:: ##...::::
echo  ##:::: ##: ##.... ##:::: ##:::: ##.... ##:::: ##::::::: ##:::: ##: ##::. ##:: ##::: ##:: ##:::::::
echo  ########:: ##:::: ##:::: ##:::: ##:::: ##:::: ##:::::::. #######:: ##:::. ##:. ######::: ########:
echo ........:::..:::::..:::::..:::::..:::::..:::::..:::::::::.......:::..:::::..:::......::::........::
echo.
echo  [BOOT] DATA-FORGER Ghost System Launcher
echo  [BOOT] No Maven required -- pure Java 17
echo.

:: -- Check Java -------------------------------------------------
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERR] Java not found!
    echo [ERR] Download Java 17+ free from: https://adoptium.net/
    pause
    exit /b 1
)

:: -- Compile ----------------------------------------------------
echo [BOOT] Compiling source files...
javac -encoding UTF-8 DataForgerServer.java IdentityForgerService.java TitanVaultService.java

if %errorlevel% neq 0 (
    echo.
    echo [ERR] Compilation failed. Check errors above.
    pause
    exit /b 1
)
echo [OK]   Compilation successful.
echo.

:: -- Open browser after short delay so server can start first --
start /b cmd /c "timeout /t 2 /nobreak >nul && start http://localhost:7070"

:: -- Launch server ----------------------------------------------
echo [BOOT] Starting server at http://localhost:7070
echo [BOOT] Press Ctrl+C to stop.
echo.
java DataForgerServer

pause
