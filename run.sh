#!/usr/bin/env bash
# ═══════════════════════════════════════════════
#  DATA-FORGER // GHOST SYSTEM v1.0 - Unix launcher
# ═══════════════════════════════════════════════
set -e

echo ""
echo " ____   __  ____   __       ____  __  ____   __  ____  ____ "
echo "(    \ (  )(  __) /  \     (  __)(  )(  _ \ /  \(  _ \(  _ \\"
echo " ) D (  )(  ) _) (  O )___  ) _)  )(  )   /(  O ))   / )   /"
echo "(____/ (__)(____) \__/(___)(__)  (__)(__\_) \__/(__\_)(__\_)"
echo ""
echo " [BOOT] Building with Maven..."
mvn package -q -DskipTests

echo " [OK] Build successful."
echo " [BOOT] Starting server on http://localhost:7070"
echo " [BOOT] Press Ctrl+C to terminate."
echo ""

java -jar target/dataforger-1.0.0.jar
