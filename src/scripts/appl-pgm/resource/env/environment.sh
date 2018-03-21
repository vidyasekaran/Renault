#!/bin/sh
export LANG=ja_JP.UTF-8
export ENV_SCRIPT_HOME=/home/cp200135/appl-pgm/resource/env
JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk-1.7.0.79.x86_64
export CLASSPATH=$JAVA_HOME/jre/lib
export BATCH_HOME=/home/cp200135/appl-pgm/batch/jar
export INTERFACE_HOME=/home/cp200135/appl-pgm/interface/jar
export DEPENDENT_JAR_HOME=/home/cp200135/appl-pgm/lib
export LOG_FILE=/home/cp200135/appl-log/shellScriptLog.log

touch $LOG_FILE

# echo "JAVA_HOME===>" $JAVA_HOME
# echo "BATCH_HOME===>" $BATCH_HOME
# echo "INTERFACE_HOME===>" $INTERFACE_HOME
# echo "DEPENDENT_JAR_HOME===>" $DEPENDENT_JAR_HOME

if [ -z "$BATCH_HOME" ] ; then
  echo "Warning: BATCH_HOME variable is not set"
  exit 1
fi

if [ -z "$INTERFACE_HOME" ] ; then
  echo "Warning: INTERFACE_HOME variable is not set"
  exit 1
fi

if [ -n "$JAVA_HOME"  ] ; then
  if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
    JAVACMD="$JAVA_HOME/jre/sh/java"
  else
    JAVACMD="/usr/bin/java"
  fi
else
  echo "Warning: JAVA_HOME variable is not set"
  exit 1
fi

cd "$BATCH_HOME"

if [ -n "$CLASSPATH" ] ; then
  LOCALCLASSPATH="$CLASSPATH"
fi

for i in $JAVA_HOME/jre/lib/*.jar
do
  if [ -f "$i" ] ; then
    if [ -z "$LOCALCLASSPATH" ] ; then
      LOCALCLASSPATH="$BATCH_HOME/$i"
    else
      LOCALCLASSPATH="$i":"$LOCALCLASSPATH"
    fi
  fi
done

for i in $BATCH_HOME/*.jar
do
  if [ -f "$i" ] ; then
    if [ -z "$LOCALCLASSPATH" ] ; then
      LOCALCLASSPATH="$BATCH_HOME/$i"
    else
      LOCALCLASSPATH="$i":"$LOCALCLASSPATH"
    fi
  fi
done

for i in $INTERFACE_HOME/*.jar
do
  if [ -f "$i" ] ; then
    if [ -z "$LOCALCLASSPATH" ] ; then
      LOCALCLASSPATH="$INTERFACE_HOME/$i"
    else
      LOCALCLASSPATH="$i":"$LOCALCLASSPATH"
    fi
  fi
done

for i in $DEPENDENT_JAR_HOME/*.jar
do
  if [ -f "$i" ] ; then
    if [ -z "$LOCALCLASSPATH" ] ; then
      LOCALCLASSPATH="$DEPENDENT_JAR_HOME/$i"
    else
      LOCALCLASSPATH="$i":"$LOCALCLASSPATH"
    fi
  fi
done


export LOCALCLASSPATH
export JAVACMD

# echo "LOCALCLASSPATH===>" $LOCALCLASSPATH
# echo "JAVACMD===>" $JAVACMD


