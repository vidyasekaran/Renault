#!/bin/sh
#.  /home/cp200135/appl-pgm/resource/env/environment.sh
. ../../resource/env/environment.sh
echo $0 "`date +%H:%M:%S`  - JOB EXECUTION STARTED - Arguments :" $* " PID : " $$  "  " >> $LOG_FILE
# echo "PID : " $$ >> $LOG_FILE
cd "$BATCH_HOME"
# echo "PID : " $$
# echo "Executed File : $0 "
# echo "Arguments : " $*
"$JAVACMD" -cp "$LOCALCLASSPATH" -Dlogfile.name=B000059 com.nissangroups.pd.b000059.main.B000059 $*
#echo "Return Value : " $?
echo $0 "`date +%H:%M:%S`  - JOB EXECUTION ENDED   - Arguments :" $* " PID : " $$  "  " >> $LOG_FILE
