FROM docker.elastic.co/elasticsearch/elasticsearch:6.5.4

RUN ["mkdir", "-p", "/opt/kibana-config/importer"]
RUN ["chmod", "777", "-R", "/opt/kibana-config"]
#RUN ["yum", "-y", "install", "build-essential"]
#RUN ["yum", "-y", "install", "git"]
#RUN ["git", "clone", "https://github.com/stedolan/jq.git"]
#RUN ["cd", "jq"]
#RUN ["autoreconf", "-i"]
#RUN ["./configure", "--disable-maintainer-mode","&&", "make", "&&", "sudo", "make", "install"]
#RUN ["yum", "install", "wget"]
RUN yum -y update && yum -y install wget
RUN wget http://stedolan.github.io/jq/download/linux64/jq && chmod 755 jq && mv jq /bin

VOLUME ["/opt/kibana-config/importer"]

ADD kibana.sh /opt/kibana-config/kibana.sh


