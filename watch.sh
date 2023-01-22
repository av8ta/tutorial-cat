#!/bin/bash
# adapted from https://stackoverflow.com/a/22885210 thanks!

# watches all .scala files for changes. runs supplied command on change
# e.g. ./watch.sh sbt "testOnly *Monoid*"

watched_files=$(find . -name '*.scala')

if [ -z "$watched_files" ]; then
  echo "Nothing to watch, abort"
  exit
else
  printf "watching: \n%s" "$watched_files"
fi

previous_checksum="dummy"
while true; do
  checksum=$(md5sum $watched_files | md5sum) # use `md5` on Mac, in linux it's `md5sum`
  if [ "$checksum" != "$previous_checksum" ]; then
    if [ "$1" ]; then
      ret=$?
      echo
      echo
      echo "Command exited with $ret"

      "$@" # run command

      if [ $ret -ne 0 ]; then
        printf "\n\n\n\n\n\n\n\n\n\n\n\n"
        echo "Failed. Restarting test"
        sleep 3
        continue
      fi
    else
      "$@" # run command
    fi
  fi
  previous_checksum="$checksum"
  sleep 1
done
