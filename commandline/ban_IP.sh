#!/bin/bash
for i in $(< all.txt) ; do
echo $i;
iptables -I INPUT -i eth1 -s "$i" -j DROP;
done
