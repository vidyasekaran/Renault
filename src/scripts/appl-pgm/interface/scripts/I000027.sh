#!/bin/sh
#/home/asp2manage/application/appl-pgm/batch/scripts
#.  /home/cp200135/appl-pgm/resource/env/environment.sh

. ../../resource/env/environment.sh
echo $0 "`date +%H:%M:%S`  - JOB EXECUTION STARTED - Arguments :" $* " PID : " $$  "  " >> $LOG_FILE
# echo "PID : " $$ >> $LOG_FILE
cd "$BATCH_HOME"
# echo "PID : " $$
# echo "Executed File : $0 "
# echo "Arguments : " $*
"$JAVACMD" -cp "$LOCALCLASSPATH" -Dlogfile.name=I000027 com.nissangroups.pd.i000027.main.I000027 $*
#echo "Return Value : " $?
echo $0 "`date +%H:%M:%S`  - JOB EXECUTION ENDED   - Arguments :" $* " PID : " $$  "  " >> $LOG_FILE