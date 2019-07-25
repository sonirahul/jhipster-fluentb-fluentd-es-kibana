#!/bin/bash

sleep 30
for filename in ./importer/*.json;
do
        id=`cat $filename | jq -r ".[0]._id"`;
        kbtype=`cat $filename | jq -r ".[0]._type"`;
        attribute=`cat $filename | jq -r ".[0]._source"`;
#       echo $id" - "$kbtype" - "$attribute;

        command=`echo "curl -X POST http://kibana:5601/api/saved_objects/$kbtype/$id -H 'Content-Type: application/json' -H 'kbn-xsrf: reporting' -d '{\"attributes\": $attribute}'"`;

        echo $command | bash
done
