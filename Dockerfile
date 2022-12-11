FROM alpine/curl

WORKDIR /

COPY scripts/wait-for.sh .
COPY scripts/run_all.sh .

ENTRYPOINT ["/bin/sh","/run_all.sh"]
