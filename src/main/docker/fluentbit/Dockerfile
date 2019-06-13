FROM fluent/fluent-bit:1.1.2-debug

#RUN mkdir -p /logs && chmod 777 -R /logs
RUN ["mkdir", "-p", "/logs"]
RUN ["chmod", "777", "-R", "/logs"]

VOLUME ["/logs"]

ADD ./fluentbit.conf /fluent-bit/etc/fluentbit.conf
ADD ./parsers.conf /fluent-bit/etc/parsers.conf
