FROM openjdk:14
CMD ["jshell", "--enable-preview"]
VOLUME /tmp
EXPOSE 8080
ADD build/libs/assessment-0.0.1-SNAPSHOT.jar assessment.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","--enable-preview","-jar","/assessment.jar"]