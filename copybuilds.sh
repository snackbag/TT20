#!/usr/bin/env bash
set -e

mod_name="$(grep -E '^mod_id=' gradle.properties | cut -d'=' -f2)"
mod_version="$(grep -E '^mod_version=' gradle.properties | cut -d'=' -f2)"

if [ -z "$mod_version" ]; then
  echo "mod_version not found!"
  exit 1
fi

echo "Found mod properties: $mod_name-$mod_version"

out_dir="publish-builds"
rm -rf "$out_dir"
mkdir -p "$out_dir"

for mcdir in versions/*; do
  [ -d "$mcdir" ] || continue

  dirname="$(basename "$mcdir")"

  mcplatform="${dirname%%-*}"
  mcver="${dirname#*-}"
  jar="$mcdir/build/libs/$mod_name-$mod_version+mc$mcver-$mcplatform.jar"

  if [ -f "$jar" ]; then
    cp "$jar" "$out_dir/"
    echo "Copied $mod_name-$mod_version+mc$mcver-$mcplatform"
  else
    echo "Skipping $mod_name-$mod_version+mc$mcver-$mcplatform (not found)"
  fi

  rm -rf "$mcdir/build/libs/"
done