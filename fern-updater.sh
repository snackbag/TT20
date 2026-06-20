#!/bin/bash

OLD_PREFIX="1.2.0"
NEW_PREFIX="1.3.0"

find versions -type f -name "gradle.properties" | while read -r file; do
    sed -i "s/\(fern_version=\)${OLD_PREFIX}/\1${NEW_PREFIX}/" "$file"
    echo "Updated $file"
done