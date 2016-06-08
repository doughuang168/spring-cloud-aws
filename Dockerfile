FROM java:8

ENV accessKey "YourAWSAccessKey"
ENV secretKey "YourAWSSecretKey"
ENV bucket    "YourAWSS3Bucket"
ENV region    "YourAWSRegion"

EXPOSE 8080

RUN  mkdir /opt/myapp
RUN  mkdir /opt/myapp/build
RUN  mkdir /opt/myapp/gradle
RUN  mkdir /opt/myapp/grails-app
RUN  mkdir /opt/myapp/src

COPY build             /opt/myapp/build
COPY gradle            /opt/myapp/gradle
COPY src               /opt/myapp/src
COPY build.gradle      /opt/myapp/
COPY settings.gradle   /opt/myapp/
COPY gradlew           /opt/myapp/

RUN  mkdir /home/myapp
RUN  echo "#!/bin/bash                                     " >/home/myapp/start-build.sh
RUN  echo "cd /opt/myapp        "                           >>/home/myapp/start-build.sh
RUN  echo "./gradlew clean build"                           >>/home/myapp/start-build.sh
RUN  chmod +x /home/myapp/start-build.sh
RUN  /home/myapp/start-build.sh

###
RUN  echo "#!/bin/bash                                     " >/home/myapp/start-app.sh
RUN  echo "cd /opt/myapp        "                           >>/home/myapp/start-app.sh
RUN  echo "./gradlew build"                                 >>/home/myapp/start-app.sh
RUN  echo "java -Dcloud.aws.credentials.accessKey=$accessKey -Dcloud.aws.credentials.secretKey=$secretKey -Dcloud.aws.region=$region -Dcloud.aws.s3.bucket=$bucket -Dserver.port=8081 -jar build/libs/mysay-spring-cloud-aws-s3-1.0-SNAPSHOT.jar" >> /home/myapp/start-app.sh

RUN  chmod +x /home/myapp/start-app.sh
CMD  /home/myapp/start-app.sh
