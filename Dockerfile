FROM alpine/curl

WORKDIR /

COPY scripts/wait-for.sh .
RUN sed -i 's/\r$//' wait-for.sh
COPY scripts/run_all.sh .
RUN sed -i 's/\r$//' run_all.sh

ENTRYPOINT ["/bin/sh","/run_all.sh"]
