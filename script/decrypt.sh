#!/usr/bin/env bash

set -e

# Echoes extra logging info
function log {
  if [ "$VERBOSE" = true ]
    then
      echo "--${1}"
  fi
}

# Checks if an env value is present and not empty
function checkEnv {
  echo "Checking"
  env_value=$(printf '%s\n' "${!1}")
  if [ -z ${env_value} ]; then
    echo "$1 is undefined, exiting..."
    exit 1
  else
    log "Found value for $1"
  fi
}

# If we don't have these two envs, bomb out early because this won't work
checkEnv XLABS_ENCRYPT_KEY

echo "Decrypting keys"
openssl aes-256-cbc -a -d -in signing/release.keystore.aes -out signing/release.keystore -k $XLABS_ENCRYPT_KEY
log "Keys decrypted"

echo "Finishing up"