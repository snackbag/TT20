#!/usr/bin/env bash
set -e

mod_id="$(grep -E '^mod_id=' gradle.properties | cut -d'=' -f2)"
mod_version="$(grep -E '^mod_version=' gradle.properties | cut -d'=' -f2)"

if [ -z "$mod_version" ]; then
  echo "mod_version not found!"
  exit 1
fi

echo "Found mod properties: $mod_id-$mod_version"

out_dir="publish-builds"
mkdir -p "$out_dir"

for mcdir in versions/*; do
  [ -d "$mcdir" ] || continue

  mcver="$(basename "$mcdir")"
  jar="$mcdir/build/libs/$mod_id-$mod_version.jar"
  out="$out_dir/$mod_id-$mod_version+mc$mcver.jar"

  if [ -f "$jar" ]; then
    cp "$jar" "$out"
    echo "Copied mc$mcver"
  else
    echo "Skipping mc$mcver (not found)"
  fi
done
