#!/usr/bin/env bash
set -euo pipefail

TARGET_URL="${1:-http://localhost:5000/health}"
MAX_RETRIES=${MAX_RETRIES:-10}
SLEEP_SECONDS=${SLEEP_SECONDS:-3}

function log() {
  printf '%s %s\n' "[healthcheck]" "$1"
}

for attempt in $(seq 1 "${MAX_RETRIES}"); do
  status_code=$(curl -s -o /dev/null -w "%{http_code}" "$TARGET_URL" || echo "000")

  if [[ "$status_code" == "200" ]]; then
    log "Service healthy at attempt ${attempt}."
    exit 0
  fi

  log "Attempt ${attempt} failed with status ${status_code}, retrying in ${SLEEP_SECONDS}s..."
  sleep "$SLEEP_SECONDS"
done

log "Service did not become healthy after ${MAX_RETRIES} attempts."
exit 1
