#FROM nginx:alpine
#COPY dist/esercizio/ /usr/share/nginx/html
#COPY nginx.conf /etc/nginx/nginx.conf

FROM node:14.17.0-alpine as build-step

ARG SERVER_URL
ENV SERVER_URL $SERVER_URL

RUN echo "Server BE: ${SERVER_URL}"

RUN mkdir -p /esercizio-app
WORKDIR /esercizio-app
COPY . .
RUN sed -i 's@xxxxx@'"${SERVER_URL}"'@g' src/environments/environment.prod.ts
RUN cat src/environments/environment.prod.ts
RUN npm install
RUN npm run build --prod

FROM nginx:1.20.1
COPY --from=build-step /esercizio-app/dist/esercizio /usr/share/nginx/html
COPY --from=build-step /esercizio-app/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80:80