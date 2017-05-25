FROM clojure

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY project.clj /usr/src/app/
RUN lein deps

COPY . /usr/src/app
RUN lein uberjar \
 && mv target/uberjar/vue-stocktrader-backend.jar /usr/local/lib/vue-stocktrader-backend.jar

CMD ["java", "-jar", "/usr/local/lib/vue-stocktrader-backend.jar"]

EXPOSE 3000
