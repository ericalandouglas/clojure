FROM clojure

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY project.clj /usr/src/app/
RUN lein deps

COPY . /usr/src/app
RUN lein uberjar \
 && mv target/uberjar/guestbook.jar /usr/local/lib/guestbook.jar

CMD ["java", "-jar", "/usr/local/lib/guestbook.jar"]

EXPOSE 3000
